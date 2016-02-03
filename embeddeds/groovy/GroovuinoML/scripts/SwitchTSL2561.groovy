importlib "scripts/TSL2561.groovy"

uselib "TSL2561"
usemeasure "light" named "thelight"

actuator "led1" digitalPin 12

state "on" means led1 becomes high
state "off" means led1 becomes low

initial off

from on to off when thelight greater_than 1
from off to on when thelight lower_than 10

export "UseLibs!"