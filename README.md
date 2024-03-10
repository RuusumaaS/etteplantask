# Servicemanual - Etteplan backend coding task

This readme-file will contain instruction on how to start this API and also some sidenotes at the end for the examiner.

## Download necessary components: Java, Maven and PostgreSQL.

In order to use this application you need to build the project with Maven. You will also need JDK.
These instructions work at least on Windows. I have never done this on MacOS or Linux.
Before building you also need to download PostgreSQL, PostgreSQL's java driver JBDC and set up the project.

If you already have installed JDK, Maven, PostgreSQL and JBDC, you can skip the download parts.

**Download JDK and Maven.**

1. For Java JDK downloads visit website https://www.oracle.com/java/technologies/downloads/#jdk21-windows.
    There you have downloads for Linux, MacOS and Windows. This link take you to the Windows-download.
2. After downloading JDK set your environment variables.
    - To global environment variables create JAVA_HOME with path to your jdk folder. With JDK 21 the path should be C:\Program Files\Java\jdk-21.
    - Add to Path-variable path to the bin folder which is located in the C:\Program Files\Java\jdk-21 -directory.
3. Now download Maven from: https://maven.apache.org/download.cgi. Instructions for Maven download: https://maven.apache.org/install.html.
4. Like in JDK, add Maven's bin-folder to Path-variable. 
5. Now you can test both Maven and JDK with commands "java -version" and "mvn -v" in your terminal.

**Download and set up PostgreSQL locally**

1. Download PostgreSQL version 16.2 from https://www.enterprisedb.com/downloads/postgres-postgresql-downloads.
    - Should work with older ones but always best to download the newest.
2. Run the installer. Do not uncheck the chekcboxes which determine whta gets downloaded.
3. Installer will ask which port you want PostgreSQL to listen. You may choose port as long as it is not 8080. The default port is 5432 which is defined in the project so I recommend going with that.
4. You will also need to provide a password to your PostgreSQL.
5. When installing is complete check the box that wants to open Stack builder and press finish.
6. In stack builder, choose from dropdownmenu your local port. Then click next. From Categories open Database drivers and choose pgJBDC. Then continue to download.
7. Add to path environment variable path to your PostgreSQL's bin file. Should be C:\Program Files\PostgreSQL\16\bin.
8. Now log in to PostgreSQL with command "psql -U postgres". It will ask for the password you chose during installation. (Postgres is default super user.)
9. You may create a new user/role with command: CREATE USER <YOUR_USERNAME> WITH PASSWORD '<YOUR_PASSWORD>';
10. Create database with command: CREATE DATABASE <YOUR_DATABASE_NAME> OWNER <YOUR_USERNAME>; (You may use postgres as owner or create new role as stated in before.)
11. Now you have created the database. What you will now need is to remember the port PSQL listens, database's name and owner and owner's password.

**Setting up the project before building**

If you have donwloaded the necessary components, you may now follow these steps to run teh project.

1. Open application.properties from servicemanual-java\src\main\resources folder.
2. Change/add the info for the these lines:
    - spring.datasource.url=jdbc:postgresql://localhost:<PORT_PSQL_LISTENS_DEFAULT_5432>/<YOUR_DATABASE_NAME>
    - spring.datasource.username=<YOUR_DATABASE_OWNER>
    - spring.datasource.password=<DATABASE_OWNER'S_PASSWORD>
3. When you have given correct information, you will be able to build the project in terminal.
4. Move to project directory (directory that contains pom.xml -file of the project).
4. Run command "mvn clean package" to build package.
5. Now move to /target -directory and run the .jar-file. The jar-file should be called service-manua-0.1.0.jar. Run the file with command "java -jar service-manual-0.1.0.jar".
6. Now the project should be listening port 8080 and you should be able to send requests to it. I recommend using POSTMAN.

## How to use API

To find documentation for API request go to http://localhost:8080/swagger-ui/index.html when application is running. There is Swagger-documentation for the API.


## Notes for the examiner

Hi! I created a new package, maintenancetask, where the code I create is located. I did very little changes to factorydevice. Most notably I changed the FactoryDeviceNotFoundException to public so I can use it in the new package.

Currently, maintenancetasks registrationtime doesn't change whe tasks are modified. I have implemented comment lines that would change the registration times. Also, since I assumed that the code should give tasks their registration times, you don't need to add registartion times to the requestbody when using post-request.

The application will fill the database when you start it. It will use the seeddata.csv you provided to fill the factorydevices. I also made a small csv for maintenance tasks. Data of those files will only be saved in the database if the tables empty. If you don't want to use that data, you may either wipe the code from ServiceAppilacation's CommandLineRunner or comment it before first use.

About testing, I did some unit-tests but mostly I did manual testing. In University testing is not common. Also, Spring boot was completely new to me, so I didin't know exactly how to test everything. For example, orderby's I had to test manually. In order for the unit-tests to work, there needs to be one factorydevice with id 1 in the factory_device table. Therefore if you don't want to have all seeddata in the repo, you still need at least one device.

If you have any questions, contact me by email or phone. 

