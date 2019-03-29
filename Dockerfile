FROM golang:1.11 as build
WORKDIR /opt/app
COPY . .
RUN GO111MODULE=on CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -a -ldflags '-w -s' -o goproxy cmd/goproxy/main.go

FROM alpine:3.8 as release
ENV TARGET=""
ENV HEALTH_CHECK="/health"

COPY --from=build /opt/app/goproxy /goproxy
EXPOSE 80
RUN echo ${TARGET}
CMD [ "sh", "-c", "/goproxy -target=${TARGET} -healthCheck=${HEALTH_CHECK} -addr=:80" ]