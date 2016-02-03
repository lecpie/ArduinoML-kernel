package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.behavioral.Type;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.language.Expression;

public class PinnedSensor extends PinnedBrick implements Expression {
	private Type type;

	@Override
	public void setup(Visitor visitor) {
		visitor.setup(this);
	}

	@Override
	public void expression(Visitor visitor) {
		visitor.expression(this);
	}

}
