package io.github.mosser.arduinoml.kernel.lib;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.language.Includable;

import java.util.*;

/**
 * Created by lecpie on 1/15/16.
 */
public class Library implements Includable {
    private String name;
    private List <String> includes = new ArrayList<>();

    private List <String> requiredArgs = new ArrayList<>();
    private Map <String, String> defaultArgs = new LinkedHashMap<>();

    private List <String> variables = new ArrayList<>();

    private List <String> globalInstructions = new ArrayList<>();
    private List <String> setupInstructions = new ArrayList<>();
    private List <String> updateInstructions = new ArrayList<>();


    private Map <String, Measure> measures = new HashMap<>();

    @Override
    public void include(Visitor visitor) {
        visitor.include(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public List<String> getGlobalInstructions() {
        return globalInstructions;
    }

    public void setGlobalInstructions(List<String> globalInstructions) {
        this.globalInstructions = globalInstructions;
    }

    public List<String> getSetupInstructions() {
        return setupInstructions;
    }

    public void setSetupInstructions(List<String> setupInstructions) {
        this.setupInstructions = setupInstructions;
    }

    public List<String> getBeforeReadInstructions() {
        return updateInstructions;
    }

    public void setBeforeReadInstructions(List<String> beforeReadInstructions) {
        this.updateInstructions = beforeReadInstructions;
    }

    public Map<String, String> getDefaultArgs() {
        return defaultArgs;
    }

    public void setDefaultArgs(Map<String, String> defaultArgs) {
        this.defaultArgs = defaultArgs;
    }

    public Map<String, Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(Map<String, Measure> measures) {
        this.measures = measures;
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public List<String> getRequiredArgs() {
        return requiredArgs;
    }

    public void setRequiredArgs(List<String> requiredArgs) {
        this.requiredArgs = requiredArgs;
    }

}
