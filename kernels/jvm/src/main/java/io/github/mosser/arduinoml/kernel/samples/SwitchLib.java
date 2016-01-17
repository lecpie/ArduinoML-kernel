package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.lib.Library;
import io.github.mosser.arduinoml.kernel.lib.LibraryUse;
import io.github.mosser.arduinoml.kernel.lib.Measure;
import io.github.mosser.arduinoml.kernel.lib.MeasureUse;
import io.github.mosser.arduinoml.kernel.structural.PinnedActuator;
import io.github.mosser.arduinoml.kernel.structural.PinnedSensor;

import java.util.*;

/**
 * Created by lecpie on 1/16/16.
 */
public class SwitchLib {
    public static void main(String[] args) {

        // Declaring libraries
        Library libdht = new Library();

        List <String> dhtincludes = new ArrayList<>();
        dhtincludes.add("DHT.h");

        Map <String, String> dhtdefaultargs = new LinkedHashMap<>();
        dhtdefaultargs.put("dht_obj_name", "dht");

        List <String> dhtglobal = new ArrayList<>();
        dhtglobal.add("DHT dht_obj_name(dht_pin, dht_type);");

        List <String> dhtsetup = new ArrayList<>();
        dhtsetup.add("dht_obj_name.begin();");

        libdht.setIncludes(dhtincludes);
        libdht.setDefaultArgs(dhtdefaultargs);
        libdht.setGlobalInstructions(dhtglobal);
        libdht.setSetupInstructions(dhtsetup);

        // Measures for dht

        // Temperature
        Measure dhttemp = new Measure();

        Map <String, String> dhttempdefaultargs = new LinkedHashMap<>();
        dhttempdefaultargs.put("dht_temp_name", "dht_temp");
        dhttempdefaultargs.put("dht_temp_format_fahr", "true");
        dhttempdefaultargs.put("dht_temp_format_celc", "false");
        dhttempdefaultargs.put("dht_temp_format", "dht_temp_format_celc");

        List <String> dhttempglobal = new ArrayList<>();
        dhttempglobal.add("int dht_temp_name;");

        List <String> dhttempupdate = new ArrayList<>();
        dhttempupdate.add("dht_temp_name = dht_obj_name.readTemperature(dht_temp_format);");

        String dhttempread = "dht_temp_name";

        dhttemp.setName("temperature");
        dhttemp.setLibrary(libdht);
        dhttemp.setType(Type.INTEGER);
        dhttemp.setDefaultArgs(dhttempdefaultargs);
        dhttemp.setGlobalInstructions(dhttempglobal);
        dhttemp.setUpdateInstructions(dhttempupdate);
        dhttemp.setReadExpressionString(dhttempread);

        libdht.getMeasures().put(dhttemp.getName(), dhttemp);

        // Humidity
        Measure dhthum = new Measure();

        Map <String, String> dhthumdefaultargs = new LinkedHashMap<>();
        dhthumdefaultargs.put("dht_hum_name","dht_hum");

        List <String> dhthumglobal = new ArrayList<>();
        dhthumglobal.add("int dht_hum_name;");

        List <String> dhthumupdate = new ArrayList<>();
        dhthumupdate.add("dht_hum_name = dht_obj_name.readHumidity();");

        String dhthumread = "dht_hum_name";

        dhthum.setName("humidity");
        dhthum.setLibrary(libdht);
        dhthum.setType(Type.INTEGER);
        dhthum.setDefaultArgs(dhthumdefaultargs);
        dhthum.setGlobalInstructions(dhthumglobal);
        dhthum.setUpdateInstructions(dhthumupdate);
        dhthum.setReadExpressionString(dhthumread);

        libdht.getMeasures().put(dhthum.getName(), dhthum);

        // Transition from library definition to ArduinoML
        Map <String, Library> librariestoload = new HashMap<>();
        librariestoload.put("DHT", libdht);

        // ArduinoML configuration
        App theSwitch = new App();
        theSwitch.setLoadedLibraries(librariestoload);

        // Declaring elementary bricks
        PinnedSensor button = new PinnedSensor();
        button.setName("button");
        button.setPin(9);

        PinnedActuator led = new PinnedActuator();
        led.setName("LED");
        led.setPin(12);

        LibraryUse usedht = new LibraryUse();

        Library dhtloaded = theSwitch.getLoadedLibraries().get("DHT");

        usedht.setLibrary(dhtloaded);

        usedht.getArgsValues().put("dht_pin", "9");
        usedht.getArgsValues().put("dht_type", "DHT11");

        MeasureUse temp = new MeasureUse();
        temp.setName("temperature");
        temp.setLibraryUse(usedht);

        temp.setMeasure(dhtloaded.getMeasures().get("temperature"));

        theSwitch.getUsedLibraries().add(usedht);

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

        // Binding actions to states
        on.setActions(Arrays.asList(switchTheLightOn));
        off.setActions(Arrays.asList(switchTheLightOff));

        // Creating transitions
        Condition sensorlow = new Condition();
        sensorlow.setLeft(temp);
        sensorlow.setOperator(Operator.GT);
        sensorlow.setRight(new IntegerExpression(35));

        Transition on2off = new Transition();
        on2off.setNext(off);
        on2off.setCondition(sensorlow);

        Condition sensorhigh = new Condition();
        sensorhigh.setLeft(temp);
        sensorhigh.setOperator(Operator.LT);
        sensorhigh.setRight(new IntegerExpression(32));

        Transition off2on = new Transition();
        off2on.setNext(on);
        off2on.setCondition(sensorhigh);

        // Binding transitions to states
        on.setTransition(on2off);
        off.setTransition(off2on);

        // Building the App
        theSwitch.setName("Switch!");
        theSwitch.setBricks(Arrays.asList(button, led, temp ));
        theSwitch.setStates(Arrays.asList(on, off));
        theSwitch.setInitial(off);

        // Generating Code
        Visitor codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);

        // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());
    }

}
