package io.github.mosser.arduinoml.kernel.generator;

import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.lib.Library;
import io.github.mosser.arduinoml.kernel.lib.LibraryUse;
import io.github.mosser.arduinoml.kernel.lib.MeasureUse;
import io.github.mosser.arduinoml.kernel.structural.*;
import io.github.mosser.arduinoml.kernel.App;

import java.util.HashMap;
import java.util.Map;

public abstract class Visitor<T> {

	public abstract void visit(App app);

	public abstract void visit(State state);
	public abstract void visit(Transition transition);

	public abstract void setup(PinnedActuator actuator);
	public abstract void setup(PinnedSensor sensor);

	public abstract void visit(Operator operator);
	public abstract void visit(Condition condition);
	public abstract void visit(ConditionTree conditionTree);

	public abstract void expression (PrimitiveExpression e);
	public abstract void expression (DigitalExpression e);
	public abstract void expression (IntegerExpression e);
	public abstract void expression (RealExpression e);

	public abstract void expression (PinnedSensor sensor);

	public abstract void action (Action action);
	public abstract void action (PinnedActuator actuator);
	public abstract void action (Sleep sleep);

	public abstract void include (Library library);

	public abstract void setup(LibraryUse libraryUse);
	public abstract void global(LibraryUse libraryUse);

	public abstract void setup      (MeasureUse measureUse);
	public abstract void global     (MeasureUse measureUse);
	public abstract void update     (MeasureUse measureUse);
	public abstract void expression (MeasureUse measureUse);


	/***********************
	 ** Helper mechanisms **
	 ***********************/

	protected Map<String,Object> context = new HashMap<>();

	protected T result;

	public T getResult() {
		return result;
	}

}

