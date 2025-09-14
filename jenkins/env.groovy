environment {
    PROJECT_NAME = 'huytm-workshop2'
    REMOTE_HOST = '118.69.34.46'
    REMOTE_PORT = '3334'
    REMOTE_USER = 'newbie'
    REMOTE_PATH = '/usr/share/nginx/html/jenkins'
    WORKSPACE_NAME = 'huytm2'
    RELEASE_DATE = sh(script: 'date +%Y%m%d', returnStdout: true).trim()
}
