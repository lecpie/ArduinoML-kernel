package io.github.mosser.arduinoml.kernel.lib;

import io.github.mosser.arduinoml.kernel.behavioral.Type;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.language.Expression;
import io.github.mosser.arduinoml.kernel.language.Global;
import io.github.mosser.arduinoml.kernel.language.Updatable;
import io.github.mosser.arduinoml.kernel.structural.Brick;

import java.util.Map;

/**
 * Created by lecpie on 1/15/16.
 */
public class MeasureUse extends Brick implements Global, Updatable, Expression {
    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public Map<String, String> getArgsValues() {
        return argsValues;
    }

    public void setArgsValues(Map<String, String> argsValues) {
        this.argsValues = argsValues;
    }

    private Measure measure;

    public LibraryUse getLibraryUse() {
        return libraryUse;
    }

    public void setLibraryUse(LibraryUse libraryUse) {
        this.libraryUse = libraryUse;
    }

    private LibraryUse libraryUse;

    private Map <String, String> argsValues;

    @Override
    public void setup(Visitor visitor) {
        visitor.setup(this);
    }

    @Override
    public void global(Visitor visitor) {
        visitor.global(this);
    }

    @Override
    public void update(Visitor visitor) {
        visitor.update(this);
    }

    @Override
    public void expression(Visitor visitor) {
        visitor.expression(this);
    }

    @Override
    public Type getType() {
        return measure.getType();
    }
}
