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

	// Let's try to hide this here
	private static final String ARDUINOML_GEN_ARG1 = "ARDUINOML_GEN_ARG1";

	@Override
	public void visit(App app) {
		w("// Wiring code generated from an ArduinoML model");
		w(String.format("// Application name: %s\n", app.getName()));

		w("void setup(){");
		for(Brick brick: app.getBricks()){
			brick.setup(this);
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
	public void setup(PinnedActuator actuator) {
	 	w(String.format("  pinMode(%d, OUTPUT); // %s [PinnedActuator]", actuator.getPin(), actuator.getName()));
	}


	@Override
	public void setup(PinnedSensor sensor) {
		w(String.format("  pinMode(%d, INPUT);  // %s [PinnedSensor]", sensor.getPin(), sensor.getName()));
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
	public void expression(PinnedSensor sensor) {
		result.append(String.format((sensor.isAnalogMode() ? "analog" : "digital") + "Read(%d)", sensor.getPin()));
	}

	@Override
	public void action(PinnedActuator actuator) {
		w(String.format("  " + (actuator.isAnalogMode() ? "analog" : "digital") + "Write(%d, " + ARDUINOML_GEN_ARG1 + " );", actuator.getPin()));
	}

	@Override
	public void visit(State state) {
		w(String.format("void state_%s() {",state.getName()));
		for(Action action: state.getActions()) {
			action.action(this);
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
	public void action(Action action) {
		add("#define " + ARDUINOML_GEN_ARG1 + " ");
		action.getSignalExpression().expression(this);
		w("");
		action.getActuator().action(this);
		w("#undef " + ARDUINOML_GEN_ARG1);
	}

}
