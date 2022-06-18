#!/bin/bash
APPS="axon-server registry edge products orders users payments"
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
echo "Please wait while all services get registered with eureka...Wait till this script terminates, this may take upto 100s"
sleep 100
echo "Ready to roll!!"
