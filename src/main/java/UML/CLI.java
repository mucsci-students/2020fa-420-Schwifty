/*
    Author: Chris, Tyler, Drew, Dominic, Cory. 
    Date: 09/24/2020
    Purpose: 
 */
import java.io.Console;
import java.util.Scanner;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
public class CLI {
    
    private Options options;
    private Store store;
    
    public CLI(String[] args)
    {
        options = addOptions();
        store = new Store();
        printHeader();
        cliLoop(args);
        //System.out.print("Schwifty-UML> ");
    }
    private void cliLoop(String[] args) throws ParseException
    {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        
        while(!cmd.getOptionValue("exit"))
        {
            if(!(cmd.getOptionValue() == null)) {
                parse(cmd);
            }
            else {
                System.out.println("That command does not exist");
            }
        }
    }
    /**
     * Add options for all possible commands.
     */
    private Options addOptions()
    {
        Options options = new Options();
        options.addOption("exit", false, "Close CLI")
        .addOption("help", false, "Show all options")
        .addOption("display", false, "Display all classes")
        .addOption("addc", true, "Create a class: addc [class]")
        .addOption("renamec", true, "Rename a class: renamec [oldName] [newName]")
        .addOption("deletec", true, "Delete a class: delete [class]")        
        .addOption("addf", true,"Create a field: addf [class] [type] [name]")
        .addOption("renamef", true, "Rename a field: renamef [className] [oldName][newName]")
        .addOption("deletef", true, "Delete a field: deletef [class] [FieldName]")
        .addOption("addm", true, "Create a method: addm [class] [methodType] [methodName] [[paramType] [paramName] ...]")
        .addOption("reanamem", true, "Rename a method: renamem [class] [methodType] [oldMethodName] [[ParamType] [paramName] ...] [newMethodName]")
        .addOption("deletem", true, "Delete a method: deletem [class] [methodType] [methodName] [[paramType] [paramName] ...]")
        .addOption("addp", true, "Create a parameter: addp [class] [methodType] [methodName] [[paramType] [paramName] ...] [newType] [newName]")
        .addOption("deletep", true, "Delete a parameter: deletep [class] [methodType] [methodName] [[paramType] [paramName] ...] [newType] [newName]")
        .addOption("addr", true, "Add a relationship: addr [classFrom] [classTo] [relateType]")
        .addOption("deleter", true, "delete a relationship: deleter [classFrom] [classTo]")
        .addOption("save", true, "Save a UML diagram: save [fileName]")
        .addOption("chungus", true, "Loads Thicc Chungus")
        .addOption("load", true, "Load a UML diagram: load [fileName]");
    //HelpFormatter formatter = new HelpFormatter();
    //formatter.printHelp("CLITester", options);
        return options;
    }

    /**
     * Parses a command line argument in order to call the correct method.
     */
    private void parse(CommandLine cmd) {
        switch(cmd.nextToken())
        {
            case "exit": exit(cmd.getOptionValues("exit"));
            break;
            case "help": help(cmd.getOptionValues("help"));
            break;
            case "display": display(cmd.getOptionValues("display"));
            break;
            case "addc": addClass(cmd.getOptionValues("addc"));
            break;
            case "renamec": renameClass(cmd.getOptionValues("renamec"));
            break;
            case "deletec": deleteClass(cmd.getOptionValues("deletec"));
            break;
            case "addf": addField(cmd.getOptionValues("addf"));
            break;
            case "renamef": renameField(cmd.getOptionValues("renamef"));
            break;
            case "deletef": deleteField(cmd.getOptionValues("deletef"));
            break;
            case "addm": addMethod(cmd.getOptionValues("addm"));
            break;
            case "renamem": renameMethod(cmd.getOptionValues("renamem"));
            break;
            case "deletem": deleteMethod(cmd.getOptionValues("deletem"));
            break;
            case "addp": addParameter(cmd.getOptionValues("addp"));
            break;
            case "deletep": deleteParameter(cmd.getOptionValues("deletep"));
            break;
            case "renamep": renameParameter(cmd.getOptionValues("renamep"));
            break;
            case "addr": addRelationship(cmd.getOptionValues("addr"));
            break;
            case "deleter": deleteRelationship(cmd.getOptionValues("deleter"));
            break;
            case "save": save(cmd.getOptionValues("save"));
            break;
            case "chungus": chungus(cmd.getOptionValues("chungus"));
            break;
            case "load": load(cmd.getOptionValues("load"));
            break;
        }
    }
//
//Abstract checking if class exists.******************************************************************************
//
    /**
     * Exits from the CLI.
     */
    private void exit()
    {
        System.exit(0);
    }
    
