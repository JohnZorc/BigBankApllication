# PPA3 Report
##Linter Style Guide Information
I used the github awesome linters link to find a java linter and ended up choosing checkstyle.
 https://github.com/checkstyle/checkstyle  I looked online for some popular xml files and found 
 the Google standard https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml
 for java styling. I thought that was a good choice of styling to try to mimic and to test our source files against. 
 Implementing all the styling that the Google standard calls for however was too big of a task so I chose just a few of the 
 styling standards they have. 
 1. Added a max line length
 2.A common tab width
 3. Checks that each top-level class resides in a source file of its own.
 4. Adopted standard of left curly going on new line after class declaration 
 5. Also that the right curly is on a line of its own.
 6. Empty catch blocks are to be suppressed
 7. Checked that class names have a specific format specified by a regex in the module
 The TravisCI is set up to throw errors during the build phase whenever there is a difference between our syntax and
 the expected syntax of the Big_Bank.xml file