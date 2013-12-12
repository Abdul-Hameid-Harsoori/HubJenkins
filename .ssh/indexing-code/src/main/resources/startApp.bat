@ECHO OFF

set MAIN_CLASS=com.secant.dcm.dump.App
set MAIN_JAR=DicomDumpToDB-1.0.jar

set DUMP_HOME=C:\DicomDumpToDB-1.0

set CP=%DUMP_HOME%
set CP=%CP%;%DUMP_HOME%\%MAIN_JAR%

set JAVA_OPTS=-Xms128M -Xmx512M -XX:PermSize=128M -XX:MaxPermSize=512M

java %JAVA_OPTS% -cp %CP% %MAIN_CLASS% > today.log &