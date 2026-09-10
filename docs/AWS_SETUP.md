# AWS Setup

## 1. Create S3 bucket

Choose your region, for example:
ap-south-1

Use a globally unique bucket name such as:
my-raj-aws-ecommerce-artifacts-2026

Keep the bucket private.

## 2. IAM

For the Jenkins identity, grant only the required bucket permissions.
For a simple lab, permissions can include:
- s3:ListBucket on the bucket
- s3:PutObject on the bucket objects
- s3:GetObject on the bucket objects

In production, use a dedicated IAM role and tighter resource policies.

## 3. Jenkins credentials

Install the Pipeline: AWS Steps plugin and AWS-related dependencies as needed.
Create a Jenkins credential with ID:

aws-s3-credentials

Do not place access keys directly in Git.

## 4. Jenkins job

Pipeline -> Definition: Pipeline script from SCM
SCM: Git
Repository URL: your GitHub repository
Branch: */main
Script Path: Jenkinsfile

Run Build Now.

## 5. Verify S3

aws s3 ls s3://YOUR_BUCKET/aws-ecommerce/latest/
aws s3 cp s3://YOUR_BUCKET/aws-ecommerce/latest/aws-ecommerce.jar .

## 6. Production extension

Use:
GitHub -> Jenkins -> Maven/Test -> Artifact -> S3 -> CodeDeploy -> EC2

or:
GitHub -> Jenkins -> Maven/Test -> S3 -> Elastic Beanstalk.

For a web-facing production architecture, put the application behind an ALB,
use private subnets where appropriate, IAM roles instead of long-lived keys,
CloudWatch monitoring, and a database such as RDS.
