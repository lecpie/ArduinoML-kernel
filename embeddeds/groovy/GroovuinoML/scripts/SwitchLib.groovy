importlib "scripts/TSL2561.groovy"

uselib "TSL2561"
usemeasure "light" named "thelight"

sensor "button" digitalPin 9
actuator "led1" analogPin 12
actuator "led2" digitalPin 13
actuator "led3" analogPin 14

state "on" means led1 becomes 250
state "off" means led1 becomes 500 and led2 becomes low and led3 becomes 0

initial off

from on to off when thelight EQ high
from off to on when button EQ high

export "UseLibs!"