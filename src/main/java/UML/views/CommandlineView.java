package UML.views;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Provides an implementation of the CLI view.
 */
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class CommandlineView implements View {

    @Override
    public void updateClass(String oldName, String newName) {
        System.out.println("Updated class:\n" + newName);
    }

    @Override
    public void createClass(String name) {
        System.out.println("New class:\n" + name);
    }

    @Override
    public void deleteClass(String name) {
        System.out.println("Class deleted");
    }

    @Override
    public String getChoiceFromUser(String msgOne, String msgTwo, ArrayList<String> options) {
        return null;
    }

    @Override
    public String getInputFromUser(String prompt) {
        return null;
    }

    @Override
    public void addListeners(ActionListener fileListener, ActionListener classListener, ActionListener fieldListener,
            ActionListener relationshipListener) {
    }

    @Override
    public void display(ArrayList<String> str) {
        for(String s : str)
        {
            System.out.println("-------------------------------");
            System.out.println(s);    
            System.out.println("-------------------------------");
        }       
    }

    @Override
    public void showError(String error) {
        System.out.println(error);
    }

    @Override
    public String save() {
        System.out.println("Your file has been saved.");
        return "";
    }

    @Override
    public String load() {
        System.out.println("Your file has been loaded.");
        return "";
    }

    @Override
    public void exit() {
        //Needs implmented in controller, here, and GUI.
        System.out.println("Closing application.");
    }

    @Override
    public void start() {
        //Maybe not needed for either?
        printHeader();
    }

    @Override
    public void showPrompt()
    {
        System.out.print("Schwifty-UML> ");
    }
    
    @Override 
    public void showHelp()
    {
        System.out.println("exit:                                                                                            Close CLI");
        System.out.println("help:                                                                                            Show all options");
        System.out.println("display:                                                                                         Display all classes");
        System.out.println("showgui:                                                                                         Displays the GUI");
        System.out.println("addc [class]:                                                                                    Create a class");
        System.out.println("renamec [oldName] [newName]:                                                                     Rename a class");
        System.out.println("deletec [class]:                                                                                 Delete a class");
        System.out.println("addf [class] [type] [name]:                                                                      Create a field");
        System.out.println("renamef [className] [oldName][newName]:                                                          Rename a field");
        System.out.println("deletef [class] [FieldName]:                                                                     Delete a field");
        System.out.println("addm [class] [methodType] [methodName] [[paramType] [paramName] ...]:                            Add a method");
        System.out.println("renamem [class] [methodType] [oldMethodName] [[ParamType] [paramName] ...] [newMethodName]:      Rename a method");
        System.out.println("deletem [class] [methodType] [methodName] [[paramType] [paramName] ...]:                         Delete a method");
        System.out.println("addp");
        System.out.println("deletep [class] [methodType] [methodName] [[paramType] [paramName] ...] [newType] [newName]:     Delete a parameter");
        System.out.println("addr [classFrom] [classTo] [relateType]:                                                         Add a relationship");
        System.out.println("deleter [classFrom] [classTo]:                                                                   Delete a relationship");
        System.out.println("save [fileName]:                                                                                 Saves to passed in file name");
        System.out.println("load [fileName]:                                                                                 Loads the passed in file name");        
    }
    
    /**
     * Prints the cli header.
     */
    private void printHeader() 
    {
        System.out.println("  _____      _             _  __ _");
        System.out.println(" /  ___|    | |           (_)/ _| |");
        System.out.println(" \\ `--.  ___| |____      ___| |_| |_ _   _");
        System.out.println("  `--. \\/ __| '_ \\ \\ /\\ / / |  _| __| | | |");
        System.out.println(" /\\__/ / (__| | | \\ V  V /| | | | |_| |_| |");
        System.out.println(" \\____/ \\___|_| |_|\\_/\\_/ |_|_|  \\__|\\__, |");
        System.out.println("                                      __/ |");
        System.out.println("                                     |___ /");
    }
}