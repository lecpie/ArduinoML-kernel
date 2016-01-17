package main.groovy.groovuinoml.init_dsl;

import groovy.lang.Script
import main.groovy.groovuinoml.dsl.GroovuinoMLBinding;

/**
 * Created by fofo on 16/01/16.
 */
abstract public class InitialisationBaseScript extends Script{
    def lib(String name){
        [args : {}]
    }

    /*
    def sensor(String name) {
        [pin: { n -> ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createSensor(name, n) }]
    }*/

}

