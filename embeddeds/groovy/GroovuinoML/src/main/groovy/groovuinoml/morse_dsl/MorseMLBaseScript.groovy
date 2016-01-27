package main.groovy.groovuinoml.morse_dsl

import io.github.mosser.arduinoml.kernel.App
import io.github.mosser.arduinoml.kernel.behavioral.Action
import io.github.mosser.arduinoml.kernel.behavioral.DigitalExpression
import io.github.mosser.arduinoml.kernel.behavioral.Sleep
import io.github.mosser.arduinoml.kernel.behavioral.State
import io.github.mosser.arduinoml.kernel.language.Actionable
import io.github.mosser.arduinoml.kernel.structural.PinnedActuator


/**
 * Created by fofo on 26/01/16.
 */
abstract class MorseMLBaseScript extends Script {

    def onpin(int pin){

        PinnedActuator actuator = new PinnedActuator();
        actuator.setPin(pin)

        ((MorseMLBinding)this.getBinding()).morseMLModel.brick = actuator;

    }

    def morse(String message){
        Action on = new Action();
        on.setActuator((PinnedActuator)((MorseMLBinding)this.getBinding()).getMorseMLModel().getBrick());
        on.setSignalExpression(new DigitalExpression(true));

        Action off = new Action();
        off.setActuator((PinnedActuator)((MorseMLBinding)this.getBinding()).getMorseMLModel().getBrick());
        off.setSignalExpression(new DigitalExpression(false));

        Sleep shortSleep = new Sleep();
        shortSleep.setDelay(500);

        Sleep longSleep = new Sleep();
        longSleep.setDelay(1000);


        message = message.toLowerCase()
        ((MorseMLBinding)this.getBinding()).getMorseMLModel().encodeMessage(message)
        for( Morse_Type type :((MorseMLBinding)this.getBinding()).getMorseMLModel().getMorse_answer()){
                if(type.equals(Morse_Type.LONG_MORSE)){
                    ((MorseMLBinding)this.getBinding()).getMorseMLModel().getActions().addAll(on, longSleep, off, shortSleep)
                }
                else if(type.equals(Morse_Type.SHORT_MORSE)){
                    ((MorseMLBinding)this.getBinding()).getMorseMLModel().getActions().addAll(on, shortSleep, off, shortSleep)

                }
                    else if (type.equals(Morse_Type.SILENCE_MORSE)) {
                    ((MorseMLBinding)this.getBinding()).getMorseMLModel().getActions().addAll(longSleep)
                }
                else{
                    print(" ")
                }
        }
        println(((MorseMLBinding) this.getBinding()).getMorseMLModel().generateCode("MORSE").toString())

    }
}
