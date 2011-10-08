@echo off

echo Entering continuous test loop ...
call "%~dp0\sbt.bat" --loop --no-jrebel %* ~test
