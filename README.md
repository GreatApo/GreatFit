[![latest release](https://img.shields.io/github/release/GreatApo/GreatFit.svg?colorB=green&label=latest%20release&style=flat-square) ![release date](https://img.shields.io/badge/release%20date-2019.08.26-orange.svg?style=flat-square) ![Downloads](https://img.shields.io/github/downloads/GreatApo/GreatFit/total.svg?style=flat-square) ![HitCount](http://hits.dwyl.io/GreatApo/GreatFit.svg)](https://github.com/GreatApo/GreatFit/releases/latest)
# GreatFit: Amazfit Pace/Stratos/Verge APK Watchface sources
![GreatFit Watchface Banner](other/images/1.jpg)

ONE WATCHFACE TO RULE THEM ALL!

XDA Topic [here](https://forum.xda-developers.com/smartwatch/amazfit/app-watchface-greatfit-v1-1-settings-t3791516)


### Features
- This is a Pace/Stratos/Verge APK watchface
- Custom apk watchface with open sources
- Supports 21 languages: English, Bulgarian, Chinese, Croatian, Czech, Dutch, French, German, Greek, Hebrew, Hungarian, Italian, Japanese, Polish, Portuguese, Romanian, Russian, Slovak, Spanish, Thai, Turkish
- Ability to change widgets/progress bars
- Seconds are enabled based on your system settings (refresh the watchface)
- More weather widgets (humidity, wind direction and strength, UV status, city, max/min temperature)
- New watch alarm widget
- New air pressure, altitude/dive depth (calculated based on air pressure & temperature)
- New xdrip values widget (will be supported with Xdrip by Klaus3d3)
- New phone battery widget/bar (needs amazfit service+phone app)
- New phone alarm widget (needs amazfit service+phone app)
- New calories progress bar (set target in settings)
- New heart-rate progress bar (min 0 bpm, max 200 bpm)
- New world time widget, you can select the time zone (GMT) it displays
- New notifications widget, see unread notifications (needs amazfit service+phone app)
- New moonphase widget
- New walked distance widget (based on daily steps and height)
- Better image resolution when raising hand
- Ability to show only time when screen is off (to save battery, doesn't apply on raise of hand screen)
- Status bar position and enable/disable
- Font weight selection in settings
- White background option
- Many other options available in settings


### Bugs
- If screen off mode is not applied or you see widgets over old widgets, just re-apply the watchface
- Xdrip widget is not working


### Tutorial for devs and stylers
This project scopes to provide an easy way to create Amazfit APK watchfaces even without coding skills! Take a look at [this](https://forum.xda-developers.com/smartwatch/amazfit/tutorial-create-apk-watchfaces-coding-t3822221) post.


### Download

Get a ready to use binary
 - From our [Github Releases](https://github.com/GreatApo/GreatFit/releases/latest)

Or if you are hardcore, compile the source code with Android Studio.


### Installation
To install this watchface, you will need a PC with the ADB installed. Connect your Amazfit on your PC and fire up a terminal.

ADB install command:
```shell
adb install -r GreatFit.X.X.apk
```
ADB uninstall command: (run this first if you get installation error message)
```shell
adb uninstall com.dinodevs.greatfitwatchface
```
Clear data: (not cleared with uninstall, run this if GreatFit crashes when changing versions)
```shell
adb shell pm clear com.dinodevs.greatfitwatchface
```

### Screenshots
![GreatFit v3.0](other/images/3.jpg)
![GreatFit v3.0](other/images/IMG_20180822_175102.jpg)
![GreatFit v3.0](other/images/IMG_20180822_175120.jpg)
![GreatFit v3.0](other/images/IMG_20180822_175128.jpg)
![GreatFit v3.0](other/images/IMG_20180822_175137.jpg)
![GreatFit v3.0](other/images/IMG_20180822_175200.jpg)
![GreatFit v3.0](other/images/IMG_20180822_175241.jpg)



### Credit where credit is due

This project couldn't be possible without getting familiar with the source code provided by Manuel Alvarez (whose code is [here](https://github.com/manuel-alvarez-alvarez/malvarez-watchface)). Additional work has be done by Fabio Barbon (whose code is [here](https://github.com/drbourbon/drbourbon-watchfaces)), Luis Baena (@LBA97) and Saúl Alemán (@Nxsaul) (whose code is [here](https://github.com/Nxsaul/AmazfitAPKs)).

Translations are ported from my Pace Calendar widget project (code [here](https://github.com/GreatApo/AmazfitPaceCalendarWidget), see translators in the changelog [here](https://forum.xda-developers.com/smartwatch/amazfit/app-widget-calendar-pace-t3751889)), @GramThanos jsCalendar project (code [here](https://github.com/GramThanos/jsCalendar)) and by users here or on XDA (see changelog).

Special thanks to:
- @lfom, @GramThanos, @renzettis and the rest of the Amazmod team for helping me out
- @KieronQuinn for the settings style code (from his [AmazfitStepNotify](https://github.com/KieronQuinn/AmazfitStepNotify) app)
- and all those invisible people giving back to the community by helping or donating! (special thanks are also included in changelogs)

This project couldn't be possible without the following free software:
• Android Studio
• APK Easy Tool (decompile-recompile)
• Notepad++ (smali editing)
• JD-gui (jar file decompiler)
• dex2jar (file type converter)
• SVADeodexerForArt (working deodexer for amazfit's system)
• 7zip

Libraries files (*.lib) have copyrights by Huami
