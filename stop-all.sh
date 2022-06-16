#!/bin/bash
APPS="axon-server eureka-server api-gateway product-service order-service user-service payment-service"

for app in $APPS
do
	cd $app && sh stop.sh
	returnCode=`echo $?`
	if [ $returnCode != 0 ]
	then
		exit $returnCode
	else
		cd ..
	fi
done
