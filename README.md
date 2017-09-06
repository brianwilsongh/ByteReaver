#ByteReaver 0.1

ByteReaver is a program that crawls webpages within a domain to retrieve information. Unlike many other spiders, this one uses chromium instead of a headless browser and can thus see content that is rendered dynamically in javascript. The program writes the information into a .txt file that is designed to be easily imported into excel, as data is delimited by commas and newline characters. Simply copy and paste into a spreadsheet and split the new column into additional columns.

###How to Use It

The program will require an installation of Java, and the ability to execute the main .jar file from the command line. The chromium executable should be included in the repository, although you can supply your own if you wish. It must be in the same directory as the .jar and it must be

To initiate with a java path of "java", one would type something like the following:

`java -jar ByteReaver.jar http://www.example.com 123 345`

'123' and '345' represent parameters telling the program the maximum number of contacts to extract and the largest number of queries to be made, respectively. These parameters will default to 100 and 1,000, respectively.

###Additional Features

ByteReaver writes files dynamically, as data are received. If the program is interrupted in the middle of a crawl, contacts will not be lost, and post-run statistics will still be calculated!

###Future Plans

The current design is focused on retrieving contact information and keywords through regex functions although there are many more functionalities that may be implemented in the future, along with some sort of GUI. I also intend to speed up searching through the filters with a binary tree when I get the chance.
