CENTROGEO Interactive Dashboard Team 27

The CSS/HTML interface can be found in : src/main/webapp/ directory, the main part of the user interface is the homepage.html file.

Installation Instructions:

	1) Open the database.java class located in src/main/java/model/ and change the database specification in the url, username and password variables. The database has to use a postgresql DBMS.
	2) Run Maven Install on the project.
	3) Add the server to a Tomcat 8.5 server and run the server.

The main page of the Dashboard can be accessed through root/CentroGeo/homepage.html 

A simulation file has to be uploaded before any chart can be generated (use the following steps):
	1) Download a SUMO simulation zip file from google drive (as zip) : https://drive.google.com/drive/u/1/folders/1TTkGniZty5Rb5uhOJCs3LPG3mMrI7lGm
	2) Run the server and got to the homepage. Then go to : Upload Simulation -> Select Simulaiton Zip -> Upload.
	3) After the file is uploaded a message will be displayed, refresh the page, the create chart function can be used.