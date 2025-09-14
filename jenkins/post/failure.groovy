failure {
    echo "===== DEPLOYMENT FAILED ====="
    slackSend(
        channel: '#lnd-2025-workshop',
        color: 'danger',
        message: ":x: *FAILED* \n" +
                ":small_red_triangle: *User:* ${env.BUILD_USER ?: 'System'}\n" +
                ":small_red_triangle: *Job:* ${env.JOB_NAME}\n" +
                ":small_red_triangle: *Build:* #${env.BUILD_NUMBER}\n" +
    )
}
