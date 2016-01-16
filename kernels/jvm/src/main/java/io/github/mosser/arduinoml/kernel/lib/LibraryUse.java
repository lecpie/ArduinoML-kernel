package io.github.mosser.arduinoml.kernel.lib;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Brick;

import java.util.Map;

/**
 * Created by lecpie on 1/15/16.
 */
public class LibraryUse {
    private Library library;
    private Map <String, String> argsValues;

    public Map<String, String> getArgsValues() {
        return argsValues;
    }

    public void setArgsValues(Map<String, String> argsValues) {
        this.argsValues = argsValues;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
