package io.github.mosser.arduinoml.kernel.generator;

import static io.github.mosser.arduinoml.kernel.behavioral.Operator.*;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.CompilationError;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.language.Actionable;
import io.github.mosser.arduinoml.kernel.language.Expression;
import io.github.mosser.arduinoml.kernel.language.Global;
import io.github.mosser.arduinoml.kernel.language.Updatable;
import io.github.mosser.arduinoml.kernel.lib.Library;
import io.github.mosser.arduinoml.kernel.lib.LibraryUse;
import io.github.mosser.arduinoml.kernel.lib.Measure;
import io.github.mosser.arduinoml.kernel.lib.MeasureUse;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.*;


/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {
	private final static String AML_LIBRARY_INSTANCE = "__ARDUINOML_LIBRARY_INSTANCE__";
	private final static String AML_MEASURE_INSTANCE = "__ARDUINOML_MEASURE_INSTANCE__";

	private final static String CURRENT_STATE = "current_state";

	private Map <LibraryUse, Map <String, String> > librarysym = new HashMap<>();
	private Map <MeasureUse, Map <String, String> > measuresym = new HashMap<>();
	private int nextsym = 0;

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

    private void def (String name) {
        add("#define " + name);
    }

    private void def (String name, String val) {
        def(name);
        w(" " + val);
    }

    private void undef (String name) {
        w("#undef " + name);
    }

	@Override
	public void visit(App app) {
		w("// Wiring code generated from an ArduinoML model");
		w(String.format("// Application name: %s\n", app.getName()));

		// Provide api for lib definition
		w("// API for Library definition");
		w("#define AML_PASTE(A,B) A ## _ ## B");
		w("#define AML_EVAL(A,B) AML_PASTE(LIB, MEASURE, INDEX)");
		w("#define AML_CAT(A,B) AML_EVAL(A,B)");

		int ctr = 0;
		for (LibraryUse usedlib : app.getUsedLibraries()) {
			Library lib = usedlib.getLibrary();

			usedlib.getArgsValues().put(AML_LIBRARY_INSTANCE, Integer.toString(ctr++));
			usedlib.loadDefaults();

			librarysym.put(usedlib, new HashMap<>());
			for (String var : lib.getVariables()) {
				int isym = nextsym++;
				String varname = "_aml_library_var_" + isym;
				librarysym.get(usedlib).put(var, varname);
				usedlib.getArgsValues().put(var, varname);
			}

			lib.include(this);
		}

		for (LibraryUse usedlib : app.getUsedLibraries()) {
			usedlib.global(this);
		}

		ctr = 0;
		for (Brick brick : app.getBricks()) {
			if (brick instanceof MeasureUse) {
				MeasureUse measureUse = ((MeasureUse) brick);
				Measure measure = measureUse.getMeasure();

				// Maintain this variable for measure instances
				measureUse.getArgsValues().put(AML_MEASURE_INSTANCE, Integer.toString(ctr++));
				measureUse.loadDefaults();

				measuresym.put(measureUse, new HashMap<>());
				for (String var : measure.getVariables()) {
					int isym = nextsym++;
					String varname = "_aml_measure_var_" + isym;

					measuresym.get(measureUse).put(var, varname);
					measureUse.getArgsValues().put(var, varname);
				}
			}
		}

		for (Brick brick : app.getBricks()) {
			if (brick instanceof Global) {
				((Global) brick).global(this);
			}
		}

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
	public void include(Library library) {
		for (String include : library.getIncludes()) {
			w("#include <" + include + ">");
		}
	}

	@SafeVarargs
    private final void loadArgs(Map <String, String> ... args) {
		w("");
        for (Map <String, String> priorityArgs: args) {
            for (String arg : priorityArgs.keySet()) {
                def(arg, priorityArgs.get(arg));
            }
        }
    }
    @SafeVarargs
    private final void unloadArgs( Map <String, String> ... args) {
        for (Map <String, String> priorityArgs: args) {
            for (String arg : priorityArgs.keySet()) {
                undef(arg);
            }
        }
    }

    @SafeVarargs
    private final void instructions(List<String> instructions , Map <String, String>  ... args) {
        loadArgs(args);

        for (String instruction : instructions) {
            w(instruction);
        }

		unloadArgs(args);
    }

    @Override
    public void global(LibraryUse libraryUse) {
        instructions(libraryUse.getLibrary().getGlobalInstructions(), libraryUse.getArgsValues());
    }

    @Override
    public void setup(LibraryUse libraryUse) {
        instructions(libraryUse.getLibrary().getSetupInstructions(), libraryUse.getArgsValues());
    }

    @Override
    public void setup(MeasureUse measureUse) {
        instructions(measureUse.getMeasure().getSetupInstructions(), measureUse.getLibraryUse().getArgsValues(),
                                                                     measureUse                .getArgsValues());
    }

    @Override
    public void global(MeasureUse measureUse) {
        instructions(measureUse.getMeasure().getGlobalInstructions(), measureUse.getLibraryUse().getArgsValues(),
                                                                      measureUse                .getArgsValues());
    }

    @Override
    public void update(MeasureUse measureUse) {
        instructions(measureUse.getMeasure().getUpdateInstructions(), measureUse.getLibraryUse().getArgsValues(),
                                                                      measureUse                .getArgsValues());
    }

    @Override
    public void expression(MeasureUse measureUse) {
        loadArgs(measureUse.getLibraryUse().getArgsValues(),
				 measureUse                .getArgsValues());

        w(measureUse.getMeasure().getReadExpressionString());

        unloadArgs(measureUse.getLibraryUse().getArgsValues(),
				   measureUse                .getArgsValues());
    }

    @Override
	public void visit(State state) {
		w(String.format("void state_%s() {",state.getName()));
		for(Actionable action: state.getActions()) {
			action.action(this);
		}
		w("  boolean guard = millis() - time > debounce;");
		context.put(CURRENT_STATE, state);

		if (state.getTransition() != null) {
			state.getTransition().accept(this);
		}

		w("}\n");

	}

	@Override
	public void visit(Transition transition) {
		Expression left = transition.getCondition().getLeft();
		Expression right = transition.getCondition().getRight();

		if (left instanceof Updatable) {
			((Updatable) left).update(this);
		}

		if (right instanceof Updatable) {
			((Updatable) right).update(this);
		}

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

	@Override
	public void action(Sleep sleep) {
		w("delay(" + sleep.getDelay() + ");");
	}

}
