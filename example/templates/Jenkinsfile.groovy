def defaultPipeline(jobname = null) {
    dir('src') {
        def commitId = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
        def authorName = sh(script: "git --no-pager show -s --format='%an' HEAD", returnStdout: true).trim()

        stage('Install Dependencies') {
            maven.InstallDependency()
        }
        stage('Check quality') {
            def projectKey = jobname
            def projectName = jobname

            sonar.Scan(
                    projectKey: "${projectKey}",
                    projectName: "${projectName}",
            )
        }
        stage('Build') {
            maven.Build()
        }
    }
}

return this