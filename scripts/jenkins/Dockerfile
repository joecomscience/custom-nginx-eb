FROM jenkins/jenkins:lts

LABEL maintainer=joewalker
USER root
RUN apt-get update; \
    apt-get install -y maven

USER jenkins