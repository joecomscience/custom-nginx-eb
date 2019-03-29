package goproxy

import (
	"log"
	"net/http"
	"net/http/httputil"
	"net/url"
)

type Proxy struct {
	Target      *url.URL
	HealthCheck string
}

func (proxy *Proxy) Handler(p *httputil.ReverseProxy) func(http.ResponseWriter, *http.Request) {
	return func(w http.ResponseWriter, r *http.Request) {
		if r.URL.Path == proxy.HealthCheck {
			log.Printf("call health check: %s", proxy.HealthCheck)
			w.WriteHeader(http.StatusOK)
			w.Write([]byte("Application healthy"))
			return
		}

		r.URL.Scheme = proxy.Target.Scheme
		r.URL.Host = proxy.Target.Host

		log.Printf("Redirect to: %s", r.URL)

		p.ServeHTTP(w, r)
	}
}
