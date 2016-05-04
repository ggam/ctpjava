# JBoss Seam Maven Archetype - WAR only Project #

## Usage ##

Open up a console, cd to your projects directory and type:

```
mvn archetype:generate -DarchetypeCatalog=http://tinyurl.com/jbsarch
```

This will start Maven and show the following command line output:

```
[INFO] Scanning for projects...
[INFO] Searching repository for plugin with prefix: 'archetype'.
[INFO] ------------------------------------------------------------------------
[INFO] Building Maven Default Project
[INFO] task-segment: [archetype:generate] (aggregator-style)
[INFO] ------------------------------------------------------------------------
[INFO] Preparing archetype:generate
[INFO] No goals needed for project - skipping
[INFO] Setting property: classpath.resource.loader.class => 'org.codehaus.plexus.velocity.ContextClassLoaderResourceLoader'.
[INFO] Setting property: velocimacro.messages.on => 'false'.
[INFO] Setting property: resource.loader => 'classpath'.
[INFO] Setting property: resource.manager.logwhenfound => 'false'.
[INFO] [archetype:generate]
[INFO] Generating project in Interactive mode
[INFO] No archetype defined. Using maven-archetype-quickstart (org.apache.maven.archetypes:maven-archetype-quickstart:1.0)
Choose archetype:
1: http://tinyurl.com/jbsarch -> jboss-seam-archetype (Archetype for JBoss Seam Projects)
Choose a number: (1):
```

The remote archetype catalog contains so far only one archetype (BTW: the `jbsarch` in `tinyurl.com/jbsarch` stands for **JB** oss **S** eam **ARCH** etype - hope you can remember this better than the full URL. Select the archetype by typing `1` and enter your Maven project properties as well as your JBoss Server directory:

```
[INFO] snapshot com.ctp.archetype:jboss-seam-archetype:1.0.0-SNAPSHOT: checking for updates from jboss-seam-archetype-repo
Define value for serverDir: : /Developer/Servers/JBoss/jboss-5.1.0.GA
Define value for groupId: : com.ctp
Define value for artifactId: : fluxcapacitor
Define value for version: 1.0-SNAPSHOT: :
Define value for package: com.ctp: : com.ctp.fluxcapacitor
Confirm properties configuration:
serverType: jboss5
ajaxLibrary: richfaces
serverDir: /Developer/Servers/JBoss/jboss-5.1.0.GA
groupId: com.ctp
artifactId: fluxcapacitor
version: 1.0-SNAPSHOT
package: com.ctp.fluxcapacitor
Y: : y
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 4 minutes 57 seconds
[INFO] Finished at: Fri Jun 19 19:12:19 CEST 2009
[INFO] Final Memory: 12M/79M
[INFO] ------------------------------------------------------------------------
```

Note that the serverType property defaults to **`jboss5`**. If you have a JBoss 4.2.x installation, quit with n and retype everything (hmm...) and use `jboss4` instead.

In case anything fails here, make sure your archetype plugin is at least version `2.0-alpha-4` (I had to delete the local repo info file in the local repository once). Now with your project created, lets build and deploy it!

```
Aragorn:sandbox thug$ cd fluxcapacitor/
Aragorn:fluxcapacitor thug$ mvn package
[INFO] Scanning for projects...
[INFO] Reactor build order:
[INFO] [fluxcapacitor]
[INFO] [fluxcapacitor :: JBoss Configuration]
[INFO] [fluxcapacitor :: Web Application]
```

The project currently features two modules: One with the actual web application, and the other one containing JBoss Server configuration files. With the basic archetype you only get a datasource there, but you can also use it to change server ports, define security realms, queues etc.

Environment specific properties are referenced in filter property files. You can find the development filter file in `${artifactId}/environment/filters/${artifactId}-development.properties`. The filter file selection happens in the parent POM. It defines a development profile which sets the environment property. Setting this property to test will look for a `${artifactId}/environment/filters/${artifactId}-test.properties` filter file.

Note that the parent POM contains your JBoss installation directory hard coded. You might want to factor this out into a developer specific profile when working in a team. Fire up your JBoss instance. Everything should start up well (fingers crossed...) and you can point your browser to the base URL of your application.

## Options ##

| **Property** | **Default** | **Value** | **Description** |
|:-------------|:------------|:----------|:----------------|
| ajaxLibrary  | richfaces   | icefaces  | Use ICEFaces instead of JBoss RichFaces |
| serverType   | jboss5      | jboss4    | Required if you target a JBoss 4.2.x installation |