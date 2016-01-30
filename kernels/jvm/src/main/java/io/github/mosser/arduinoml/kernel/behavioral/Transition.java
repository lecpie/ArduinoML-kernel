package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.language.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class Transition implements Visitable {

	private State next;

	Condition condition;

	public State getNext() {
		return next;
	}

	public void setNext(State next) {
		this.next = next;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
