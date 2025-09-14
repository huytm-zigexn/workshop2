stage('Lint/Test') {
    steps {
        echo "===== LINT/TEST STAGE ====="
        sh 'npm run test:ci'
    }
}
