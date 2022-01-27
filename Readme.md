# README #

+ Imagine you are working on the design for the software controlling banking ATM machines. Using UML prepare a Class Diagram (and if you have time add some explanation notes)
We are not looking at full details (attributes, methods), but rather the general ability to see and present a full picture with the most important classes and their associations


+ Develop software code for the application described below in the Directory Scanner section and instructions on how to run it.
When evaluating this task we will specifically look into:

How structured and readable code is

How correctly it fulfills the requirements

If you do not have enough time to finish all the requirements from the task – it’s OK, but state clearly which part has been implemented

If the software does not fulfill the requirements in the most performing way using the fast algorithms – it’s OK. What we need is a good structured, extendable, maintainable first iteration even if it implements only a part of the requirements.

# Directory Scanner

Requirement is to develop a Java application, which will scan a filesystem directory and copy all files that match a certain mask to
another output directory. The application should support several command line parameters:
* input - Path to input directory to be scanned
* output - Path to output directory, where files will be copied
* mask - File names mask, which is used to detect required files
* waitInterval - Time interval with which application poll the filesystem
* includeSubfolders - Turns on/off scanning of subfolders within input folder (if turned on, original folders
structure should also be recreated in output folder)
* autodelete - Turns on/off automatic deleting of copied files
 
The application should support multithreading and be launched from a command line with following syntax:

    C:>java my.app.DirectoryScannerConsole
    >scan –input “C:/test/in” –output “C:/test/out” –mask “*test*.xml”
    –waitInterval 60000 – includeSubfolders false –autoDelete true

    >scan –input “C:/test1/in” –output “C:/test1/out” –mask “*test1*.xml”
    –waitInterval 90000 – includeSubfolders true –autoDelete false
    …

    >exit

    c:>;

Where after the launch and application waits for user to enter commands. There are 2 possible commands that user can enter:
* scan – starts a new thread for processing files with entered parameters. It should be possible to start multiple threads.
* exit – stops all currently running threads and stops the application

All running scanners should write log file with sufficient level of information (developer should define required level of logging
granularity).
The application should display readable error messages if some of the parameters are incorrect. E.g.:

    >scan –input “C:/test/in” –output “C:/test/out” –mask “*test*.xml” –waitInterval AAA – includeSubfolders BBB –autoDelete true
    Error: Scanner was not started due to following reasons:
        Invalid value for waitInterval
        Invalid value for includeSubfolders
    >

All running scanners should write log file with sufficient level of information (developer should define required level of logging
granularity).

What will be evaluated?
* Clear, readable and well documented (using comments) code
* Unit tests coverage
* Use of OOP principles

Any other improvements will be welcome!