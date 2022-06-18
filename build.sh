#!/bin/bash
APPS="registry edge core products orders users payments"

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
