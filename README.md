# big-bank-cfi-suite-sw-testing-team-9
big-bank-cfi-suite-sw-testing-team-9 created by GitHub Classroom

## Execution instructions:
### Docker:

From the project root, run `docker-compose up` and the project modules will start in the following ports:
- frontend `3000`
- backend `8080`
- database `27017`


## Continous Integration:
Our CI pipeline uses **GitHub Actions** and has the following jobs:

1. Build
2. Unit Test
3. Integration Test
4. End-to-End Test

After the commit passes through the pipeline, it is added to our staging environment.
Our staging environment is the master branch of our project.


## Admin Login:
- email: admin
- password: admin


## Link to deployed Apps:
frontend: 
backend: 
