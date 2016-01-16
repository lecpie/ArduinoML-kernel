package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.behavioral.Type;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.visitable.Expression;

public class Sensor extends Brick implements Expression {
	private Type type;

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void expression(Visitor visitor) {
		visitor.expression(this);
	}

	@Override
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
