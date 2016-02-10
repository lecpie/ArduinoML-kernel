library "GPS"
includes "SoftwareSerial.h" and "BufferedGPS.h"
requires "rx" and "tx"
variables "gps"
args "baudrate" valued "4800" and "refreshrate" valued "50"
global "MinimalGPS gps(rx, tx, refreshrate);"
setup "gps.begin(baudrate);"

measure "latitude" typed real
variable_based "l" reads "gps.getLatitude();"

measure "longitude" typed real
variable_based "l" reads "gps.getLongitude();"