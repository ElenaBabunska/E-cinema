pipeline{
    agent any
    tools{
        maven 'maven_3_8_1'
    }
    stages{
        stage('Build Maven'){
            steps{
            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/ElenaBabunska/E-cinema']]])
            sh 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t elemajka/e-cinema .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                    sh 'docker login -u elemajka -p ${dockerhubpwd}'
                    }
                    sh 'docker push elemajka/e-cinema'
                }
            }
        }



    }
}