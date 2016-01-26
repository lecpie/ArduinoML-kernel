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

import java.util.*;

/**
 * Created by lecpie on 1/21/16.
 */
public class SwitchMultiLib {
    public static void main(String[] args) {

        // Declaring libraries
        Library libdht = new Library();

        List<String> dhtvar = Arrays.asList("dht");

        Map<String, String> dhtdefaultargs = new LinkedHashMap<>();

        List <String> dhtrequired = Arrays.asList("dht_pin", "dht_type");

        List <String> dhtincludes = Arrays.asList("DHT.h");
        List <String> dhtglobal = Arrays.asList("DHT dht(dht_pin, dht_type);");
        List <String> dhtsetup = Arrays.asList("dht.begin();");

        libdht.setName("DHT");
        libdht.setIncludes(dhtincludes);
        libdht.setVariables(dhtvar);
        libdht.setDefaultArgs(dhtdefaultargs);
        libdht.setGlobalInstructions(dhtglobal);
        libdht.setSetupInstructions(dhtsetup);
        libdht.setRequiredArgs(dhtrequired);

        // Measures for dht

        // Temperature
        Measure dhttemp = new Measure();

        List <String> dhttempvar = Arrays.asList("temp");

        Map <String, String> dhttempdefaultargs = new LinkedHashMap<>();
        dhttempdefaultargs.put("dht_temp_format_fahr", "true");
        dhttempdefaultargs.put("dht_temp_format_celc", "false");
        dhttempdefaultargs.put("dht_temp_format", "dht_temp_format_celc");

        List <String> dhttempglobal = new ArrayList<>();
        dhttempglobal.add("int temp;");

        List <String> dhttempupdate = new ArrayList<>();
        dhttempupdate.add("temp = (int) dht.readTemperature(dht_temp_format);");

        String dhttempread = "temp";

        dhttemp.setName("temperature");
        dhttemp.setLibrary(libdht);
        dhttemp.setType(Type.INTEGER);
        dhttemp.setVariables(dhttempvar);
        dhttemp.setDefaultArgs(dhttempdefaultargs);
        dhttemp.setGlobalInstructions(dhttempglobal);
        dhttemp.setUpdateInstructions(dhttempupdate);
        dhttemp.setReadExpressionString(dhttempread);

        libdht.getMeasures().put(dhttemp.getName(), dhttemp);

        // Humidity
        Measure dhthum = new Measure();

        List <String> dhthumvar = Arrays.asList("temp");

        List <String> dhthumglobal = new ArrayList<>();
        dhthumglobal.add("int temp;");

        List <String> dhthumupdate = new ArrayList<>();
        dhthumupdate.add("temp = (int) dht.readHumidity();");

        String dhthumread = "temp";

        dhthum.setName("humidity");
        dhthum.setLibrary(libdht);
        dhthum.setType(Type.INTEGER);
        dhthum.setVariables(dhttempvar);
        dhthum.setGlobalInstructions(dhthumglobal);
        dhthum.setUpdateInstructions(dhthumupdate);
        dhthum.setReadExpressionString(dhthumread);

        libdht.getMeasures().put(dhthum.getName(), dhthum);

        // Digital light I2C

        Library groovelightsensor = new Library();

        groovelightsensor.setIncludes(Arrays.asList("Wire.h", "Digital_Light_TSL2561.h"));
        groovelightsensor.setName("GROOVELIGHTSENSOR");
        groovelightsensor.setSetupInstructions(Arrays.asList("Wire.begin();"));

        Measure groovelight = new Measure();
        groovelight.setLibrary(groovelightsensor);
        groovelight.setVariables(Arrays.asList("light"));
        groovelight.setType(Type.INTEGER);
        groovelight.setGlobalInstructions(Arrays.asList("int light;"));
        groovelight.setUpdateInstructions(Arrays.asList("light = (int) TSL2561.readVisibleLux();"));
        groovelight.setReadExpressionString("light");
        groovelight.setName("light");
        groovelightsensor.getMeasures().put("light", groovelight);

        Library libhp20x = new Library();
        libhp20x.setName("HP20x");
        libhp20x.setIncludes(Arrays.asList("HP20x_dev.h","Arduino.h","Wire.h"));
        libhp20x.setSetupInstructions(Arrays.asList("delay(150);", "HP20x.begin();", "delay(100);"));

        Measure hp20temp = new Measure();
        hp20temp.setName("temperature");
        hp20temp.setLibrary(libhp20x);
        hp20temp.setType(Type.INTEGER);
        hp20temp.setVariables(Arrays.asList("temp"));
        hp20temp.setGlobalInstructions(Arrays.asList("int temp;"));
        hp20temp.setUpdateInstructions(Arrays.asList("temp = (int) HP20x.ReadTemperature();"));
        hp20temp.setReadExpressionString("temp");
        libhp20x.getMeasures().put(hp20temp.getName(), hp20temp);

        Measure hp20alt = new Measure();
        hp20alt.setLibrary(libhp20x);
        hp20alt.setName("altitude");
        hp20alt.setType(Type.INTEGER);
        hp20alt.setVariables(Arrays.asList("alt"));
        hp20alt.setGlobalInstructions(Arrays.asList("int alt;"));
        hp20alt.setUpdateInstructions(Arrays.asList("alt = (int) HP20x.ReadAltitude();"));
        hp20alt.setReadExpressionString("alt");
        libhp20x.getMeasures().put(hp20alt.getName(), hp20alt);

        Measure hp20pres = new Measure();
        hp20pres.setLibrary(libhp20x);
        hp20pres.setName("pressure");
        hp20pres.setType(Type.INTEGER);
        hp20pres.setVariables(Arrays.asList("pres"));
        hp20pres.setGlobalInstructions(Arrays.asList("int pres;"));
        hp20pres.setUpdateInstructions(Arrays.asList("pres = (int) HP20x.ReadPressure();"));
        hp20pres.setReadExpressionString("pres");
        libhp20x.getMeasures().put(hp20pres.getName(), hp20pres);

        // Transition from library definition to ArduinoML
        Map <String, Library> librariestoload = new HashMap<>();
        librariestoload.put(libdht.getName(), libdht);
        librariestoload.put(groovelightsensor.getName(), groovelightsensor);
        librariestoload.put(libhp20x.getName(), libhp20x);

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

        Library lightloaded = theSwitch.getLoadedLibraries().get("GROOVELIGHTSENSOR");

        LibraryUse usedlight = new LibraryUse();
        usedlight.setLibrary(lightloaded);
        theSwitch.getUsedLibraries().add(usedlight);

        Library hp20loaded = theSwitch.getLoadedLibraries().get("HP20x");

        LibraryUse usedhp20 = new LibraryUse();
        usedhp20.setLibrary(hp20loaded);
        theSwitch.getUsedLibraries().add(usedhp20);

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

        // other used measures

        MeasureUse hptemp = new MeasureUse();
        hptemp.setName("temperature_redundancy");
        hptemp.setLibraryUse(usedhp20);
        hptemp.setMeasure(hp20loaded.getMeasures().get("temperature"));

        MeasureUse hpalt = new MeasureUse();
        hpalt.setName("altitude");
        hpalt.setLibraryUse(usedhp20);
        hpalt.setMeasure(hp20loaded.getMeasures().get("altitude"));

        MeasureUse hppres = new MeasureUse();
        hppres.setName("pressure");
        hppres.setLibraryUse(usedhp20);
        hppres.setMeasure(hp20loaded.getMeasures().get("pressure"));

        MeasureUse light = new MeasureUse();
        light.setName("light");
        light.setLibraryUse(usedlight);
        light.setMeasure(lightloaded.getMeasures().get("light"));

        // Declaring states
        State on = new State();
        on.setName("on");

        State humid = new State();
        humid.setName("humid");

        State coldincoming = new State();
        coldincoming.setName("coldincoming");

        State feelhigh = new State();
        feelhigh.setName("feelhigh");

        State sick = new State();
        sick.setName("sick");

        State blinded = new State();
        blinded.setName("blinded");

        State rememberwork = new State();
        rememberwork.setName("rememberwork");

        State recovery = new State();
        recovery.setName("recovery");

        State sleep = new State();
        sleep.setName("sleep");

        // Creating actions
        Action normalmood = new Action();
        normalmood.setActuator(mood);
        normalmood.setSignalExpression(new IntegerExpression(500));

        Action humidmood = new Action();
        humidmood.setActuator(mood);
        humidmood.setSignalExpression(new IntegerExpression(400));

        Action gettincoldmood = new Action();
        gettincoldmood.setActuator(mood);
        gettincoldmood.setSignalExpression(new IntegerExpression(200));

        Action feelinhighmood = new Action();
        feelinhighmood.setActuator(mood);
        feelinhighmood.setSignalExpression(new IntegerExpression(150));

        Action sickmood = new Action();
        sickmood.setActuator(mood);
        sickmood.setSignalExpression(new IntegerExpression(50));

        Action blindedmood = new Action();
        blindedmood.setActuator(mood);
        blindedmood.setSignalExpression(new IntegerExpression(300));

        Action pressuredmood = new Action();
        pressuredmood.setActuator(mood);
        pressuredmood.setSignalExpression(new IntegerExpression(230));

        Action recoveringmood = new Action();
        recoveringmood.setActuator(mood);
        recoveringmood.setSignalExpression(new IntegerExpression(75));

        Action sleepmood = new Action();
        sleepmood.setActuator(mood);
        sleepmood.setSignalExpression(new IntegerExpression(0));

        Action rememberworkmood = new Action();
        rememberworkmood.setActuator(mood);
        rememberworkmood.setSignalExpression(new IntegerExpression(1000));

        // Binding actions to states

        on.setActions(Arrays.asList(normalmood));
        humid.setActions(Arrays.asList(humidmood));
        coldincoming.setActions(Arrays.asList(gettincoldmood));
        feelhigh.setActions(Arrays.asList(feelinhighmood));
        sick.setActions(Arrays.asList(sickmood));
        blinded.setActions(Arrays.asList(blindedmood));
        rememberwork.setActions(Arrays.asList(rememberworkmood));
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

        Condition confirmedlowtempeature = new Condition();
        confirmedlowtempeature.setLeft(hptemp);
        confirmedlowtempeature.setOperator(Operator.LT);
        confirmedlowtempeature.setRight(new IntegerExpression(10));

        Condition normaltemperature = new Condition();
        normaltemperature.setLeft(tempfahr); // Americains define what is normal
        normaltemperature.setOperator(Operator.GT);
        normaltemperature.setRight(new IntegerExpression(53));

        Condition normalhumidity = new Condition();
        normalhumidity.setLeft(hum);
        normalhumidity.setOperator(Operator.LT);
        normalhumidity.setRight(new IntegerExpression(90));

        Condition highlight = new Condition();
        highlight.setLeft(light);
        highlight.setOperator(Operator.GT);
        highlight.setRight(new IntegerExpression(80));

        Condition lowlight = new Condition();
        lowlight.setLeft(light);
        lowlight.setOperator(Operator.LT);
        lowlight.setRight(new IntegerExpression(10));

        Condition highpressure = new Condition();
        highpressure.setLeft(hppres);
        highpressure.setOperator(Operator.GT);
        highpressure.setRight(new IntegerExpression(120));

        Condition highaltitude = new Condition();
        highaltitude.setLeft(hpalt);
        highaltitude.setOperator(Operator.GT);
        highaltitude.setRight(new IntegerExpression(10000));

        // Creating transitions

        Transition on2humid = new Transition();
        on2humid.setNext(humid);
        on2humid.setCondition(highhumidity);

        Transition humid2coldincoming = new Transition();
        humid2coldincoming.setNext(coldincoming);
        humid2coldincoming.setCondition(lowtemperature);

        Transition coldincoming2high = new Transition();
        coldincoming2high.setNext(feelhigh);
        coldincoming2high.setCondition(highaltitude);

        Transition high2sick = new Transition();
        high2sick.setNext(sick);
        high2sick.setCondition(confirmedlowtempeature);

        Transition sick2blinded = new Transition();
        sick2blinded.setNext(blinded);
        sick2blinded.setCondition(highlight);

        Transition blind2pres = new Transition();
        blind2pres.setNext(rememberwork);
        blind2pres.setCondition(highpressure);

        Transition pres2recover = new Transition();
        pres2recover.setNext(recovery);
        pres2recover.setCondition(normaltemperature);

        Transition recover2sleep = new Transition();
        recover2sleep.setNext(sleep);
        recover2sleep.setCondition(lowlight);

        Transition recovering2ok = new Transition();
        recovering2ok.setNext(on);
        recovering2ok.setCondition(normalhumidity);

        // Binding transitions to states
        on.setTransition(on2humid);
        humid.setTransition(humid2coldincoming);
        coldincoming.setTransition(coldincoming2high);
        feelhigh.setTransition(high2sick);
        sick.setTransition(sick2blinded);
        blinded.setTransition(blind2pres);
        rememberwork.setTransition(pres2recover);
        recovery.setTransition(recover2sleep);
        sleep.setTransition(recovering2ok);

        // Building the App
        theSwitch.setName("Switch!");
        theSwitch.setBricks(Arrays.asList(mood, tempcelc, tempfahr, hum, light, hpalt, hppres, hptemp));
        theSwitch.setStates(Arrays.asList(on, humid, sick, recovery, blinded, coldincoming, feelhigh, rememberwork, sleep));
        theSwitch.setInitial(on);

        // Generating Code
        Visitor codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);

        // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());
    }
}
