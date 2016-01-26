/**
 * Created by fofo on 20/01/16.
 */
withlib()  setup "DHTsetup1" and "DHTsetup2" and "DHTsetup3"
withlib() args "DHTargKey1","DHTargVal1" and "DHTargKey2","DHTargVal2"
withlib() includes "DHTinclude1" and "DHTinclude2"
withlib() global "DHTglobal1" and "DHTglobal2"
withlib() before "DHTbefore1" and "DHTbefore2"
exportlib "DHT"

withlib() setup "DHT2setup1" and "DHT2setup2" and "DHT2setup3"
withlib() args "DHT2argKey1","DHT2argVal1" and "DHT2argKey2","DHT2argVal2"
withlib() includes "DHT2include1" and "DHT2include2"
withlib() variables "var1" and "var2"
withlib() global "DHT2global1" and "DHT2global2"
withlib() before "DHT2before1" and "DHT2before2"
exportlib "DHT2"

withlib() setup "DHT3setup1" and "DHT3setup2" and "DHT3setup3"
withlib() args "DHT3argKey1","argVal1" and "DHT3argKey2","DHT3argVal2"
withlib() includes "DHT3include1" and "DHT3include2"
withlib() global "DHT3global1" and "DHT3global2"
withlib() before "DHT3before1" and "DHT3before2"
exportlib "DHT3"

withmeasure() type("REAL")
withmeasure() setup "Measure1setup1" and "Measure1setup2"
withmeasure() args "Measure1args1","Measure1args2" and "Measure1args3","Measure1args4"
withmeasure() global "Measure1global1" and "Measure1global2" and "Measure1global3"
withmeasure() update "Measure1update1" and "Measure1update2" and "Measure1update3"
withmeasure() read "Measure1read1"
exportmeasure "Measure1"

withmeasure() type("REAL")
withmeasure() setup "Measure2setup1" and "Measure2setup2"
withmeasure() args "Measure2args1","Measure2args2" and "Measure2args3","Measure2args4"
withmeasure() global "Measure2global1" and "Measure2global2" and "Measure2global3"
withmeasure() update "Measure2update1" and "Measure2update2" and "Measure2update3"
withmeasure() read "Measure2read1"
exportmeasure "Measure2"

withmeasure() type("REAL")
withmeasure() setup "Measure3setup1" and "Measure3setup2"
withmeasure() args "Measure3args1","Measure3args2" and "Measure3args3","Measure3args4"
withmeasure() global "Measure3global1" and "Measure3global2" and "Measure3global3"
withmeasure() update "Measure3update1" and "Measure3update2" and "Measure3update3"
withmeasure() read "Measure3read1"
exportmeasure "Measure3"

associate "Measure1","DHT"
associate "Measure3","DHT2"
associate "Measure2","DHT3"


