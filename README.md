#Query logging in application framework
![logo](doc/Circular-logging-database-DB2.gif)

This repository contain team project from subject _PA036 Database System Project_

#PA036 Project team
* Kundrát, Vladimír(409764)
* Gdovin, Filip(410328)
* Baranová, Zuzana(422364)
* Kamil Triščík(410030)

Project uses business layer from PA165 Java project SEM. 


# SEM
![logo](doc/start.png)


Team project of _PA165 Enterprise Applications in Java_


# Project information
More information about our project you can find in [project Wiki](https://bitbucket.org/KamilTriscik/sem/wiki/Home)



#Former PA165 SEM team
* Matej Majdiš(410246)
* Veronika Aksamitová(410280)
* Vít Hovězák(359488)
* Kamil Triščík(410030)



#How to use application
####How to run application ####
1. `git clone https://github.com/kamil-triscik/PA036.git`
2. `cd logging`
3. `mvn clean install`
3. `cd logging-mvc`
4. `mvn tomcat7:run` - in case of default port **8080** it`s not available, in _pom.xml_ on line 107 set another available port
5. application is available on `http://localhost:8080/pa036`

####How to login ####
* **As super manager**
    * **Login**: _kamil@example.com_(for example)
    * **Password**: _supersilneheslo_
    
* **As basic user**
    * You can use **registration**
