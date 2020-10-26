package UML.controllers;

/*
    Author: Chris, Tyler, Drew, Dominic, Cory. 
    Date: 09/24/2020
    Purpose: Controls the actions taken when commands are used in the CLI.
 */
import java.io.IOException;

import org.jline.reader.*;

import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.Terminal;
import java.util.ArrayList;
import UML.model.*;
import UML.model.Store;
import UML.views.GraphicalView;
import UML.views.View;
import org.jline.builtins.Completers.TreeCompleter;
import static org.jline.builtins.Completers.TreeCompleter.node;

import org.jline.reader.impl.history.DefaultHistory;
import java.util.List;
import java.util.LinkedList;
import org.jline.reader.Candidate;
import java.util.Set;

public class CLI
{
    
    private Store store;
    private View view;
    private Controller controller;
    private Terminal terminal;
    private LineReader reader;
    private Completer completer;
    private History history;
    private Parser parser;

    public CLI(Store s, View v, Controller c) 
    {
        store = new Store();
        this.store = s;
        this.view = v;
        this.controller = c;
        v.start();
        
        try{
            terminal = TerminalBuilder.builder()
            .system(true)
            .build();

            cliLoop();
            terminal.close();
        }
        catch(IOException e)
        {
            //Catch!
        }
    }

    
    /**
     * As long as the user hasn't typed in exit, continue the app.
     */
    private void cliLoop() throws IOException
    {
        //Stores the command history
        history = new DefaultHistory();
        //The parser
        parser = new DefaultParser();
        //LineReader.Option.AUTO_REMOVE_SLASH
        //Command completer for tab completion
        StringsCompleter s2 = new StringsCompleter("test");
        //completer = new ArgumentCompleter(new StringsCompleter(buildCandidateList()), s2);
        completer = new TreeCompleter(
            node("addc", "exit", "help", "showgui", "load"));

        //Build the reader and it's options
        reader = LineReaderBuilder.builder()
                                .terminal(terminal)
                                .history(history)
                                .completer(completer)
                                .parser(parser)
                                .variable(LineReader.MENU_COMPLETE, true).build();

        reader.option(LineReader.Option.COMPLETE_IN_WORD, true);
        reader.option(LineReader.Option.RECOGNIZE_EXACT, true);
        reader.option(LineReader.Option.CASE_INSENSITIVE, true);
        reader.option(LineReader.Option.AUTO_REMOVE_SLASH, true);
        //unset default tab behavior
        reader.unsetOpt(LineReader.Option.INSERT_TAB);
        history.attach(reader);

        boolean go = true;
        while(go) 
        {
            try
            {
                //Reads the line from the user
                String readLine = reader.readLine(">", "", (MaskingCallback) null, null);
                readLine = readLine.trim();
                //Writer back completions to console. 
                terminal.writer().println(readLine);
                terminal.flush();
                String[] line = readLine.split(" ");
                if(line[0].equals("showgui"))
                    go = false;
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
        else if (line[0].equals("changeft")) 
        {
            changeFieldType(line);
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
        else if (line[0].equals("changema")) 
        {
            changeMethodAccess(line);
        }
        else if (line[0].equals("changemt")) 
        {
            changeMethodType(line);
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
            terminal.close();
            c.load("toLoad.JSON");
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
            makeReader();
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
            makeReader();
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
            makeReader();
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
            makeReader();        
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
            makeReader();  
        }
        else
        {
            view.showError("Invalid arguments for renaming a field, please refer to help.");
        }
    }

    /**
     * Calls the controller to change a field's type.
     */
    private void changeFieldType(String args[])
    {
        if (args.length == 4 && store.findClass(args[1]) != null)
        {
            controller.changeFieldType(args[1], args[2], args[3]);
            makeReader();  
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
        {
            controller.deleteField(args[1], args[2]);
            makeReader();  
        }
        else
        {
            view.showError("Invalid arguments for renaming a field, please refer to help.");
        }
    }

    /**
     * Changes the access type of a field.
     */
    private void changeFieldAccess(String[] args)
    {
        if(args.length == 3 || store.findClass(args[1]) != null)
        {
            controller.changeFieldAccess(args[1], args[2], args[3]);
            makeReader();
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
            makeReader();  
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
        makeReader();  
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
        makeReader();  

    }

    /**
     * Changes a method return type.
     */
    private void changeMethodType(String[] args)
    {
        ArrayList<String> params = new ArrayList<String>();
        if (args.length < 5 || (args.length - 5) % 2 != 0 || store.findClass(args[1]) == null) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 4; counter < args.length - 2; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.changeMethodType(args[1], args[2], args[3], params, args[args.length - 2], args[args.length - 1]);
        makeReader();  
    }
    
    /**
     * Changes a method access type
     */
    private void changeMethodAccess(String[] args)
    {
        ArrayList<String> params = new ArrayList<String>();
        if (args.length < 5 || (args.length - 5) % 2 != 0 || store.findClass(args[1]) == null) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 4; counter < args.length - 2; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.changeMethodAccess(args[1], args[2], args[3], params, args[args.length - 2], args[args.length - 1]);
        makeReader();  
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
        makeReader();  
    }

    /**
     * Deletes a given parameter from a methodDeletes a parameters from a method from a class in the store.
     */
    private void deleteParameter(String[] args) 
    {
        makeReader();
    }

    /**
     * Creates a relationship between two classes Adds a relationship bewteen two classees in the store.
     */
    private void addRelationship(String[] args) {
        if(args.length == 4)
        {
            controller.addRelationship(args[1], args[2], RelationshipType.valueOf(args[3].toUpperCase()));
            makeReader();
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

    /**
     * Gets a list of field names.
     */
    private ArrayList<String> getFieldNames(String className)
    {
        UML.model.Class c = store.findClass(className);
        Set<Field> fields = c.getFields();
        ArrayList<String> toReturn = new ArrayList<String>();
        for(Field f : fields)
        {
            toReturn.add(f.getName());
        }
        return toReturn;
    }

    /**
     * Gets a list of method strings.
     */
    private ArrayList<String> getMethodNames(String className)
    {
        UML.model.Class c = store.findClass(className);
        Set<Method> methods = c.getMethods();
        ArrayList<String> toReturn = new ArrayList<String>();
        for(Method m : methods)
        {
            String toAdd = "";
            toAdd += m.getType() + " ";
            toAdd += m.getName() + " ";
            for(Parameter param : m.getParams())
            {
                toAdd += param.toString() + " ";
            }
            toAdd += m.getAccessString(); 
            toReturn.add(toAdd);
        }
        return toReturn;
    }


    private List<String> buildCandidateList()
    {
        List<String> candidates = new LinkedList<String>();
        candidates.add("addc");
        candidates.add("deletec");
        candidates.add("renamec");
        candidates.add("addf");
        candidates.add("deletef");
        candidates.add("renamef");
        candidates.add("changefa");
        candidates.add("changeft");
        candidates.add("addm");
        candidates.add("deletem");
        candidates.add("renamem");
        candidates.add("changema");
        candidates.add("changemt");
        candidates.add("addr");
        candidates.add("deleter");
        candidates.add("exit");
        candidates.add("help");

        return candidates;
    }

    /**
     * Makes a new completer and reader based on state of the model.
     */
    private void makeReader()
    {
        if(store.getClassStore().isEmpty())
        {
            completer = new TreeCompleter(
            node("addc", "exit", "help", "showgui", "save", "load"));
        }
        else
        {
            StringsCompleter classes = new StringsCompleter(store.getClassList());
            String[] str = reader.getHistory().get(reader.getHistory().last()).split(" ");
            if(store.findClass(str[1]).getFields().isEmpty() && store.findClass(str[1]).getMethods().isEmpty())
            {
                completer = new TreeCompleter(
                                    node("renamec",
                                        node(classes)
                                        ),
                                    node("deletec",
                                        node(classes)
                                        ),
                                    node("addf",
                                        node(classes)
                                        ),
                                    node("addm",
                                        node(classes)
                                        ),
                                        node("addr",
                                        node(classes,
                                            node(classes, 
                                                node(new StringsCompleter("Aggregation", "Composition", "Generalization", "Realization"))
                                            )
                                        )
                                    ),
                                    node("deleter",
                                        node(classes,
                                            node(classes))
                                        ),
                                    node("help", "exit", "showgui", "save", "load")
                                    );
            }
            else if(store.findClass(str[1]).getFields().isEmpty())
            {
                completer = new TreeCompleter(
                                    node("addc"),
                                    node("renamec",
                                        node(classes)
                                    ),
                                    node("deletec",
                                        node(classes)
                                    ),
                                    node("addf",
                                        node(classes)
                                    ),
                                    node("addm",
                                        node(classes)
                                    ),
                                    node("renamem",
                                        node(classes,
                                            node(new StringsCompleter(getMethodNames(str[1]))
                                            )
                                        )
                                    ),   
                                    node("changemt",
                                        node(classes,
                                            node(new StringsCompleter(getMethodNames(str[1]))
                                            )
                                        )
                                    ),
                                    node("changema",
                                        node(classes,
                                            node(new StringsCompleter(getMethodNames(str[1]))
                                            )
                                        )
                                    ),
                                    node("addr",
                                        node(classes,
                                            node(classes, 
                                                node(new StringsCompleter("Aggregation", "Composition", "Generalization", "Realization"))
                                            )
                                        )
                                    ),
                                    node("deleter",
                                        node(classes,
                                            node(classes))
                                        ),
                                    node("help", "exit", "showgui", "save", "load")
                                    );
            }
            else if(store.findClass(str[1]).getMethods().isEmpty())
            {
                completer = new TreeCompleter(
                                    node("addc"),
                                    node("renamec",
                                        node(classes)
                                    ),
                                    node("deletec",
                                        node(classes)
                                    ),
                                    node("addf",
                                        node(classes)
                                    ),
                                    node("addm",
                                        node(classes)
                                    ),
                                    node("renamef",
                                        node(classes,
                                            node(new StringsCompleter(getFieldNames(str[1]))
                                            )
                                        )
                                    ),   
                                    node("changeft",
                                        node(classes,
                                            node(new StringsCompleter(getFieldNames(str[1]))
                                            )
                                        )
                                    ),
                                    node("changefa",
                                        node(classes,
                                            node(new StringsCompleter(getFieldNames(str[1]))
                                            )
                                        )
                                    ),
                                    node("addr",
                                        node(classes,
                                            node(classes, 
                                                node(new StringsCompleter("Aggregation", "Composition", "Generalization", "Realization"))
                                            )
                                        )
                                    ),
                                    node("deleter",
                                        node(classes,
                                            node(classes))
                                        ),
                                    node("help", "exit", "showgui", "save", "load")
                                    );
            }
            else 
            {
                completer = new TreeCompleter(
                                    node("addc"),
                                    node("renamec",
                                        node(classes)
                                    ),
                                    node("deletec",
                                        node(classes)
                                    ),
                                    node("addf",
                                        node(classes)
                                    ),
                                    node("renamef",
                                        node(classes,
                                            node(new StringsCompleter(getFieldNames(str[1]))
                                            )
                                        )
                                    ), 
                                    node("changeft",
                                        node(classes,
                                            node(new StringsCompleter(getFieldNames(str[1]))
                                            )
                                        )
                                    ),
                                    node("changefa",
                                        node(classes,
                                            node(new StringsCompleter(getFieldNames(str[1]))
                                            )
                                        )
                                    ),
                                    node("addm",
                                        node(classes)
                                    ),
                                    node("renamem",
                                        node(classes,
                                            node(new StringsCompleter(getMethodNames(str[1]))
                                            )
                                        )
                                    ),   
                                    node("changemt",
                                        node(classes,
                                            node(new StringsCompleter(getMethodNames(str[1]))
                                            )
                                        )
                                    ),
                                    node("changema",
                                        node(classes,
                                            node(new StringsCompleter(getMethodNames(str[1]))
                                            )
                                        )
                                    ),
                                    node("addr",
                                        node(classes,
                                            node(classes, 
                                                node(new StringsCompleter("Aggregation", "Composition", "Generalization", "Realization"))
                                            )
                                        )
                                    ),
                                    node("deleter",
                                        node(classes,
                                            node(classes))
                                        ),
                                    node("help", "exit", "showgui", "save", "load")
                                    );
            }
        }
        reader = LineReaderBuilder.builder()
        .terminal(terminal)
        .history(history)
        .completer(completer)
        .parser(parser)
        .variable(LineReader.MENU_COMPLETE, true).build();

        reader.option(LineReader.Option.COMPLETE_IN_WORD, true);
        reader.option(LineReader.Option.RECOGNIZE_EXACT, true);
        reader.option(LineReader.Option.CASE_INSENSITIVE, true);
        reader.option(LineReader.Option.AUTO_REMOVE_SLASH, true);
    }
}