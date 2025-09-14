stage('Deploy to Remote Server') {
    steps {
        echo "===== DEPLOY TO REMOTE SERVER ====="
        script {
            def releaseDir = "${REMOTE_PATH}/${WORKSPACE_NAME}/deploy/${RELEASE_DATE}"
            withCredentials([file(credentialsId: 'remote-server-ssh-key', variable: 'SSH_KEY')]) {
                sh """
                    ssh -o StrictHostKeyChecking=no -i $SSH_KEY -p ${REMOTE_PORT} ${REMOTE_USER}@${REMOTE_HOST} "
                        mkdir -p ${releaseDir}
                    "
                    scp -o StrictHostKeyChecking=no -i $SSH_KEY -P ${REMOTE_PORT} index.html ${REMOTE_USER}@${REMOTE_HOST}:${releaseDir}/
                    scp -o StrictHostKeyChecking=no -i $SSH_KEY -P ${REMOTE_PORT} 404.html ${REMOTE_USER}@${REMOTE_HOST}:${releaseDir}/
                    scp -o StrictHostKeyChecking=no -i $SSH_KEY -P ${REMOTE_PORT} -r css ${REMOTE_USER}@${REMOTE_HOST}:${releaseDir}/
                    scp -o StrictHostKeyChecking=no -i $SSH_KEY -P ${REMOTE_PORT} -r js ${REMOTE_USER}@${REMOTE_HOST}:${releaseDir}/
                    scp -o StrictHostKeyChecking=no -i $SSH_KEY -P ${REMOTE_PORT} -r images ${REMOTE_USER}@${REMOTE_HOST}:${releaseDir}/
                    ssh -o StrictHostKeyChecking=no -i $SSH_KEY -p ${REMOTE_PORT} ${REMOTE_USER}@${REMOTE_HOST} "
                        cd ${REMOTE_PATH}/${WORKSPACE_NAME}/deploy
                        rm -f current
                        ln -s ${RELEASE_DATE} current
                        ls -1t | grep -E '^[0-9]{8}$' | tail -n +6 | xargs -r rm -rf
                    "
                """
            }
        }
    }
}
