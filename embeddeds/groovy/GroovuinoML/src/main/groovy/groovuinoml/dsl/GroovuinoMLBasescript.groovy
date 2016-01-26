package main.groovy.groovuinoml.dsl

import com.sun.java.util.jar.pack.ConstantPool
import io.github.mosser.arduinoml.kernel.behavioral.Action
import io.github.mosser.arduinoml.kernel.behavioral.State
import io.github.mosser.arduinoml.kernel.lib.Library
import io.github.mosser.arduinoml.kernel.lib.Measure
import main.groovy.groovuinoml.init_dsl.InitialisationBinding
import main.groovy.groovuinoml.init_dsl.InitialisationDSL;


abstract class GroovuinoMLBasescript extends Script {

	def importlib(String path) {
			((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().importlib(path)
	}

	def uselib(String libName){
        Map<String, String> args = new LinkedHashMap<String,String>()
        ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createLibraryUse(libName, args)
        def closure
        [with: closure = {
            String key, String val ->
                args.put(key, val)
                println("uselib : libname "+key+" "+val+" size : "+((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().getUsedLibraries().size())
                [and: closure]
        }]
	}

    def usemeasure(String libUseName, String measureName){
        Map<String, String> args = new LinkedHashMap<String,String>()
        ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createMeasureUse(libUseName, measureName, args)

        def closure
        [with: closure = {
            String key, String val ->
                args.put(key, val)
                println("usemeasure : libUseName "+key+" "+val+" size : "+((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().getUsedLibraries().size())
                [and: closure]
        }]
    }

	def dump(String fuck) {
		println("Libraries Loaded : ")
		for (Map.Entry<String,Library> libraries : ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().loaded_librairies){
			println("\t\t - " + libraries.key);
			for(Map.Entry<String,Measure> measure : ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().loaded_measures){
				if(measure.getValue().getLibrary().equals(libraries.value)){
					println("\t\t\t\t\t (associate with) : " + measure.getKey());
				}
			}
		}

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
