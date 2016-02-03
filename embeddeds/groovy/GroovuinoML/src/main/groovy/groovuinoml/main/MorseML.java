package main.groovy.groovuinoml.main;

import main.groovy.groovuinoml.morse_dsl.*;

import java.io.File;

/**
 * Created by fofo on 26/01/16.
 */
public class MorseML {
    public static void main(String[] args) {
        MorseMLDSL dsl = new MorseMLDSL();
        if(args.length > 0) {
            dsl.eval(new File(args[0]));
        } else {
            System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
        }
    }
}
