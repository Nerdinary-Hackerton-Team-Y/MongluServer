#!/bin/bash

# 환경변수 설정
source .env

host=$EC2_HOST
username=$EC2_USERNAME

output=$(./gradlew bootJar --info)
status=$?

if [ $status -ne 0 ]; then
  echo " === build error ==="
  echo "$output"
  exit 1
fi

# Upload deployment scripts
chmod 755 scripts/deploy/*
scp -i MongleKeypair.pem -r scripts/deploy/* "${username}@${host}:deploy"

# Upload sql schema
scp -i MongleKeypair.pem -r schema.sql "${username}@${host}:~"

# Update database
ssh -i MongleKeypair.pem "${username}@${host}" "deploy/load_schema.sh"

# Kill current running Spring process
ssh -i MongleKeypair.pem "${username}@${host}" "deploy/shutdown.sh; deploy/backup.sh"

# Send jar file to EC2
jarPath=$(./gradlew -q jarPath)
scp -i MongleKeypair.pem $jarPath "${username}@${host}:~/server.jar"

# Startup new uploaded jar
ssh -i MongleKeypair.pem "${username}@${host}" "source ~/.env; deploy/startup.sh"

# Wait 30 seconds for server to startup
echo "wating for 30 seconds..."
sleep 30

# Check and Recover
ssh -i MongleKeypair.pem "${username}@${host}" "source ~/.env; deploy/check_and_recover.sh"

