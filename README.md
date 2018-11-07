# proto-lint-maven-plugin

[![Travis CI](https://travis-ci.org/dimitrovvlado/proto-lint-maven-plugin.svg?branch=master)](https://github.com/dimitrovvlado/proto-lint-maven-plugin)
[![codecov](https://codecov.io/gh/dimitrovvlado/proto-lint-maven-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/dimitrovvlado/proto-lint-maven-plugin)

A maven linter plugin for gRPC `.proto` files.

## Usage

Add a plugin declaration to your project's pom file as follows:
```
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>io.github.dimitrovvlado</groupId>
        <artifactId>proto-lint-maven-plugin</artifactId>
        <version>1.0.1</version>
        <executions>
          <execution>
            <goals>
              <goal>lint</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
  ...
</project>
```
The plugin will search recursively for `.proto` files in the source folder of the module. 

Additionally you can configure the path to your `.proto` files as follows:
```
<configuration>
  <protoFiles>
    <protoFile>sample.proto</protoFile>
  </protoFiles>
</configuration>
```
or to a folder:
```
<configuration>
  <protoDirectory>src/main/proto/</protoDirectory>
</configuration>
```

## Dependencies

This plug-in depends on the following third-party libraries:

* com.squareup.wire:wire-schema
* com.squareup.okio:okio
* com.google.guava:guava