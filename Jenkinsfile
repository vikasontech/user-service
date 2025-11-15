pipeline {
    agent any

    environment {
        REGISTRY = "vikason"
        APP_NAME = "user-service"
        REPOSITORY = "https://github.com/vikasontech/user-service.git"
    }
    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch to build')
    }
    triggers {
        // Poll repo every 1 minute
        pollSCM('H/1 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    def branch = params.BRANCH_NAME ?: 'main'
                    echo "Checking out branch: ${branch}"

                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/${branch}"]],
                        userRemoteConfigs: [[
                            url: REPOSITORY,
                            credentialsId: 'vikas-github'
                        ]]
                    ])
                }
            }
        }
        
        stage('Get Git Info') {
            steps {
                script {
                    // Get git tag and store in environment variable
                    def gitTag = sh(
                        script: "git describe --tags --exact-match 2>/dev/null || echo ''",
                        returnStdout: true
                    ).trim()

                    echo "Git tag: ${gitTag}"
                    env.GIT_TAG = gitTag
                    
                    // Also get commit hash as fallback
                    def gitCommit = sh(
                        script: "git rev-parse --short HEAD",
                        returnStdout: true
                    ).trim()
                    
                    echo "Git commit: ${gitCommit}"
                    env.GIT_COMMIT = gitCommit
                }
            }
        }

        stage('Build JAR') {
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-11'
                    args '-v /var/jenkins_home/.m2:/root/.m2'
                    // Reuse the node to keep the workspace
                    reuseNode true
                }
            }
            steps {
                echo "Starting Maven build..."
                sh "mvn clean package spring-boot:repackage -DskipTests"
                sh "ls -lh target/"
                stash includes: 'target/*.jar', name: 'jar-files'
                echo "Maven build finished"
            }
        }
        
        stage('Docker Login') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-vikason', usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PASS')]) {
                        sh '''
                            echo "$DOCKERHUB_PASS" | docker login -u "$DOCKERHUB_USER" --password-stdin
                        '''
                    }
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    // Get Git tag from environment variable set in Checkout stage
                    def gitTag = env.GIT_TAG ?: ''
                    
                    // Decide environment based on branch
                    def branch = params.BRANCH_NAME ?: env.GIT_BRANCH ?: "unknown"
                    def environment = (branch == "main") ? "prod" : "staging"

                    // Tag formats
                    def versionedTag = gitTag ? "${environment}_${gitTag}" : "${environment}_${BUILD_NUMBER}"
                    def latestTag = "${environment}_latest"

                    def versionedImage = "${REGISTRY}/${APP_NAME}:${versionedTag}"
                    def latestImage = "${REGISTRY}/${APP_NAME}:${latestTag}"
                    def buildNumberImage = "${REGISTRY}/${APP_NAME}:${BUILD_NUMBER}"

                    // Unstash jar files
                    unstash 'jar-files'
                    
                    sh """
                        ls -lh target/
                        docker buildx build \
                            --platform linux/amd64,linux/arm64 \
                            -t ${versionedImage} -t ${latestTag} -t ${buildNumberImage} \
                            --push .
                    """
                }
            }
        }
    }
}