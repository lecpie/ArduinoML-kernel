sensor   "button" digitalPin 1
actuator "led"    digitalPin 2
actuator "buzzer" digitalPin 3

state "active"  means led becomes low
state "alarm" means buzzer becomes high
state "safety" means buzzer becomes low and led becomes high

initial active

from active to alarm  when button eq high
from alarm  to safety when button eq high
from safety to active when button eq high

export "MultiStateAlarm"