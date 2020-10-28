package UML.views;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Provides an implementation of the CLI view.
 */
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import UML.controllers.FieldClickController;
import UML.controllers.MouseClickAndDragController;
import java.awt.Dimension;

public class CommandlineView implements View {

    // ================================================================================================================================================
    // Class changing methods.
    // ================================================================================================================================================

    /**
     * Print updated class.
     */
    @Override
    public void updateClass(String oldName, String newName) {
        System.out.println("Updated class:\n" + newName);
    }

    /**
     * Print new class.
     */
    @Override
    public void createClass(String name, int x, int y) {
        System.out.println("New class:\n" + name);
    }

    /**
     * Print deleted class message.
     */
    @Override
    public void deleteClass(String name) {
        System.out.println("Class deleted");
    }

    // ================================================================================================================================================
    // Information displaying methods.
    // ================================================================================================================================================

    /**
     * Print out each class.
     */
    @Override
    public void display(ArrayList<String> str) {
        for (String s : str) {
            System.out.println("-------------------------------");
            System.out.println(s);
            System.out.println("-------------------------------");
        }
    }

    /**
     * Print error message.
     */
    @Override
    public void showError(String error) {
        System.out.println(error);
    }

    /**
     * Print save message.
     */
    @Override
    public String save() {
        System.out.println("Your file has been saved.");
        return "";
    }

    /**
     * Print load message.
     */
    @Override
    public String load() {
        System.out.println("Your file has been loaded.");
        return "";
    }

    /**
     * Print exit message.
     */
    @Override
    public void exit() {
        // Needs implmented in controller, here, and GUI.
        System.out.println("Closing application.");
    }

    /**
     * Run printHeader the cli header.
     */
    @Override
    public void start() {
        printHeader();
    }

    /**
     * Print command list.
     */
    @Override
    public void showHelp() {
        System.out.println(
                "exit:                                                                                            Close CLI");
        System.out.println(
                "help:                                                                                            Show all options");
        System.out.println(
                "display:                                                                                         Display all classes");
        System.out.println(
                "showgui:                                                                                         Displays the GUI");
        System.out.println(
                "addc [class]:                                                                                    Create a class");
        System.out.println(
                "renamec [oldName] [newName]:                                                                     Rename a class");
        System.out.println(
                "deletec [class]:                                                                                 Delete a class");
        System.out.println(
                "addf [class] [type] [name] [public or private or protected]:                                                                      Create a field");
        System.out.println(
                "renamef [className] [oldName][newName]:                                                          Rename a field");
        System.out.println(
                "deletef [class] [FieldName]:                                                                     Delete a field");
        System.out.println(
                "changefa [class] [fieldName] [public or private or protected]:                                   Change field access");
        System.out.println(
                "addm [class] [methodType] [methodName] [[paramType] [paramName] ...]:                            Add a method");
        System.out.println(
                "renamem [class] [methodType] [oldMethodName] [[ParamType] [paramName] ...] [newMethodName]:      Rename a method");
        System.out.println(
                "deletem [class] [methodType] [methodName] [[paramType] [paramName] ...]:                         Delete a method");
        System.out.println(
                "addr [classFrom] [classTo] [relateType]:                                                         Add a relationship");
        System.out.println(
                "deleter [classFrom] [classTo]:                                                                   Delete a relationship");
        System.out.println(
                "save [fileName]:                                                                                 Saves to passed in file name");
        System.out.println(
                "load [fileName]:                                                                                 Loads the passed in file name");
    }

    /**
     * Prints the cli header.
     */
    private void printHeader() {
        System.out.println("  _____      _             _  __ _");
        System.out.println(" /  ___|    | |           (_)/ _| |");
        System.out.println(" \\ `--.  ___| |____      ___| |_| |_ _   _");
        System.out.println("  `--. \\/ __| '_ \\ \\ /\\ / / |  _| __| | | |");
        System.out.println(" /\\__/ / (__| | | \\ V  V /| | | | |_| |_| |");
        System.out.println(" \\____/ \\___|_| |_|\\_/\\_/ |_|_|  \\__|\\__, |");
        System.out.println("                                      __/ |");
        System.out.println("                                     |___/ ");
    }

    // ================================================================================================================================================
    // "Do Nothing" methods
    // ================================================================================================================================================

    @Override
    public void addListener(ActionListener listener) {
        // Do nothing.
    }

    @Override
    public void addListener(MouseClickAndDragController mouseListener, String classText) {
        // Do nothing.
    }

    @Override
    public Dimension getLoc(String name) {
        // Do nothing.
        return null;
    }

    @Override
    public void addRelationship(String from, String to, String type) {
        // Do nothing.
    }

    @Override
    public void deleteRelationship(String from, String to) {
        // Do nothing.
    }

    @Override
    public Map<ArrayList<String>, String> getRelationships() {
        // Do nothing.
        return null;
    }

    @Override
    public JFrame getMainWindow() {
        // Do nothing.
        return null;
    }

    @Override
    public Map<String, JPanel> getPanels() {
        // Do nothing.
        return null;
    }

    @Override
    public String getChoiceFromUser(String msgOne, String msgTwo, ArrayList<String> options) 
    {
        // Do nothing.
        return null;
    }

    @Override
    public String getInputFromUser(String prompt) 
    {
        // Do nothing.
        return null;
    }

    @Override
    public void addListeners(ActionListener fileListener, ActionListener classListener, ActionListener fieldListener,
            ActionListener relationshipListener) 
    {
        // Do nothing.
    }

    @Override
    public void addPanelListener(FieldClickController fieldController, String classText) 
    {
        //Do nothing.
    }
}