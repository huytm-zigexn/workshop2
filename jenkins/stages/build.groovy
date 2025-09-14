stage('Build') {
    steps {
        echo "===== BUILD STAGE ====="
        sh 'npm install'
    }
}
