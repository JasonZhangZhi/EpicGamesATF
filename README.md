# EpicGamesATF ReadMe File
Repository for Java-Selenium Automation Framework - EpicGames ATF Project

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

To run the project locally, please make sure the following are downloaded and installed (if applicable):

** 1. [Java SE Development Kit 8u271] **

	https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
	Follow the instruction to download and install on your local machine.	

** 2. [Apache Maven 3.6.3] **

	http://maven.apache.org/download.cg
	Follow the instruction to download and install on your local machine.

** 3. [Eclipse IDE for Java Developers] **

	http://www.eclipse.org/downloads/eclipse-packages
	Follow the instruction to download and install the latest version on your local machine.

** 4. [GitHub Desktop] **

	https://desktop.github.com/
	Follow the instruction to download and install on your local machine.
	This is optional, you can use git command line or other tools.  

### Setup the Local Repository
	
** 1. [Install TestNG in Eclipse] **

	Eclipse >> Help >> Install New Software
	Work with: "http://dl.bintray.com/testng-team/testng-eclipse-release/" >> Add
	Name: "TestNG" >> Add >> Waiting for TestNG option is showing up.
	Follow the instruction to install TestNG in Eclipse.

** 2. [Clone "EpicGamesATF" repository from GitHub] **

	Open https://github.com/zhangzhi02117@gmail.com/EpicGamesATF
	Click "Code" button >> "Open with GitHub Desktop"
	Follow up the instruction to clone the repository to your local Eclipse workspace folder. 

** 3. [Import "EpicGamesATF" as Existing Maven Project to Eclipse] **

	Eclipse >> File >> Import >> Existing Maven Projects >> Next >> Select root directory >> Finish
	Root Directory should be your eclipse-workspace folder 
	e.g: Users/username/eclipse-workspace/EpicGamesATF

** 4. [Setup chrome.binary.version for each platform in POM.xml] **

SETUP the correct binary version based on your local Chrome version. e.g. Every time when the local Chrome browser is upgraded, the corresponding chrome.binary.version need to be updated as well. 

    <chrome.binary.version.windows>91.0.4472.101</chrome.binary.version.windows>
    <chrome.binary.version.mac>90.0.4430.24</chrome.binary.version.mac>
    <chrome.binary.version.linux>89.0.4389.23</chrome.binary.version.linux>


** 5. [Maven install the project] **

	Eclipse >> EpicGamesATF >> pom.xml  -- right click it >> Run As >> Maven Install.

	BUILD SUCCESS - Should be showing up finally. 

### Running the tests

** 1. To run individual test: **

	Eclipse >> src/test/java within package explorer
	Right mouse click any test (.java file) within any test package >> Run As >> TestNG Test

** 2. To run group of tests within the test suit: **

	Eclipse >> resources
	Right mouse click any suite (.xml file) >> Run As >> TestNG Suite

** 3. To run regression test suite: **

	Eclipse >> src/main/resources >> TestNG-EpicGames-RegressionTestsuite.xml
	- Right click the file >> Run As >> TestNG suite

** 4. Check the test report: **

	Eclipse >> test-output
	1. emailable-report.html
	There are more detailed infomation added to this report. 

	2. index.html
	The generic testNG report that shows the brief information of the test. 

	
******************** The End of README ***********************	