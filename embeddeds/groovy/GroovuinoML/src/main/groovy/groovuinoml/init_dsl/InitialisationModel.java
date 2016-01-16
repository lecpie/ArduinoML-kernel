package main.groovy.groovuinoml.init_dsl;

import groovy.lang.Binding;
import io.github.mosser.arduinoml.kernel.behavioral.Type;
import io.github.mosser.arduinoml.kernel.lib.Library;
import io.github.mosser.arduinoml.kernel.lib.Measure;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fofo on 16/01/16.
 */
public class InitialisationModel {

    private List <Library> libraries;
    private List <Measure> measures;

    private Binding binding;

    public InitialisationModel(Binding binding){

        this.libraries = new ArrayList<Library>();
        this.measures = new ArrayList<Measure>();
        this.binding = binding;
    }

    public void createLibrary(String name,List <String> includes,List <String> globalInstructions,List <String> setupInstructions,List <String> beforeReadInstructions,Map<String, String> defaultArgs){

        Library library = new Library();
        library.setName(name);
        library.setIncludes(includes);
        library.setBeforeReadInstructions(beforeReadInstructions);
        library.setGlobalInstructions(globalInstructions);
        library.setSetupInstructions(setupInstructions);
        library.setDefaultArgs(defaultArgs);
        this.libraries.add(library);
    }

    public void createMeasure(String name, Type type, List <String> globalInstructions, List <String> setupInstructions,List <String> updateInstructions,String readExpressionString,Map<String, String> defaultArgs){

        Measure measure = new Measure();
        measure.setName(name);
        measure.setDefaultArgs(defaultArgs);
        measure.setUpdateInstructions(updateInstructions);
        measure.setGlobalInstructions(globalInstructions);
        measure.setReadExpressionString(readExpressionString);
        measure.setSetupInstructions(setupInstructions);
        measure.setType(type);

        this.measures.add(measure);
    }


}
