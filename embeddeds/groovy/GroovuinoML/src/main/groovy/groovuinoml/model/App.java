package main.groovy.groovuinoml.model;

import main.groovy.groovuinoml.model.generator.Visitor;
import main.groovy.groovuinoml.model.generator.Visitable;
import main.groovy.groovuinoml.model.structural.CodeModel;
import main.groovy.groovuinoml.model.structural.Library;
import main.groovy.groovuinoml.model.structural.Measure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fofo on 16/01/16.
 */
public class App implements NamedElement,Visitable {

    private List<CodeModel> models = new ArrayList();
    private String name;

    public App() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public List<CodeModel> getModels() {
        return models;
    }

    public void setModels(List<CodeModel> models) {
        this.models = models;
    }
}
