package main.groovy.groovuinoml.init_dsl;

import groovy.lang.Script
import main.groovy.groovuinoml.dsl.GroovuinoMLBinding;

/**
 * Created by fofo on 16/01/16.
 */
abstract public class InitialisationBaseScript extends Script{

    def deflib(String name){
        Map<String, String> args = new LinkedHashMap<String,String>()
        ((InitialisationBinding)this.getBinding()).getInitialisationModel().createLibrary(name,new ArrayList <String>(),new ArrayList <String>(),new ArrayList <String> (),new ArrayList <String>(),args)
        def closure
        [with: closure = {
            String key, String val ->
                args.put(key, val)
            [and: closure]
        }]
    }

}

