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
