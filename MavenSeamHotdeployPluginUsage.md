# Seam Hotdeploy Maven Plugin Usage #

The Seam Hotdeploy plugin allows you to build a Maven WAR project directly into a JBoss installation. This includes copying config files as well as XHTML/JSPs, and taking care about copying hot deployable classes into `WEB-INF/dev` instead of `WEB-INF/classes`.

Tested with:
  * JBoss Seam 2.1.1.GA
  * JBoss AS 4.2.3.GA
  * Maven 2.0.10

## Seam Project Setup ##

We propose following Maven setup for your Seam WAR project:

```
src
  |-- main
        |-- java       (Your entities, JSF classes, custom Identity component, ...)
        |-- hot        (Your hot deploy components)
        |-- resources  (Your config files, META-INF/persistence.xml, META-INF/components.xml, seam.properties etc.)
        |-- webapp     (Your XHTMLs, WEB-INF/faces-config.xml, WEB-INF/pages.xml, ...)
  |-- test
        |-- java       (Your test classes)
        |-- resources  (Your test config files, components.properties, seam.properties, ...)
        |-- bootstrap  (Embeddable JBoss config files)
```

**UPDATE**: For embedding the WAR inside an EAR, see [further descriptions here](SeamHotdeployPluginEARConfiguration.md).

## Project POM Configuration ##

Add the following plugin repositories to your POM:

```
<pluginRepositories>
    <pluginRepository>
        <id>ctpjava</id>
        <name>CTP Public Repository</name>
        <url>http://ctpjava.googlecode.com/svn/trunk/repository</url>
    </pluginRepository>
    <pluginRepository>
        <id>repository.jboss.org</id>
        <name>JBoss Repository</name>
        <url>http://repository.jboss.org/maven2</url>
    </pluginRepository>
</pluginRepositories>
```

Use the build helper plugin to add the `hot` source folder to your Maven build, and integrate the CLI plugin for much more build convenience:

```
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>build-helper-maven-plugin</artifactId>
    <executions>
        <execution>
            <id>add-source</id>
            <phase>generate-sources</phase>
            <goals>
                <goal>add-source</goal>
            </goals>
            <configuration>
                <sources>
                    <source>${basedir}/src/main/hot</source>
                </sources>
            </configuration>
        </execution>
    </executions>
</plugin>
<plugin>
    <groupId>org.twdata.maven</groupId>
    <artifactId>maven-cli-plugin</artifactId>
    <configuration>
        <userAliases>
            <hot>hotdeploy:exploded -o -Pdevelopment</hot>
            <cln>clean -o -Pdevelopment</cln>
        </userAliases>
    </configuration>
</plugin>
```

Add the additional test resource folders to your build:

```
<testResources>
    <testResource>
        <directory>src/test/resources</directory>
    </testResource>
    <testResource>
        <directory>src/test/bootstrap</directory>
    </testResource>
    <testResource>
        <directory>src/main/webapp</directory>
    </testResource>
</testResources>
```


## Plugin Configuration ##

Now for the plugin configuration. Create a dedicated build profile in your POM. We will change the behavior of the `clean` plugin there as well:

```
<profile>
    <id>development</id>
    <build>
        <defaultGoal>hotdeploy:exploded</defaultGoal>
...
```

Add the following plugins to the profile build:

```
<plugin>
    <groupId>com.ctp.seam.maven</groupId>
    <artifactId>maven-hotdeploy-plugin</artifactId>
    <version>0.2.1</version>
    <configuration>
        <source>${java.source.version}</source>
        <target>${java.source.version}</target>
        <sourceDirectory>src/main/hot</sourceDirectory>
        <deployDirectory>
            ${directory.deploy.jboss}/${build.finalName}.${project.packaging}
        </deployDirectory>
    </configuration>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-clean-plugin</artifactId>
    <version>2.2</version>
    <configuration>
        <filesets>
            <fileset>
                <directory>${directory.deploy.jboss}</directory>
                <includes>
                    <include>**/${build.finalName}.${project.packaging}*</include>
                </includes>
            </fileset>
        </filesets>
    </configuration>
</plugin>
```

Add appropriate values in the POMs properties section. Values to replace are:
  * `java.source.version`: e.g. 1.5
  * `directory.deploy.jboss`: Pointing to the JBoss server deploy folder, e.g. `/usr/local/jboss/jboss-4.2.3.GA/server/default/deploy`

You can also overwrite the WAR file name if you want to get rid of the version number. Add this to your build config or to your development profile.

```
<finalName>${project.artifactId}</finalName>
```

For a complete POM example see the [sample POM](http://code.google.com/p/ctpjava/wiki/MavenSeamSamplePOM) Wiki page.


## Run the Plugin ##

On the command line, first type

```
mvn cli:execute-phase
```

which starts a 'Maven shell' from the CLI plugin, speeding up reexecutions. From here, start the build with the alias

```
maven2> hot
```

(which stands for `mvn hotdeploy:exploded -P development`). This will deploy your WAR file into the JBoss deploy folder. If you need to clean out your running application first, use

```
maven2> cln hot
```

If you have a multi-module project, make sure this is only run on the web module.