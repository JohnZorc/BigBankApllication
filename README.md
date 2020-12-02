# big-bank-cfi-suite-sw-testing-team-9
big-bank-cfi-suite-sw-testing-team-9 created by GitHub Classroom

## Execution instructions:
### Docker:
__After compiling the project__, run the following: 

1. `gradle build -x test`
2. `docker build -t bbapp -f ./docker/Dockerfile .`
4. `docker run -p 27017:27017 -d mongo:latest `
3. `docker run -p 8080:8080 bbap`

### Vagrant:  
#### Setting up Vagrant 
Download and install our repository. The Vagrantfile includes commands for setting up the 
virtual machine and installing Java JDK8.
#### Commands
##### Vagrant init
Initializes Vagrant
##### Vagrant up
Starts the virtual machine and runs the vagrantfile
##### Vagrant ssh 
Use to log into your vitual machine.

##### Running Tests
All of the tests will be included in a tests folder. Navigate to the test folder and compile the test file you wish to run using javac, then run that test file using the java command. 

## Link to deployed application:
http://35.193.68.187:8080/


