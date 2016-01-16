package main.groovy.groovuinoml.model.sample;

import main.groovy.groovuinoml.model.App;
import main.groovy.groovuinoml.model.generator.ToWiring;
import main.groovy.groovuinoml.model.structural.CodeModel;
import main.groovy.groovuinoml.model.structural.Library;
import main.groovy.groovuinoml.model.structural.Measure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fofo on 16/01/16.
 */
public class Switch {
    public Switch() {
    }

    public static void main(String[] args) {
        App theSwitch = new App();
        CodeModel lib1 = new Library();
        lib1.setName("DHT");
        lib1.setSetup_instruction(new ArrayList<String>());
        lib1.getSetup_instruction().add("ReadTemp");

        CodeModel measure1 = new Measure();
        measure1.setName("Température");
        measure1.setSetup_instruction(new ArrayList<String>());
        measure1.getSetup_instruction().add(lib1.getName()+".begin()\n");

        theSwitch.setName("Switch!");
        //si on veut ajouter du String avant dans le ToWiring theSwitch.setModels(Arrays.asList(new CodeModel[][]{lib1,measure1}));
        theSwitch.setModels(Arrays.asList(new CodeModel[]{lib1,measure1}));
        ToWiring codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);
        System.out.println(codeGenerator.getResult());
    }
}