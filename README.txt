Project creation
================

mvn archetype:generate -DgroupId=dk.purplegreen.musiclibrary -DartifactId=MusicLibraryService -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false

Fix pom.xml (add Java Servlet API, fix Junit version, compiler level, etc.) and web.xml (Web app version) in text editor




Logging: Add system property, e.g. for Tomcat:

-Ddk.purplegreen.logdir="/home/per/Development/apache-tomcat-8.0.36/logs"