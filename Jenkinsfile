pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-south-1'
        S3_BUCKET  = 'REPLACE_WITH_YOUR_BUCKET'
        APP_NAME   = 'aws-ecommerce'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn -B package -DskipTests'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Upload to S3') {
            when {
                expression {
                    return env.S3_BUCKET != 'REPLACE_WITH_YOUR_BUCKET'
                }
            }
            steps {
                withAWS(credentials: 'aws-s3-credentials', region: "${AWS_REGION}") {
                    sh '''
                        VERSION="${BUILD_NUMBER}"
                        aws s3 cp target/aws-ecommerce.jar \
                          "s3://${S3_BUCKET}/${APP_NAME}/${VERSION}/aws-ecommerce.jar"
                        aws s3 cp target/aws-ecommerce.jar \
                          "s3://${S3_BUCKET}/${APP_NAME}/latest/aws-ecommerce.jar"
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully.'
        }
        failure {
            echo 'Build failed. Check the failed stage and console log.'
        }
    }
}
