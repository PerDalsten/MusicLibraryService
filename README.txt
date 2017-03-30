Project creation
================

mvn archetype:generate -DgroupId=dk.purplegreen.musiclibrary -DartifactId=MusicLibraryService -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false

Fix pom.xml (add Java Servlet API, fix Junit version, compiler level, etc.) and web.xml (Web app version) in text editor



Logging: Add system property, e.g. for Tomcat:

-Ddk.purplegreen.logdir="$HOME/Development/apache-tomcat-8.0.36/logs"



JNDI Data Source Setup
======================

Tomcat:

Copy $JAVA_HOME/db/lib/derbyclient.jar $CATALINA_HOME/lib

(and cp $JAVA_HOME/db/lib/derbyLocale*.jar $CATALINA_HOME/lib to avoid warnings).

server.xml:
    <Resource auth="Container" driverClassName="org.apache.derby.jdbc.ClientDriver" maxIdle="10" maxTotal="20" maxWaitMillis="-1" type="javax.sql.DataSource" url="jdbc:derby://localhost:1527/musiclibrarydb" name="jdbc/MusicLibraryDS" username="musiclibrary" password="musiclibrary"  />

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
