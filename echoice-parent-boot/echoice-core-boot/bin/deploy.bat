@echo off
echo [INFO] Package the war in target dir.

cd %~dp0
cd ..
call mvn clean deploy -Dmaven.test.skip=true -U
cd bin
pause