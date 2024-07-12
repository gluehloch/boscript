#!/bin/bash
ssh mariadb.tdkb2 << 'ENDSSH'
~/bin/database_backup.sh
ENDSSH
exit 0;
