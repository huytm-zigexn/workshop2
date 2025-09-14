pipeline {
    agent any
    environment {
        PROJECT_NAME = 'huytm-workshop2'
        REMOTE_HOST = '118.69.34.46'
        REMOTE_PORT = '3334'
        REMOTE_USER = 'newbie'
        REMOTE_PATH = '/usr/share/nginx/html/jenkins'
        WORKSPACE_NAME = 'huytm2'
        RELEASE_DATE = sh(script: 'date +%Y%m%d', returnStdout: true).trim()
    }
    stages {
        stage('Checkout') {
            steps {
                echo "===== CHECKOUT(SCM) STAGE ====="
                checkout scm
            }
        }
        stage('Build') {
            steps {
                echo "===== BUILD STAGE ====="
                sh 'npm install'
            }
        }
        stage('Lint/Test') {
            steps {
                echo "===== LINT/TEST STAGE ====="
                sh 'npm run test:ci'
            }
        }
        stage('Deploy') {
            parallel {
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
                stage('Deploy to Remote Server') {
                    steps {
                        echo "===== DEPLOY TO REMOTE SERVER ====="
                        script {
                            def releaseDir = "${REMOTE_PATH}/${WORKSPACE_NAME}/deploy/${RELEASE_DATE}"
                            withCredentials([file(credentialsId: 'remote-server-ssh-key', variable: 'SSH_KEY')]) {
                                sh """
                                    ssh -o StrictHostKeyChecking=no -i $SSH_KEY -p ${REMOTE_PORT} ${REMOTE_USER}@${REMOTE_HOST} "
                                        mkdir -p \${releaseDir}
                                    "
                                    scp -o StrictHostKeyChecking=no -i $SSH_KEY -P ${REMOTE_PORT} index.html ${REMOTE_USER}@${REMOTE_HOST}:\${releaseDir}/
                                    scp -o StrictHostKeyChecking=no -i $SSH_KEY -P ${REMOTE_PORT} 404.html ${REMOTE_USER}@${REMOTE_HOST}:\${releaseDir}/
                                    scp -o StrictHostKeyChecking=no -i $SSH_KEY -P ${REMOTE_PORT} -r css ${REMOTE_USER}@${REMOTE_HOST}:\${releaseDir}/
                                    scp -o StrictHostKeyChecking=no -i $SSH_KEY -P ${REMOTE_PORT} -r js ${REMOTE_USER}@${REMOTE_HOST}:\${releaseDir}/
                                    scp -o StrictHostKeyChecking=no -i $SSH_KEY -P ${REMOTE_PORT} -r images ${REMOTE_USER}@${REMOTE_HOST}:\${releaseDir}/
                                    ssh -o StrictHostKeyChecking=no -i $SSH_KEY -p ${REMOTE_PORT} ${REMOTE_USER}@${REMOTE_HOST} "
                                        cd \${REMOTE_PATH}/\${WORKSPACE_NAME}/deploy
                                        rm -f current
                                        ln -s \"\${RELEASE_DATE}\" current
                                        ls -1t | grep -E '^[0-9]{8}$' | tail -n +6 | xargs -r rm -rf
                                    "
                                """
                            }
                        }
                    }
                }
            }
        }
    }
    post {
        success {
            echo "===== DEPLOYMENT SUCCESS ====="
            slackSend(
                channel: '#lnd-2025-workshop',
                color: 'good',
                message: ":white_check_mark: *SUCCESS*\n" +
                        ":small_blue_diamond: *User:* ${env.BUILD_USER ?: 'System'}\n" +
                        ":small_blue_diamond: *Job:* ${env.JOB_NAME}\n" +
                        ":small_blue_diamond: *Build:* #${env.BUILD_NUMBER}\n" +
                        ":small_blue_diamond: *Release:* ${RELEASE_DATE}\n" +
                        ":small_blue_diamond: *Firebase:* https://workshop2-488e3.web.app\n" +
                        ":small_blue_diamond: *Remote:* http://${REMOTE_HOST}/jenkins/${WORKSPACE_NAME}/deploy/current/"
            )
        }
        failure {
            echo "===== DEPLOYMENT FAILED ====="
            slackSend(
                channel: '#lnd-2025-workshop',
                color: 'danger',
                message: ":x: *FAILED* \n" +
                        ":small_red_triangle: *User:* ${env.BUILD_USER ?: 'System'}\n" +
                        ":small_red_triangle: *Job:* ${env.JOB_NAME}\n" +
                        ":small_red_triangle: *Build:* #${env.BUILD_NUMBER}\n"
            )
        }
        always {
            echo "===== CLEANUP ====="
            cleanWs()
        }
    }
} 