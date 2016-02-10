importlib "scripts/GPS.groovy"

uselib "GPS" with "rx" valued "2" and "tx" valued "1"

usemeasure "latitude" named "latitude"
usemeasure "longitude" named "longitude"

actuator "buzzer" digitalPin 12

state "on"  means buzzer becomes high
state "off" means buzzer becomes low

initial off

from on to off when latitude lower_than 1.0 and longitude greater_than -1.0 and latitude lower_than 1.0 and latitude greater_than -1.0
from off to on when latitude greater_than 1.0 or longitude lower_than -1.0 or latitude greater_than 1.0 or latitude lower_than -1.0

export "HomePrison"