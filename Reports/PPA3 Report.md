# PPA3 Report
## Linter Style Guide Information
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
 
 ## CI Set Up and Implementation
 Our CI and CD pipeline was built using Travis and contains 4 stages :
 1. **Build and Linting** - During this stage, the project is built and checked against linting rules. If the build fails or
 or any of the linting rules are violated, the pipeline is halted.
 
 2. **Unit testing** - After the build, all of the project's unit tests are ran. 
 If any of the unit test fails, the pipeline is halted.
 
 3. **Integration Testing** - This stage runs all the of the project's integration tests.
  If any of the integration test fails, the pipeline is halted.

4. **Deployment** - This stage deploys the application to our google cloud engine app via configurations 
specified in the .travis.yml file.

A push to the `master` branch serves as the trigger to start the CI/CD pipeline.

## Emergency Deployment Set Up:
The project has three branches that can be used for emergency deployments.
* Pushing to branch `emg-skip-all` will build and deploy the project, without running any unit or integration tests.
* Pushing to branch `emg-skip-unit` will build the project, run all unit test and deploy the application if all unit tests are succesful.
* Pushing to branch `emg-skip-int` will build the project, run all integration tests, and deploy the application.

We chose to go with this emergency deployment implementation because it allows us to quickly the app while choosing a select stage of the 
deployment pipeline we want to go through. Additionally, since we don't override the travis.yml file, doing a regular deployment after an 
emergency deployment can be done by pushing to the `master` branch. 

## Rollback Process:
This project can be rolled back easily to previous versions by switching all traffic on the Google App Engine to a previous version of 
the project. This can be done by deploying a previous version of the app. After deploying a new version of the project, we can easily 
revert to previous versions by changing the default deployed version to a previous version. Travis CI ensures that each consecutive 
update will be deployed, and by visiting https://console.developers.google.com/appengine/versions?project=[YOUR_PROJECT_ID]
we can set any previous version of the project to the default version, or the command “gcloud app services set-traffic [MY_SERVICE] --splits [MY_VERSION]=1” 
can be used to switch to a previous version. In the Google App Engine, each deployment, corresponding to each commit, is assigned an ID, 
which can either be set manually or generated automatically. These can be used to determine which commit to redeploy.
Outside of the Google App Engine, we can manually revert to previous versions by using git reset --hard <old-commit-id> and pushing it. 
This will reset the project to a previous version of the project.
 
## Travis CI Slack Integration
![PPA3 Travis CI Slack Scrnshot](https://user-images.githubusercontent.com/15644940/98496096-ce3e2d80-220e-11eb-9be3-2cec0a2f87a0.PNG)
