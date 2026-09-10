#!/bin/bash
set -e

: "${S3_BUCKET:?Set S3_BUCKET first}"
: "${AWS_REGION:?Set AWS_REGION first}"

APP_NAME="aws-ecommerce"
VERSION="${BUILD_NUMBER:-manual}"

mvn -B clean package -DskipTests

aws s3 cp target/aws-ecommerce.jar \
  "s3://${S3_BUCKET}/${APP_NAME}/${VERSION}/aws-ecommerce.jar" \
  --region "${AWS_REGION}"

echo "Uploaded ${APP_NAME} version ${VERSION} to S3."
