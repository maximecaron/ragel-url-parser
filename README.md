Ragel URL Parser
================

## Summary

This project contains a Ragel language file that provides a robust URL Java parser.
 
Requirements
============

You need [Ragel](http://www.complang.org/ragel/). You can get your platform binaries from the website URL or build from the source using gcc.

Parser Generation
=================

To generate the URL Java parser, change directory at the location of the `URLParser.rl` file and execute the following command-line (whereas the ragel executable should be available in your environment *PATH* variable):
```
ragel -J URLParser.rl
```

Usage
=====

```
String url = ...;
Url parsedUrl = urlParser.parse(url);
System.out.println(parsedUrl);
```
