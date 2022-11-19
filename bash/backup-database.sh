#!/bin/bash
PASSWORD_BETOFFICE=<password-database-betoffice>
PASSWORD_REGISTER=<password-database-register>
CURRENTTIME=`date '+%Y-%m-%d-%H%M%S'`

BACKUP_DIR="/home/mariadb/backup"

BETOFFICE_FILENAME="$BACKUP_DIR/betoffice-$CURRENTTIME.sql"
REGISTER_FILENAME="$BACKUP_DIR/register-$CURRENTTIME.sql"

mysqldump -c betoffice -u betofficesu --password=$PASSWORD_BETOFFICE -h 127.0.0.1 > $BETOFFICE_FILENAME
gzip $BETOFFICE_FILENAME

mysqldump -c register -u registersu --password=$PASSWORD_REGISTER -h 127.0.0.1 > $REGISTER_FILENAME
gzip $REGISTER_FILENAME

exit 0;