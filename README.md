Zipkin RSocket Sender
=====================

Zipkin RSocket Sender sends a list of encoded spans to zipkin RSocket collector by RSocket's FireAndForget command.

# Why RSocket sender?

* Fast: Async and NoAck(Fire and Forget)
* Easy to integrate with Reactive System

# Where is Zipkin Server with RSocket Collector?

Please visit https://github.com/linux-china/zipkin-rsocket-server for detail and usage.

# References

* Tracing (Zipkin) Metadata Extension: https://github.com/rsocket/rsocket/blob/master/Extensions/Tracing-Zipkin.md
* zipkin-reporter-java:  https://github.com/openzipkin/zipkin-reporter-java
* RSocket: https://rsocket.io/
