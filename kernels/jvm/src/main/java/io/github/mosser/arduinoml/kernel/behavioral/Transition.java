package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.language.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class Transition implements Visitable {

	private State next;

	Condition condition;

	// private PinnedSensor sensor;
	// private SIGNAL value;


	public State getNext() {
		return next;
	}

	public void setNext(State next) {
		this.next = next;
	}

	/*

	public PinnedSensor getSensor() {
		return sensor;
	}

	//public void setSensor(PinnedSensor sensor) {
		this.sensor = sensor;
	}

	public SIGNAL getValue() {
		return value;
	}

	public void setValue(SIGNAL value) {
		this.value = value;
	}

	*/

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
