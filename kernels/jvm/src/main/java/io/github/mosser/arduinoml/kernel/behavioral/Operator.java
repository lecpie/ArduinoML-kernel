package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.visitable.Visitable;

public enum Operator implements Visitable {
    EQ,NE,GT,LT,GE,LE;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
