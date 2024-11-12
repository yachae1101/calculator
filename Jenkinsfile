pipeline {
    agent any
    environment{
       DOCKERHUB_CREDENTIALS = credentials("dockerhub-username-password")
    }
    stages {
        stage("Permission") {
            steps {
                sh "chmod +x ./gradlew"
            }
        }
        stage("Compile") {
            steps {
                sh "./gradlew compileJava"
            }
        }
        stage("Test") {
            steps {
                sh "./gradlew test"
            }
        }
        stage("Test Code Coverage"){
            steps{
                sh "./gradlew jacocoTestCoverageVerification"
                sh "./gradlew jacocoTestReport"
            }
       }
       stage("Gradle Build"){
         steps{
             sh "./gradlew clean build"
         }
       }
       stage("Docker Image Build"){
         steps{
             sh "docker build -t yachae1101/calculator:${env.BUILD_NUMBER} ."
         }
       }
       stage('Docker Hub Login'){
         steps{
             sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
         }
       }
       stage('Docker Hub Push'){
         steps{
             sh "docker push yachae1101/calculator:${env.BUILD_NUMBER}"
         }
       }
       stage('Deploy'){
          steps{
              sh "docker run -d --rm -p 8765:8080 --name calculator yachae1101/calculator:${env.BUILD_NUMBER}"
          }
       }
       stage('Acceptance Test'){
         steps{
             sleep 60
             sh 'chmod +x acceptance_test.sh && ./acceptance_test.sh'
         }
       }
        stage('Clean Up Docker Images') {
            steps {
                script {
                    def imageTag = "${env.BUILD_NUMBER}"
                    def previousTag = (imageTag.toInteger() - 1).toString()

                    // Delete older images locally, keeping only the current and previous build images
                    sh """
                        docker images --filter=reference='yachae1101/calculator:*' --format '{{.Tag}}' | \
                        grep -Ev '^(${imageTag}|${previousTag})\$' | \
                        xargs -I {} docker rmi -f yachae1101/calculator:{}
                    """

                    // Clean up old images on Docker Hub using Docker Hub API
                    sh """
                        curl -s -u ${DOCKERHUB_CREDENTIALS_USR}:${DOCKERHUB_CREDENTIALS_PSW} \
                        "https://hub.docker.com/v2/repositories/yachae1101/calculator/tags/" | \
                        jq -r '.results[].name' | \
                        grep -Ev '^(${imageTag}|${previousTag})\$' | \
                        xargs -I {} curl -X DELETE -u ${DOCKERHUB_CREDENTIALS_USR}:${DOCKERHUB_CREDENTIALS_PSW} \
                        "https://hub.docker.com/v2/repositories/yachae1101/calculator/tags/{}"
                    """
                }
            }
        }
    }
    post{
       always{
           sh 'docker stop calculator'
       }
    }
}