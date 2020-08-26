def defaultPipeline(jobname = null) {
    dir('src') {
        def commitId = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
        def authorName = sh(script: "git --no-pager show -s --format='%an' HEAD", returnStdout: true).trim()

        stage('Install Dependencies') {
            maven.InstallDependency()
        }

        stage('SonarScanner') {
            def projectKey = jobname
            def projectName = jobname
            sonar.Scan(
                    projectKey,
                    projectName,
            )
        }

        stage('DependencyCheck') {
            def project = jobname
            def projectPath = sh(script: "pwd", returnStdout: true).trim()
            owasp.DependencyCheck(
                    project,
                    projectPath,
            )
        }
    }
}

return this