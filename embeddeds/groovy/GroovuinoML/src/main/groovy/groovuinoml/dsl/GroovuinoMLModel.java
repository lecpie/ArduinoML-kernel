package main.groovy.groovuinoml.dsl;

import java.io.File;
import java.util.*;

import groovy.lang.Binding;
import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.language.Expression;
import io.github.mosser.arduinoml.kernel.lib.Library;
import io.github.mosser.arduinoml.kernel.lib.LibraryUse;
import io.github.mosser.arduinoml.kernel.lib.Measure;
import io.github.mosser.arduinoml.kernel.lib.MeasureUse;
import io.github.mosser.arduinoml.kernel.structural.Brick;
import io.github.mosser.arduinoml.kernel.structural.PinnedActuator;
import io.github.mosser.arduinoml.kernel.structural.PinnedSensor;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;
import main.groovy.groovuinoml.init_dsl.InitialisationDSL;


public class GroovuinoMLModel {
    private List<Brick> bricks;
    private List<State> states;

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    private State initialState;
    private Map<String, Library> loaded_librairies = new HashMap<>();
    private Map<String, Measure> loaded_measures = new HashMap<>();
    private List<LibraryUse> usedLibraries;
    private List<MeasureUse> usedMeasure;
    private Binding binding;

    public Map<String, Measure> getLoaded_measures() {
        return loaded_measures;
    }

    public void setLoaded_measures(Map<String, Measure> loaded_measures) {
        this.loaded_measures = loaded_measures;
    }
    public Map<String, Library> getLoaded_librairies() {
        return loaded_librairies;
    }

    public void setLoaded_librairies(Map<String, Library> loaded_librairies) {
        this.loaded_librairies = loaded_librairies;
    }

    public List<LibraryUse> getUsedLibraries() {
        return usedLibraries;
    }

    public void setUsedLibraries(List<LibraryUse> usedLibraries) {
        this.usedLibraries = usedLibraries;
    }

    public List<MeasureUse> getUsedMeasure() {
        return usedMeasure;
    }

    public void setUsedMeasure(List<MeasureUse> usedMeasure) {
        this.usedMeasure = usedMeasure;
    }


    public GroovuinoMLModel(Binding binding) {
        this.bricks = new ArrayList<Brick>();
        this.states = new ArrayList<State>();
        this.usedLibraries = new ArrayList<LibraryUse>();
        this.usedMeasure = new ArrayList<MeasureUse>();
        this.binding = binding;
    }


    public void createPinnedSensor(String name, Integer pinNumber, boolean analog) {
        PinnedSensor sensor = new PinnedSensor();
        sensor.setName(name);
        sensor.setPin(pinNumber);
        sensor.setAnalogMode(analog);
        this.bricks.add(sensor);
        this.binding.setVariable(name, sensor);
//		System.out.println("> sensor " + name + " on pin " + pinNumber);
    }

    public void createActuator(String name, Integer pinNumber, boolean analog) {
        PinnedActuator actuator = new PinnedActuator();
        actuator.setName(name);
        actuator.setPin(pinNumber);
        actuator.setAnalogMode(analog);
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

        Condition condition = new Condition();
        condition.setLeft(sensor);
        condition.setOperator(Operator.EQ);
        condition.setRight(new DigitalExpression((value == SIGNAL.HIGH) ? true : false));
        transition.setCondition(condition);

        from.setTransition(transition);
    }

    public void createLibraryUse(String libName, Map<String, String> argsValues) {
        LibraryUse libraryUse = new LibraryUse();
        libraryUse.setArgsValues(argsValues);
        if (loaded_librairies.get(libName) != null) {
            libraryUse.setLibrary(loaded_librairies.get(libName));
            this.usedLibraries.add(libraryUse);
        }
    }

    public Expression createExpression(SIGNAL value) {
        return new DigitalExpression(value == SIGNAL.HIGH);
    }

    public Expression createExpression(int value) {
        return new IntegerExpression(value);
    }

    public Condition createCondition(Expression left, Operator operator, Expression right) {
        Condition condition = new Condition();
        condition.setLeft(left);
        condition.setOperator(operator);
        condition.setRight(right);

        return condition;
    }

    public void createMeasureUse(String libUseName, String measureName, Map<String, String> argsValues) {
        MeasureUse measureUse = new MeasureUse();
        for(LibraryUse libUse : usedLibraries){
            Library currentLib = libUse.getLibrary();
            if(currentLib.getName().equals(libUseName)){
                measureUse.setLibraryUse(libUse);
                Measure currentMeasure = currentLib.getMeasures().get(measureName);
                if(currentMeasure != null) {
                    measureUse.setMeasure(currentMeasure);
                    measureUse.setArgsValues(argsValues);
                    this.usedMeasure.add(measureUse);
                }
            }

        }

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

    public void importlib(String path) {
        InitialisationDSL initdsl = new InitialisationDSL();

        initdsl.eval(new File(path));

        for (Library lib : initdsl.getModel().getLibraries()) {
            loaded_librairies.put(lib.getName(), lib);
        }
        for (Measure measures : initdsl.getModel().getMeasures()) {
            loaded_measures.put(measures.getName(), measures);
        }
    }
}
