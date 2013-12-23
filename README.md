Ragel URL Parser
================

## Summary

This project contains a Ragel language file that provides a robust URL Java parser.
 
Requirements
============

You need [Ragel](http://www.complang.org/ragel/). You can get your platform binaries from the website URL or build from the source using gcc.

Parser Generation
=================

Manual
------

To generate the URL Java parser, change directory at the location of the `URLParser.rl` file and execute the following command-line (whereas the ragel executable should be available in your environment *PATH* variable):
```
ragel -J URLParser.rl
```

Maven
-----

You will need Maven **3.0.4** at minimum (Ragel compilation does not work on lower versions). Execute the following command-line at the project's root directory:

```
mvn compile
```

The generated class should be located in the `target/generated-sources/ragel/org/caron/ragel/url` directory.

Usage
=====

```
String url = ...;
Url parsedUrl = urlParser.parse(url);
System.out.println(parsedUrl);
```

Tests
=====

To execute the tests and verify the coverage of this grammar, you can dig into the `UrlParserTest` class and execute it with Maven:

```
# To get the XML report
mvn test

# To get the HTML report, the site phase should be executed due to [this bug](http://jira.codehaus.org/browse/SUREFIRE-616))
mvn site
```

Alternative
===========

If you dont need a robust implementation, this regexp might be enough:

```
String regex = "^(([^:/?#.]+):)?(//)?(([^:/]*)?(\\:([^/]*))?\\@)?(([^/:]+)|\\[[^/\\]]+\\])?(:(\\d*))?(/[^?#]*)(\\?([^#]*))?(#(.*))?";
Pattern p = Pattern.compile(regex);
Matcher m = p.matcher(INPUT); // get a matcher object
```

Whereas each capturing groups would correspond to:

```
$2  = scheme
$5  = username
$7  = password
$8  = server
$11 = port
$12 = path
$14 = query
$16 = fragment
```

Unit-tests for this regular expression is provided, if you want to verify the support coverage or add your own tests.