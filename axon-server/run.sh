APP_NAME=axonserver
nohup java -jar $APP_NAME.jar >> $APP_NAME-nohup.log 2>>$APP_NAME-nohup-error.log  &
PID=`echo $!`
returnCode=`echo $?`
if [ "$returnCode" -eq 0 ]
then
	echo "Started $APP_NAME with PID $PID"
	echo $PID > $APP_NAME.pid
else
	echo "Error while start $APP_NAME. Please check nohup logs."
	exit 1
fi
