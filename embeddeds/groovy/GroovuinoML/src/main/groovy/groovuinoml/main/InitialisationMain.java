package main.groovy.groovuinoml.main;

import main.groovy.groovuinoml.init_dsl.InitialisationDSL;

import java.io.File;

/**
 * Created by fofo on 16/01/16.
 */
public class InitialisationMain {
    public static void main(String[] args) {
        InitialisationDSL dsl = new InitialisationDSL();
        if(args.length > 0) {
            dsl.eval(new File(args[0]));
        } else {
            System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
        }
    }
}