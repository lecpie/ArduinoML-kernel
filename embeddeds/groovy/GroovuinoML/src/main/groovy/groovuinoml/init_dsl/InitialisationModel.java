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

    public void createLibrary(Library library){
        this.libraries.add(library);
    }

    public void createMeasure(Measure measure){
        this.measures.add(measure);
    }


    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }
}
