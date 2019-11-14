# ATM Simulation
This repository was created in order to implement the requirement on https://github.com/Mitrais/java-bootcamp-working/wiki/ATM-Simulation with some adjustment changes to reflect the consistency behaviour
## How to build and run ATM Simulation using command line
- Open command line (git bash)
- Clone the repository: ```git clone https://github.com/madfat/atm_1.git atm_simulation```
- Get inside the project directory: ```cd atm_simulation```
- Checkout the tagged code: ```git checkout stage1_after_review```
- Compile all the *.java resources: ```javac src/cdc/atm/*.java```
- Create Manifest and jar file: 
  - ```echo Main-Class: src.cdc.atm.Atm >manifest.txt```
  - ```jar cvfm Atm.jar manifest.txt src/cdc/atm/*.class src/cdc/atm/**/*.class```
- run the application: ```java -jar Atm.jar```
