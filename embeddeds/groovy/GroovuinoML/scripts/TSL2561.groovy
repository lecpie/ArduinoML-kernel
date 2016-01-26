/**
 * Created by lecpie on 1/26/16.
 */
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
