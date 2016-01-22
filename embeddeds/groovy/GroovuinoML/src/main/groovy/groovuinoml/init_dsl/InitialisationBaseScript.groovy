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

    //FIXME : NOM UNIQUE AUX LIBRARY + MEASURE A L'AJOUT????

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
        includes: closureBefore = {String includes ->
            current_librairy.getIncludes().add(includes)
            [and : closureBefore]
        },
        global: closureGlobal = {String includes ->
            current_librairy.getIncludes().add(includes)
            [and : closureGlobal]
        },
        before: closureBefore = {String before ->
            current_librairy.getBeforeReadInstructions().add(before)
            [and : closureBefore]
        }
        ]
    }
    def exportlib(String nameLibrary) {
        current_librairy.setName(nameLibrary)
        ((InitialisationBinding)this.getBinding()).getInitialisationModel()
                .createLibrary(current_librairy.getName(),
                current_librairy.getIncludes(),
                current_librairy.getGlobalInstructions(),
                current_librairy.getSetupInstructions(),
                current_librairy.getBeforeReadInstructions(),
                current_librairy.getDefaultArgs())
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
                }
        ]
    }

    def associate(String measureName,String libraryName) {

        for(Library librairies : ((InitialisationBinding)this.getBinding()).getInitialisationModel().libraries){
                if(librairies.getName().equals(libraryName)){
                    for(Measure measure : ((InitialisationBinding)this.getBinding()).getInitialisationModel().measures) {
                        if (measure.getName().equals(measureName) && measure.getLibrary().equals(null)) {
                            measure.setLibrary(librairies)
                            println("Found Matching Library/Measure")
                        }
                        else if(measure.getName().equals(measureName) && !measure.getLibrary().equals(null)){
                            println("Measure already assigned")
                        }
                        else{
                            println("Measure not Found")
                        }
                    }
                }
            else{
                    println("Library not Found")
                }
        }
    }
    def exportmeasure(String nameMeasure) {

        current_measure.setName(nameMeasure)
        ((InitialisationBinding)this.getBinding()).getInitialisationModel().createMeasure(
                current_measure.getName(),
                current_measure.getType(),
                current_measure.getGlobalInstructions(),
                current_measure.getSetupInstructions(),
                current_measure.getUpdateInstructions(),
                current_measure.getReadExpressionString(),
                current_measure.getDefaultArgs()

        )
        current_measure = null
        current_measure = new Measure()
    }


}

