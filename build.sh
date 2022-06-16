#!/bin/bash
APPS="eureka-server api-gateway core product-service order-service user-service payment-service"

for app in $APPS
do
	cd $app && mvn clean install
	returnCode=`echo $?`
	if [ $returnCode == 0 ]
	then
			echo ">>>>>>>>Build successfull for $app.<<<<<<<<<"
			cd ..
	else
			echo ">>>>>>>>Build failure for $app.<<<<<<<<<"
			exit $returnCode
	fi
done
