# News #

## JBoss Seam: Hotdeploy Maven Plugin and Archetype updated ##

  * The latest hotdeploy plugin (0.3.3) fixes a problem with hot deployed components both deployed to /WEB-INF/classes as well as /WEB-INF/dev on Linux - which crashed the application during app server startup
  * The updated Archetype SNAPSHOT includes the new plugin version as well as a fix for the plugin version management

`[`2009-11-16 by thug`]`

## JBoss Seam Archetype SNAPSHOT updated ##

Fixes errors in the persistence configuration. This failed unit tests when they started to do some real integration work... Also:
  * Upgrade to latest Maven Plugin version
  * References to original CLI plugin repository instead of relying on the JBoss Maven repo. The latest version of the CLI plugin also works with Maven 2.2.

`[`2009-08-09 by thug`]`

## JBoss Seam Hotdeploy Maven Plugin v0.3.2 ##

The latest version supports now also packaging regular WAR files for production deployment:

```
<plugin>
    <groupId>com.ctp.seam.maven</groupId>
    <artifactId>maven-hotdeploy-plugin</artifactId>
    <extensions>true</extensions>
    <configuration>
        <source>${java.source.version}</source>
        <target>${java.source.version}</target>
        <sourceDirectory>${basedir}/src/main/hot</sourceDirectory>
        <useWarPackaging>true</useWarPackaging>
    </configuration>
</plugin>
```

You can put this configuration in a dedicated profile to create a hot-deploy free WAR file. The plugin repository now also contains Maven meta data.

`[`2009-08-09 by thug`]`

## JBoss Seam Archetype - now with ICEFaces ##

The latest JBoss Seam Archetype snapshot supports creating your project also with ICEFaces as rich component library, similar to `seam-gen`. Simply use:

```
>mvn archetype:generate -DarchetypeCatalog=http://tinyurl.com/jbsarch -DajaxLibrary=icefaces
[INFO] Scanning for projects...
[INFO] Searching repository for plugin with prefix: 'archetype'.
...
Choose archetype:
1: http://tinyurl.com/jbsarch -> jboss-seam-archetype (Archetype for JBoss Seam Projects)
Choose a number: (1): 1
Define value for serverDir: : [your JBoss 5 server location]
Define value for groupId: : [your groupId]
Define value for artifactId: : [your artifactId]
Define value for version: 1.0-SNAPSHOT: : [your version]
Define value for package: : [your package]
Confirm properties configuration:
serverType: jboss5
ajaxLibrary: icefaces
...
Y: : y
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------
```

See the full blog post [here](http://ctpjava.blogspot.com/2009/07/jboss-seam-archetype-now-with-icefaces.html). The Wiki page can be found [here](JBossSeamMavenArchetype.md).

## JBoss Seam Archetype ##

Instead of our previous sample JBoss Seam POM, we have now a Seam Maven Archetype for you. Simply type:

```
mvn archetype:generate -DarchetypeCatalog=http://tinyurl.com/jbsarch
```

and fill out the values you're prompted for.

See the full blog post [here](http://ctpjava.blogspot.com/2009/06/creating-jboss-seam-maven-archetype.html). The Wiki page can be found [here](JBossSeamMavenArchetype.md).