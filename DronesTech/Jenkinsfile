pipeline {

    agent { any { image 'maven:3.9.5' } }
    stages {
        stage('build') {
            steps {
            
            echo 'maven clean install completed'
            
                bat 'mvn -f pom.xml clean install'
                


            }
        }
        
        stage('deploy') {
                    steps {   
       
                
    //            timeout(time: 2, unit: "MINUTES") {
                            input message: "Deploy to production?", ok: "Yes"
     //                   }

            }
        }

  }
}