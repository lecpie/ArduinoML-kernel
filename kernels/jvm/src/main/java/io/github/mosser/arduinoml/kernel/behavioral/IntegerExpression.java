package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class IntegerExpression extends PrimitiveExpression <Integer> {

    public IntegerExpression(Integer value) {
        super(value);
    }

    @Override
    public Type getType() {
        return Type.INTEGER;
    }

    @Override
    public void expression(Visitor visitor) {
        visitor.expression(this);
    }
}
