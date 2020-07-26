Neptune - Tool for migrating database schema from one server to another
=============

This is a personal project that aims to help me practice how to retrieve database metadata using JDBC. At the current status
it is only performing database metadata export function.

## Requirements

The following requirements are necessary to use this project:
- Docker
- Java 11
- Gradle

## Creating the postgresql image for unit testing

Run the following command to create the postgresql container used by the current unit test:

```
$ cd docker/postgres
$ docker image build -t neptune-psql:latest .
```

## Build

In order to build this project just simply:

```
$ gradle clean build
```

## Test

The existing tests need to be improved and for now to execute them, simply type:

```
$ gradle test
```

Since this project depends on a database to be used, it relies on creating Docker containers to spin up a database to be 
used during the test execution. For such, it relies on [Test containers](https://www.testcontainers.org) library to do so.

