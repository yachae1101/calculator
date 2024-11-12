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
             sh "docker build -t yachae1101/calculator ."
         }
       }
       stage('Docker Hub Login'){
         steps{
             sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
         }
       }
       stage('Docker Hub Push'){
         steps{
             sh "docker push yachae1101/calculator:latest"
         }
       }
       stage('Deploy'){
          steps{
              sh "docker run -d --rm -p 8765:8080 --name calculator yachae1101/calculator"
          }
       }
       stage('Acceptance Test'){
         steps{
             sleep 60
             sh 'chmod +x acceptance_test.sh && ./acceptance_test.sh'
         }
       }
    }
    post{
       always{
           sh 'docker stop calculator'
       }
    }
}