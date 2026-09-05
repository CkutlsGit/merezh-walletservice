FROM eclipse-temurin:21-alpine

WORKDIR /app

RUN addgroup -S walletservice && adduser -S walletservice -G walletservice

COPY target/walletservice-merezh-0.0.1-SNAPSHOT.jar /app/walletservice-merezh.jar

RUN chown -R walletservice:walletservice /app

USER walletservice

ENTRYPOINT ["java", "-jar", "walletservice-merezh.jar"]