package main.groovy.groovuinoml.init_dsl;

import groovy.lang.GroovyShell
import groovy.lang.Script
import io.github.mosser.arduinoml.kernel.behavioral.Type
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
    private InitialisationModel model;

    InitialisationDSL() {
        binding = new InitialisationBinding()
        binding.setInitialisationModel(model = new InitialisationModel(binding))
        configuration = new CompilerConfiguration()
        configuration.setScriptBaseClass("main.groovy.groovuinoml.init_dsl.InitialisationBaseScript")
        shell = new GroovyShell(configuration)

        binding.setVariable("real",    Type.REAL)
        binding.setVariable("integer", Type.INTEGER)
        binding.setVariable("digital", Type.DIGITAL)
    }

    void eval(File scriptFile) {
        Script script = shell.parse(scriptFile)
        binding.setScript(script)
        script.setBinding(binding)
        script.run()
    }

    InitialisationModel getModel() {
        return model;
    }
}