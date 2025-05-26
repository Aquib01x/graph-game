# Dijkstra's Graph Game

# Program Description

The application is a game where the user will have to guess the distance between two random nodes, and then the user will be provided with the correct answer against which they can compare their guess. The user will be able to select a username, and after one game is played and a score is given, they can choose to play a successive game, which will keep a record of the total of scores. When comparing their guess, the user will also be able to see the correct path with detailed weights between each path. 

The game was designed with the Model-View-Controller architecture. The Model package handles the creation of the graph using data from a formatted text file, to create the nodes and edges. The View package creates the foundation of a GUI to receive the user input and display the graph in a readable format. The Controller package uses the interfaces and classes from both the Model and the View to bridge the communication barrier between the two packages. The reason for this is that since the program decouples the Model from the View, a third package is necessary to ensure the program can be easily modified with minimal modifications required. As such, each package can be independently modified without having to change the others. The interfaces will also help in the decoupling, as the other classes that will implement these methods will need minimal changes to work efficiently. 

# JGrapht

JGrapht is a Java library for dealing with the creation of nodes and edges in a visual representation for swing.

## Installation

Installation is very easy and quick. 

Access the [JGrapht Library](https://sourceforge.net/projects/jgrapht/), and download the latest version (1.5.1)


Follow these instructions and add the following JARs as libraries in Intellij:
* jgrapht-core
* jgrapht-ext
* jgraphx.

__Instructions__:

Importing JGraphT as a Jar.



1. Download the desired version of JGraphT, and unpack to the desired location, for instance ```<project_folder>/lib/jgrapht-X.Y.Z``` where ```X.Y.Z``` is the JGraphT version number you downloaded.

2. In IntelliJ IDEA, libraries can be defined in the __Project Structure__ dialog. Go to __File | Project Structure__, or press __Ctrl+Shift+Alt+S__

3. To define a project library, select __Libraries__ under the Project Settings section.

4. Click the __green__ + sign, and select __Java__. Browse to the location where you unpacked jgrapht, and select the desired package inside the lib folder, e.g. ```<project_folder>/lib/jgrapht-X.Y.Z/lib/jgrapht-core-X.Y.Z.jar```. Next, click __ok__ twice.

5. Add the following modules as libraries:__jgrapht-core,__ __jgrapht-ext__ and __jgraphx__.

This is what the [Project Structure](https://www.dropbox.com/scl/fi/vvejk32pv3pdoq8c6cil7/Untitled.paper?dl=0&rlkey=1ytoyogepbx4sqcqp5dnpgbfo) should look like after adding the libraries.

You can also find a more detailed version of these instructions at : https://github.com/jgrapht/jgrapht/wiki/Users:-How-to-use-JGraphT-as-a-dependency-in-your-projects
# Testing

## How to run the test package

To run the test package you will need to go to:
* __Model__ -> __Data_Import.java__

  * Comment the __line 24__ which is the path of the normal game data:
    ```
    private final Path path_notJar = Paths.get("src/Data/log2023-01-14 18=13.txt");
    ```

  * Comment out the __line 23__ which is the path of the test game data:
    ```
    private final Path path_notJar = Paths.get("src/Data/test_data.txt");
    ```
  
  * Rebuild the project to register the path change:
  in __Intellij__,simply click: __build__ ->__rebuild  project__ on the navigation bar located at the top of the window.
  * Run the ```JUnit_testing``` test package

# JAR

## How to run the project as a JAR

To run the project as a JAR, you will need the file ```log2023-01-14 18=13.txt``` to be placed __in the same folder__ as the JAR file.

Then, simply execute the JAR and the game will run.

Two image files will be created during the first run of the game.

# Application Overview
![image](https://github.com/user-attachments/assets/809a51b5-de23-485d-b10f-8612a88f8c11)
