## Installation Guide 

This document gives the instructions to install the Rasta PMS, i.e., the POI managament system.

## Local installation
### System Requirements
The software requirements can be summarized as:

* Microsoft Windows, Mac OS X or Unix Operating Systems and derived are supported
* Latest OpenJDK 21 (or other Java distribution like Eclipse Adoptium https://adoptium.net/) is recommended. 
* Latest [Apache Maven](https://maven.apache.org/) installed.
* A MySQL installed. For Mac OS or Windows, the [MAMP](https://www.mamp.info/) product contains MySQL.

### Create a MySQL Database
Create a user and a database with the following data:
```
username= rastapms
password= rastapms
database= rastapms
```
The user should have the right permission to access and modify the database. You can use a terminal or `phpmyadmin` if installed.
Run the `rasta-pms.sql` sql script file contained in the `database` folder.

If username, password, and database are different, please update the file `application-prod.yml` accordingly.   

### Start Rasta PMS application
To launch the Rasta PMS application, run these maven commands that will start the application on the port `8080`:
1. Open a terminal in the directory where you downloaded the source code and type the following command:

    ```
    mvn clean compile
    ```

2. Open a terminal in the directory where you downloaded the source code and type the following command: 


    ```
    mvn spring-boot:run
    ```
3. Open a browser and type the following URL. 


    ```
    http://localhost:8080/rastapms/
    ```
## Docker installation
### System Requirements
Docker has to be installed to your machine

### Start Rasta PMS application
To launch the Rasta PMS application, run this docker command that will create a container containing a MySQL installation and the Rasta PMS application on the port `8080`:
1. Open a terminal in the directory where you downloaded the source code and type the following command:

    ```
    docker compose up
    ```
2. Open a browser and type the following URL.


    ```
    http://localhost:8080/rastapms/