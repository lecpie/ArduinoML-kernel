package io.github.mosser.arduinoml.kernel.lib;

import io.github.mosser.arduinoml.kernel.behavioral.Type;

import java.util.List;
import java.util.Map;

/**
 * Created by lecpie on 1/15/16.
 */
public class Measure {
    private String name;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private Type type;

    private List <String> globalInstructions;
    private List <String> setupInstructions;
    private List <String> updateInstructions;

    private String readExpressionString;

    public Map<String, String> getDefaultArgs() {
        return defaultArgs;
    }

    public void setDefaultArgs(Map<String, String> defaultArgs) {
        this.defaultArgs = defaultArgs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getUpdateInstructions() {
        return updateInstructions;
    }

    public void setUpdateInstructions(List<String> updateInstructions) {
        this.updateInstructions = updateInstructions;
    }

    public String getReadExpressionString() {
        return readExpressionString;
    }

    public void setReadExpressionString(String readExpressionString) {
        this.readExpressionString = readExpressionString;
    }

    private Map <String, String> defaultArgs;

}
