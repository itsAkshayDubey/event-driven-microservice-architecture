APP_NAME=axonserver
if [ -f $APP_NAME.pid ]
then
	kill -TERM $(cat $APP_NAME.pid)
	returnCode=`echo $?`
	if [ "$returnCode" -eq 0 ]
	then
		echo "$APP_NAME service stopped.."
		rm -rf $APP_NAME.pid
	else
		echo "Error while stopping $APP_NAME."
		exit 1
	fi
else
	echo "$APP_NAME service not running"
fi
