FROM alpine:3.8
ENV TARGET=""

COPY goproxy /goproxy
EXPOSE 80
RUN echo ${TARGET}
CMD [ "sh", "-c", "/goproxy -target=${TARGET} -addr=:80" ]