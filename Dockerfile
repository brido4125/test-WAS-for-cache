FROM openjdk:11

ARG JAR_FILE=./build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENV ZK_ADDR="127.0.0.1:2181"
ENV ARCUS_SERVICE_CODE="test"
ENV ARCUS_EARLY_CACHING_ENABLE=true
ENV SPRING_PORT=8080

EXPOSE ${SPRING_PORT}

ENTRYPOINT ["java","-Darcus.address=${ZK_ADDR}","-Darcus.serviceCode=${ARCUS_SERVICE_CODE}","-Dserver.port=${SPRING_PORT}","-Darcus.earlyCaching.enabled=${ARCUS_EARLY_CACHING_ENABLE}","-jar","/app.jar"]