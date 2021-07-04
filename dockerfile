# Preparation - build App (not used for tests)
FROM gradle:4.7.0-jdk8-alpine AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build --no-daemon


########################################
# Main part - container with test suites
FROM openjdk:8

# To run test suites we use gradle custom tasks
RUN wget -q https://services.gradle.org/distributions/gradle-7.1-bin.zip \
    && unzip gradle-7.1-bin.zip -d /opt \
    && rm gradle-7.1-bin.zip

ENV GRADLE_HOME /opt/gradle-7.1
ENV PATH $PATH:/opt/gradle-7.1/bin

# For allure serve
EXPOSE 62225

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/App.jar

COPY . /app/.

WORKDIR /app

ENTRYPOINT ["./gradlew"]
CMD ["testngRunSmokeTests"]

# Manual
# To build use up env variables
### docker build --tag taf:latest .

# To start set up env variables and run tests (using gradle tasks)
# %CD% is used for Windows. For Linux use 'pwd'
### docker run --rm --name taf -v %CD%/fromDocker/reports/:/app/build/reports/tests -v %CD%/fromDocker/screenshots/:/app/screenshots --env KS_PASS="Mrt#42MSs2$2" --env KS_PATH="keystore.ks" --env target="uat" taf testngRunApiTests
# Reports and screenshots should be on host in folder "fromDocker"
# Container can be removed automatically with -rm

# To copy reports from docker manually use
### docker cp taf:/app/build/reports/tests/. fromDocker/reports/.
### docker cp taf:/app/screenshots/. fromDocker/screenshots/.
