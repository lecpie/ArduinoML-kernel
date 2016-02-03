package main.groovy.groovuinoml.init_dsl

import io.github.mosser.arduinoml.kernel.behavioral.Type;
import io.github.mosser.arduinoml.kernel.lib.Library
import io.github.mosser.arduinoml.kernel.lib.Measure
import org.codehaus.groovy.syntax.SyntaxException

/**
 * Created by fofo on 16/01/16.
 */
abstract public class InitialisationBaseScript extends Script{
    Library current_librairy = new Library();
    Measure current_measure = new Measure();

    boolean isLib = true

    //FIXME : NOM UNIQUE AUX LIBRARY + MEASURE A L'AJOUT????

    def library(String name) {
        current_librairy = new Library()
        current_librairy.setName(name)
        ((InitialisationBinding)this.getBinding()).getInitialisationModel().getLibraries().add(current_librairy)

        isLib = true
    }

    def measure(String name) {
        current_measure = new Measure()
        current_measure.setName(name)
        current_measure.setLibrary(current_librairy)
        current_librairy.getMeasures().put(name, current_measure)
        ((InitialisationBinding)this.getBinding()).getInitialisationModel().getMeasures().add(current_measure)

        isLib = false

        [typed: { Type type ->
            current_measure.setType(type)
        }]
    }

    def reads(String readExpression) {
        if (isLib) return

        current_measure.setReadExpressionString(readExpression)
    }

    def setup (String first) {
        if (isLib) {
            current_librairy.getSetupInstructions().add(first)

            def libsetup
            [and: libsetup = { String other ->
                current_librairy.getSetupInstructions().add(other)
                [and: libsetup]
            }]
        }
        else {
            current_measure.getSetupInstructions().add(first)

            def meassetup
            [and: meassetup = { String other ->
                current_measure.getSetupInstructions().add(other)
                [and: meassetup]
            }]
        }
    }

    def variable_based(String varName) {
        if (isLib) {
            // Syntax error
        }
        else {
            String typeName;

            switch (current_measure.getType()) {
                case Type.INTEGER:
                    typeName = "int";
                    break;
                case Type.DIGITAL:
                    typeName = "boolean";
                    break;
                case Type.REAL:
                    typeName = "double";
                    break;

                default:
                    // Syntax error
                    throw new SyntaxException();
            }

            current_measure.getVariables().add(varName);
            current_measure.getGlobalInstructions().add(typeName + " " + varName + ";");
            current_measure.setReadExpressionString(varName);

            [reads: { String instr ->
                current_measure.getUpdateInstructions().add(varName + " = " + instr)
            }]
        }
    }

    def variables (String first) {
        List <String> varref = (isLib ? current_librairy.getVariables() : current_measure.getVariables())

        varref.add(first)

        [and: {
            String next ->
                varref.add(next)
        }]
    }

    def global(String first) {
        List <String> instrref = (isLib ? current_librairy.getGlobalInstructions() : current_measure.getGlobalInstructions())

        instrref.add(first)

        def libclosure
        [and: libclosure = { String other ->
            instrref.add(other)
            [and: libclosure]
        }]
    }

    def args(String firstkey) {
        Map <String, String> argref = (isLib ? current_librairy.getDefaultArgs() : current_measure.getDefaultArgs())

        [valued: {
            String firstValue -> argref.put(firstkey, firstValue)
                def andclosure
                [and: andclosure = {
                    String key ->
                        [valued: {
                            String value ->
                                argref.put(key, value)

                                [and: andclosure]
                        }]
                }]
        }]
    }

    def requires(String firstname) {
        List <String> reqlist = (isLib ? current_librairy.getRequiredArgs() : current_measure.getRequiredArgs());

        reqlist.add(firstname)

        def andclosure
        [and : andclosure = {
            String next ->
                reqlist.add(next)

                [and : andclosure]
        }]
    }

    def includes(String first) {
        if (! isLib) return

        current_librairy.getIncludes().add(first)

        def libclosure
        [and: libclosure = { String other ->
            current_librairy.getIncludes().add(other)
            [and: libclosure]
        }]
    }

    def update(String update) {
        if (isLib) return
        current_measure.getUpdateInstructions().add(update)

        [and: {
            String next ->
                current_measure.getUpdateInstructions().add(next)
        }]
    }

}

