package main.groovy.groovuinoml.init_dsl;

import groovy.lang.Script
import io.github.mosser.arduinoml.kernel.lib.Library
import io.github.mosser.arduinoml.kernel.lib.Measure
import main.groovy.groovuinoml.dsl.GroovuinoMLBinding

import java.lang.reflect.Type;

/**
 * Created by fofo on 16/01/16.
 */
abstract public class InitialisationBaseScript extends Script{
    Library current_librairy = new Library();
    Measure current_measure = new Measure();

    boolean isLib = true

    //FIXME : NOM UNIQUE AUX LIBRARY + MEASURE A L'AJOUT????

    def deflib(String name) {
        current_librairy = new Library()
        current_librairy.setName(name)
        ((InitialisationBinding)this.getBinding()).getInitialisationModel().getLibraries().add(current_librairy)

        isLib = true
    }

    def defmeasure(String name) {
        current_measure = new Measure()
        current_measure.setName(name)
        current_measure.setLibrary(current_librairy)
        current_librairy.getMeasures().put(name, current_measure)
        ((InitialisationBinding)this.getBinding()).getInitialisationModel().getMeasures().add(current_measure)

        isLib = false
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
                [and: {
                    String key ->
                        [valued: {
                            String value ->
                                argref.put(key, value)
                        }]
                }]
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

    def withlib(){

        [
        setup: closureSetup = { String setup ->
            current_librairy.getSetupInstructions().add(setup)
            [and : closureSetup ]
        },
        args: closureArgs = {String key,String val ->
            current_librairy.getDefaultArgs().put(key,val)
            [and : closureArgs]
            //[setup: closureSetup]
        },
        includes: closureIncludes = {String includes ->
            current_librairy.getIncludes().add(includes)
            [and : closureIncludes]
        },
        global: closureGlobal = {String global ->
            current_librairy.getGlobalInstructions().add(global)
            [and : closureGlobal]
        },
        before: closureBefore = {String before ->
            current_librairy.getBeforeReadInstructions().add(before)
            [and : closureBefore]
        },
        variables: closureVariable = {String variable->
            current_librairy.getVariables().add(variable)
            [and : closureVariable]
        }
        ]
    }
    def exportlib(String nameLibrary) {
        current_librairy.setName(nameLibrary)
        ((InitialisationBinding)this.getBinding()).getInitialisationModel()
                .createLibrary(current_librairy)
        current_librairy = null;
        current_librairy = new Library()
    }

    def withmeasure(){

        [
                type: closureType = { String type ->

                    if (io.github.mosser.arduinoml.kernel.behavioral.Type.DIGITAL.name().equals(type)){
                        current_measure.setType(io.github.mosser.arduinoml.kernel.behavioral.Type.DIGITAL)
                    }
                    else if(io.github.mosser.arduinoml.kernel.behavioral.Type.INTEGER.name().equals(type)){
                        current_measure.setType(io.github.mosser.arduinoml.kernel.behavioral.Type.INTEGER)
                    }
                    else if(io.github.mosser.arduinoml.kernel.behavioral.Type.REAL.name().equals(type)){
                        current_measure.setType(io.github.mosser.arduinoml.kernel.behavioral.Type.REAL)
                    }
                    else{
                        println("Incorrect Type \n Possible : \n\t\t "+io.github.mosser.arduinoml.kernel.behavioral.Type.REAL.name())
                    }

                    [and : closureType]
                },
                setup: closureSetup = { String setup ->
                    current_measure.getSetupInstructions().add(setup)
                    [and : closureSetup]
                },
                args: closureArgs = {String key,String val ->
                    current_measure.getDefaultArgs().put(key,val)
                    [and : closureArgs]
                },
                global: closureGlobal = {String global ->
                    current_measure.getGlobalInstructions().add(global)
                    [and : closureGlobal]
                },
                update: closureUpdate = {String update ->
                    current_measure.getUpdateInstructions().add(update)
                    [and : closureUpdate]
                },
                read: closureRead = {String read ->
                    current_measure.setReadExpressionString(read)
                    [and : closureRead]
                },
                variables: closureVariable = {String variable ->
                    current_measure.getVariables().add(variable)
                    [and : closureVariable]
                },
        ]
    }

    def associate(String measureName,String libraryName) {

        for(Library librairies : ((InitialisationBinding)this.getBinding()).getInitialisationModel().libraries){
                if(librairies.getName().equals(libraryName)){
                    for(Measure measure : ((InitialisationBinding)this.getBinding()).getInitialisationModel().measures) {
                        if (measure.getName().equals(measureName) && measure.getLibrary().equals(null)) {
                            measure.setLibrary(librairies)
                            librairies.getMeasures().put(measure.getName(), measure)
                            println("Found Matching Library/Measure")
                        }
                        else if(measure.getName().equals(measureName) && !measure.getLibrary().equals(null)){
                            println("Measure already assigned")
                        }
                        else{
                           // println("Measure not Found")
                        }
                    }
                }
            else{
                   // println("Library not Found")
                }
        }
    }
    def exportmeasure(String nameMeasure) {

        current_measure.setName(nameMeasure)
        ((InitialisationBinding)this.getBinding()).getInitialisationModel().createMeasure(current_measure)
        current_measure = null
        current_measure = new Measure()
    }
}

