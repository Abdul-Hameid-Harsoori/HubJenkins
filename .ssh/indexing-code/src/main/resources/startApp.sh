MAIN_CLASS=com.secant.dcm.dump.App
MAIN_JAR=Indexing-1.0.jar

DUMP_HOME=/home/matrix/Indexing-1.0

# Setup the JVM
if [ "x$JAVA_HOME" != "x" ]; then
    JAVA=$JAVA_HOME/bin/java
else
    JAVA="java"
fi

# Setup the classpath
CP="$DUMP_HOME"
CP="$CP:$DUMP_HOME/$MAIN_JAR"

JAVA_OPTS="-Xms128m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=512m"

exec $JAVA $JAVA_OPTS -cp $CP $MAIN_CLASS