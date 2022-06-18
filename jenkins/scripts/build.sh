#!/bin/bash
APPS="registry edge core products orders users payments"

cd ../..

for app in $APPS
do
	cd $app && mvn clean install
	returnCode=`echo $?`
	if [ "$returnCode" -eq 0 ]
	then
			echo ">>>>>>>>Build successfull for $app.<<<<<<<<<"
			cd ..
	else
			echo ">>>>>>>>Build failure for $app.<<<<<<<<<"
			exit $returnCode
	fi
done
