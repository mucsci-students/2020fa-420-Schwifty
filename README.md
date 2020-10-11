# UML Editor

## Running the UML Editor
To run the application, navigate to the project directory. Using Gradle, run the command **gradle clean run** to start the application. A interface will load asking you to choose between a graphical or command line view. Instructions for each are below. 

## Requirements
* Java 8
* Gradle

## How to use the GUI UML Editor
Once the graphical application is brought up, There will be be a blank window with a menubar showing all current operations of the UML.
**File**, **Class**, **Field**, and **Relate**. 

## File
**Save - Save as:** Creates .json file that can be loaded

**Load:** loads .json file to be edited

**Exit:** exits application

## Class
**Create class:** Opens display prompting for input of class name. Accepts after clicking the okay button.

**Delete class:** Select from a drop-box of created classes. Selecting class and clicking the okay button will delete the class.

**Rename class:** Select from a drop-box of created classes. 
Selecting class and clicking the okay button will open a prompt to rename the class. Clicking the okay after will change the name.

*Each of the following operations will result in the display updating concurrently to changes made.

## Attribute
**Create attribute:** Select from a drop-box of created classes. Selecting class and clicking the okay button will open two prompts to create an attribute under this class. This prompt includes a **type** and a **name**.  

**Delete attribute:** Select from a drop-box of created classes. Selecting class and clicking the okay button will open a drop-box of attributes under that class. Clicking okay after selecting an attribute will delete it.  

**Rename attribute:** Select from a drop-box of created classes. Selecting class and clicking the okay button will open a drop-box of attributes under that class. Clicking okay after selecting an attribute will open a prompt to rename the attribute.

*Each of the above operations will result in the display updating concurrently to changes made.

## Relate
**Association - Aggregation - Composition - Generalization:** Select from two drop-boxes of created classes to create the selected relation ship type.  

**Delete Relationship:** Select from two drop-boxes of created classes to delete the relationship between two classes

*Each of the above operations will result in the display updating concurrently to changes made.

## Command line commands
If you choose the command line version, use the following commands to interact with the application. 
exit:                                                                                            Close CLI
help:                                                                                            Show all options
display:                                                                                         Display all classes
showgui:                                                                                         Displays the GUI
addc [class]:                                                                                    Create a class
renamec [oldName] [newName]:                                                                     Rename a class
deletec [class]:                                                                                 Delete a class
addf [class] [type] [name]:                                                                      Create a field
renamef [className] [oldName][newName]:                                                          Rename a field
deletef [class] [FieldName]:                                                                     Delete a field
addm [class] [methodType] [methodName] [[paramType] [paramName] ...]:                            Add a method
renamem [class] [methodType] [oldMethodName] [[ParamType] [paramName] ...] [newMethodName]:      Rename a method
deletem [class] [methodType] [methodName] [[paramType] [paramName] ...]:                         Delete a method
addr [classFrom] [classTo] [relateType]:                                                         Add a relationship
deleter [classFrom] [classTo]:                                                                   Delete a relationship
save [fileName]:                                                                                 Saves to passed in file name
load [fileName]:   


## Developers
* Dominic D
* Tyler B
* Chris S
* Drew W
* Cory J
