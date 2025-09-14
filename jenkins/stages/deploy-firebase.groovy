stage('Deploy to Firebase') {
    steps {
        echo "===== DEPLOY TO FIREBASE ====="
        // withCredentials([file(credentialsId: 'firebase_adc', variable: 'GOOGLE_APPLICATION_CREDENTIALS')]) {
        //     sh '''
        //         export GOOGLE_APPLICATION_CREDENTIALS=$GOOGLE_APPLICATION_CREDENTIALS
        //         firebase deploy --only hosting --project=${PROJECT_NAME}
        //     '''
        // }
        withCredentials([file(credentialsId: 'legacy_token', variable: 'FIREBASE_TOKEN')]) {
            sh '''
                export FIREBASE_TOKEN=$FIREBASE_TOKEN
                firebase deploy --token "$FIREBASE_TOKEN" --only hosting --project=${PROJECT_NAME}
            '''
        }
    }
}
