java-service-template
=====================

Is a template project to create runnable services in java.
Using:
	Eclipse
	Hibernate Standalone
	Apache Ivy
	Apache Ant
	Google Guice
	Log4J

config.properties:
	Contains the global project properties
	This file will be stand outside of project jar to simplify changes.
	
	Defaults:
		connection.url - The default database connection string.
		connection.username - The default database connection username.
		connection.password - The default databse connection password.

ivy.xml:
	File that configures the project dependencies (using apache ivy)
