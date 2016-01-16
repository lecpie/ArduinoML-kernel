package io.github.mosser.arduinoml.kernel.lib;

import java.util.List;
import java.util.Map;

/**
 * Created by lecpie on 1/15/16.
 */
public class Library {
    private String name;
    private List <String> includes;

    private List <String> globalInstructions;
    private List <String> setupInstructions;
    private List <String> beforeReadInstructions;

    private Map<String, String> defaultArgs;

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
        return beforeReadInstructions;
    }

    public void setBeforeReadInstructions(List<String> beforeReadInstructions) {
        this.beforeReadInstructions = beforeReadInstructions;
    }

    public Map<String, String> getDefaultArgs() {
        return defaultArgs;
    }

    public void setDefaultArgs(Map<String, String> defaultArgs) {
        this.defaultArgs = defaultArgs;
    }
}
