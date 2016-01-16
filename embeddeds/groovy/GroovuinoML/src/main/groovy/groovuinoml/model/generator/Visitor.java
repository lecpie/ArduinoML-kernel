package main.groovy.groovuinoml.model.generator;

import main.groovy.groovuinoml.model.structural.Library;
import main.groovy.groovuinoml.model.structural.Measure;
import main.groovy.groovuinoml.model.App;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fofo on 16/01/16.
 */
public abstract class Visitor<T> {
    protected Map<String, Object> context = new HashMap();
    protected T result;

    public Visitor() {
    }

    public abstract void visit(App var1);

    public abstract void visit(Library var1);

    public abstract void visit(Measure var1);

    public T getResult() {
        return this.result;
    }
}