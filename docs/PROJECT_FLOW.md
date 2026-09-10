# High-level project flow

1. Developer changes Java code.
2. Developer pushes to GitHub.
3. Jenkins receives a webhook or scheduled build.
4. Jenkins checks out the repository.
5. Maven compiles the code.
6. Maven executes unit tests.
7. Maven creates the Spring Boot JAR.
8. Jenkins archives the JAR.
9. Jenkins uploads the versioned JAR to Amazon S3.
10. The latest JAR is also copied to the S3 latest/ prefix.
11. Optional next stage: AWS CodeDeploy deploys the artifact to EC2.

Example:

GitHub
   |
   v
Jenkins
   |
   +--> Maven compile
   |
   +--> Unit tests
   |
   +--> Maven package
   |
   v
aws-ecommerce.jar
   |
   v
Amazon S3
   |
   v
Optional CodeDeploy
   |
   v
EC2 / Application environment
