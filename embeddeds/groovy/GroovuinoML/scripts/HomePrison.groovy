importlib "scripts/GPS.groovy"

uselib "GPS" with "rx" valued "2" and "tx" valued "1"
usemeasure "latitude"
usemeasure "longitude"

actuator "buzzer" digitalPin 12

state "on"  means buzzer becomes high
state "off" means buzzer becomes low

initial off

// Around lab room in polytech south building
from on to off when latitude lower_than   43.615911 and latitude greater_than 43.615107 and longitude lower_than   7.073454 and longitude greater_than 7.072564
from off to on when latitude greater_than 43.615911 or  latitude lower_than   43.615107 or  longitude greater_than 7.073454 or  longitude lower_than   7.072564

export "StayInLabRoom!"