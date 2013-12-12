#!/bin/bash

MAIN_CLASS=com.secant.migration.App
MAIN_JAR=Migration-2.0.jar

MIGRATOR_HOME=/home/matrix/Migration-2.0

# Setup the JVM
if [ "x$JAVA_HOME" != "x" ]; then
    JAVA=$JAVA_HOME/bin/java
else
    JAVA="java"
fi

CP="$MIGRATOR_HOME"
CP="$CP:$MIGRATOR_HOME/$MAIN_JAR"

JAVA_OPTS="-Xms128m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=512m"

# Run the MigrationAction based upon given argument

#Translate the first argument of command to upper case and
#stores the result in to variable COMMAND
  COMMAND=`echo $1 | tr a-z A-Z`
if [ $# -lt 1 ]; then  #check for the number of argument should not less than 1
        echo "Provide the action you want to perform on migration..."
        exit 127
elif [ $COMMAND == "STOP" ]; then
        ACTION="STOP"
elif [ $COMMAND == "RESTART" ]; then
        ACTION="STOP"
elif [ $COMMAND == "START" ]; then
        ACTION="START"
elif [ $COMMAND == "STATUS" ]; then
        ACTION="STATUS"
else
        echo "You can ONLY perform START or STOP or RESTART action."
        exit 127
fi

#to get the process id of running Migration process.
processIds="$(ls $MIGRATOR_HOME/PID)"		  # stores all filenames of given dir, in to array called processIds
PID=0                                             # default PID=0, incase process is running, same will be overridden
RUNNING=1
RESTARTMESSAGE=0;
for processId in $processIds ; do                 # for processing every element of processIds array, same to be stored in to processId var
        pstatus=$(ps -p $processId o stat=)       # "ps -p $processId o stat= " command will return a status like 'S1','R+' if process is running, empty string otherwise.
        if [ "$pstatus" != "" ]; then             # if status of above command is not empty string then
                PID=$processId                    # override PID var with the pid of running process
                RUNNING=0                         # mark RUNNING flag as 0 (true) as process is running.
        else
                rm $MIGRATOR_HOME/PID/$processId # if processId process is not running simply delete the same from specified directory.
        fi
done
if [ $ACTION == "STATUS" ]; then
        if [ ${RUNNING} -ne 0 ]; then            # check for the flag indicating process is running or not.
                echo "false"
        else
                echo "true"
        fi
        exit 127
fi

if [ $ACTION == "STOP" ]; then
        if [ ${PID} -ne 0 ]; then
                #Execute stop command to stop the migration process.
                exec kill -9 $PID &                       # execute stop signal command.
                while [ "$pstatus" != "" ]
                do
                        pstatus=$(ps -p $PID o stat=)
                done
                if [ $COMMAND == "STOP" ]; then
                        echo "Migration process has been STOPPED." # return a massage saying migration process stopped.
                fi
        elif [ $COMMAND == "STOP" ]; then
                echo "Migration process is not Running." # return a massage saying action performed.
        fi

        if [ $COMMAND == "RESTART" ]; then
                while [ "$pstatus" != "" ]
                do
                        pstatus=$(ps -p $PID o stat=)
                done
                COMMAND="START"
                RESTARTMESSAGE=1
                RUNNING=1
        fi
fi
if [ $COMMAND == "START" ]; then                         # incase user has given start command then migration process needs to start if not already running.
        if [ ${RUNNING} -ne 0 ]; then            # check for the flag indicating process is running or not.
                #Execute the JVM
echo $JAVA $JAVA_OPTS -cp $CP $MAIN_CLASS "$@"
                exec $JAVA $JAVA_OPTS -cp $CP $MAIN_CLASS "$@" & filename=$(echo $!)     #start migration process will return process id, store the same in to var filename.
                touch $MIGRATOR_HOME/PID/$filename &              # create a file named processid , which is returned by above command.
                if [ ${RESTARTMESSAGE} -ne 0 ]; then
                        echo "Migration process has been RESTARTED"     # return a massage saying action performed.
                else
                        echo "Migration process has been STARTED"               # return a massage saying action performed.
                fi
        else
                echo "Migration Process is Already Running".
                exit 127
        fi
fi
