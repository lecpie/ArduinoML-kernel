package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.language.Actionable;

public class PinnedActuator extends PinnedBrick implements Actionable {

	@Override
	public void setup(Visitor visitor) {
		visitor.setup(this);
	}

	@Override
	public void action(Visitor visitor) {
		visitor.action(this);
	}
}
