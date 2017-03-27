java-service-template
=====================

Template project to create runnable services in java.

Using:
- Gradle
- Hibernate Standalone
- C3P0
- Jadira User Types
- Apache commons-lang
- Google Guice
- Log4J
- jUnit
- Mockito

HOW-TO-USE
==========

- Rename project folder
- Rename project name 'rootProject.name' on 'settings.gradle' file
- Rename mainClassName on 'build.gradle' file
- Generate ide project files (ex. gradle eclipse)
- Read the "CONFIGURING" section.
- Be happy!


CONFIGURING
===========

- Injection:
	- Any classes inside of Manager and respectively, can use @Inject and will be handled by Guice.

- Hibernate:
	- Configure hibernate.cfg.xml.

- Dependencies:
	- Abuse and use of the gradle file.

config.properties:
	Contains the global project properties
	This file will be stand outside of project jar to simplify changes.
	
	Defaults:
		connection.url - The default database connection string.
		connection.username - The default database connection username.
		connection.password - The default databse connection password.
