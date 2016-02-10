package main.groovy.groovuinoml.dsl


import io.github.mosser.arduinoml.kernel.behavioral.Action
import io.github.mosser.arduinoml.kernel.behavioral.BinaryOperator
import io.github.mosser.arduinoml.kernel.behavioral.ConditionTree
import io.github.mosser.arduinoml.kernel.behavioral.DigitalExpression
import io.github.mosser.arduinoml.kernel.behavioral.IntegerExpression
import io.github.mosser.arduinoml.kernel.behavioral.Operator
import io.github.mosser.arduinoml.kernel.behavioral.RealExpression
import io.github.mosser.arduinoml.kernel.behavioral.State
import io.github.mosser.arduinoml.kernel.behavioral.Transition
import io.github.mosser.arduinoml.kernel.lib.Library
import io.github.mosser.arduinoml.kernel.lib.LibraryUse
import io.github.mosser.arduinoml.kernel.lib.MeasureUse
import io.github.mosser.arduinoml.kernel.structural.SIGNAL


abstract class GroovuinoMLBasescript extends Script {

    def importlib(String path) {
        ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().importlib(path)
    }

    private LibraryUse current = null

	def uselib(String libName){
        GroovuinoMLModel model = ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel()

        LibraryUse libraryUse = new LibraryUse();
        Library usedLibrary =  model.getLoaded_librairies().get(libName)
        libraryUse.setLibrary(usedLibrary)
        Map <String, String> args = usedLibrary.getDefaultArgs()

        model.getUsedLibraries().add(libraryUse)
        current = libraryUse

        def closure
        [with: closure = {
            String key ->
                [valued: {
                    String val ->
                        args.put(key, val)
                        [and: closure]
                }]

        }]
	}

    def usemeasure(String measureName) {
        GroovuinoMLModel model = ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel()

        Map<String, String> args = new LinkedHashMap<String, String>()

        MeasureUse measureUse = new MeasureUse();
        measureUse.setName(measureName)
        measureUse.setLibraryUse(current)
        measureUse.setMeasure(current.getLibrary().getMeasures().get(measureName))

        model.getUsedMeasure().add(measureUse)
        model.getBricks()     .add(measureUse)

        def nameclosure
        def argsclosure

        String previousname = measureName
        binding.setVariable(previousname, measureUse)

        [with: argsclosure = {
            String key ->
                [valued: {
                    String val ->
                        args.put(key, val)
                        [and: argsclosure]
                }]
        },
         named: nameclosure = {
             String name ->
                 measureUse.setName(name)

                 binding.getVariables().remove(previousname)
                 previousname = name

                 binding.setVariable(previousname, measureUse)

                 [with: argsclosure = argsclosure]
        }]
    }

    // sensor "name" pin n
    def sensor(String name) {
        [analogPin : { n ->
            ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createPinnedSensor(name, n, true)
        },
         digitalPin: { n ->
             ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createPinnedSensor(name, n, false)
         }]
    }

    // actuator "name" pin n
    def actuator(String name) {
        [
                digitalPin: { n -> ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createActuator(name, n, false) },
                analogPin : { n -> ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createActuator(name, n, true) }
        ]
    }

    // state "name" means actuator becomes signal [and actuator becomes signal]*n
    def state(String name) {
        List<Action> actions = new ArrayList<Action>()
        ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createState(name, actions)
        // recursive closure to allow multiple and statements
        def closure
        closure = { actuator ->
            [becomes: { signal ->
                Action action = new Action()
                action.setActuator(actuator)
                action.setSignalExpression(((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createExpression(signal))
                actions.add(action)
                [and: closure]
            }]
        }
        [means: closure]
    }

    // initial state
    def initial(State state) {
        ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().setInitialState(state)
    }

    // from state1 to state2 when sensor becomes signal
    def from(State state1) {
        GroovuinoMLModel model = ((GroovuinoMLBinding) binding).getGroovuinoMLModel()
        Transition transition = new Transition();
        state1.setTransition(transition)
        [to: { State state2 ->
            transition.setNext(state2)

            ConditionTree conditionTree = null

            def addConditionClosure
            def nextConditionClosure
            def conditionClosure

            addConditionClosure = {
                [and: { nextsensor ->
                    conditionTree.setNextOperator(BinaryOperator.AND)
                    conditionClosure(nextsensor)
                },
                 or : { nextsensor ->
                     conditionTree.setNextOperator(BinaryOperator.OR)
                     conditionClosure(nextsensor)
                 }
                ]
            }

            nextConditionClosure = { signal, Operator operator ->
                conditionTree.setOperator(Operator.EQ)
                conditionTree.setRight(model.makeExpression(signal))

                addConditionClosure()
            }

            [when: conditionClosure = { sensor ->

                if (conditionTree == null) {
                    conditionTree = new ConditionTree();
                    transition.setCondition(conditionTree);
                }
                else {
                    ConditionTree next = new ConditionTree()
                    conditionTree.setNext(next)
                    conditionTree = next
                }

                conditionTree.setLeft(sensor)
                [eq: { signal ->
                    nextConditionClosure(signal, Operator.EQ)
                },
                 greater_eq: { signal ->
                     nextConditionClosure(signal, Operator.GE)

                 },
                 greater_than: { signal ->
                     nextConditionClosure(signal, Operator.GT)
                 },
                 lower_eq: { signal ->
                     nextConditionClosure(signal, Operator.LE)

                 },
                 lower_than: { signal ->
                     nextConditionClosure(signal, Operator.LT)
                 },
                 not_eq: { signal ->
                     nextConditionClosure(signal, Operator.NE)
                 }
                ]
            }]
        }]
    }

    // export name
    def export(String name) {
        println(((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
    }


}
