package main.groovy.groovuinoml.init_dsl;

import groovy.lang.Script
import io.github.mosser.arduinoml.kernel.lib.Library
import main.groovy.groovuinoml.dsl.GroovuinoMLBinding

import java.lang.reflect.Type;

/**
 * Created by fofo on 16/01/16.
 */
abstract public class InitialisationBaseScript extends Script{
    static Library current = new Library();

    def with(){

        def tab = 0
        [
        setup: closureSetup = { String setup ->
            println(setup)
            print(tab)

            //current.getSetupInstructions().add(setup);
            [and : closureSetup ]
        },
        args: closureArgs = {String key,String val ->
            println(key+val)
            //current.getDefaultArgs().put(key,val)
            [and : closureArgs]
            //[setup: closureSetup]
        },
        ]





/*

        def closureSetup
        [setup: closureSetup = {String setup ->
            println(setup)
            //current.getSetupInstructions().add(setup);
            [and : closureSetup]
        }]



             def closureInclude
             [global: closureGlobal = {String includes ->
                //current.getIncludes().add(includes);
                 [and : closureGlobal]
             }]

             def closureGlobal
             [global: closureGlobal = {String global ->
                // current.getGlobalInstructions().add(global);
                 [and : closureGlobal]
             }]


             def closureBefore
             [read: closureBefore = {String before ->
                 //current.getBeforeReadInstructions().add(before);
                 [and : closureBefore]
             }]*/
    }

    def export(String name) {
        ((InitialisationBinding)this.getBinding()).getInitialisationModel().createLibrary(current.getName(),current.getIncludes(),current.getGlobalInstructions(),current.getSetupInstructions(),current.getBeforeReadInstructions(),current.getDefaultArgs())
        current = null;
    }


}

