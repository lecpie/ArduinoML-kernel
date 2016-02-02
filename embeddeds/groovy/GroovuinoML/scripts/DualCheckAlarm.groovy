sensor   "button1" digitalPin 1
sensor "button2" digitalPin 2
actuator "buzzer"  digitalPin 3


state "on"  means buzzer becomes high
state "off" means buzzer becomes low

initial off

from off to on when button1 eq high and button2 eq high
from on to off when button1 eq low   or button2 eq low

export "DuakCheckAlarm"