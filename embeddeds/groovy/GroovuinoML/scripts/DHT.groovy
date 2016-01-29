deflib "DHT"
includes "DHT.h"
//required dht_pin and dht_type
variables "dht"
global "DHT dht(dht_pin, dht_type);"
setup "dht.begin();" and "Serial.begin(9600);"

defmeasure "temperature"
global "int t;"
variables "t"
update "t = (int) dht.readTemperature();" and "Serial.println(t);"
reads "t"

defmeasure "humidity"
global "int h;"
variables "h"
update "h = (int) dht.readHumidity();"
reads "h"
