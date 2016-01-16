package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.language.Expression;

public abstract class PrimitiveExpression <T> implements Expression {
    T value;

    public PrimitiveExpression(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public void expression(Visitor visitor) {
        visitor.expression(this);
    }
}
