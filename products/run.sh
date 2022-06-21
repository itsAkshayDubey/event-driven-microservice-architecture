APP_NAME=products
VERSION=`xmllint --xpath '/*[local-name()="project"]/*[local-name()="version"]/text()' pom.xml`
nohup java -jar target/$APP_NAME-$VERSION.jar >> $APP_NAME-nohup.log 2>>$APP_NAME-nohup-error.log  &
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
