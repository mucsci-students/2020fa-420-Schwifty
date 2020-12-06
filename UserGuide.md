## How to use the GUI UML Editor
Once the graphical application is brought up, There will be be a blank window with a menubar showing all current operations of the UML.

**File** ,**Class** , and **State**. 

## File
**Save - Save as:** Creates .json file that can be loaded

**Load:** loads .json file to be edited

**Exit:** exits application

## Class
**Create class:** Opens display prompting for input of class name. Accepts after clicking the okay button.

## State 
**Undo:** Reverses the last change made to the GUI.

**Redo:** Redo a change that was undone by undo.

**CLI:** Shows the CLI 

**Color** Changes background color.

## Class Menu
Menu within a class panel

**Create field:** Prompts the user to select an **access type** as well as provide a **type** and **name**.  

**Edit field:** Select from a drop-box of created fields. Selecting a field and clicking the okay button will open a panel to provide an **access type**, **type**, and **name**. There also exist the option to delete the field.

**Create method:** Prompts the user to select an **access type** as well as provide return type for the method, a method name, parameters. Parameters should be entered as **type** **name** with commas seperating each parameter.  

**Edit method:** Select from a drop-box of created methods. Selecting a method and clicking the okay button will open a panel to provide an **access type**, **type**, **name**, and **parameters**. There also exist the option to delete the field.

**Create relationship** Select from a drop-box of created classes to create a relationship to. 

**Delete relationship** Select from a drop-box of related classes to delete a relationship from. 

**Edit class** Rename or delete the class.

* Using the **scroll wheel** allows the user to zoom in and out.

## How to use the Command Line Interface
If you choose the command line version, use the following commands to interact with the application.
help
exit:                                                                                                                                        Close CLI
help:                                                                                                                                        Show all options
showgui:                                                                                                                                     Displays the GUI
addc [class]:                                                                                                                                Create a class
renamec [oldName] [newName]:                                                                                                                 Rename a class
deletec [class]:                                                                                                                             Delete a class
addf [class] [public or private or protected] [type] [name]:                                                                                 Create a field
renamef [className] [oldName][newName]:                                                                                                      Rename a field
deletef [class] [FieldName]:                                                                                                                 Delete a field
changefa [class] [fieldName] [public or private or protected]:                                                                               Change field access
changeft [class] [fieldName] [newType]:                                                                                                      Change field type
addm [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...]:                                       Add a method
renamem [class] [public or private or protected] [methodType] [oldMethodName] [[ParamType] [paramName] ...] [newMethodName]:                 Rename a method
deletem [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...]:                                    Delete a method
changema [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...] [public or private or protected]:  Change method access
changemt [class] [public or private or protected] [methodType] [methodName] [[paramType] [paramName] ...] [newType]:                         Change method type
addr [classFrom] [classTo] [relateType]:                                                                                                     Add a relationship
deleter [classFrom] [classTo]:                                                                                                               Delete a relationship
save [fileName]:                                                                                                                             Saves to passed in file name
load [fileName]:                                                                                                                             Loads the passed in file name
display [(Optional) className]:                                                                                                              Displays all classes or a single class
displayr:                                                                                                                                    Displays all relationships
undo:                                                                                                                                        Reverts to a previous state
redo:                                                                                                                                        Restores the latest undo
* Pressing the **TAB** key will auto complete commands. 
* Pressing the **Up** key will auto fill with the last command entered. 
* Pressing **Ctrl + L** will clear the screen.