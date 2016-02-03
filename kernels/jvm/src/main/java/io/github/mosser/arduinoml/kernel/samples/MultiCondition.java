package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.PinnedActuator;
import io.github.mosser.arduinoml.kernel.structural.PinnedSensor;

import java.util.Arrays;

/**
 * Created by lecpie on 1/30/16.
 */
public class MultiCondition {
    public static void main (String[] args) {

        // Declaring elementary bricks
        PinnedSensor button = new PinnedSensor();
        button.setName("button");
        button.setPin(9);

        PinnedSensor button2 = new PinnedSensor();
        button2.setName("button2");
        button2.setPin(3);

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

        Action switchTheLightOff = new Action();
        switchTheLightOff.setActuator(led);
        switchTheLightOff.setSignalExpression(new DigitalExpression(false));

        Sleep sleep = new Sleep();
        sleep.setDelay(1000);

        // Binding actions to states
        on.setActions(Arrays.asList(switchTheLightOn, sleep));
        off.setActions(Arrays.asList(switchTheLightOff));

        // Creating transitions
        ConditionTree sensorlow = new ConditionTree();
        sensorlow.setLeft(button);
        sensorlow.setOperator(Operator.EQ);
        sensorlow.setRight(new DigitalExpression(false));

        Condition secondSensorLow = new Condition();
        secondSensorLow.setLeft(button2);
        secondSensorLow.setOperator(Operator.EQ);
        secondSensorLow.setRight(new DigitalExpression(true));

        sensorlow.setNextOperator(BinaryOperator.AND);
        sensorlow.setNext(secondSensorLow);

        Transition on2off = new Transition();
        on2off.setNext(off);
        on2off.setCondition(sensorlow);

        ConditionTree sensorhigh = new ConditionTree();
        sensorhigh.setLeft(button);
        sensorhigh.setOperator(Operator.EQ);
        sensorhigh.setRight(new DigitalExpression(true));

        Condition secondsensorhigh = new Condition();
        secondsensorhigh.setLeft(button2);
        secondsensorhigh.setOperator(Operator.EQ);
        secondsensorhigh.setRight(new DigitalExpression(true));

        sensorhigh.setNextOperator(BinaryOperator.OR);
        sensorhigh.setNext(secondsensorhigh);

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
        theSwitch.setBricks(Arrays.asList(button, led, button2));
        theSwitch.setStates(Arrays.asList(on, off));
        theSwitch.setInitial(off);

        // Generating Code
        Visitor codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);

        // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());
    }

}
