package main

import (
	"flag"
	"log"
	"net/http"
	"net/http/httputil"
	"net/url"

	"github.com/joecomscience/goproxy"
)

func main() {
	var (
		target      = flag.String("target", "http://localhost:8081", "destination ")
		healthCheck = flag.String("healthCheck", "/health", "application health check path")
		addr        = flag.String("addr", ":8080", "server port")
	)

	flag.Parse()

	uri, err := url.Parse(*target)
	if err != nil {
		panic(err)
	}

	reversProxy := httputil.NewSingleHostReverseProxy(uri)

	proxy := goproxy.Proxy{
		Target:      uri,
		HealthCheck: *healthCheck,
	}

	log.Printf("target: %s", *target)
	log.Printf("application health check path: %s", *healthCheck)
	log.Printf("server port: %s", *addr)

	http.HandleFunc("/", proxy.Handler(reversProxy))
	log.Fatal(http.ListenAndServe(*addr, nil))
}
