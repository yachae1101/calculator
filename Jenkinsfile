pipeline {
    agent any
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
       stage("Docker Build"){
          steps{
              sh "sudo docker build -t jenkinspipeline ."
         }
       }
    }
}