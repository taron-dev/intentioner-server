FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

COPY . .
RUN ./gradlew clean build --refresh-dependencies -x test
RUN mkdir -p target/dependency && (java -Djarmode=layertools -jar build/libs/*SNAPSHOT.jar extract --destination target/extracted)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG EXTRACTED=/workspace/app/target/extracted
COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]