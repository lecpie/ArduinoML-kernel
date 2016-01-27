/**
 * Created by lecpie on 1/26/16.
 */

deflib "TSL2561"
includes "Wire.h" and "Digital_Light_TSL2561.h"
setup "Wire.begin();"

defmeasure "light"
variables "light"
global "int light;"
update "light = (int) TSL2561.readVisibleLux();"
reads "light"

/*
withlib() includes "Wire.h" and "Digital_Light_TSL2561.h"
withlib() setup "Wire.begin();"
exportlib "TSL2561"

withmeasure() type("INTEGER")
withmeasure() variables "light"
withmeasure() global "int light;"
withmeasure() update "light = (int) TSL2561.readVisibleLux();"
withmeasure() read "light"
exportmeasure "light"

associate "light","TSL2561"
*/