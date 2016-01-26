package main.groovy.groovuinoml.morse_dsl


/**
 * Created by fofo on 26/01/16.
 */
abstract class MorseMLBaseScript extends Script {


    def morse(String message){
        message = message.toLowerCase()
        ((MorseMLBinding)this.getBinding()).getMorseMLModel().encodeMessage(message)
        for( Morse_Type type :((MorseMLBinding)this.getBinding()).getMorseMLModel().getMorse_answer()){
                if(type.equals(Morse_Type.LONG_MORSE)){
                    print("-")
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
