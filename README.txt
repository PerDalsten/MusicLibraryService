Application server versions
===========================

Tomcat 8.5.31
Websphere Liberty 17.0.0.4
Wildfly 11.0.0.Final
Glassfish 4.1.2

Project creation
================

mvn archetype:generate -DgroupId=dk.purplegreen.musiclibrary -DartifactId=MusicLibraryService -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false

Fix pom.xml (add Java Servlet API, fix Junit version, compiler level, etc.) and web.xml (Web app version) in text editor



Logging: Add system property, e.g. for Tomcat:

-Ddk.purplegreen.logdir="$HOME/Development/apache-tomcat-8.5.31/logs"

or in setenv.sh:

export CATALINA_OPTS="$CATALINA_OPTS -Ddk.purplegreen.logdir=../logs"


MySQL: Add system property to override default Derby:

<system-properties><property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/></system-properties> (Wildfly standalone.xml)

-Dhibernate.dialect=org.hibernate.dialect.MySQLDialect (Liberty jvm.properties)

-Dhibernate.dialect=org.hibernate.dialect.MySQLDialect (Tomcat setenv.sh CATALINA_OPTS)

<system-property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"></system-property> (Glassfish domain.xml)
    


JNDI Data Source Setup
======================

Tomcat:

Copy $JAVA_HOME/db/lib/derbyclient.jar $CATALINA_HOME/lib

(and cp $JAVA_HOME/db/lib/derbyLocale*.jar $CATALINA_HOME/lib to avoid warnings).

server.xml:
    <Resource auth="Container" driverClassName="org.apache.derby.jdbc.ClientDriver" maxIdle="10" maxTotal="20" maxWaitMillis="-1" type="javax.sql.DataSource" url="jdbc:derby://localhost:1527/musiclibrarydb" name="jdbc/MusicLibraryDS" username="musiclibrary" password="musiclibrary"  />

(For MySQL use <Resource auth="Container" driverClassName="com.mysql.jdbc.Driver" maxIdle="10" maxTotal="20" maxWaitMillis="-1" name="jdbc/MusicLibraryDS" password="musiclibrary" type="javax.sql.DataSource" url="jdbc:mysql://localhost:3306/musiclibrarydb" username="musiclibrary"/> 
and copy the MySQL JDBC driver jar to lib)


context.xml:
	<ResourceLink  global="jdbc/MusicLibraryDS" name="jdbc/MusicLibraryDS" type="javax.sql.DataSource"/>
	
	
Liberty:

Copy $JAVA_HOME/db/lib/derbyclient.jar  to WLP server lib/derby  

	<library id="DerbyLib">
		<fileset dir="${server.config.dir}/lib/derby" includes="*.jar"/>
	</library>
	<dataSource jndiName="jdbc/MusicLibraryDS">
		<jdbcDriver libraryRef="DerbyLib"/>
		<properties.derby.client createDatabase="false" databaseName="musiclibrarydb" password="{xor}MiosNjwzNj0tPi0m" user="musiclibrary"/>
	</dataSource>	
	
    <cors allowedHeaders="Content-Type" allowedMethods="GET,POST,PUT,DELETE,HEAD,OPTIONS" allowedOrigins="http://localhost" domain="/MusicLibraryService/rest"/>
	
Wildfly:	

Install $JAVA_HOME/db/lib/derbyclient.jar as module/driver 'derby'.

    <datasource jndi-name="java:/jdbc/MusicLibraryDS" pool-name="MusicLibrary">
		<connection-url>jdbc:derby://localhost:1527/musiclibrarydb</connection-url>
        <driver>derby</driver>
        <security>
        	<user-name>musiclibrary</user-name>
            <password>musiclibrary</password>
        </security>
    </datasource>

    
Glassfish:

    <jdbc-connection-pool datasource-classname="org.apache.derby.jdbc.ClientDataSource" name="MusicLibrary" res-type="javax.sql.DataSource">
      <property name="PortNumber" value="1527"></property>
      <property name="Password" value="musiclibrary"></property>
      <property name="ServerName" value="localhost"></property>
      <property name="ConnectionAttributes" value=";create=false"></property>
      <property name="DatabaseName" value="musiclibrarydb"></property>
      <property name="User" value="musiclibrary"></property>
    </jdbc-connection-pool>
    <jdbc-resource pool-name="MusicLibrary" jndi-name="jdbc/MusicLibraryDS"></jdbc-resource>
    