package main.groovy.groovuinoml.dsl


import com.sun.java.util.jar.pack.ConstantPool
import io.github.mosser.arduinoml.kernel.behavioral.Action
import io.github.mosser.arduinoml.kernel.behavioral.DigitalExpression
import io.github.mosser.arduinoml.kernel.behavioral.Operator
import io.github.mosser.arduinoml.kernel.behavioral.State
import io.github.mosser.arduinoml.kernel.lib.Library
import io.github.mosser.arduinoml.kernel.lib.LibraryUse
import io.github.mosser.arduinoml.kernel.lib.Measure
import io.github.mosser.arduinoml.kernel.lib.MeasureUse
import io.github.mosser.arduinoml.kernel.structural.SIGNAL
import main.groovy.groovuinoml.init_dsl.InitialisationBinding
import main.groovy.groovuinoml.init_dsl.InitialisationDSL
import sun.misc.Signal;


abstract class GroovuinoMLBasescript extends Script {

    def importlib(String path) {
        ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().importlib(path)
    }


	def uselib(String libName){
        /*
        Map<String, String> args = new LinkedHashMap<String,String>()
        ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createLibraryUse(libName, args)
>>>>>>> 74e29604a90609c10a350175a8e33d8167e83ee8
        def closure
        [with: closure = {
            String key, String val ->
                args.put(key, val)
                println("uselib : libname " + key + " " + val + " size : " + ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().getUsedLibraries().size())
                [and: closure]
        }]
<<<<<<< HEAD
    }
=======
        */

        GroovuinoMLModel model = ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel()

        LibraryUse libraryUse = new LibraryUse();
        Library usedLibrary =  model.getLoaded_librairies().get(libName)


        def closure
        [measure: closure = { name ->
            Measure measure = usedLibrary.getMeasures().get(name)
            [named: { measureName ->
                MeasureUse measureUse = new MeasureUse()
                measureUse.setName(measureName)

                model.getUsedMeasure().add(measureName)
                model.get
            }]
        }]
	}

    def usemeasure(String libUseName, String measureName) {
        Map<String, String> args = new LinkedHashMap<String, String>()
        ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createMeasureUse(libUseName, measureName, args)

        def closure
        [with: closure = {
            String key, String val ->
                args.put(key, val)
                println("usemeasure : libUseName " + key + " " + val + " size : " + ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().getUsedLibraries().size())
                [and: closure]
        }]
    }

    def dump(String fuck) {
        println("Libraries Loaded : ")
        for (Map.Entry<String, Library> libraries : ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().loaded_librairies) {
            println("\t\t - " + libraries.key);
            for (Map.Entry<String, Measure> measure : ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().loaded_measures) {
                if (measure.getValue().getLibrary().equals(libraries.value)) {
                    println("\t\t\t\t\t (associate with) : " + measure.getKey());
                }
            }
        }

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
        [to: { state2 ->
            [when: { sensor ->
                [eq: { int signal ->
                    ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2, sensor, signal, Operator.EQ)
                },
                 greater_eq: { int signal ->
                     // println("cc")
                     ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2, sensor, signal, Operator.GE)
                 },
                 greater_than: { int signal ->
                     ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2, sensor, signal, Operator.GT)
                 },
                 lower_eq: { int signal ->
                     ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2, sensor, signal, Operator.LE)
                 },
                 lower_than: { int signal ->
                     ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2, sensor, signal, Operator.LT)
                 },
                 not_eq: { int signal ->
                     ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2, sensor, signal, Operator.NE)
                 },
                 eq: { SIGNAL signal ->
                     ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2, sensor, signal, Operator.EQ)
                 },
                 not_eq: { SIGNAL signal ->
                     ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2, sensor, signal, Operator.NE)
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
