//https://github.com/Seeed-Studio/Grove_Digital_Light_Sensor

library "TSL2561"
includes "Wire.h" and "Digital_Light_TSL2561.h"
setup "Wire.begin();" and "TSL2561.init();" and "Serial.begin(9600);"

measure "light" typed integer
variables "light"
global "int light;"
update "light = (int) TSL2561.readVisibleLux();" and "Serial.println(light);"
reads "light"
