@echo off

echo Entering continuous compilation loop ...
call "%~dp0\sbt.bat" %* console
