package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.language.Visitable;

public enum Operator implements Visitable {
    EQ,NE,GT,LT,GE,LE,OR,AND;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
