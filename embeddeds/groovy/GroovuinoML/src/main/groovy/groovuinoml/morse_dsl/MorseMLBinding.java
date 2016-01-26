package main.groovy.groovuinoml.morse_dsl;

import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.Map;

/**
 * Created by fofo on 26/01/16.
 */

    public class MorseMLBinding extends Binding {
        // can be useful to return the script in case of syntax trick
        private Script script;

        private MorseMLModel model;

        public MorseMLBinding() {
            super();
        }

    @SuppressWarnings("rawtypes")
    public MorseMLBinding(Map variables) {
        super(variables);
    }

    public MorseMLBinding(Script script) {
        super();
        this.script = script;
    }

        public void setScript(Script script) {
            this.script = script;
        }

        public void setMorseMLModel(MorseMLModel model) {
            this.model = model;
        }

        public Object getVariable(String name) {
            // Easter egg (to show you this trick: seb is now a keyword!)
            if ("seb".equals(name)) {
                // could do something else like: ((App) this.getVariable("app")).action();
                System.out.println("Seb, c'est bien");
                return script;
            }
            return super.getVariable(name);
        }

        public void setVariable(String name, Object value) {
            super.setVariable(name, value);
        }

        public MorseMLModel getMorseMLModel() {
            return this.model;
        }
    }
