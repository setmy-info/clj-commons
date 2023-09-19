# clj-commons

## Development

Test

```shell
lein test
```

Build documentation

```shell
lein codox
```

Build

```shell
lein uberjar
```

Deploy to Clojars repository

```shell
lein deploy clojars
```

Run

With lein:

```shell
lein run ./src/test/resources/root
```

With built uberjar:

```shell
java -jar ./target/uberjar/clj-commons-1.0.0-SNAPSHOT-standalone.jar ./src/test/resources/root
```

## TODO

1. Codox to generate also documentation for private functions.
2. Codox: document map content once, for many functions and linking to that (like @see in JavaDocs)
