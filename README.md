# Watchface for Amazfit smartwatch

Thanks Malvarez, Marco Fregapane and Dany for their help.

The only prerequisites to compile it are:

* Add the *HuamiWatchFaces* application as a jar dependency (instructions in the main folder) into the libs folder (app/libs and app/src/main/java/es/LBA97/PSourceInverted/libs). You will also have to modify the build.gradle stored in app
* Use Android SDK 21

This is the source code for the modded Prototype Source. The code is mainly from Malvarez and mine. Dany and Marco Fregapane helped me but I can't share Marco's code because of IP.

The missing code is the following:
* WeatherWidget.java, previously in LBA97Watchfaces/prototype-source-Inverted/app/src/main/java/es/LBA97/PSourceInverted/widget
* Weekdays translation, previously in MainClock.java

Because of this, the watchface will lack of weather and translated weekdays (they will be only in English)

Finally the watch face looks like this:

![Watchface Preview](https://github.com/Nxsaul/AmazfitAPKs/blob/master/LBA97Watchfaces/prototype-source-Inverted/app/src/main/res/drawable-nodpi/preview.png)

