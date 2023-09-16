pipeline {
    agent {label 'linux'}
    stages {
            stage('Build') {
                steps {
                    sh './gradlew clean build'
                }
            }
         }
}