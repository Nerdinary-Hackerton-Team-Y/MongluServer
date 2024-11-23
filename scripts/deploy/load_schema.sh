#!/bin/bash

# schema.sql 파일을 로드한다.

source ~/.env

rds_host=$RDS_HOST
rds_username=$RDS_USERNAME
rds_password=$RDS_PASSWORD

mysql -h${rds_host} -u${rds_username} -p${rds_password} < schema.sql
echo "schema is loaded"
