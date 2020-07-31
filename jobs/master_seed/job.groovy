#!/usr/bin/env groovy

def entities = [
        [
                name: 'example'
        ]
].each { item ->

    entity = item['name']

    def jobname = 'seed_job'

    folder("${entity}")
    job("${entity}/${jobname}") {
        label('master-node')
        description "Seed Job for ${entity}"
        disabled(false)
        concurrentBuild(false)
        logRotator(-1, 5)

        scm {
            git {
                remote {
                    url("${GIT_HOST_NAME}/${JENKINS_CONFIGURATION_REPO}")
                    credentials('git_credential')
                }
                branch('master')
            }
        }

        steps {
            jobDsl {
                targets("${entity}/seeds/*.groovy")
                sandbox(false)
                ignoreExisting(false)
            }
        }
    }
}