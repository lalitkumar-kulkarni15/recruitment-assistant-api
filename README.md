# recruitment-assistant-api

# Instructions for users

1) If you are using any IDE like Eclipse , STS or Intellij idea,
   Kindly import this source code as a maven project. As maven is
   used as a build tool.
2) After importing the source code as a maven project, kindly set up the
   IDE workspace by setting up the JDK and maven directory structure.
3) Once the workspace is set up then please perform maven clean and install
   by using the command mvn clean install( Please note that JDK and maven are
   pre-requisites for running this project.
4) Once step 3 is in progress all the maven dependencies will get downloaded and
   you should see a build success message from maven.
5) After the successfull completion of maven clean install build lifecycle it is
   now time to get the API started. To do so (In case you are using any of the IDE),
   kindly run \src\main\java\com\recruitment\assistant\RecruitmentAssistantApiApplication.java
   class as a Java application. Since this class is the main class of the application.
6) After the successfull completion of step 5, you should get an application successfully
   started messaged in the console. If you are able to see this message Great..!!! now we are
   good to use the API.
7) Once the application is ready to use and up and running kindly open the web browser and
   visit the URL : http://localhost:9098/swagger-ui.html. ( On the machine where this application is running )
8) After visiting the url mentioned in point 7, you should be able to see the swagger ui page. Swagger is a great
   API documentation and quick test tool which will have a list of the API present in this application, with the
   usage details like endpoint urls, status codes that will be returned in specific scenarios and also the request and
   response structure of the API.
9) What all activities can a user do after visiting the swagger ui page? The user can get complete information about
   the API available along with a ready to use tool where the user can quickly enter the api request hit the API over
   http and get the http response on the same page to get more information about how the API works before actual integrating
   with the application.

That's all about importing and running the application.



