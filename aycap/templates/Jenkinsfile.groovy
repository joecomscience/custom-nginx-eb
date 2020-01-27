def defaultPipeline(region=null, awsProfile=null, applicationId=null) {
  dir('src') {
    def commitId          = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
    def authorName        = sh(script: "git --no-pager show -s --format='%an' HEAD", returnStdout: true).trim()
    
    stage('Install Dependencies') {
      println("joewalker ${commitId}-${authorName}")
    }

    stage('Check quality') {
      sh """
        /home/sonar-scanner/bin/sonar-scanner -Dsonar.projectKey=spring-boot-demo -Dsonar.sources=src -Dproject.settings=./src/sonar.properties -Dsonar.host.url=http://host.docker.internal:9000
      """
    }
    stage('Unit Test') {}
    stage('Integration Test') {}
    stage('E2E Test') {}
    stage('Build & Push Image') {}
    stage('Deploy') {}
  }
}

return this;