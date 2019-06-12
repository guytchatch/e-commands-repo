node('master') {
 
checkout scm

stage('build') {
    withMaven(jdk: 'default java', maven: 'maven') {
    sh label: '', script: 'mvn clean install'
}
}
}
