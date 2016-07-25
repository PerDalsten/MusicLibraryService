@echo off
setlocal

set DERBY_HOME="%JAVA_HOME%\db"
set DERBY_DATA=%~dp0..\database
set JAVA=%JAVA_HOME%\bin\java

REM Embedded: CONNECT 'jdbc:derby:musiclibrarydb;create=true';

%JAVA% -Dderby.system.home=%DERBY_DATA% -jar %DERBY_HOME%/lib/derbyrun.jar ij
                 
endlocal