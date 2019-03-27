goproxy:
	GO111MODULE=on CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -a -ldflags '-w -s' -o goproxy cmd/goproxy/main.go

build:
	docker build -t joewalker/goproxy .