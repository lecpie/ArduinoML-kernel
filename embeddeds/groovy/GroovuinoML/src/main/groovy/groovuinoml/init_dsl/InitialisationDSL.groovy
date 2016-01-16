package main.groovy.groovuinoml.init_dsl;

import groovy.lang.GroovyShell
import groovy.lang.Script
import main.groovy.groovuinoml.init_dsl.InitialisationBaseScript
import main.groovy.groovuinoml.init_dsl.InitialisationBinding
import main.groovy.groovuinoml.init_dsl.InitialisationModel
import org.codehaus.groovy.control.CompilerConfiguration

import io.github.mosser.arduinoml.kernel.structural.SIGNAL
import java.io.File
import java.io.IOException

class InitialisationDSL {
    private GroovyShell shell
    private CompilerConfiguration configuration
    private InitialisationBinding binding
    private InitialisationBaseScript basescript

    InitialisationDSL() {
        binding = new InitialisationBinding()
        binding.setInitialisationModel(new InitialisationModel(binding))
        configuration = new CompilerConfiguration()
        configuration.setScriptBaseClass("main.groovy.groovuinoml.dsl.InitailisationBaseScript")
        shell = new GroovyShell(configuration)
    }

    void eval(File scriptFile) {
        Script script = null;
        try {
            script = shell.parse(scriptFile)
        } catch (IOException e) {
            e.printStackTrace()
        }

        binding.setScript(script)
        script.setBinding(binding)

        script.run()
    }
}