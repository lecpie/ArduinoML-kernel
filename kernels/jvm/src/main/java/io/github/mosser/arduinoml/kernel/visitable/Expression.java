package io.github.mosser.arduinoml.kernel.visitable;

import io.github.mosser.arduinoml.kernel.behavioral.Type;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

public interface Expression {
    void expression(Visitor visitor);
    Type getType();
}
