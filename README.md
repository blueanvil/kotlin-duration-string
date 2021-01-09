# kotlin-duration-string
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Build Status](https://travis-ci.com/blueanvil/kotlin-duration-string.svg?branch=master)](https://travis-ci.com/blueanvil/kotlin-duration-string)

A tiny Kotlin library for dealing with human-readable duration strings like `2h 45m 50s`

# Gradle
```
repositories {
    maven { url 'https://jitpack.io' }
}
```
```
dependencies {
    implementation 'com.github.blueanvil:kotlin-duration-string:1.0'
}
```

# Features
The tool is designed as two Kotlin extension functions for parsing and formatting respectively.

The precision of both operations goes from days down to milliseconds. For example `24d 3h 45m 56s 345ms`.
```
"16d 4h 35s 782ms".toDuration()
Duration.ofDays(16).toHumanReadableString()
```
## Parsing
* Converts a human readable format like `5h 30m` to a Java `Duration` object
* Accepts two types of text:
  * Time units are constrained to 24/60/60/1000: `24d 3h 45m 56s 345ms`
  * Time units are unconstrained, but only one unit is allowed: `1034h` or `837429ms`
#### Examples
_For more examples see [DurationParseTest.kt](https://github.com/blueanvil/kotlin-duration-string/blob/master/src/test/kotlin/com/blueanvil/durationstr/DurationParseTest.kt)_
```kotlin
"16d 4h 35s 782ms".toDuration()
"16d 04h 035s 0782ms".toDuration()
"56m 13s 923ms".toDuration()
"12h 0m 3s".toDuration()
"12h 3s".toDuration()

"94h".toDuration()
"324489ms".toDuration()
```

## Printing
* Skips units which are `0` at the beginning and end of the String: you will get `16h 0m 27s`, not `0d 16h 0m 27s 0ms`
* Only prints the output in the 24/60/60/1000 constrained format
#### Examples
```kotlin
// "16d"
Duration.ofDays(16)
    .toHumanReadableString() 

// "16d 23h 45m"
Duration.ofDays(16)
    .plus(Duration.ofHours(23))
    .plus(Duration.ofMinutes(45))
    .toHumanReadableString()

// "16d 0h 0m 12s 345ms"
Duration.ofDays(16)
    .plus(Duration.ofSeconds(12).plus(Duration.ofMillis(345)))
    .toHumanReadableString()
```

# License Information
The code is licensed under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

