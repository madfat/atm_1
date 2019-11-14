# ATM Simulation
This repository was created in order to implement the requirement on https://github.com/Mitrais/java-bootcamp-working/wiki/ATM-Simulation with some adjustment changes to reflect the consistency behaviour
## The finalized code can be looked at the tags:
- Case 1 is on tag [stage1](https://github.com/madfat/atm_1/tree/stage1)
- Case 2 is on tag [stage2](https://github.com/madfat/atm_1/tree/stage2)
## How to build and run ATM Simulation using command line
- Open command line
- Clone the repository: ```git clone https://github.com/madfat/atm_1.git atm_simulation```
- Get inside the project directory: ```cd atm_simulation```
- Checkout the tagged code: ```git checkout stage2```
- Compile all the *.java resources: ```javac src/cdc/atm/*.java```
- Create Manifest and jar file: 
  - ```echo Main-Class: src.cdc.atm.Atm >manifest.txt```
  - ```jar cvfm Atm.jar manifest.txt src/cdc/atm/*.class src/cdc/atm/**/*.class```
- run the application: ```java -jar Atm.jar```
