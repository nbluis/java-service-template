java-service-template
=====================

Template project to create runnable services in java.

Using:
	Eclipse (with Apache Ivy IDE)
	Hibernate Standalone
	Apache Ivy
	Apache Ant
	Apache commons-lang
	Google Guice
	Log4J
	jUnit
	Mockito

HOW-TO-USE
==========

- Import eclipse project
- Run: Eclipse -> Project -> Ivy -> Resolve
- Read the "CONFIGURING" section
- Be happy!


CONFIGURING
===========

- Project and package names:
	- Replace the project name
	- Replace the package names (src and test source folders)
	- Replace the project name into .project and .classpath files
	- Replace the package names into build.xml

config.properties:
	Contains the global project properties
	This file will be stand outside of project jar to simplify changes.
	
	Defaults:
		connection.url - The default database connection string.
		connection.username - The default database connection username.
		connection.password - The default databse connection password.

ivy.xml:
	File that configures the project dependencies (using apache ivy)
