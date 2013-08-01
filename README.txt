java-service-template
=====================

Template project to create runnable services in java.

Using:
	Eclipse (with Apache Ivy IDE)
	Hibernate Standalone
	Apache Ivy
	Apache Ant
	Google Guice
	Log4J
	jUnit

HOW-TO-USE
==========

- Import eclipse project
- Run: Eclipse -> Project -> Ivy -> Resolve
- Read the "CONFIGURING" section
- Be happy!


CONFIGURING
==========

config.properties:
	Contains the global project properties
	This file will be stand outside of project jar to simplify changes.
	
	Defaults:
		connection.url - The default database connection string.
		connection.username - The default database connection username.
		connection.password - The default databse connection password.

ivy.xml:
	File that configures the project dependencies (using apache ivy)
