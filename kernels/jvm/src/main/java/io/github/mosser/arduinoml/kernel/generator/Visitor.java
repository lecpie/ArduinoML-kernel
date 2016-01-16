package io.github.mosser.arduinoml.kernel.generator;

import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.structural.*;
import io.github.mosser.arduinoml.kernel.App;

import java.util.HashMap;
import java.util.Map;

public abstract class Visitor<T> {

	public abstract void visit(App app);

	public abstract void visit(State state);
	public abstract void visit(Transition transition);
	public abstract void visit(Action action);

	public abstract void visit(Actuator actuator);
	public abstract void visit(Sensor sensor);

	public abstract void visit(Operator operator);
	public abstract void visit(Condition condition);

	public abstract void expression (PrimitiveExpression e);
	public abstract void expression (DigitalExpression e);
	public abstract void expression (IntegerExpression e);
	public abstract void expression (RealExpression e);

	public abstract void expression(Sensor sensor);


	/***********************
	 ** Helper mechanisms **
	 ***********************/

	protected Map<String,Object> context = new HashMap<>();

	protected T result;

	public T getResult() {
		return result;
	}

}

