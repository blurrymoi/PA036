#Query logging in application framework
![logo](doc/Circular-logging-database-DB2.gif)

This repository contain team project from sobject _PA036 Database System Project_

#Project team
* Kundrát, Vladimír(409764)
* Gdovin, Filip(410328)
* Baranová, Zuzana(422364)
* Kamil Triščík(410030)

Project use bussiness layer from another java project SEM. 


# SEM
![logo](doc/start.png)


Team project of _PA165 Enterprise Applications in Java_


# Project information
More information about our project you can find in [project Wiki](https://bitbucket.org/KamilTriscik/sem/wiki/Home)



#Gold team
* Matej Majdiš(410246)
* Veronika Aksamitová(410280)
* Vít Hovězák(359488)
* Kamil Triščík(410030)



#How to use application
####How to run application ####
1. `git clone https://github.com/m-mato/sem.git`
2. `cd sem`
3. `mvn clean install`
3. `cd sem-mvc`
4. `mvn tomcat7:run` - in case of default port **8080** it`s not available, in _pom.xml_ on line 107 set another available port
5. application is available on `http://localhost:8080/pa165`

####How to login ####
* **As super manager**
    * **Login**: _kamil@example.com_(for example)
    * **Password**: _supersilneheslo_
    
* **As basic user**
    * You can use **registration**
