# Using Hot Deploy on JBoss 5 #

If you see JBoss constantly redeploy you application when using hot deploy, apply these settings to your JBoss installation:

Modify file: server/_servername_/conf/bootstrap/profile.xml

```
<bean name="MetaDataStructureModificationChecker" class="org.jboss.deployers.vfs.spi.structure.modified.MetaDataStructureModificationChecker">
    <constructor>
        <parameter><inject bean="MainDeployer" /></parameter>
    </constructor>
    <property name="cache"><inject bean="StructureModCache" /></property>
    <!--property name="filter"><bean class="org.jboss.system.server.profile.basic.XmlIncludeVirtualFileFilter" /></property-->
    <property name="filter">
        <!-- A pattern matching filter. We can configure this to our custom pattern.
            Here we configure it to include only application.xml and web.xml 
            for modification check (and any subsequent redeployments) -->
        <bean class="org.jboss.system.server.profile.basic.PatternIncludeVirtualFileFilter">
            <constructor>
                <!-- Remember, the parameter is a regex so use the correct syntax as below -->
                <parameter class="java.lang.String">web.xml</parameter>
            </constructor>
        </bean>
    </property>
</bean>
```

Modify file: server/_servername_/deployers/seam.deployer/META-INF/seam-deployers-jboss-beans.xml

```
<deployment xmlns="urn:jboss:bean-deployer:2.0">

    <!-- Seam deployer -->
    <bean name="SeamDeployer" class="org.jboss.seam.integration.microcontainer.deployers.SeamWebUrlIntegrationDeployer"/>

    <!-- Seam modification type matcher -->
    <!-- bean name="SeamMTMatcher" class="org.jboss.seam.integration.microcontainer.deployers.SeamTempModificationTypeMatcher"/ -->

</deployment>
```