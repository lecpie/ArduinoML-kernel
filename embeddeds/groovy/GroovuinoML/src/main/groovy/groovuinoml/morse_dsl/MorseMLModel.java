package main.groovy.groovuinoml.morse_dsl;

import groovy.lang.Binding;
import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.Action;
import io.github.mosser.arduinoml.kernel.behavioral.DigitalExpression;
import io.github.mosser.arduinoml.kernel.behavioral.Sleep;
import io.github.mosser.arduinoml.kernel.behavioral.State;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.language.Actionable;
import io.github.mosser.arduinoml.kernel.structural.Brick;
import io.github.mosser.arduinoml.kernel.structural.PinnedActuator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by fofo on 26/01/16.
 */
public class MorseMLModel {

    public Brick getBrick() {
        return brick;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    private Brick brick;
    private State state;
    private MorseAlphabet morseAlphabet;
    private List<Morse_Type> morse_answer;

    public List<Actionable> getActions() {
        return actions;
    }

    public void setActions(List<Actionable> actions) {
        this.actions = actions;
    }

    private List <Actionable> actions;
    private Binding binding;

    public List<Morse_Type> getMorse_answer() {
        return morse_answer;
    }

    public MorseMLModel(Binding binding) {
        this.state = new State();
        actions = new ArrayList<>();
        state.setName("morse");
        state.setActions(actions);
        this.brick = new PinnedActuator();
        this.morseAlphabet = new MorseAlphabet();
        this.morse_answer = new ArrayList<>();
        this.binding = binding;
    }
    public void encodeMessage(String message){

        for(int i=0;i<message.toCharArray().length;i++){
            if(message.toCharArray()[i]==' '){
                this.morse_answer.add(Morse_Type.SILENCE_MORSE);
            }
            else{
                for(Morse_Type t : this.morseAlphabet.getListByLetter(message.toCharArray()[i])){
                    this.morse_answer.add(t);
                 }
            }
        }
    }
/*
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

    public void createLibraryUse(Library library,Map<String, String> argsValues){
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

*/
    @SuppressWarnings("rawtypes")
    public Object generateCode(String appName) {
        App app = new App();
        app.setName(appName);
        app.setBricks(Arrays.asList(this.brick));
        app.setStates(Arrays.asList(this.state));
        app.setInitial(state);
        Visitor codeGenerator = new ToWiring();
        app.accept(codeGenerator);
        return codeGenerator.getResult();
    }
}
