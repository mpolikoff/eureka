#!/bin/bash

TYPE="service"
INSTANCE=1

while getopts "i:t:" opt; do
	case $opt in
		i ) INSTANCE=$OPTARG ;;
		t ) TYPE=$OPTARG ;;
		\? ) echo "Usage: $0"
			 exit
	esac
done

EUREKA_PORT=$(($INSTANCE + 8000))
EUREKA_NAME="$TYPE$INSTANCE"
EUREKA_ADDRESS="service$INSTANCE"
EUREKA_PROPS="sample-eureka-$TYPE"

CLASS="Service"
if [[ "$TYPE" == "client" ]]; then
	CLASS="Client"
fi
EUREKA_CLASS="com.netflix.eureka.ExampleEureka$CLASS"


# Set version.
if [ -z "$JAVA_VERSION" ]; then
	JAVA_VERSION="1.8"
fi

# Set home.
if [ -z "$JAVA_HOME" ]; then
	export JAVA_HOME=$(/usr/libexec/java_home -v $JAVA_VERSION)
fi

# Set classpath.
if [ ! -d eureka-examples/build/libs/WEB-INF ]; then
	unzip $(find eureka-server -name '*.war') -d eureka-examples/build/libs > /dev/null
fi
CLASSPATH=$(find eureka-examples/build/libs -name '*.jar' | xargs | sed -e 's/ /:/g')
export CLASSPATH=eureka-examples/conf:$CLASSPATH

$JAVA_HOME/bin/java -Deureka.client.props=$EUREKA_PROPS -Deureka.vipAddress=$EUREKA_ADDRESS -Deureka.name=$EUREKA_NAME -Deureka.port=$EUREKA_PORT $EUREKA_CLASS
