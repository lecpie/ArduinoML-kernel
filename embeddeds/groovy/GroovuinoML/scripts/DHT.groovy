library "DHT"
includes "DHT.h"
requires "dht_pin" and "dht_type"
variables "dht"
global "DHT dht(dht_pin, dht_type);"
setup "dht.begin();" and "Serial.begin(9600);"

measure "temperature" typed integer
global "int t;"
variables "t"
args "celcius" valued "false" and "fahrenheit" valued "false" and "format" valued "celcius"
update "t = (int) dht.readTemperature(format);" and "Serial.println(t);"
reads "t"

measure "humidity" typed integer
global "int h;"
variables "h"
update "h = (int) dht.readHumidity();"
reads "h"
