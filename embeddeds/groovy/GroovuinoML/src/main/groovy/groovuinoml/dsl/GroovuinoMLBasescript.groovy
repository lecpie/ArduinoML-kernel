package main.groovy.groovuinoml.dsl



import io.github.mosser.arduinoml.kernel.behavioral.Action
import io.github.mosser.arduinoml.kernel.behavioral.State
import io.github.mosser.arduinoml.kernel.lib.Library
import main.groovy.groovuinoml.init_dsl.InitialisationBinding
import main.groovy.groovuinoml.init_dsl.InitialisationDSL;


abstract class GroovuinoMLBasescript extends Script {

	def importlib(String path) {
			((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().importlib(path)

	}
	def uselib(String name){

	}
	def dump(String fuck){
		println(((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().getLoaded_librairies().size());

	}

	/*
	// sensor "name" pin n
	def sensor(String name) {
		[pin: { n -> ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createSensor(name, n) }]
	}

	// actuator "name" pin n
	def actuator(String name) {
		[pin: { n -> ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createActuator(name, n) }]
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
				action.setValue(signal)
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
				[becomes: { signal -> 
					((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2, sensor, signal)
				}]
			}]
		}]
	}
	
	// export name
	def export(String name) {
		println(((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
	}

	*/
}
