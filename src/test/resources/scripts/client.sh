#/bin/bash

DERBY_HOME=$JAVA_HOME/db
DERBY_DATA=$PWD/../database

JAVA=$JAVA_HOME/bin/java

#Embedded: CONNECT 'jdbc:derby:musiclibrarydb;create=true';

$JAVA -Dderby.system.home=$DERBY_DATA -jar $DERBY_HOME/lib/derbyrun.jar ij
                 