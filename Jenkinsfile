pipeline {
    agent {label 'linux'}
    stages {
        stage('Build') {
                        steps {
                            sh './gradlew clean build'
                        }
                    }
                    stage('Sonar') {
                        steps {
                            withSonarQubeEnv(installationName: 'sonarqube') {
                                sh "./gradlew sonar"
                                }
                            }
                        }
                    }

}