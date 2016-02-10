importlib "scripts/DHT.groovy"

uselib "DHT" with "dht_type" valued "DHT11" and "dht_pin" valued "A3"
usemeasure "temperature" named "temp1"

uselib "DHT" with "dht_type" valued "DHT11" and "dht_pin" valued "A2"
usemeasure "temperature" named "temp2"

actuator "led1" digitalPin 9

state "on" means led1 becomes high
state "off" means led1 becomes low

initial off

from on to off when temp1 greater_than 28 and temp2 greater_than 28
from off to on when temp1 lower_than   27 and temp2 lower_than   27

export "UseLibs!"