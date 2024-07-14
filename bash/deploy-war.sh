#!/bin/bash
_BETOFFICE_HOME="/c/development/projects/betoffice/web/bo-rest/betoffice-war"
_CURRENTTIME=`date '+%Y-%m-%d-%H%M%S'`
_NEW_WAR_FILE="betoffice-war-1.5.1-SNAPSHOT.war"
_WAR_FILE="~/webapps/betoffice-war.war"
_BACKUP_WAR="$_WAR_FILE.$_CURRENTTIME"

scp $_BETOFFICE_HOME/target/$_NEW_WAR_FILE boprod.tdkb2:~/download

ssh mariadb.tdkb2 << 'ENDSSH'
~/bin/database_backup.sh
ENDSSH

ssh boprod.tdkb2 << 'ENDSSH'
cp ~/webapps/betoffice-war.war ~/webapps/betoffice-war.war-$_CURRENTTIME
ENDSSH

exit 0;
