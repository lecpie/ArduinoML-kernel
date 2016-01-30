sensor   "button1" digitalPin 1
actuator "button2" digitalPin 2
actuator "led"     digitalPin 3

state "on"  means led becomes high
state "off" means led becomes low

initial off

from on to off when button eq high
from on to off when button eq high

export "StatedBasedAlarm"