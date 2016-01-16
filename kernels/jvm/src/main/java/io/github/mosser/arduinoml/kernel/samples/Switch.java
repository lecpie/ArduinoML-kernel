package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.Arrays;

public class Switch {

	public static void main(String[] args) {

		// Declaring elementary bricks
		PinnedSensor button = new PinnedSensor();
		button.setName("button");
		button.setPin(9);

		PinnedActuator led = new PinnedActuator();
		led.setName("LED");
		led.setPin(12);

		// Declaring states
		State on = new State();
		on.setName("on");

		State off = new State();
		off.setName("off");

		// Creating actions
		Action switchTheLightOn = new Action();
		switchTheLightOn.setActuator(led);
		switchTheLightOn.setSignalExpression(new DigitalExpression(true));
		//switchTheLightOn.setValue(SIGNAL.HIGH);

		Action switchTheLightOff = new Action();
		switchTheLightOff.setActuator(led);
		switchTheLightOff.setSignalExpression(new DigitalExpression(false));
		//switchTheLightOff.setValue(SIGNAL.LOW);

		// Binding actions to states
		on.setActions(Arrays.asList(switchTheLightOn));
		off.setActions(Arrays.asList(switchTheLightOff));

		// Creating transitions
		Condition sensorlow = new Condition();
		sensorlow.setLeft(button);
		sensorlow.setOperator(Operator.EQ);
		sensorlow.setRight(new DigitalExpression(true));

		Transition on2off = new Transition();
		on2off.setNext(off);
		on2off.setCondition(sensorlow);
		//on2off.setSensor(button);
		//on2off.setValue(SIGNAL.HIGH);

		Condition sensorhigh = new Condition();
		sensorhigh.setLeft(button);
		sensorhigh.setOperator(Operator.EQ);
		sensorhigh.setRight(new DigitalExpression(false));

		Transition off2on = new Transition();
		off2on.setNext(on);
		off2on.setCondition(sensorhigh);

		//off2on.setSensor(button);
		//off2on.setValue(SIGNAL.HIGH);

		// Binding transitions to states
		on.setTransition(on2off);
		off.setTransition(off2on);

		// Building the App
		App theSwitch = new App();
		theSwitch.setName("Switch!");
		theSwitch.setBricks(Arrays.asList(button, led ));
		theSwitch.setStates(Arrays.asList(on, off));
		theSwitch.setInitial(off);

		// Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);

		// Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}
