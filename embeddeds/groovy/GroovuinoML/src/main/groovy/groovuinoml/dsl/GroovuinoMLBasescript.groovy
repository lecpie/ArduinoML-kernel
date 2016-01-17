package main.groovy.groovuinoml.dsl

import io.github.mosser.arduinoml.kernel.behavioral.Action
import io.github.mosser.arduinoml.kernel.behavioral.State
import io.github.mosser.arduinoml.kernel.lib.Library;
import io.github.mosser.arduinoml.kernel.structural.PinnedSensor

abstract class GroovuinoMLBasescript extends Script {

    // uselib "libname" like param key val [and param key val]*n
    def uselib(String libname){
        Map<String, String> args = new LinkedHashMap<String, String>()
        //FIXME : Create an instance of a library based on "libname" instead of a mocked library
        ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createLibraryUse(new Library(), args)
        def closure
        closure = {
           key, val ->
               args.put(key, val)
               [and: closure]
        }
        [like: closure]
    }

    // sensor "name" pin n
    def sensor(String name) {
        [pin: { n -> ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createPinnedSensor(name, n) }]
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
}
