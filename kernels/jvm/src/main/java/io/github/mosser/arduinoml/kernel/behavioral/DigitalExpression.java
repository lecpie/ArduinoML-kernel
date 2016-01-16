package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class DigitalExpression extends PrimitiveExpression <Boolean> {

    public DigitalExpression(Boolean value) {
        super(value);
    }

    @Override
    public Type getType() {
        return Type.DIGITAL;
    }

    @Override
    public void expression(Visitor visitor) {
        visitor.expression(this);
    }
}
