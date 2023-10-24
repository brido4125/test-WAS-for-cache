# !/bin/bash

TARGET=$1

./gradlew clean build -x test

docker build -t hcs4125/"${TARGET}"was:latest .

docker push hcs4125/"${TARGET}"was:latest

