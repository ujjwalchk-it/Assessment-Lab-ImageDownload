# Assessment-Lab-ImageDownload
This code is to download images from URLs which takes URL list as plain text input. It supports both Website address as well as URL with definitive image address.

The code has 3 classes -
  1.Helper.java
  2.ImageDownloader.java
  3.Executor.java
  
 As the name suggests, Helper Class is the internal helper method with supplies the required formatted details to the ImageDownloader Class.ImageDownloader Class is use to download the actual images from ImageURL/Website URL. Executor class is the main class which wires the inputs/connection between the Helper  & ImageDownloader Class.
 
 Pre-Requisites -
   1. Java 1.7 or above.
   2. Maven (latest version recommended 3.6.X)
   
About running the program -

  git clone https://github.com/ujjwalchk-it/Assessment-Lab-ImageDownload.git
  
  cd Assessment-Lab-ImageDownload/assessment
  
  mvn clean install
  
  java -jar target/assessment-0.0.1-SNAPSHOT-jar-with-dependencies.jar 
  
The project has to be built/complied using maven. While running 'mvn clean install' the integrated Junit test would be executed, if the test fails then inspect the error logs and take appropriate actions. 

Once the build is done , you can run the simple 'java-jar XXXFAT.JAR' command to execute the functionality. 
if everything goes fine you will find this in the console -

======================================================================

 java -jar target/assessment-0.0.1-SNAPSHOT-jar-with-dependencies.jar
 
 $Enter the absolute path of the url file :
 
 /some-dir/url-list.txt
 
 $Enter the destination where the images to be saved : 
 
 /some-dir/images

====================================================================== 
 
You will the 'HelperClass' execution starts immediately and after the downloader class. All the logs is self-explainatory on the each of the task thats happening in background. Once all the execution done please go the the download directory and check out the images that are downladed.

The code has been tested in both Windows (10) & Linux env.

Please make sure the url file you are providing having proper access from the user you are running the code and same goes for the download directory (RD/WR permission should be there).

Sample url-list file that contains the list of URLs
(PLEASE PROVIDE ONE URL IN ONE LINE)

https://cricket.yahoo.net/
https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png
https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png
https://i.imgur.com/DfQqM.gif
https://homepages.cae.wisc.edu/~ece533/images/airplane.png
https://homepages.cae.wisc.edu/~ece533/images/cameraman.tif
http://mywebserver.com/images/24174.jpg
http://somewebsrv.com/img/992147.jpg

It would be always good to have some definitive diectory name where the images to be saved.
