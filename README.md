# ByteReaver 0.1

ByteReaver is a program that crawls webpages within a domain to retrieve information. Unlike many other spiders, this one uses Google Chrome Driver instead of a headless browser and can thus see content that is rendered dynamically in javascript. The program writes the information into a .txt file that is designed to be easily imported into excel, as data is delimited by commas and newline characters. Simply copy and paste into a spreadsheet and split the new column into additional columns.

### How to Use It

The program will require an installation of Java, and a Unix command line (Terminal) to run the program. ByteReaver has not yet been tested on Windows or Linux.

On MacOS, you can launch an instance of Terminal by finding the icon in the Launchpad in the "Other" section.

![screenshot of options section of Launchpad](https://i.imgur.com/xWSSo9X.png)

To check if Java is installed on your computer, type the following into Terminal.

`java -version`

If a version number is not displayed, visit [this page](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and follow the installation instructions. It is also recommended that you upgrade Java if you are not using version 1.8 or higher.

After verifying that Java is on your machine, ensure that all files and folders are in place. The Chrome Driver executable (v 2.29) should be included in the root of the repository as a 'chromedriver' Unix executable, and should work with any Chrome Drivers v2.29 or higher. It must be in the same directory as the .jar to work.

If the installation was performed properly, and if the terminal window was navigated to the correct directory, one would type something like the following to initiate ByteReaver:

`java -jar ByteReaver.jar http://www.example.com 123 345`

Note that if an error like this is received:
`Error: Unable to access jarfile Test.jar`
 it means that the terminal window is not in the same directory as the jar file. Please see [this tutorial](http://www.westwind.com/reference/os-x/commandline/navigation.html) to learn how to navigate using the terminal if you are unsure how to change directory in Terminal.

In the initialization command, the '123' and '345' represent parameters telling the program the maximum number of contacts to extract and the largest number of queries to be made, respectively. These parameters will default to 100 and 1,000, respectively.

You will know that the program initiated successfully if a new instance of chrome appears on your screen and if a new file was created in the root directory of ByteReaver.

### Additional Features

ByteReaver writes files dynamically, as data are received. If the program is interrupted in the middle of a crawl, contacts will not be lost, and post-run statistics will still be calculated!

### Future Plans

The current design is focused on retrieving contact information and keywords through regex functions although there are many more functionalities that may be implemented in the future, along with some sort of GUI.

Binary search has just been implemented for quicker searching through filters, although customizability of the filters is still lacking. In future designs, users will have the ability to load custom filters into the program.
