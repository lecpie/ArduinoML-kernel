package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.language.Expression;
import io.github.mosser.arduinoml.kernel.language.Typed;
import io.github.mosser.arduinoml.kernel.structural.PinnedActuator;
import io.github.mosser.arduinoml.kernel.language.Actionable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;

public class Action implements Actionable, Typed {

	private PinnedActuator actuator;
	private Expression signalExpression;

	public PinnedActuator getActuator() {
		return actuator;
	}

	public void setActuator(PinnedActuator actuator) {
		this.actuator = actuator;
	}

	@Override
	public void action(Visitor visitor) {
		visitor.action(this);
	}


	@Override
	public Type getType() {
		return signalExpression.getType();
	}

	public Expression getSignalExpression() {
		return signalExpression;
	}

	public void setSignalExpression(Expression signalExpression) {
		this.signalExpression = signalExpression;
	}
}
