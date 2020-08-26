package com.pipeline

import groovy.text.SimpleTemplateEngine

class DefaultTemplateHelper {
    public String JobFolder;
    public String JobName;
    public String JenkinsConfigRepo = this.global.JENKINS_CONFIGURATION_REPO;
    public String GitHostName = this.global.GIT_HOST_NAME;
    public String Branch = "master";
    public String Repository;
    public String TemplateFileName;
    private String JenkinsHome = this.global.JENKINS_HOME;
    def global;

    def GetPipelineScript() {
        def upSteamJobScriptFileLocation = "${this.JenkinsHome}/workspace/${this.JobFolder}/seed_job/upsteam_jobs/default.groovy";
        def templateEngine = new SimpleTemplateEngine();
        def upSteamJobScript = new File(upSteamJobScriptFileLocation)
                .text
                .stripIndent()
                .trim();
        def dataBindingToTemplate = [
                "jenkinsConfigRepo": "${this.JenkinsConfigRepo}",
                "gitHostName"      : "${this.GitHostName}",
                "branch"           : "${this.Branch}",
                "projectRepo"      : "${this.Repository}",
                "jobname"          : "${this.JobName}",
                "template"         : "jenkinsfile/${this.JobName}/templates/${this.TemplateFileName}.groovy",
        ];
        return templateEngine
                .createTemplate(upSteamJobScript)
                .make(dataBindingToTemplate);
    }
}

