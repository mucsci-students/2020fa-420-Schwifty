package UML.controllers;

/*
    Author: Chris, Tyler, Drew, Dominic, Cory. 
    Date: 09/24/2020
    Purpose: Controls the actions taken when commands are used in the CLI.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Scanner;
import org.jline.reader.*;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.impl.AbstractTerminal;
import org.jline.terminal.impl.DumbTerminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import java.util.ArrayList;
import UML.model.*;
import UML.model.Store;
import UML.views.GraphicalView;
import UML.views.View;

import org.jline.keymap.KeyMap;

public class CLI {
    
    private Store store;
    private View view;
    private Controller controller;
    private Terminal terminal;
    private LineReader reader;

    public CLI(Store s, View v, Controller c) 
    {
        store = new Store();
        this.store = s;
        this.view = v;
        this.controller = c; 
        v.start();
        try
        {
            terminal = TerminalBuilder.builder()
            .system(false).streams(System.in, System.out)
            .build();
            //terminal.setSize(new Size(100, 5));
            cliLoop(terminal);//close out terminal when finished
            terminal.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

    }

    /**
     * As long as the user hasn't typed in exit, continue the app.
     */
    private void cliLoop(Terminal terminal) throws IOException
    {
        reader = LineReaderBuilder.builder()
                                             .terminal(terminal)
                                             .build();
        Parser parser = new DefaultParser();

        Completer s1 = new StringsCompleter("add class", "delete class", "rename class");

        Completer completer = new ArgumentCompleter(s1);
        boolean go = true;
        while(go) 
        {
            //Get the view's prompt
            //view.showPrompt();
            try
            {
                String nextLine = reader.readLine(">");
                String[] line = nextLine.split(" ");
                parse(line);
            }
            catch(EndOfFileException e)
            {
                go = false;
            }
        }
    }

    /**
     * Parses a command line argument in order to call the correct method.
     */
    private void parse(String[] line) 
    {
        if (line[0].equals("exit")) 
        {
            exit();
        } 
        else if (line[0].equals("help")) 
        {
            help();
        } 
        else if (line[0].equals(("display"))) 
        {
            display();
        } 
        else if (line[0].equals(("showgui"))) 
        {
            showGUI();
        } 
        else if (line[0].equals("addc")) 
        {
            addClass(line);
        } 
        else if (line[0].equals("renamec")) 
        {
            renameClass(line);
        } 
        else if (line[0].equals("deletec")) 
        {
            deleteClass(line);
        } 
        else if (line[0].equals("addf")) 
        {
            addField(line);
        } 
        else if (line[0].equals("renamef")) 
        {
            renameField(line);
        }
        else if (line[0].equals("changefa")) 
        {
            changeFieldAccess(line);
        }
        else if (line[0].equals("renamef")) 
        {
            renameField(line);
        }
        else if (line[0].equals("renamef")) 
        {
            renameField(line);
        }
        else if (line[0].equals("renamef")) 
        {
            renameField(line);
        }
        else if (line[0].equals("renamef")) 
        {
            renameField(line);
        }
        else if (line[0].equals("renamef")) 
        {
            renameField(line);
        }
        else if (line[0].equals("renamef")) 
        {
            renameField(line);
        }
        else if (line[0].equals("renamef")) 
        {
            renameField(line);
        }
        else if (line[0].equals("renamef")) 
        {
            renameField(line);
        } 
        else if (line[0].equals("deletef")) 
        {
            deleteField(line);
        } 
        else if (line[0].equals("addm")) 
        {
            addMethod(line);
        } 
        else if (line[0].equals("renamem")) 
        {
            renameMethod(line);
        } 
        else if (line[0].equals("deletem")) 
        {
            deleteMethod(line);
        } 
        else if (line[0].equals("addp")) 
        {
            addParameter(line);
        } 
        else if (line[0].equals("deletep")) 
        {
            deleteParameter(line);
        } else if (line[0].equals("deleter")) 
        {
            deleteRelationship(line);
        } 
        else if (line[0].equals("save")) 
        {
            save(line);
        } 
        else if (line[0].equals("chungus")) 
        {
            chungus();
        } else if (line[0].equals(("load"))) 
        {
            load(line);
        } 
        else if (line[0].equals(("addr"))) 
        {
            addRelationship(line);
        } 
        else if (line[0].equals(("deleter"))) 
        {
            deleteRelationship(line);
        } 
        else 
        {
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
    private void exit() 
    {
        view.exit();
        System.exit(0);
    }

    /**
     * Displays help menu.
     */
    private void help() 
    {
        helpPage();
    }

    /**
     * Displays the current structure of the models.
     */
    private void display() 
    {
        view.display(store.stringOfClasses());
    }

    /**
     * Displays a GUI version of the app with all current changes loaded.
     */
    private void showGUI() 
    {
        try 
        {
            controller.save("toLoad");
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        Store s = new Store();
        GraphicalView v = new GraphicalView();
        Controller c = new Controller(s, v);
        v.start();
        c.addListeners();
        try 
        {
            c.load("toLoad.JSON");
            terminal.close();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        } 
        catch (org.json.simple.parser.ParseException e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Adds a class to the store.
     */
    private void addClass(String[] args) 
    {
        if(args.length == 2)
        {
            controller.createClass(args[1]);
        }
        else
        {
            view.showError("Invalid arguments for adding a class, please refer to help.");
        }  
    }

    /**
     * Renames a class in the store.
     */
    private void renameClass(String[] args) 
    {
        if (args.length == 3)
        {
            controller.renameClass(args[1], args[2]);
        }
        else
        {
            view.showError("Invalid arguments for renaming a class, please refer to help.");
        }
    }

    /**
     * Deletes a class in the store.
     */
    private void deleteClass(String[] args) 
    {
        if(args.length == 2)
        {
            controller.deleteClass(args[1]);
        }
        else
        {
            view.showError("Invalid arguments for deleting a class, please refer to help.");
        }
    }

    /**
     * Adds a field to a class in the store.
     */
    private void addField(String[] args) 
    {
        if(args.length == 5 && store.findClass(args[1]) != null)
        {
            controller.createField(args[1], args[2], args[3], args[4]);            
        }
        else
        {
            view.showError("Invalid arguments for adding a field, please refer to help.");
        }
    }
    /**
     * Calls the controller to add a field to the given class.
     */
    private void renameField(String[] args) 
    {
        if (args.length == 4 && store.findClass(args[1]) != null)
        {
            controller.renameField(args[1], args[2], args[3]);
        }
        else
        {
            view.showError("Invalid arguments for renaming a field, please refer to help.");
        }
    }

    /**
     * Deletes a field from a class in the store.
     */
    private void deleteField(String[] args) 
    {
        if (args.length == 3 && store.findClass(args[1]) != null)
            controller.deleteField(args[1], args[2]);
        else
        {
            view.showError("Invalid arguments for renaming a field, please refer to help.");
        }
    }

    private void changeFieldAccess(String[] args)
    {
        if(args.length == 3 || store.findClass(args[1]) != null)
        {
            controller.changeFieldAccess(args[1], args[2], args[3]);
        }
        else
        {
            view.showError("Invalid arguments for changing field access, please refer to help.");
        }
    }

    /**
     * Adds a method to a class in the store.
     */
    private void addMethod(String[] args) 
    {
        ArrayList<String> params = new ArrayList<String>();
        
        if (args.length < 5 || (args.length - 5) % 2 != 0 || store.findClass(args[1]) == null)
            view.showError("Invalid arguments for adding method, please refer to help.");
        else
        {
            for (int counter = 4; counter < args.length - 1; counter += 2) 
                params.add(args[counter] + " " + args[counter + 1]);

            controller.createMethod(args[1], args[2], args[3], params, args[args.length - 1]);
        }
    }
    
    /**
     * Renames a method in a class in the store.
     */
    private void renameMethod(String[] args) {
        ArrayList<String> params = new ArrayList<String>();
        if (args.length < 5 || (args.length - 5) % 2 != 0 || store.findClass(args[1]) == null) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 4; counter < args.length - 2; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.renameMethod(args[1], args[2], args[3], params, args[args.length - 2], args[args.length - 1]);
    }

    /**
     * Deletes a method from a class in the store.
     */
    private void deleteMethod(String[] args) {
        ArrayList<String> params = new ArrayList<String>();
        if (args.length < 5 || (args.length - 5) % 2 != 0 ) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 4; counter < args.length - 1; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.deleteMethod(args[1], args[2], args[3], params, args[args.length - 1]);

    }

    /**
     * Adds a parameter to a given method.
     */
    private void addParameter(String[] args) {
        ArrayList<String> params = new ArrayList<String>();
        if ((args.length - 7) % 2 != 0) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 4; counter < args.length - 3; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.addParameter(args[1], args[2], args[3], params, args[args.length - 3], args[args.length - 2], args[args.length - 1]);
    }
    /**
     * Deletes a given parameter from a methodDeletes a parameters from a method from a class in the store.
     */
    private void deleteParameter(String[] args) 
    {

    }
    /**
     * Creates a relationship between two classes Adds a relationship bewteen two classees in the store.
     */
    private void addRelationship(String[] args) {
        if(args.length == 4)
        {
            controller.addRelationship(args[1], args[2], RelationshipType.valueOf(args[3].toUpperCase()));
        }
        else
        {
            view.showError("Invalid arguments for adding a relationship, please refer to help.");
        }
    }
    
     /**
     * Deletes a relationship created between two classes
     */
    private void deleteRelationship(String[] args) 
    {
        if(args.length == 3)
        {
            controller.deleteRelationship(args[1], args[2]);
        }
        else
        {
            view.showError("Invalid arguments for removing a class, please refer to help.");
        } 
    }

     /**
     * Saves work into a json fileSaves a current diagram to a JSON file.
     */
    private void save(String[] args) {
        try {
            controller.save(args[1]);

        } catch (Exception e) {
            System.out.println("Invalid arguments");
        }
        view.save();
    }
     /**
     * Loads a jsonLoads a current diagram from a JSON file.
     */
    private void load(String[] args) {
        try {
            controller.load(args[1]);
        } catch (Exception e) {
            System.out.println("Invalid arguments");
        }
        view.load();
    }
    /**
     * Prints a thicc boi.
     */
    private void chungus() {
        System.out.println("THICCC BOY void");
    }

    /**
     * Displays the help menu.
     */
    public void helpPage()
    {
        view.showHelp();
    }
}