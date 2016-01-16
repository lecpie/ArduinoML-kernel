package main.groovy.groovuinoml.model.generator;

import groovyjarjarantlr.actions.python.CodeLexer;
import main.groovy.groovuinoml.model.App;
import main.groovy.groovuinoml.model.structural.CodeModel;
import main.groovy.groovuinoml.model.structural.Library;
import main.groovy.groovuinoml.model.structural.Measure;

import java.util.Iterator;

/**
 * Created by fofo on 16/01/16.
 */

public class ToWiring extends Visitor<StringBuffer> {
    private static final String CURRENT_STATE = "current_state";

    public ToWiring() {
        this.result = new StringBuffer();
    }

    private void w(String s) {
        ((StringBuffer)this.result).append(String.format("%s\n", new Object[]{s}));
    }

    public void visit(App app) {
        this.w("// Wiring code generated from an ArduinoML model");
        this.w(String.format("// Application name: %s\n", new Object[]{app.getName()}));
        this.w("void setup(){");
        Iterator var2 = app.getModels().iterator();

        while(var2.hasNext()) {
            CodeModel model = (CodeModel)var2.next();
            model.accept(this);
        }

        /*this.w("}\n");
        this.w("long time = 0; long debounce = 200;\n");*/

       /* this.w("void loop() {");
        this.w(String.format("  state_%s();", new Object[]{app.getInitial().getName()}));
        this.w("}");*/
    }


    public void visit(Measure measure) {
        //this.w(String.format("  pinMode(%d, INPUT);  // %s [Sensor]", new Object[]{Integer.valueOf(sensor.getPin()), sensor.getName()}));
        Iterator var2 = measure.getSetup_instruction().iterator();

        while(var2.hasNext()) {
            this.w(String.format(var2.next().toString()));
        }
    }

    public void visit(Library lib) {
        //this.w(String.format("void state_%s() {", new Object[]{state.getName()}));
        Iterator var2 = lib.getSetup_instruction().iterator();

        while(var2.hasNext()) {
            this.w(String.format("setup_instruction :  %s", var2.next().toString()));
        }

       // this.w("  boolean guard = millis() - time > debounce;");
      //  this.context.put("current_state", state);
       // state.getTransition().accept(this);
       // this.w("}\n");
    }
}
