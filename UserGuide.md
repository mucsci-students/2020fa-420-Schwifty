## How to use the GUI UML Editor
Once the graphical application is brought up, There will be be a blank window with a menubar showing all current operations of the UML.

**File** ,**Class** ,**Field** , and **Relate**. 

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

## Field
**Create field:** Select from a drop-box of created classes. Selecting class and clicking the okay button will open two prompts to create an attribute under this class. This prompt includes a **type** and a **name**.  

**Delete field:** Select from a drop-box of created classes. Selecting class and clicking the okay button will open a drop-box of attributes under that class. Clicking okay after selecting an attribute will delete it.  

**Rename field:** Select from a drop-box of created classes. Selecting class and clicking the okay button will open a drop-box of attributes under that class. Clicking okay after selecting an attribute will open a prompt to rename the attribute.

**Create method:** Select from a drop-box of created classes. Selecting class and clicking the okay button will prompt the user to enter a return type for the method, a method name, and the number of parameters. This will prompt the user to enter a type and name for the number of parameters chosen. 

**Delete method:** Select from a drop-box of created classes. Selecting class and clicking the okay button will bring up a list of methods from that class. You can then choose which to delete. 

**Rename method:** Select from a drop-box of created classes. Selecting class and clicking the okay button will bring up a list of methods from that class. You can then choose which to rename.
*Each of the above operations will result in the display updating concurrently to changes made.

## Relate
**Realization - Aggregation - Composition - Generalization:** Select from two drop-boxes of created classes to create the selected relation ship type.  

**Delete Relationship:** Select from two drop-boxes of created classes to delete the relationship between two classes

*Each of the above operations will result in the display updating concurrently to changes made.

## Command line commands
If you choose the command line version, use the following commands to interact with the application. 
* exit:                                                                                            Close CLI
* help:                                                                                            Show all options
* display:                                                                                         Display all classes
* showgui:                                                                                         Displays the GUI
* addc [class]:                                                                                    Create a class
* renamec [oldName] [newName]:                                                                     Rename a class
* deletec [class]:                                                                                 Delete a class
* addf [class] [type] [name]:                                                                      Create a field
* renamef [className] [oldName][newName]:                                                          Rename a field
* deletef [class] [FieldName]:                                                                     Delete a field
* addm [class] [methodType] [methodName] [[paramType] [paramName] ...]:                            Add a method
* renamem [class] [methodType] [oldMethodName] [[ParamType] [paramName] ...] [newMethodName]:      Rename a method
* deletem [class] [methodType] [methodName] [[paramType] [paramName] ...]:                         Delete a method
* addr [classFrom] [classTo] [relateType]:                                                         Add a relationship
* deleter [classFrom] [classTo]:                                                                   Delete a relationship
* save [fileName]:                                                                                 Saves to passed in file name
* load [fileName]:   

