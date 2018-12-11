FROM golang:alpine

COPY main.go /go
RUN go build -o main .
CMD [ "./main" ]
