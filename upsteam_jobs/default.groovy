#!/usr/bin/env groovy

@Library('pipeline-library') _

node(label: 'docker') {
    stage('Refetch Script') {
        checkout([
                changelog: false,
                poll     : false,
                scm      : [
                        '\$class'                        : 'GitSCM',
                        branches                         : [
                                [
                                        name: '*/master'
                                ]
                        ],
                        doGenerateSubmoduleConfigurations: false,
                        extensions                       : [
                                [
                                        '\$class'        : 'RelativeTargetDirectory',
                                        relativeTargetDir: 'jenkinsfile'
                                ],
                                [
                                        '\$class': 'IgnoreNotifyCommit'
                                ],
                                [
                                        '\$class': 'WipeWorkspace'
                                ]
                        ],
                        submoduleCfg                     : [],
                        userRemoteConfigs                : [
                                [
                                        credentialsId: 'github_credential',
                                        url          : '$gitHostName/$repository' + ('.git' as java.lang.CharSequence)
                                ]
                        ]
                ]
        ]);
    }

    stage('Checkout Source Code') {
        checkout([
                changelog: true,
                scm      : [
                        '\$class'                          : 'GitSCM',
                          branches                         : [
                                  [
                                          name: '*/$branch'
                                  ]
                          ],
                          doGenerateSubmoduleConfigurations: false,
                          extensions                       : [
                                  [
                                          // set timeout when clone huge repo
                                          '\$class' : 'CloneOption',
                                            timeout: 60
                                  ],
                                  [
                                          '\$class'           : 'RelativeTargetDirectory',
                                            relativeTargetDir: 'src'
                                  ],
                                  [
                                          '\$class': 'WipeWorkspace'
                                  ]
                          ],
                          submoduleCfg                     : [],
                          userRemoteConfigs                : [
                                  [
                                          credentialsId: 'github_credential',
                                          url          : '$gitHostName$repository' + '.git'
                                  ]
                          ]
                ]
        ]);
    }

    def jf = load('$template');
    jf.defaultPipeline('$jobname');
}
