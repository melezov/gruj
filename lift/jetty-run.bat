@echo off

echo Starting Lift, firing up Jetty ...
call "%~dp0sbt.bat" --loop %* ~lift
