@echo off
cd %~dp0
gradlew runApp -PtestArgs="['%~1']" --no-daemon