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
        message = message.toLowerCase()
        ((MorseMLBinding)this.getBinding()).getMorseMLModel().encodeMessage(message);
        println(((MorseMLBinding) this.getBinding()).getMorseMLModel().generateCode("MORSE").toString())

    }
}
