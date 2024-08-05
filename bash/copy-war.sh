#!/bin/bash
_current_time=`date '+%Y-%m-%d-%H%M%S'`

cp ~/webapps/betoffice-war.war ~/webapps/betoffice-war.war-$_current_time
rm ~/webapps/betoffice-war.war
sleep 10s
cp ~/download/betoffice-war.war ~/webapps/betoffice-war.war
