package main

import (
	"flag"
	"log"
	"net/http"
	"net/http/httputil"
	"net/url"
)

func main() {
	var (
		target = flag.String("target", "http://localhost:8082", "destination ")
		addr   = flag.String("addr", ":8080", "server port")
	)
	flag.Parse()
	uri, err := url.Parse(*target)
	if err != nil {
		panic(err)
	}

	proxy := httputil.NewSingleHostReverseProxy(uri)

	log.Printf("target: %s", *target)
	log.Printf("server port: %s", *addr)

	http.HandleFunc("/", handler(uri, proxy))
	log.Fatal(http.ListenAndServe(*addr, nil))
}

func handler(target *url.URL, p *httputil.ReverseProxy) func(http.ResponseWriter, *http.Request) {
	return func(w http.ResponseWriter, r *http.Request) {
		if r.URL.Path == "/health" {
			w.WriteHeader(http.StatusOK)
			w.Write([]byte("Application healthy"))
			return
		}

		r.URL.Scheme = target.Scheme
		r.URL.Host = target.Host

		log.Printf("Redirect to: %s", r.URL)

		p.ServeHTTP(w, r)
	}
}
