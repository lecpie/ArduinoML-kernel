package main.groovy.groovuinoml.model.structural;


import main.groovy.groovuinoml.model.generator.Visitor;


/**
 * Created by fofo on 16/01/16.
 */
public class Measure extends CodeModel {
    public Measure() {

    }
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
