stage('Checkout') {
    steps {
        echo "===== CHECKOUT(SCM) STAGE ====="
        checkout scm
    }
}
