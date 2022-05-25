FROM public.ecr.aws/docker/library/amazoncorretto:11 as builder
#
# Long term, something like this would be in a customized base image
#
WORKDIR /opt
RUN curl -s -O https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip && \
    jar -xf newrelic-java.zip && \
    rm newrelic-java.zip

WORKDIR /application
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} application.war
RUN java -Djarmode=layertools -jar application.war extract

FROM public.ecr.aws/docker/library/amazoncorretto:8-alpine-jre
ENV NEW_RELIC_APP_NAME="merlin-app"

# As with downloading/extracting NR in the builder, this is something that would be set up in the
# base image.
COPY --from=builder /opt/newrelic/ /opt/newrelic/

# Install Tomcat native libraries.  On Alpine, these are installed into /usr/lib
# /usr/lib is already in the default java.library.path list, so nothing more needs to be done
RUN apk add --quiet -U --no-cache --no-progress tomcat-native ttf-dejavu fontconfig

WORKDIR /application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader ./
COPY --from=builder application/application/ ./

# Make a note that WarLauncher is required for war files, which are required when JSP files are present.
# If no JSP is required, then executable JAR is fine.
# Executable JAR also works with Jetty
ENTRYPOINT [ "java", "-XX:MaxRAMPercentage=60.0", "-javaagent:/opt/newrelic/newrelic.jar", "org.springframework.boot.loader.WarLauncher" ]
