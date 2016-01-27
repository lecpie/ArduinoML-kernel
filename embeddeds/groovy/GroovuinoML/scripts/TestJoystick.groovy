sensor "x" analogPin 3
sensor "y" analogPin 4
sensor "button" analogPin 2
actuator "led1" digitalPin 9

state "on" means led1 becomes high
state "off" means led1 becomes low

initial off

from on to off when x greater_than 512
from off to on when x lower_than 511

export("Joystick")