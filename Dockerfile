FROM maven:3.8.3-openjdk-11-slim AS maven_build

WORKDIR /build

# Copy the dependency specifications
COPY pom.xml pom.xml
COPY common/pom.xml common/pom.xml
COPY domain/pom.xml domain/pom.xml
COPY application/pom.xml application/pom.xml

#RUN mvn -q -pl common -am dependency:go-offline

COPY common common

RUN mvn -q -pl common install -DskipTests

#RUN mvn -q -pl domain -am dependency:go-offline

COPY domain domain

RUN mvn -q -pl domain install -DskipTests

#RUN mvn -q -pl application -am dependency:go-offline

COPY application application

RUN mvn package -DskipTests

FROM openjdk:11.0.15-slim as locadora

WORKDIR /app

ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS

ARG PORT=8080
ENV PORT=${PORT}
EXPOSE ${PORT}

COPY --from=maven_build /build/application/target/*.jar /app/locadora.jar

ENTRYPOINT java $JAVA_OPTS -Dserver.port=${PORT} -jar locadora.jar
