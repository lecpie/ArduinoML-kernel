package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.visitable.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;

public class Transition implements Visitable {

	private State next;

	Condition condition;

	// private Sensor sensor;
	// private SIGNAL value;


	public State getNext() {
		return next;
	}

	public void setNext(State next) {
		this.next = next;
	}

	/*

	public Sensor getSensor() {
		return sensor;
	}

	//public void setSensor(Sensor sensor) {
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
