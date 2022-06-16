#!/bin/bash
APPS="axon-server eureka-server api-gateway product-service order-service user-service payment-service"
echo ">>>>> Stopping <<<<<"
sh stop-all.sh
echo ">>>>> Deploying <<<<<"
for app in $APPS
do
	cd $app && sh run.sh
	returnCode=`echo $?`
	if [ $returnCode != 0 ]
	then
		exit $returnCode
	else
		cd ..
	fi
done
