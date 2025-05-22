jCoAP is a java implementation of the Contrained Application Protocol (RFC 7252).
It supports:
* RFC 7641 CoAP Observe
* RFC 7390 CoAP Group Communication
* RFC 7959 CoAP Blockwise Transfer
* RFC 6690 CoRE Link Format (.well-known/core)

# Latest Releases

The latest releases can be downloadede here: [ws4d-jcoap/release](https://gitlab.amd.e-technik.uni-rostock.de/ws4d/jcoap/tree/master/ws4d-jcoap/release)

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

To use jCoAP core as a library in your projects, add the following dependency to your `pom.xml`:
```xml
<dependency>
	<groupId>org.ws4d.jcoap</groupId>
	<artifactId>jcoap-core</artifactId>
	<version>1.1.5</version>
</dependency>
```

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

# Tutorial and Examples

The [ws4d-jcoap-handsOn](https://gitlab.amd.e-technik.uni-rostock.de/ws4d/jcoap/tree/master/ws4d-jcoap-handsOn) project provides a guided tutorial to the most common functions of jCoAP.

Example-code can be found in the [ws4d-jcoap-examples](https://gitlab.amd.e-technik.uni-rostock.de/ws4d/jcoap/tree/master/ws4d-jcoap-examples) project.

# License
jCoAP is licensed under [Apache License, Version 2.0](./license.txt) see `license.txt`

# Third Party Software
jCoAP uses the folowing third party components
* [log4j](https://logging.apache.org/log4j/) - [Apache License - v 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
* [jUnit](http://junit.org) - [Eclipse Public License - v 1.0](https://www.eclipse.org/legal/epl-v10.html)