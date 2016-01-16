package main.groovy.groovuinoml.model.structural;

import main.groovy.groovuinoml.model.NamedElement;
import main.groovy.groovuinoml.model.generator.Visitable;
import main.groovy.groovuinoml.model.generator.Visitor;

import java.util.List;

/**
 * Created by fofo on 16/01/16.
 */

    public abstract class CodeModel implements NamedElement, Visitable {

        public CodeModel() {
        }
        private String name;
        private List<String> setup_instruction;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public List<String> getSetup_instruction() {
            return setup_instruction;
        }

        public void setSetup_instruction(List<String> setup_instruction) {

            this.setup_instruction = setup_instruction;
        }
    }


