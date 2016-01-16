package io.github.mosser.arduinoml.kernel.generator;

import static io.github.mosser.arduinoml.kernel.behavioral.Operator.*;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.CompilationError;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.Arrays;


/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {

	private final static String CURRENT_STATE = "current_state";

	public ToWiring() {
		this.result = new StringBuffer();
	}

	private void add(String s) {
		result.append(s);
	}

	private void w(String s) {
		result.append(String.format("%s\n",s));
	}

	@Override
	public void visit(App app) {
		w("// Wiring code generated from an ArduinoML model");
		w(String.format("// Application name: %s\n", app.getName()));

		w("void setup(){");
		for(Brick brick: app.getBricks()){
			brick.accept(this);
		}
		w("}\n");

		w("long time = 0; long debounce = 200;\n");

		for(State state: app.getStates()){
			state.accept(this);
		}

		w("void loop() {");
		w(String.format("  state_%s();", app.getInitial().getName()));
		w("}");
	}

	@Override
	public void visit(Actuator actuator) {
	 	w(String.format("  pinMode(%d, OUTPUT); // %s [Actuator]", actuator.getPin(), actuator.getName()));
	}


	@Override
	public void visit(Sensor sensor) {
		w(String.format("  pinMode(%d, INPUT);  // %s [Sensor]", sensor.getPin(), sensor.getName()));
	}

	private static final Operator[] searchOpRepr = { EQ,   NE,  GT,  LT,   GE,   LE };
	private static final String[]   opRepr       = {"==", "!=", ">", "<", ">=", "<="};

	@Override
	public void visit(Operator operator) {
		int iop = Arrays.binarySearch(searchOpRepr,operator);

		if (iop >= opRepr.length) {
			throw new CompilationError("operator not implemented : " + operator);
		}

		result.append(opRepr[iop]);
	}

	@Override
	public void visit(Condition condition) {
		condition.getLeft().expression(this);
		add(" ");
		condition.getOperator().accept(this);
		add(" ");
		condition.getRight().expression(this);
	}

	@Override
	public void expression(PrimitiveExpression e) {
		throw new CompilationError("untyped expression");
	}

	public void expression(DigitalExpression e) {
		add (e.getValue() ? "HIGH" : "LOW");
	}

	public void expression(IntegerExpression e) {
		add(e.getValue().toString());
	}

	public void expression(RealExpression e) {
		add(e.getValue().toString());
	}

	@Override
	public void expression(Sensor sensor) {
		result.append(String.format("digitalRead(%d)",sensor.getPin()));
	}

	@Override
	public void visit(State state) {
		w(String.format("void state_%s() {",state.getName()));
		for(Action action: state.getActions()) {
			action.accept(this);
		}
		w("  boolean guard = millis() - time > debounce;");
		context.put(CURRENT_STATE, state);
		state.getTransition().accept(this);
		w("}\n");

	}

	@Override
	public void visit(Transition transition) {

		add("if (");
		transition.getCondition().accept(this);
		w(" && guard) {");

		w("    time = millis();");
		w(String.format("    state_%s();",transition.getNext().getName()));
		w("  } else {");
		w(String.format("    state_%s();",((State) context.get(CURRENT_STATE)).getName()));
		w("  }");
	}

	@Override
	public void visit(Action action) {
		w(String.format("  digitalWrite(%d,%s);",action.getActuator().getPin(),action.getValue()));
	}

}
