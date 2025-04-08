# jCoAP Proxy

Welcome to the jCoAP Proxy

This implementation provides a forward proxy between CoAP and HTTP, supporting:
* HTTP to CoAP translation and caching
* CoAP to HTTP translation and caching and
* CoAP to CoAP caching

As forward proxy the client needs to be aware of the proxy and therfor needs to interact directly with the proxy.

The HTTP to CoAP Proxy functionality is used by a protocol-agnostic access.
This means, that the HTTP client is not aware of the translation to CoAP.
The URI specified in the HTTP request is assumed as URI to the CoAP server that will be acessed by the CoAP client of the Proxy.
In order to send HTTP requests from a web-browser to the jCoAP proxy, make sure to configure the proxy in your browsers settings.
The HTTP proxy server is started on Port 8080.

```text
                    +-----------------------------------------------+
                    |                  jCoAP Proxy                  | 
                    |                                               |
+-------------+     +-------------+     +-------+     +-------------+     +-------------+
| HTTP Client |---->| HTTP Server |---->| Cache |---->| CoAP Client |---->| CoAP Server |
+-------------+     +-------------+     +-------+     +-------------+     +-------------+
                    |                                               |
                    +-----------------------------------------------+
```
As CoAP was designed with proxies in mind, CoAP messages can contain a Proxy-URI option.

The Proxy-URI option is mandatory and should either start with 'http://' or 'coap://' to select the target protocol.

Notice that https:// and coaps:// are not supported as security is not implemented yet.

As example the Proxy-URI 'http://127.0.0.1/wanted' will cause the proxy to fetch the 'wanted' resource using HTTP, while the Proxy-URI 'coap://127.0.0.1/wanted' will cause the proxy to fetch the 'wanted' resource using CoAP.
The CoAP proxy server is started on port 5683 (CoAP default port).
```text
                    +-----------------------------------------------+
                    |                  jCoAP Proxy                  |
                    |                                               |
+-------------+     +-------------+     +-------+     +-------------+     +-------------+
| CoAP Client |---->| CoAP Server |---->|       |---->| CoAP Client |---->| CoAP Server |
+-------------+     +-------------+     |       |     +-------------+     +-------------+
                    |                   | Cache |                   |
                    |                   |       |     +-------------+     +-------------+
                    |                   |       |---->| HTTP Client |---->| HTTP Server |
                    |                   +-------+     +-------------+     +-------------+
                    |                                               |
                    +-----------------------------------------------+
```
Aditionally the proxy starts a second CoAP server on port 5684 offering the '/statistic' resource.

A closer look on the proxy implementation can be foud in:

Christian Lerche, Nico Laum, Frank Golatowski, Christoph Niedermeier, Dirk Timmermann:

[Connecting the Web with the Web of Things: Lessons Learned From Implementing a CoAP-HTTP Proxy](http://www.amd.e-technik.uni-rostock.de/veroeff/2012_Connecting%20the%20Web%20with%20the%20Web%20of%20Things.pdf) 

Proceedings of the IoTech Workshop 2012, Las Vegas, USA, Oktober 2012


# Latest Releases

The latest releases can be downloadede here: [ws4d-jcoap-applications/release](https://gitlab.amd.e-technik.uni-rostock.de/ws4d/jcoap/tree/master/ws4d-jcoap-applications/release)

# Build using Maven

You need to have a working maven installation to build jCoAP.
Then simply run the following from the project's root directory:

```sh
$ mvn clean install
```

Executable JARs including all dependencies can be found in the `target\` folder afterwards.

# Usage in Maven Projects

jCoAP and its projects are currently not uploaded to any Maven repository!
Thus you need to build jcoap projects once on your local machine (see "Build using Maven") before you can use them.
During build, the binary will be put to the local maven repository on your machine.

To use jCoAP proxy as a library in your projects, add the following dependency to your `pom.xml`:
```xml
<dependency>
	<groupId>org.ws4d.jcoap</groupId>
	<artifactId>jcoap-proxy</artifactId>
	<version>0.0.2</version>
</dependency>
```

# Eclipse

The project can be easily imported into a recent version of the Eclipse IDE.
Make sure to have the following before importing:

* [Eclipse EGit](http://www.eclipse.org/egit/) (should be the case with every recent Eclipse version)
* [m2e - Maven Integration for Eclipse](http://www.eclipse.org/m2e/) (should be the case with every recent Eclipse version)
* UTF-8 workspace text file encoding (Preferences &raquo; General &raquo; Workspace)

Then choose *[Import... &raquo; Maven &raquo; Existing Maven Projects]* to import `jCoAP projects` into Eclipse.

To run the build process from Eclipse select a project then right click *[Run As &raquo; Maven install]*

# IntelliJ

The project can also be imported to IntelliJ as follows:

In IntelliJ, choose *[File.. &raquo; Open]* then select the location of the cloned repository in your filesystem.
IntelliJ will then automatically import all projects and resolve required Maven dependencies.

# License
jCoAP is licensed under Apache License, Version 2.0 see [license.txt](./license.txt)

# Third Party Software
The jCoAP Proxy uses the folowing third party components
* [log4j](https://logging.apache.org/log4j/) - [Apache License - v 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
* [ehcache](http://www.ehcache.org/) - [Apache License - v 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
* [httpasyncclient](https://hc.apache.org/httpcomponents-asyncclient-dev/) - [Apache License - v 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
* [commons-cli](http://commons.apache.org/proper/commons-cli/) - [Apache License - v 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)