package main.groovy.groovuinoml.morse_dsl

/**
 * Created by fofo on 26/01/16.
 */

import org.codehaus.groovy.control.CompilerConfiguration

import io.github.mosser.arduinoml.kernel.structural.SIGNAL

class MorseMLDSL {
    private GroovyShell shell
    private CompilerConfiguration configuration
    private MorseMLBinding binding

    MorseMLDSL() {
        binding = new MorseMLBinding()
        binding.setMorseMLModel(new MorseMLModel(binding));
        configuration = new CompilerConfiguration()
        configuration.setScriptBaseClass("main.groovy.groovuinoml.morse_dsl.MorseMLBaseScript")
        shell = new GroovyShell(configuration)

        //binding.setVariable("LONG", Morse_Type.LONG_MORSE)
        //binding.setVariable("SHORT", Morse_Type.SHORT_MORSE)
    }

    void eval(File scriptFile) {
        Script script = shell.parse(scriptFile)
        binding.setScript(script)
        script.setBinding(binding)
        script.run()
    }
}
