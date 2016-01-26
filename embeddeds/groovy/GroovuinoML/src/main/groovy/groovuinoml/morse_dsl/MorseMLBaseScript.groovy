package main.groovy.groovuinoml.morse_dsl

import io.github.mosser.arduinoml.kernel.App
import io.github.mosser.arduinoml.kernel.behavioral.Action
import io.github.mosser.arduinoml.kernel.behavioral.DigitalExpression
import io.github.mosser.arduinoml.kernel.behavioral.State
import io.github.mosser.arduinoml.kernel.language.Actionable
import io.github.mosser.arduinoml.kernel.structural.PinnedActuator


/**
 * Created by fofo on 26/01/16.
 */
abstract class MorseMLBaseScript extends Script {


    def morse(String message){

        App app = new App();

        State state = new State()
        List <Actionable> actions = new ArrayList<>();
        state.setName("morse")
        state.setActions(actions)

        PinnedActuator actuator = app.getBricks().get(0)

        Action on = new Action()
        on.setActuator(actuator)
        on.setSignalExpression(new DigitalExpression(true))

        Action off = new Action()
        off.setActuator(actuator)
        off.setSignalExpression(new DigitalExpression(false))

        Sleep sleep = new Sleep()

        message = message.toLowerCase()
        ((MorseMLBinding)this.getBinding()).getMorseMLModel().encodeMessage(message)
        for( Morse_Type type :((MorseMLBinding)this.getBinding()).getMorseMLModel().getMorse_answer()){
                if(type.equals(Morse_Type.LONG_MORSE)){
                    actions.add()
                }
                else if(type.equals(Morse_Type.SHORT_MORSE)){
                    print(".")
                }
                else{
                    print(" ")
                }
        }
    }
    /*
    def export(String name) {
        println(((MorseMLBinding) this.getBinding()).getMorseMLModel().generateCode(name).toString())
    }*/
}
