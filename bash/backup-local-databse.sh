#!/bin/bash
mysqldump -c betoffice -u betofficesu --password=betoffice -h 127.0.0.1 > betoffice.sql
