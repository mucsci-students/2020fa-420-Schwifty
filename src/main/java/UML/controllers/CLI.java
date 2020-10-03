package UML.controllers;

/*
    Author: Chris, Tyler, Drew, Dominic, Cory. 
    Date: 09/24/2020
    Purpose: 
 */
import java.io.Console;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import java.util.ArrayList;
import UML.model.*;
import UML.model.Store;
import UML.views.GraphicalView;
import UML.views.View;

public class CLI {

    private Options options;
    private Store store;
    private View view;
    private Controller controller;

    public CLI(Store s, View v, Controller c, Scanner console) {
        store = new Store();
        this.store = s;
        this.view = v;
        this.controller = c;

        // printHeader();
        try {
            cliLoop(console);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // System.out.print("Schwifty-UML> ");
    }

    /**
     * As long as the user hasn't typed in exit, continue the app.
     */
    private void cliLoop(Scanner console) throws ParseException {
        while (true) {
            String nextLine = console.nextLine();
            String[] line = nextLine.split(" ");
            parse(line);
        }
    }

    /**
     * Parses a command line argument in order to call the correct method.
     */
    private void parse(String[] line) {

        if(line[0].equals("exit")){
            exit();
        }
        else if (line[0].equals("help")) {
            help();
        } else if (line[0].equals(("display"))) {
            display();
        } else if (line[0].equals(("showgui"))) {
            showGUI();
        } else if (line[0].equals("addc")) {
            addClass(line);
        } else if (line[0].equals("renamec")) {
            renameClass(line);
        } else if (line[0].equals("deletec")) {
            deleteClass(line);
        } else if (line[0].equals("addf")) {
            addField(line);
        } else if (line[0].equals("renamef")) {
            renameField(line);
        } else if (line[0].equals("deletef")) {
            deleteField(line);
        } else if (line[0].equals("addm")) {
            addMethod(line);
        } else if (line[0].equals("renamem")) {
            renameMethod(line);
        } else if (line[0].equals("deletem")) {
            deleteMethod(line);
        } else if (line[0].equals("addp")) {
            addParameter(line);
        } else if (line[0].equals("deletep")) {
            deleteParameter(line);
        } else if (line[0].equals("deleter")) {
            deleteRelationship(line);
        } else if (line[0].equals("save")) {
            save(line);
        } else if (line[0].equals("chungus")) {
            chungus();
        } else if (line[0].equals(("load"))) {
            load(line);
        } else {
            System.out.println("That is not a valid command.");
        }
    }

    //
    // Abstract checking if class
    // exists.******************************************************************************
    //
    /**
     * Exits from the CLI.
     */
    private void exit() {
        System.out.println("Closing application.");
        System.exit(0);
    }

    /**
     * Displays help menu.
     */
    private void help() {
        helpPage();
    }

    /**
     * Displays the current structure of the models.
     */
    private void display() {
        store.stringOfClasses();
    }

    // Displays the GUI for the user.
    private void showGUI() {
        Store s = new Store();
        GraphicalView v = new GraphicalView();
        Controller c = new Controller(s, v);
        v.start();
        c.addListeners();
    }

    /**
     * Adds a class to the store.
     */
    private void addClass(String[] args) {
        controller.createClass(args[0]);
    }

    /**
     * Renames a class in the store.
     */
    private void renameClass(String[] args) {
        controller.renameClass(args[0], args[1]);
    }

    /**
     * Deletes a class in the store.
     */
    private void deleteClass(String[] args) {
        controller.deleteClass(args[0]);
    }

    /**
     * Adds a field to a class in the store.
     */
    private void addField(String[] args) {
        controller.createField(args[0], args[1], args[2]);
    }
    /**
     * Calls the controller to add a field to the given class.
     */
    private void renameField(String[] args) {
        controller.renameField(args[0], args[1], args[2]);
    }

    /**
     * Deletes a field from a class in the store.
     */
    private void deleteField(String[] args) {
        controller.deleteField(args[0], args[1]);
    }

    /**
     * Adds a method to a class in the store.
     */
    private void addMethod(String[] args) 
    {
        ArrayList<String> params = new ArrayList<String>();
        
        if ((args.length - 4) % 2 != 0) 
            System.out.println("Invalid arguments");
        
        for (int counter = 3; counter < args.length; counter += 2) 
            params.add(args[counter] + " " + args[counter + 1]);

        controller.createMethod(args[0], args[1], args[2], params);
    }
    
    /**
     * Renames a method in a class in the store.
     */
    private void renameMethod(String[] args) {
        ArrayList<String> params = new ArrayList<String>();
        if ((args.length - 4) % 2 != 0) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 3; counter < args.length - 1; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.renameMethod(args[0], args[1], args[2], params, args[args.length - 1]);
    }

    /**
     * Deletes a method from a class in the store
     */
    private void deleteMethod(String[] args) {
        ArrayList<String> params = new ArrayList<String>();
        if ((args.length - 3) % 2 != 0) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 3; counter < args.length; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.deleteMethod(args[0], args[1], args[2], params);

    }

    /**
     * Adds a given parameter to a method
     */    /**
     * Adds a parameter to a given method
     */
    private void addParameter(String[] args) {
        ArrayList<String> params = new ArrayList<String>();
        if ((args.length - 5) % 2 != 0) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 3; counter < args.length - 2; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
    }
    /**
     * Deletes a given parameter from a methodDeletes a parameters from a method from a class in the store.
     */
    private void deleteParameter(String[] args) {

    }
    /**
     * Creates a relationship between two classes Adds a relationship bewteen two classees in the store.
     */
    private void addRelationship(String[] args) {
        controller.addRelationship(args[0], args[1], RelationshipType.valueOf(args[2].toUpperCase()));
    }
    
     /**
     * Deletes a relationship created between two classes
     */
    private void deleteRelationship(String[] args) {
        
        controller.deleteRelationship(args[0], args[1]);
    }

     /**
     * Saves work into a json fileSaves a current diagram to a JSON file.
     */
    private void save(String[] args) {
        try {
            controller.save(args[0]);

        } catch (Exception e) {
            System.out.println("Invalid arguments");
        }

    }
     /**
     * Loads a jsonLoads a current diagram from a JSON file.
     */
    private void load(String[] args) {
        try {
            controller.load(args[0]);
        } catch (Exception e) {
            System.out.println("Invalid arguments");
        }
    }
    /**
     * Prints a thicc boi.
     */
    private void chungus() {
        System.out.println("THICCC BOY void");
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
        System.out.println("                                     |___ /");
    }

    public void helpPage()
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
        System.out.println("deletep [class] [methodType] [methodName] [[paramType] [paramName] ...] [newType] [newName]:     Delete a parameter");
        System.out.println("addr [classFrom] [classTo] [relateType]:                                                         Add a relationship");
        System.out.println("deleter [classFrom] [classTo]:                                                                   Delete a relationship");
        System.out.println("save [fileName]:                                                                                 Saves to passed in file name");
        System.out.println("load [fileName]:                                                                                 Loads the passed in file name");
    }
}