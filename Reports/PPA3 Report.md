# PPA3 Report
##Linter Style Guide Information
I used the github awesome linters link to find a java linter and ended up choosing checkstyle.
 https://github.com/checkstyle/checkstyle  I looked online for some popular xml files and found 
 the google standard https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml
 for java styling. I thought that was a good choice of styling to try to mimic and to test our source files against.
 The TravisCI is set up to throw warnings during the build phase whenever there is a difference between our syntax and
 the expected syntax of the google checks file. 