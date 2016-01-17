package main.groovy.groovuinoml.init_dsl;

import groovy.lang.Binding;
import groovy.lang.Script;


import java.util.Map;

/**
 * Created by fofo on 16/01/16.
 */
public class InitialisationBinding extends Binding {

    private Script script;
    private InitialisationModel model;

    public InitialisationBinding() {
        super();
    }

    @SuppressWarnings("rawtypes")
    public InitialisationBinding(Map variables) {
        super(variables);
    }

    public InitialisationBinding(Script script) {
        super();
        this.script = script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public void setInitialisationModel(InitialisationModel model) {
        this.model = model;
    }

    public Object getVariable(String name) {
        /*
        if ("seb".equals(name)) {
            // could do something else like: ((App) this.getVariable("app")).action();
            System.out.println("Seb, c'est bien");
            return script;
        }*/
        return super.getVariable(name);
    }

    public void setVariable(String name, Object value) {
        super.setVariable(name, value);
    }

    public InitialisationModel getInitialisationModel() {
        return this.model;
    }
}
