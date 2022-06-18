#!/bin/bash
APPS="axon-server registry edge products orders users payments"

cd ../..

for app in $APPS
do
	cd $app && sh stop.sh
	returnCode=`echo $?`
	if [ "$returnCode" -ne 0 ]
	then
		exit $returnCode
	else
		cd ..
	fi
done
