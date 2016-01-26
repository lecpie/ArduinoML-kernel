sensor "button" digitalPin 9
actuator "led1" analogPin 12
actuator "led2" digitalPin 13
actuator "led3" analogPin 14

state "on" means led1 becomes 250
state "off" means led1 becomes 500 and led2 becomes low and led3 becomes 0

initial off

from on to off when button becomes high
from off to on when button becomes high

export "UseLibs!"