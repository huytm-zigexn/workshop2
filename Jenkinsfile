pipeline {
    agent any
    environment {
        load 'jenkins/env.groovy'
    }
    stages {
        load 'jenkins/stages/checkout.groovy'
        load 'jenkins/stages/build.groovy'
        load 'jenkins/stages/lint-test.groovy'
        stage('Deploy') {
            parallel {
                load 'jenkins/stages/deploy-firebase.groovy'
                load 'jenkins/stages/deploy-remote.groovy'
            }
        }
    }
    post {
        load 'jenkins/post/success.groovy'
        load 'jenkins/post/failure.groovy'
        load 'jenkins/post/always.groovy'
    }
} 