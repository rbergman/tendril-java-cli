# About

This sample application is provided simply to demonstrate basic 0-legged (password) OAuth login to the [Tendril Connect HTTP APIs](https://dev.tendrilinc.com/docs) as could be implemented in Java.

# Installation

First clone the repo:

    git clone git@github.com:TendrilDevProgram/tendril-java-cli.git

Then, with [Maven](http://maven.apache.org):

    cd tendril-java-cli

Next:

    cp src/main/resources/META-INF/application.properties.sample src/main/resources/META-INF/application.properties

Then at [Tendril's developer site](https://dev.tendrilinc.com), create an app to acquire an OAuth2 app id and key.  Edit application.properties to set these values.  At this point you should be ready to run the application like so:

    mvn exec:java

At the username prompt, enter "kim.deal@tendril.com" (or another user as decribed at the Tendril developer website), and "password" at the password prompt.

Or, run the simple test, as follows:

    mvn test

For a more comprehensive example of a Java client accessing the Tendril APIs, see the [Tendril Cirrus](https://github.com/drichelson/4308Cirrus) project.
