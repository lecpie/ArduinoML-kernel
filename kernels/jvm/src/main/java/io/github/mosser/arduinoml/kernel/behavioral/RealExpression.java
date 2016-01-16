package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class RealExpression extends PrimitiveExpression {
    public RealExpression(Object value) {
        super(value);
    }

    @Override
    public Type getType() {
        return Type.REAL;
    }

    @Override
    public void expression(Visitor visitor) {
        visitor.expression(this);
    }
}
