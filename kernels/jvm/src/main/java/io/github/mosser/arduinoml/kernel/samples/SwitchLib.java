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
        dhttempdefaultargs.put("dht_temp_name", "MEASURE_NAME(dht_obj_name,dht_temp_name,arduinoml_measure_instance)");
        dhttempdefaultargs.put("dht_temp_format_fahr", "true");
        dhttempdefaultargs.put("dht_temp_format_celc", "false");
        dhttempdefaultargs.put("dht_temp_format", "dht_temp_format_celc");
        dhttempdefaultargs.put("arduinoml_measure_instance","0");

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

        PinnedActuator mood = new PinnedActuator();
        mood.setName("mood");
        mood.setPin(9);
        mood.setAnalogMode(true);

        Library dhtloaded = theSwitch.getLoadedLibraries().get("DHT");

        LibraryUse usedht = new LibraryUse();
        usedht.setLibrary(dhtloaded);
        theSwitch.getUsedLibraries().add(usedht);

        // Type and pin for one dht sensor using one instance of the DHT library

        usedht.getArgsValues().put("dht_pin", "9");
        usedht.getArgsValues().put("dht_type", "DHT11");

        // Used measures for this dht

        MeasureUse tempcelc = new MeasureUse();
        tempcelc.setName("temperature_celcius");
        tempcelc.setLibraryUse(usedht);
        tempcelc.setMeasure(dhtloaded.getMeasures().get("temperature"));

        MeasureUse tempfahr = new MeasureUse();
        tempfahr.setName("temperature_fahr");
        tempfahr.setLibraryUse(usedht);
        tempfahr.setMeasure(dhtloaded.getMeasures().get("temperature"));
        tempfahr.getArgsValues().put("format", "dht_temp_format_fahr");

        MeasureUse hum = new MeasureUse();
        hum.setName("humidity");
        hum.setLibraryUse(usedht);
        hum.setMeasure(dhtloaded.getMeasures().get("humidity"));

        // Declaring states
        State on = new State();
        on.setName("on");

        State humid = new State();
        humid.setName("humid");

        State sick = new State();
        sick.setName("sick");

        State recovery = new State();
        recovery.setName("recovery");



        // Creating actions
        Action normalmood = new Action();
        normalmood.setActuator(mood);
        normalmood.setSignalExpression(new IntegerExpression(500));

        Action humidmood = new Action();
        humidmood.setActuator(mood);
        humidmood.setSignalExpression(new IntegerExpression(400));

        Action sickmood = new Action();
        sickmood.setActuator(mood);
        sickmood.setSignalExpression(new IntegerExpression(50));

        Action recoveringmood = new Action();
        recoveringmood.setActuator(mood);
        recoveringmood.setSignalExpression(new IntegerExpression(150));

        // Binding actions to states

        on.setActions(Arrays.asList(normalmood));
        humid.setActions(Arrays.asList(humidmood));
        sick.setActions(Arrays.asList(sickmood));
        recovery.setActions(Arrays.asList(recoveringmood));

        // Defining conditions

        Condition highhumidity = new Condition();
        highhumidity.setLeft(hum);
        highhumidity.setOperator(Operator.GT);
        highhumidity.setRight(new IntegerExpression(100)); // No idea what the measured data means yet

        Condition lowtemperature = new Condition();
        lowtemperature.setLeft(tempcelc); // Europeans complain
        lowtemperature.setOperator(Operator.LT);
        lowtemperature.setRight(new IntegerExpression(10));

        Condition normaltemperature = new Condition();
        normaltemperature.setLeft(tempfahr); // Americains define what is normal
        normaltemperature.setOperator(Operator.GT);
        normaltemperature.setRight(new IntegerExpression(53));

        Condition normalhumidity = new Condition();
        normalhumidity.setLeft(hum);
        normalhumidity.setOperator(Operator.LT);
        normalhumidity.setRight(new IntegerExpression(90));

        // Creating transitions

        Transition on2humid = new Transition();
        on2humid.setNext(humid);
        on2humid.setCondition(highhumidity);

        Transition humid2sick = new Transition();
        humid2sick.setNext(sick);
        humid2sick.setCondition(lowtemperature);

        Transition sick2recovering = new Transition();
        sick2recovering.setNext(recovery);
        sick2recovering.setCondition(normaltemperature);

        Transition recovering2ok = new Transition();
        recovering2ok.setNext(on);
        recovering2ok.setCondition(normalhumidity);

        // Binding transitions to states
        on.setTransition(on2humid);
        humid.setTransition(humid2sick);
        sick.setTransition(sick2recovering);
        recovery.setTransition(recovering2ok);

        // Building the App
        theSwitch.setName("Switch!");
        theSwitch.setBricks(Arrays.asList(mood, tempcelc, tempfahr, hum));
        theSwitch.setStates(Arrays.asList(on, humid, sick, recovery));
        theSwitch.setInitial(on);

        // Generating Code
        Visitor codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);

        // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());
    }

}
