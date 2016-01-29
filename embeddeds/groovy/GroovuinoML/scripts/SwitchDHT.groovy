importlib "scripts/DHT.groovy"

uselib "DHT" with "dht_type" valued "DHT11" and "dht_pin" valued "A3"
usemeasure "temperature" named "thetemperature"

actuator "led1" digitalPin 9

state "on" means led1 becomes high
state "off" means led1 becomes low

initial off

from on to off when thetemperature greater_than 28
from off to on when thetemperature lower_than 27

export "UseLibs!"