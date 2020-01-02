# ATM Simulation
This repository was created in order to implement the requirement on https://github.com/Mitrais/java-bootcamp-working/wiki/ATM-Simulation with some adjustment changes to reflect the consistency behaviour
## The finalized code can be looked at the tags:
- Case 1 is on tag [stage1](https://github.com/madfat/atm_1/tree/stage1)
  - decided to create new branch from tag stage1 to apply the feedback changes [here](https://github.com/madfat/atm_1/tree/stage1_after_review)
- Case 2 is on tag [stage2](https://github.com/madfat/atm_1/tree/stage2)
  - decided to create new branch form tag stage2 to apply the feedback changes [here](https://github.com/madfat/atm_1/tree/stage2_after_review)
- Case 3 is on tag [stage3](https://github.com/madfat/atm_1/tree/stage3)
  - database: postgresql
  - db name: atm
  - initial account data (account no/pin): 112233/121212 and 112244/111111
- Case 4 is still on development branch [stage4](https://github.com/madfat/atm_1/tree/stage4)
  - database: postgresql
  - db name: atm
  - created 200 initial accounts and some transaction.
    - accounts: ```112000``` until ```112119```, all has password ```121212```
  - provided a branch that contains docker to encapsulate the application and the database, [```branch4_docker```](https://github.com/madfat/atm_1/tree/stage4_docker)
## How to build and run ATM Simulation using command line
### docker 
This will spin up the database, it uses port 5433
- Open command line (git bash)
- Clone the repository: ```git clone https://github.com/madfat/atm_1.git atm_simulation```
- Get inside the project directory: ```cd atm_simulation```
- Checkout the tagged code: ```git checkout stage4_docker```
- run the apps: ```docker-compose up``` 
### non docker
It's assumed the local environment already has all the support development things
- Open command line (git bash)
- Clone the repository: ```git clone https://github.com/madfat/atm_1.git atm_simulation```
- Get inside the project directory: ```cd atm_simulation```
- Checkout the tagged code: ```git checkout stage4```
- Build the run application, using profile ```local```: ```mvn spring-boot:run -Dspring-boot.run.profiles=local```
