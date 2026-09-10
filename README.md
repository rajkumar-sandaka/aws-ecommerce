# AWS E-Commerce DevOps Demo

A real-world-style Java/Maven e-commerce application used to practice:
GitHub -> Jenkins -> Maven -> Tests -> Package -> S3 artifact deployment.

## Architecture

Developer -> GitHub -> Jenkins -> Maven/Test -> JAR -> AWS S3
                                      |
                                      +-> optional deployment to EC2/Elastic Beanstalk

This starter project deliberately keeps AWS deployment simple: Jenkins uploads the
versioned JAR artifact to S3. You can later add AWS CodeDeploy/EC2 deployment.

## Application

Spring Boot REST API with:
- Product listing
- Product lookup by ID
- Product creation
- Health endpoint

## Requirements

- Java 17+
- Maven 3.8+
- Git
- Jenkins
- AWS account
- S3 bucket
- AWS CLI (recommended)

## Run locally

mvn clean test
mvn spring-boot:run

Then open:
http://localhost:8080/api/products
http://localhost:8080/actuator/health

## Git commands

git init
git add .
git commit -m "Initial AWS ecommerce project"
git branch -M main
git remote add origin https://github.com/<YOUR_USER>/<YOUR_REPO>.git
git push -u origin main

## Jenkins

Create a Pipeline job and point it to your Git repository. Jenkins will read the
Jenkinsfile from the repository.

Create a Jenkins credential:
- Kind: AWS Credentials
- ID: aws-s3-credentials

Set the Jenkins environment variables:
- S3_BUCKET = your unique bucket name
- AWS_REGION = ap-south-1 (or your region)

The pipeline runs:
1. Checkout
2. Maven compile
3. Unit tests
4. Package JAR
5. Archive artifact in Jenkins
6. Upload JAR to S3

## Important

Do not hard-code AWS access keys in the Jenkinsfile or source code.
Use Jenkins Credentials or an IAM role. The IAM principal needs only the S3
permissions required for this demo.

Example minimal S3 permissions:
- s3:PutObject
- s3:GetObject
- s3:ListBucket

## Optional next step

For a more production-like setup, deploy the JAR from S3 to an EC2/Tomcat or
Elastic Beanstalk environment using AWS CodeDeploy.
