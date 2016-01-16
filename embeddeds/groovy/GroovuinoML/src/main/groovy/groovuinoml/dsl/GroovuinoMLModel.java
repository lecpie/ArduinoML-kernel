package main.groovy.groovuinoml.dsl;

import java.util.*;

import groovy.lang.Binding;
import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.Action;
import io.github.mosser.arduinoml.kernel.behavioral.State;
import io.github.mosser.arduinoml.kernel.behavioral.Transition;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.lib.Library;
import io.github.mosser.arduinoml.kernel.lib.LibraryUse;
import io.github.mosser.arduinoml.kernel.lib.Measure;
import io.github.mosser.arduinoml.kernel.lib.MeasureUse;
import io.github.mosser.arduinoml.kernel.structural.Brick;
import io.github.mosser.arduinoml.kernel.structural.PinnedActuator;
import io.github.mosser.arduinoml.kernel.structural.PinnedSensor;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;



public class GroovuinoMLModel {
	private List<Brick> bricks;
	private List<State> states;
	private State initialState;
	private List <LibraryUse> usedLibraries;
    private List <MeasureUse> usedMeasure;
	
	private Binding binding;
	
	public GroovuinoMLModel(Binding binding) {
		this.bricks = new ArrayList<Brick>();
		this.states = new ArrayList<State>();
		this.usedLibraries = new ArrayList<LibraryUse>();
        this.usedMeasure = new ArrayList<MeasureUse>();
		this.binding = binding;
	}
	
	public void createPinnedSensor(String name, Integer pinNumber) {
		PinnedSensor sensor = new PinnedSensor();
		sensor.setName(name);
		sensor.setPin(pinNumber);
		this.bricks.add(sensor);
		this.binding.setVariable(name, sensor);
//		System.out.println("> sensor " + name + " on pin " + pinNumber);
	}
	
	public void createActuator(String name, Integer pinNumber) {
		PinnedActuator actuator = new PinnedActuator();
		actuator.setName(name);
		//actuator.setPin(pinNumber);
		this.bricks.add(actuator);
		this.binding.setVariable(name, actuator);
	}
	
	public void createState(String name, List<Action> actions) {
		State state = new State();
		state.setName(name);
		state.setActions(actions);
		this.states.add(state);
		this.binding.setVariable(name, state);
	}
	
	public void createTransition(State from, State to, PinnedSensor sensor, SIGNAL value) {
		Transition transition = new Transition();
		transition.setNext(to);
		//transition.setSensor(sensor);
		//transition.setValue(value);
		from.setTransition(transition);
	}

	public void createLibraryUse(Library library,Map <String, String> argsValues){
		LibraryUse libraryUse = new LibraryUse();
		libraryUse.setLibrary(library);
		libraryUse.setArgsValues(argsValues);
        this.usedLibraries.add(libraryUse);
	}

    public void createMeasureUse(LibraryUse libraryUse, Measure measure,Map <String, String> argsValues){
        MeasureUse measureUse = new MeasureUse();
        measureUse.setLibraryUse(libraryUse);
        measureUse.setMeasure(measure);
        measureUse.setArgsValues(argsValues);
        this.usedMeasure.add(measureUse);
    }

	
	@SuppressWarnings("rawtypes")
	public Object generateCode(String appName) {
		App app = new App();
		app.setName(appName);
		app.setBricks(this.bricks);
		app.setStates(this.states);
		app.setInitial(this.initialState);
		Visitor codeGenerator = new ToWiring();
		app.accept(codeGenerator);
		
		return codeGenerator.getResult();
	}
}
