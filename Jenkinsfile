pipeline {
    agent {label 'linux'}
     stages {
        stage('Build') {
            steps {
                bat './gradlew clean build'
            }
        }
     }
}