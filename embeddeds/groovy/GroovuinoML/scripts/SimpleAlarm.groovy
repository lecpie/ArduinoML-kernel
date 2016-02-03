sensor "button"   digitalPin 1
actuator "led"    digitalPin 2
actuator "buzzer" digitalPin 3

state "on"  means led becomes high and buzzer becomes high
state "off" means led becomes low  and buzzer becomes low

initial off

from  on to off when button eq high
from off to  on when button eq low


export "SimpleAlarm"