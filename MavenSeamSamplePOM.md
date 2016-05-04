# Deprecated #

This POM is not maintained anymore. Please use the [JBossSeamMavenArchetype](JBossSeamMavenArchetype.md) instead.

# Sample Maven POM #

Starting reference for Seam Web applications deployed as WAR file.

Features:
  * JBoss Seam 2.1.1.GA with RichFaces 3.3.1.GA
  * Building Seam hot deployable code into a different output directory
  * Deploy changed XHTMLs, source code and hot deployable code into a JBoss installation.

```
<project xmlns="http://maven.apache.org/POM/4.0.0" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.ctp.seam</groupId>
    <artifactId>mavenseam</artifactId>
    <packaging>war</packaging>
    <version>0.0.1-SNAPSHOT</version>
    <name>Seam Maven Webapp</name>
    <url>http://www.seamframework.org</url>

    <repositories>
        <repository>
            <id>jboss</id>
            <name>JBoss Release Repository</name>
            <url>http://repository.jboss.org/maven2</url>
        </repository>
    </repositories>

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

    <dependencies>

        <!-- *************** Build Dependencies *************** -->

        <dependency>
            <groupId>org.jboss.seam.embedded</groupId>
            <artifactId>hibernate-all</artifactId>
            <version>${jboss.embedded.version}</version>
            <scope>provided</scope>
        </dependency>

        <!-- *************** Test Dependencies *************** -->

        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>5.8</version>
            <classifier>jdk15</classifier>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.seam.embedded</groupId>
            <artifactId>jboss-embedded-all</artifactId>
            <version>${jboss.embedded.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.seam.embedded</groupId>
            <artifactId>jboss-embedded-api</artifactId>
            <version>${jboss.embedded.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.seam.embedded</groupId>
            <artifactId>thirdparty-all</artifactId>
            <version>${jboss.embedded.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>javax.faces</groupId>
            <artifactId>jsf-api</artifactId>
            <version>${javax.faces.version}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>javax.faces</groupId>
            <artifactId>jsf-impl</artifactId>
            <version>${javax.faces.version}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>javax.el</groupId>
            <artifactId>el-api</artifactId>
            <version>1.2</version>
            <scope>provided</scope>
        </dependency>

        <!-- *************** Seam Dependencies *************** -->

        <dependency>
            <groupId>org.jboss.seam</groupId>
            <artifactId>jboss-seam</artifactId>
            <version>${jboss.seam.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>javax.el</groupId>
                    <artifactId>el-api</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.jboss.seam</groupId>
            <artifactId>jboss-seam-ui</artifactId>
            <version>${jboss.seam.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>javax.el</groupId>
                    <artifactId>el-api</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.jboss.seam</groupId>
                    <artifactId>jboss-seam-jul</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.jboss.seam</groupId>
            <artifactId>jboss-seam-ioc</artifactId>
            <version>${jboss.seam.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jboss.seam</groupId>
            <artifactId>jboss-seam-debug</artifactId>
            <version>${jboss.seam.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jboss.seam</groupId>
            <artifactId>jboss-seam-mail</artifactId>
            <version>${jboss.seam.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jboss.seam</groupId>
            <artifactId>jboss-seam-remoting</artifactId>
            <version>${jboss.seam.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>dom4j</groupId>
                    <artifactId>dom4j</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.jboss.seam</groupId>
            <artifactId>jboss-seam-pdf</artifactId>
            <version>${jboss.seam.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>com.lowagie</groupId>
                    <artifactId>itext</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>com.lowagie</groupId>
                    <artifactId>itext-rtf</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- *************** RichFaces Dependency *************** -->

        <dependency>
            <groupId>org.richfaces.ui</groupId>
            <artifactId>richfaces-ui</artifactId>
            <version>${jboss.richfaces.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>commons-collections</groupId>
                    <artifactId>commons-collections</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>commons-logging</groupId>
                    <artifactId>commons-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- *************** Drools / jBPM Dependency *************** -->

        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-compiler</artifactId>
            <version>${drools.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>xerces</groupId>
                    <artifactId>xercesImpl</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>xml-apis</groupId>
                    <artifactId>xml-apis</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>janino</groupId>
                    <artifactId>janino</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.jbpm</groupId>
            <artifactId>jbpm-jpdl</artifactId>
            <version>${jboss.jbpm-jpdl.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>commons-logging</groupId>
                    <artifactId>commons-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

    </dependencies>

    <build>
        <finalName>${project.artifactId}</finalName>
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
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java.source.version}</source>
                    <target>${java.source.version}</target>
                </configuration>
            </plugin>
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
                                <source>${java.source.hotdeploy}</source>
                            </sources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <childDelegation>true</childDelegation>
                    <useSystemClassLoader>true</useSystemClassLoader>
                    <argLine>
                        -Dsun.lang.ClassLoader.allowArraySyntax=true
                    </argLine>
                </configuration>
            </plugin>
            <plugin>
                <!-- run with 'mvn cli:execute-phase' and use 'hot' -->
                <groupId>org.twdata.maven</groupId>
                <artifactId>maven-cli-plugin</artifactId>
                <configuration>
                    <userAliases>
                        <hot>hotdeploy:exploded -o -Plocal</hot>
                    </userAliases>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <profiles>
        <profile>
            <id>local</id>
            <build>
                <defaultGoal>hotdeploy:exploded</defaultGoal>
                <plugins>
                    <plugin>
                        <groupId>com.ctp.seam.maven</groupId>
                        <artifactId>maven-hotdeploy-plugin</artifactId>
                        <version>0.3.1</version>
                        <configuration>
                            <source>${java.source.version}</source>
                            <target>${java.source.version}</target>
                            <sourceDirectory>${java.source.hotdeploy}</sourceDirectory>
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
                                    <followSymlinks>false</followSymlinks>
                                </fileset>
                            </filesets>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
            <properties>
                <directory.deploy.jboss>/work/servers/jboss/jboss-4.2.3.GA/server/default/deploy</directory.deploy.jboss>
            </properties>
        </profile>
    </profiles>

    <properties>
        <java.source.version>1.5</java.source.version>
        <java.source.hotdeploy>${basedir}/src/main/hot</java.source.hotdeploy>
        <jboss.seam.version>2.1.1.GA</jboss.seam.version>
        <jboss.richfaces.version>3.3.1.GA</jboss.richfaces.version>
        <jboss.jbpm-jpdl.version>3.2.3</jboss.jbpm-jpdl.version>
        <jboss.embedded.version>beta3.SP4</jboss.embedded.version>
        <drools.version>4.0.7</drools.version>
        <javax.faces.version>1.2_08</javax.faces.version>
    </properties>

</project>
```