#!/usr/bin/env groovy
@Library('pipeline-library') _
import com.joewalker.pipeline.github.*

def pipelineLists = [
        [
                repository: 'joecomscience/spring-boot-demo',
                jobfolder : 'example',
                jobname   : 'spring-boot-demo',
                template  : 'Jenkinsfile',
        ]
].each { item ->
    def projectRepo = item['repository']
    def jobfolder = item['jobfolder']
    def jobname = item['jobname']
    def branch = item.containsKey('branch') ? item['branch'].toLowerCase() : 'master'
    def jobTemplateFile = item.containsKey('template') ? item['template'] : 'Jenkinsfile'

//    def upSteamJobScriptFileLocation = "${JENKINS_HOME}/workspace/${jobfolder}/seed_job/upsteam_jobs/default.groovy"
//    def templateEngine = new SimpleTemplateEngine()
//    def upSteamJobScript = new File(upSteamJobScriptFileLocation)
//            .text
//            .stripIndent()
//            .trim()
//    def dataBindingToTemplate = [
//            "jenkinsConfigRepo": "${JENKINS_CONFIGURATION_REPO}",
//            "gitHostName"      : "${GIT_HOST_NAME}",
//            "branch"           : "${branch}",
//            "projectRepo"      : "${projectRepo}",
//            "jobname"          : "${jobname}",
//            "template"         : "jenkinsfile/${jobfolder}/templates/${jobTemplateFile}.groovy",
//    ]
//    def pipelineScript = templateEngine
//            .createTemplate(upSteamJobScript)
//            .make(dataBindingToTemplate)
    def pipeline = new DefaultTemplateHelper(
            JobFolder: jobfolder,
            JobName: jobname,
            Repository: projectRepo,
            TemplateFileName: jobTemplateFile,
    );
    def pipelineScript = pipeline.GetPipelineScript();

    folder(jobfolder)
    pipelineJob("${jobfolder}/${jobname}") {
        description "Pipeline for ${jobname}"
        disabled(false)
        logRotator(-1, 5)

        properties {
            disableConcurrentBuilds()
            pipelineTriggers {
                triggers {
                    gitlab {
                        branchFilterType("NameBasedFilter")
                        includeBranchesSpec(branch)
                        secretToken("${GITLAB_TOKEN}")
                        triggerOnAcceptedMergeRequest(false)
                        triggerOnApprovedMergeRequest(false)
                        triggerOnPipelineEvent(false)
                        triggerOnMergeRequest(false)
                        triggerOnPipelineEvent(false)
                        triggerOpenMergeRequestOnPush('never')
                    }
                }
            }
        }

        definition {
            cps {
                sandbox(true)
                script(pipelineScript.toString())
            }
        }
    }
}
