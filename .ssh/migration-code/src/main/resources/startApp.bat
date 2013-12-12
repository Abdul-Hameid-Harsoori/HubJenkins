@ECHO OFF

set MAIN_CLASS=com.secant.migration.App
set MAIN_JAR=media-migration-2.0.jar

set MIGRATOR_HOME=C:\MIGRATOR

set CP=%MIGRATOR_HOME%
set CP=%CP%;%MIGRATOR_HOME%\%MAIN_JAR%

set JAVA_OPTS=-Xms512M -Xmx1024M -XX:PermSize=128M -XX:MaxPermSize=512M

java %JAVA_OPTS% -cp %CP% %MAIN_CLASS% > run.log