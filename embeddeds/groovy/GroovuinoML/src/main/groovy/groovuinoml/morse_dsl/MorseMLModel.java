package main.groovy.groovuinoml.morse_dsl;

import groovy.lang.Binding;
import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.language.Actionable;
import io.github.mosser.arduinoml.kernel.structural.Brick;
import io.github.mosser.arduinoml.kernel.structural.PinnedActuator;

import java.util.*;


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

    public List<State> getStates() {
        return this.states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    private Brick brick;
    private List<State> states;
    private MorseAlphabet morseAlphabet;
    private Binding binding;

    public MorseMLModel(Binding binding) {
        this.states = new  ArrayList<>();
        this.brick = new PinnedActuator();
        this.morseAlphabet = new MorseAlphabet();
        this.binding = binding;
    }
    public void encodeMessage(String message){


        Action on = new Action();
        on.setActuator((PinnedActuator)(this.brick));
        on.setSignalExpression(new DigitalExpression(true));

        Action off = new Action();
        off.setActuator((PinnedActuator)(this.brick));
        off.setSignalExpression(new DigitalExpression(false));

        Sleep shortSleep = new Sleep();
        shortSleep.setDelay(500);

        Sleep longSleep = new Sleep();
        longSleep.setDelay(1000);

        State previous = null;
        for(int i=0;i<message.toCharArray().length;i++){
            State newState = new State();
            newState.setName("State"+i);
            for(Morse_Type type : this.morseAlphabet.getListByLetter(message.toCharArray()[i])){
                switch(type){
                    case LONG_MORSE:
                        newState.getActions().add(on);
                        newState.getActions().add(longSleep);
                        newState.getActions().add(off);
                        newState.getActions().add(shortSleep);
                        break;
                    case SHORT_MORSE:
                        newState.getActions().add(on);
                        newState.getActions().add(shortSleep);
                        newState.getActions().add(off);
                        newState.getActions().add(shortSleep);
                        break;
                    case SILENCE_MORSE:
                        newState.getActions().add(longSleep);
                        break;
                }
            }
            this.states.add(newState);

            if (previous != null) {
                Transition transition = new Transition();
                transition.setNext(newState);
                previous.setTransition(transition);
            }

            previous = newState;
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
        app.setStates(this.states);
        app.setInitial(this.states.get(0));
        Visitor codeGenerator = new ToWiring();
        app.accept(codeGenerator);
        return codeGenerator.getResult();
    }
}