    /**
     * Displays help menu.
     */
    private void help()
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("CLITester", options);
    }

    /**
     * Displays the current structure of the models.
     */
    private void display()
    {
        
    }

    /**
     * Adds a class to the store.
     */
    private void addClass(String [] args)
    {
        try
        { 
            boolean temp = store.addClass(args[0]);
            if(!temp)
                System.out.println("This class already exists.");
        } 
        catch(Exception e) 
        { 
            System.out.println(e.getMessage()); 
        }
    }

    /**
     * Renames class in the store.
     */
    private void renameClass(String [] args)
    {
        try 
        {
            store.classExists(args[0]);
            store.renameClass(args[0], args[1]); 
        } 
        catch(Exception e) 
        { 
            System.out.println(e.getMessage()); 
        }
    }

    private void deleteClass(String [] args)
    {
        try
        {
            store.classExists(args[0]);
            store.deleteClass(args[0]);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void addField(String [] args)
    {
        try 
        {
            boolean temp = store.addField(args[0], args[1], args[2]);
            if(!temp)
                System.out.println("This field already exists.");
        } 
        catch (Exception e) {
            System.out.println(e.getMessage()); 
        }
    }

    private void renameField(String [] args) 
    {
        try {
            store.fieldExists(args[0], args[1]);
            boolean temp = store.renameField(args[0], args[1], args[2]);
            if(!temp)
                System.out.println("The new field name already exists.");
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage()); 
        }

    }
    
    private void deleteField(String [] args) 
    {
        try
        {
            store.fieldExists(args[0], args[1]);
            store.deleteField(args[0], args[1]);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
    }

    private void addMethod(String [] args)
    {
        try {
            if((args.length - 3) % 2 != 0)
            {
                System.out.println("Invalid arguments");
            }
            else 
            {
                ArrayList<String> params = new ArrayList<String>();
                for(int counter = 3; counter < args.length; counter += 2) 
                {
                    params.add(args[counter] + " " + args[counter + 1]);
                }
                boolean temp = store.addMethod(args[0], args[1], args[2], params);
                if(!temp)
                    System.out.println("This method already exists.");
            }
        } 
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void renameMethod(String [] args)
    {
        try {
           
            ArrayList<String> params = new ArrayList<String>();
            if((args.length - 4) % 2 != 0)
            {
                System.out.println("Invalid arguments");
            }
            for(int counter = 3; counter < args.length - 1; counter += 2) 
            {
                params.add(args[counter] + " " + args[counter + 1]);
            }

            store.methodExits(args[0], args[1], args[2], params);
            boolean temp = store.renameMethod(args[0], args[1], args[2], params, args[args.length - 1]);
            if(!temp)
                System.out.println("The new method name already exists.");
        }       
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
    }

    private void deleteMethod(String [] args)
    {
        try {
            ArrayList<String> params = new ArrayList<String>();
            if((args.length - 3) % 2 != 0)
            {
                System.out.println("Invalid arguments");
            }
            for(int counter = 3; counter < args.length; counter += 2) 
            {
                params.add(args[counter] + " " + args[counter + 1]);
            }
            store.methodExits(args[0], args[1], args[2], params);
            store.deleteMethod(args[0], args[1], args[2], params);
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        
    }
            
    private void addParameter(String [] args)
    {
        try 
        {
            ArrayList<String> params = new ArrayList<String>();
            if((args.length - 5) % 2 != 0)
            {
                System.out.println("Invalid arguments");
            }
            for(int counter = 3; counter < args.length - 2; counter += 2) 
            {
                params.add(args[counter] + " " + args[counter + 1]);
            }
            boolean temp = store.addParameter(args[0], args[1], args[2], params, args[args.length - 2], args[args.length - 1]);
            if(!temp)
                System.out.println("This parameter already exists.");
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
    }

    private void deleteParameter(String [] args)
    {
        
    }

    private void addRelationship(String [] args)
    {
        
    }

    private void deleteRelationship(String [] args)
    {
        
    }

    private void save(String [] args)
    {
        try 
        {
            store.save(args[0]);
            
        } 
        catch (Exception e) 
        {
            System.out.println("Invalid arguments");
        }
        
    }

    private void load(String [] args)
    {
        try 
        {
            store.load(args[0]);
        } 
        catch (Exception e) 
        {
            System.out.println("Invalid arguments");
        }
    }




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
