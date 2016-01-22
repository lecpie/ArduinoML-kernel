/**
 * Created by fofo on 20/01/16.
 */
withlib().setup "setup1" and "setup2" and "setup3"
withlib().args "argKey1","argVal1" and "argKey2","argVal2"
withlib().includes "include1" and "include2"
withlib().global "global1" and "global2"
withlib().before "before1" and "before2"
exportlib "DHT"

withlib().setup "setup1" and "setup2" and "setup3"
withlib().args "argKey1","argVal1" and "argKey2","argVal2"
withlib().includes "include1" and "include2"
withlib().global "global1" and "global2"
withlib().before "before1" and "before2"
exportlib "DHT2"

withlib().setup "setup1" and "setup2" and "setup3"
withlib().args "argKey1","argVal1" and "argKey2","argVal2"
withlib().includes "include1" and "include2"
withlib().global "global1" and "global2"
withlib().before "before1" and "before2"
exportlib "DHT3"

withmeasure().type("REAL")
withmeasure().setup "setup1" and "setup2"
withmeasure().args "args1","args2" and "args3","args4"
withmeasure().global "global1" and "global2" and "global3"
withmeasure().update "update1" and "update2" and "update3"
withmeasure().read "read1"
exportmeasure "Measure1"

withmeasure().type("REAL")
withmeasure().setup "setup1" and "setup2"
withmeasure().args "args1","args2" and "args3","args4"
withmeasure().global "global1" and "global2" and "global3"
withmeasure().update "update1" and "update2" and "update3"
withmeasure().read "read1"
exportmeasure "Measure2"

withmeasure().type("REAL")
withmeasure().setup "setup1" and "setup2"
withmeasure().args "args1","args2" and "args3","args4"
withmeasure().global "global1" and "global2" and "global3"
withmeasure().update "update1" and "update2" and "update3"
withmeasure().read "read1"
exportmeasure "Measure3"

associate "Measure1","DHT"
associate "Measure3","DHT2"
associate "Measure2","DHT3"


