node{
    stage('SCM Checkout'){
        git 'https://github.com/madfat/atm_1'
    }
    stage('Compile-Package'){
        // get maven home path
        def mvnHome = tool name: 'maven-1', type: 'maven'
        sh "${mvnHome}/bin/mvn package"
    }
}