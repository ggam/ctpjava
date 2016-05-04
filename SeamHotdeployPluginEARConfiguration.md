# Seam Hotdeploy Maven Plugin - Usage with EAR Projects #

If you want to use a [Seam WAR module](MavenSeamHotdeployPluginUsage.md) within an EAR project, you can do this by using a specific packaging type the plugin defines.

## Parent POM Configuration ##

Both the WAR as well as the EAR module have to be aware of the packaging type, so it's easiest to register the plugin in the **parent POM** of both projects:

```
...
<modules>
    <module>../[your module]-ear</module>
    <module>../[your module]-war</module>
</modules>

<build>
    <plugins>
        <plugin>
            <groupId>com.ctp.seam.maven</groupId>
            <artifactId>maven-hotdeploy-plugin</artifactId>
            <extensions>true</extensions>
        </plugin>
    </plugins>
</build>
...
```

## WAR Module Configuration ##

Reconfigure the packaging and plugin-settings of the **WAR module POM**:
  * Change the packaging to `seam-war`
  * Use the plugin in the default profile (`<activeByDefault>true</activeByDefault>`)
  * Remove the `<deployDirectory/>` from the plugin configuration

```
<packaging>seam-war</packaging>

...

<plugin>
    <groupId>com.ctp.seam.maven</groupId>
    <artifactId>maven-hotdeploy-plugin</artifactId>
    <extensions>true</extensions>
    <configuration>
        <source>${java.source.version}</source>
        <target>${java.source.version}</target>
        <sourceDirectory>${basedir}/src/main/hot</sourceDirectory>
    </configuration>
</plugin>
```

If you want to build a war without hot deploy packaged classes (e.g. for a production deployment), you can switch this off by configuring the plugin with

```
<useWarPackaging>true</useWarPackaging>
```

## EAR Module Configuration ##

Change the dependency type of your WAR project:

```
<dependency>
    <groupId>[your group]</groupId>
    <artifactId>[your WAR]</artifactId>
    <version>[your version]-SNAPSHOT</version>
    <type>seam-war</type>
</dependency>
```

You also have to tell the EAR plugin that this packaging type is to be handled like a WAR file:

```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-ear-plugin</artifactId>
    <version>2.3.2</version>
    <configuration>
        <version>5</version>
        <artifactTypeMappings>
            <artifactTypeMapping type="seam-war" mapping="war"/>
        </artifactTypeMappings>
    </configuration>
</plugin>
```

If you want to combine both hotdeploys for EAR and WAR, you might want to deploy the WAR project exploded. This goes in the EAR plugin configuration:

```
<unpackTypes>war</unpackTypes>
```