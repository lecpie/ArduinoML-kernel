sensor   "button" digitalPin 1
actuator "led"    digitalPin 2

state "on"  means led becomes high
state "off" means led becomes high

initial off

from on to off when button eq high
from on to off when button eq high

export "StatedBasedAlarm"