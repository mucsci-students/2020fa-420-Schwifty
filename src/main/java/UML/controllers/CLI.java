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
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.history.DefaultHistory;
import java.util.Set;
import java.util.Stack;
import java.util.Map;

public class CLI {
    
    //The store
    private Store store;
    
    //The view
    private View view;

    //The controller
    private Controller controller;

    //Terminal for the CLI
    private Terminal terminal;
    
    //Reads input from the user
    private LineReader reader;
    
    //handles tab completion
    private Completer completer;
    
    //JLine's object to store the history from the CLI.
    private History history;
    
    //The parser to parse input.
    private Parser parser;

    //The acccess types that are defined.
    private ArrayList<String> theAccesses;

    //The developer and user defined types.
    private ArrayList<String> theTypes;

    public CLI(Store s, View v, Controller c) {
        store = new Store();
        this.store = s;
        this.view = v;
        this.controller = c;
        v.start();

        //Initialize theAccesses.
        theAccesses = new ArrayList<String>();
        theAccesses.add("public");
        theAccesses.add("private");
        theAccesses.add("protected");

        //Initialize theTypes.
        theTypes = new ArrayList<String>();
        theTypes.add("String");
        theTypes.add("boolean");
        theTypes.add("char");
        theTypes.add("double");
        theTypes.add("float");
        theTypes.add("int");
        theTypes.add("short");
        theTypes.add("size_t");
        theTypes.add("unsigned");

        for(UML.model.Class theClass : store.getClassStore())
        {
            for(Method m : theClass.getMethods())
            {
                if(!theTypes.contains(m.getType()))
                {
                    theTypes.add(m.getType());
                }
            }
            
            for(Field f : theClass.getFields())
            {
                if(!theTypes.contains(f.getType()))
                {
                    theTypes.add(f.getType());
                }
            }
        }

        try {
            terminal = TerminalBuilder.builder().system(true).build();

            cliLoop();
            terminal.close();
        } catch (IOException e) {
            // Catch!
        }
    }

    /**
     * As long as the user hasn't typed in exit, continue the app.
     */
    private void cliLoop() throws IOException {
        // Stores the command history
        history = new DefaultHistory();
        // The parser
        parser = new DefaultParser();

        completer = new TreeCompleter(node("addc", "exit", "help", "showgui", "load", "displayr"));

        // Build the reader and it's options
        reader = LineReaderBuilder.builder().terminal(terminal).history(history).completer(completer).parser(parser)
                .variable(LineReader.MENU_COMPLETE, true).build();

        reader.option(LineReader.Option.COMPLETE_IN_WORD, true);
        reader.option(LineReader.Option.RECOGNIZE_EXACT, true);
        reader.option(LineReader.Option.CASE_INSENSITIVE, true);
        reader.option(LineReader.Option.AUTO_REMOVE_SLASH, true);
        // Unset default tab behavior.
        reader.unsetOpt(LineReader.Option.INSERT_TAB);
        history.attach(reader);

        // Make sure the correct tab completions are used.
        makeReader();

        boolean go = true;
        while (go) {
            try {
                String readLine = reader.readLine(String.format("%sSCHWIFTY>%s ", "\u001B[36m", "\u001B[36m"), "", (MaskingCallback) null, null);
                readLine = readLine.trim();
                // Writer back completions to console.
                terminal.writer().println(readLine);
                terminal.flush();
                //Prevents errors caused by the user adding commas and spaces in params.
                readLine = readLine.replaceAll(",", " ");
                readLine = readLine.replaceAll("  ", " ");
                readLine = readLine.replaceAll("  ", " ");
                String[] line = readLine.split(" ");
                if (line[0].equals("showgui"))
                    go = false;
                parse(line);
            } catch (EndOfFileException e) {
                go = false;
            }
        }
    }

    /**
     * Parses a command line argument in order to call the correct method.
     */
    private void parse(String[] line) {

        if (line[0].equals("exit")) {
            exit();
        } else if (line[0].equals("help")) {
            help();
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
        } else if (line[0].equals("changefa")) {
            changeFieldAccess(line);
        } else if (line[0].equals("changeft")) {
            changeFieldType(line);
        } else if (line[0].equals("deletef")) {
            deleteField(line);
        } else if (line[0].equals("addm")) {
            addMethod(line);
        } else if (line[0].equals("renamem")) {
            renameMethod(line);
        } else if (line[0].equals("deletem")) {
            deleteMethod(line);
        } else if (line[0].equals("changema")) {
            changeMethodAccess(line);
        } else if (line[0].equals("changemt")) {
            changeMethodType(line);
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
        } else if (line[0].equals(("addr"))) {
            addRelationship(line);
        } else if (line[0].equals(("deleter"))) {
            deleteRelationship(line);
        } else if (line[0].equals(("display"))) {
            display(line);
        } else if (line[0].equals(("undo"))) {
            undo();
        } else if (line[0].equals(("redo"))) {
            redo();
        } else if (line[0].equals(("displayr"))) {
            displayRelationships();
        } else {
            System.out.println("That is not a valid command.");
        }
    }


    /**
     * Exits from the CLI.
     */
    private void exit() {
        view.exit();
        System.exit(0);
    }

    /**
     * Displays help menu.
     */
    private void help() {
        view.showHelp();
    }

    /**
     * Displays a GUI version of the app with all current changes loaded.
     */
    private void showGUI() 
    {
        if (controller.getGUIExists()) 
        {
            view.setGUIVisible();
            try 
            { 
                terminal.close();
            } 
            catch (Exception e) 
            {

            }
        } 

        //Initialize verything needed for GUI transistion to work.
        GraphicalView v = new GraphicalView();

        StateController stateController = controller.getStateController();
        Stack<Store> undoState = stateController.getUndoStack();
        Stack<Store> redoState = stateController.getRedoStack();
        Store currentState = stateController.getCurrentState();

        Controller c = new Controller(store, v);
        StateController state = new StateController(currentState, undoState, redoState);
        c.setStateController(state);
        v.start();
        c.addListeners();
        c.rebuild();
         try 
        {
            terminal.close();
            v.refresh();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    
//================================================================================================================================================
//Class Methods
//================================================================================================================================================


    /**
     * Adds a class to the store.
     */
    private void addClass(String[] args) {
        if (args.length == 2) {
            controller.createClass(args[1]);
            makeReader();
        } else {
            view.showError("Invalid arguments for adding a class, please refer to help.");
        }
    }

    /**
     * Renames a class in the store.
     */
    private void renameClass(String[] args) {
        if (args.length == 3) {
            controller.renameClass(args[1], args[2]);
            makeReader();
        } else {
            view.showError("Invalid arguments for renaming a class, please refer to help.");
        }
    }

    /**
     * Deletes a class in the store.
     */
    private void deleteClass(String[] args) {
        if (args.length == 2) {
            controller.deleteClass(args[1]);
            makeReader();
        } else {
            view.showError("Invalid arguments for deleting a class, please refer to help.");
        }
    }


//================================================================================================================================================
//Field Methods
//================================================================================================================================================


    /**
     * Adds a field to a class in the store.
     */
    private void addField(String[] args) {
        if (args.length == 5 && store.findClass(args[1]) != null) {
            controller.createField(args[1], args[3], args[4], args[2]);

            //Add type if new user-defined.
            if(!theTypes.contains(args[3]))
            {
                theTypes.add(args[3]);
            }

            makeReader();
        } else {
            view.showError("Invalid arguments for adding a field, please refer to help.");
        }
    }

    /**
     * Calls the controller to add a field to the given class.
     */
    private void renameField(String[] args) {
        if (args.length == 4 && store.findClass(args[1]) != null) {
            controller.renameField(args[1], args[2], args[3]);
            makeReader();
        } else {
            view.showError("Invalid arguments for renaming a field, please refer to help.");
        }
    }

    /**
     * Calls the controller to change a field's type.
     */
    private void changeFieldType(String args[]) {
        if (args.length == 4 && store.findClass(args[1]) != null) {
            controller.changeFieldType(args[1], args[2], args[3]);

            //Add type if new user-defined.
            if(!theTypes.contains(args[3]))
            {
                theTypes.add(args[3]);
            }

            makeReader();
        } else {
            view.showError("Invalid arguments for renaming a field, please refer to help.");
        }
    }

    /**
     * Deletes a field from a class in the store.
     */
    private void deleteField(String[] args) {
        if (args.length == 3 && store.findClass(args[1]) != null) {
            controller.deleteField(args[1], args[2]);
            makeReader();
        } else {
            view.showError("Invalid arguments for renaming a field, please refer to help.");
        }
    }

    /**
     * Changes the access type of a field.
     */
    private void changeFieldAccess(String[] args) {
        if (args.length == 3 || store.findClass(args[1]) != null) {
            controller.changeFieldAccess(args[1], args[2], args[3]);
            makeReader();
        } else {
            view.showError("Invalid arguments for changing field access, please refer to help.");
        }
    }


//================================================================================================================================================
//Method Methods
//================================================================================================================================================


    /**
     * Adds a method to a class in the store.
     */
    private void addMethod(String[] args) {
        ArrayList<String> params = new ArrayList<String>();

        if (args.length < 5 || (args.length - 5) % 2 != 0 || store.findClass(args[1]) == null)
            view.showError("Invalid arguments for adding method, please refer to help.");
        else {
            for (int counter = 5; counter < args.length; counter += 2)
                params.add(args[counter] + " " + args[counter + 1]);

            controller.createMethod(args[1], args[3], args[4], params, args[2]);

            //Add type if new user-defined.
            if(!theTypes.contains(args[3]))
            {
                theTypes.add(args[3]);
            }

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
        for (int counter = 5; counter < args.length - 1; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.renameMethod(args[1], args[3], args[4], params, args[2], args[args.length - 1]);
        makeReader();
    }

    /**
     * Deletes a method from a class in the store.
     */
    private void deleteMethod(String[] args) {
        ArrayList<String> params = new ArrayList<String>();
        if (args.length < 5 || (args.length - 5) % 2 != 0) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 5; counter < args.length; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.deleteMethod(args[1], args[3], args[4], params, args[2]);
        makeReader();
    }

    /**
     * Changes a method return type.
     */
    private void changeMethodType(String[] args) {
        ArrayList<String> params = new ArrayList<String>();
        if (args.length < 5 || (args.length - 5) % 2 != 0 || store.findClass(args[1]) == null) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 5; counter < args.length - 1; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.changeMethodType(args[1], args[3], args[4], params, args[2], args[args.length - 1]);

        //Add type if new user-defined.
        if(!theTypes.contains(args[args.length - 1]))
        {
            theTypes.add(args[args.length - 1]);
        }

        makeReader();
    }

    /**
     * Changes a method access type
     */
    private void changeMethodAccess(String[] args) {
        ArrayList<String> params = new ArrayList<String>();
        if (args.length < 5 || (args.length - 5) % 2 != 0 || store.findClass(args[1]) == null) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 5; counter < args.length - 1; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.changeMethodAccess(args[1], args[3], args[4], params, args[2], args[args.length - 1]);
        makeReader();
    }


//================================================================================================================================================
//Parameter Methods
//================================================================================================================================================


    /**
     * Adds a parameter to a given method.
     */
    private void addParameter(String[] args) {
        ArrayList<String> params = new ArrayList<String>();
        if ((args.length - 7) % 2 != 0) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 5; counter < args.length - 2; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.addParameter(args[1], args[3], args[4], params, args[2], args[args.length - 2],
                args[args.length - 1]);
        makeReader();
    }

    /**
     * Deletes a given parameter from a methodDeletes a parameters from a method
     * from a class in the store.
     */
    private void deleteParameter(String[] args) {
        ArrayList<String> params = new ArrayList<String>();
        if ((args.length - 7) % 2 != 0) {
            System.out.println("Invalid arguments");
        }
        for (int counter = 5; counter < args.length - 2; counter += 2) {
            params.add(args[counter] + " " + args[counter + 1]);
        }
        controller.deleteParameter(args[1], args[3], args[4], params, args[2], args[args.length - 2],
                args[args.length - 1]);
        makeReader();
    }


//================================================================================================================================================
//Relationship Methods
//================================================================================================================================================


    /**
     * Creates a relationship between two classes Adds a relationship bewteen two
     * classees in the store.
     */
    private void addRelationship(String[] args) {
        if (args.length == 4) {
            controller.addRelationship(args[1], args[2], RelationshipType.valueOf(args[3].toUpperCase()));
            makeReader();
        } else {
            view.showError("Invalid arguments for adding a relationship, please refer to help.");
        }
    }

    /**
     * Deletes a relationship created between two classes
     */
    private void deleteRelationship(String[] args) {
        if (args.length == 3) {
            controller.deleteRelationship(args[1], args[2]);

        } else {
            view.showError("Invalid arguments for removing a class, please refer to help.");
        }
    }


//================================================================================================================================================
//Save and Load
//================================================================================================================================================


    /**
     * Saves work into a json fileSaves a current diagram to a JSON file.
     */
    private void save(String[] args) {
        try {
            if(!args[1].contains(".json"))
            {
                view.showError("Must save as .json file");
            }
            else
            {
                controller.save(args[1]);
                view.save();
            }

        } catch (Exception e) {
            System.out.println("Invalid arguments");
        }
    }

    /**
     * Loads a jsonLoads a current diagram from a JSON file.
     */
    private void load(String[] args) {
        try {
            if(!args[1].contains(".json"))
            {
                view.showError("Must load a .json file");
            }
            else
            {
                controller.load(args[1]);
                view.load();
                makeReader();
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Invalid file name");
        }
    }

    /**
     * Prints a thicc boi.
     */
    private void chungus() 
    {
        //Secret method call.
        view.setGUIVisible();                                                           
    }

    /**
     * Undo an action.
     */
    private void undo() {
        controller.undo();
    }

    /**
     * Redo an action.
     */
    private void redo() {
        controller.redo();
    }


//================================================================================================================================================
//Getters
//================================================================================================================================================

    /**
     * Gets a list of field names.
     */
    private ArrayList<String> getFieldNames(String className) {
        UML.model.Class c = store.findClass(className);
        Set<Field> fields = c.getFields();
        ArrayList<String> toReturn = new ArrayList<String>();
        for (Field f : fields) {
            toReturn.add(f.getName());
        }
        return toReturn;
    }

    /**
     * Gets a list of method strings.
     */
    private ArrayList<String> getMethodNames(String className) {
        UML.model.Class c = store.findClass(className);
        Set<Method> methods = c.getMethods();
        ArrayList<String> toReturn = new ArrayList<String>();
        for (Method m : methods) {
            String toAdd = "";
            toAdd += m.getAccessString() + " ";
            toAdd += m.getType() + " ";
            toAdd += m.getName();
            if (!m.getParams().isEmpty()) {
                toAdd += " ";
                int count = 0;
                for (Parameter param : m.getParams()) {
                    toAdd += param.toString();
                    count++;
                    if (count != m.getParams().size())
                        toAdd += " ";
                }
            }
            toReturn.add(toAdd);
        }
        return toReturn;
    }

    /**
     * Gets a list of parameter strings.
     */
    private ArrayList<String> getParameters(String className) {
        UML.model.Class c = store.findClass(className);
        Set<Method> methods = c.getMethods();
        ArrayList<String> toReturn = new ArrayList<String>();

        for(Method m : methods)
        {
            for(Parameter param : m.getParams())
            {
                String toAdd = "";
                toAdd += param.toString();
                toReturn.add(toAdd);
            }
        }
        return toReturn;
    }


//================================================================================================================================================
//
//================================================================================================================================================
 
    /**
     * Displays a specified class.
     */
    private void display(String[] args) {
        if (args.length == 1) 
        {
            //With no arguments display all the classes.
            String classes = "";
            for (UML.model.Class c : store.getClassStore()) 
            {
                classes += "Class Name: " + c.getName() + "\n";
            }
            view.display(classes);
        }
        else 
        {
            //With arguments, display a specified class.
            UML.model.Class aClass = store.findClass(args[1]);
            if (aClass == null) 
            {
                view.showError("Class does not exist");
            } 
            else 
            {
                int stop = aClass.toString().indexOf("Relationships To Others: ");
                view.display(aClass.toString().substring(0, stop - 32));
            }
        }
    }

    /**
     * Displays the current relationships between classes.
     */
    private void displayRelationships()
    {
        String relationships = "";
        for (UML.model.Class c : store.getClassStore()) 
        {
            for(Map.Entry<String, RelationshipType> entry : c.getRelationshipsToOther().entrySet())
            {
                relationships += c.getName() + " ==" + entry.getValue() + "==> " + entry.getKey() + "\n";
            }
        }
        view.display(relationships);
    }

    /**
     * Makes a new completer and reader based on state of the model.
     */
    private void makeReader() {
        if (store.getClassStore().isEmpty()) {
            completer = new TreeCompleter(node("addc", "exit", "help", "showgui", "save", "load", "undo", "redo"));
        } else {
            // Simulate an addc to allow node building to work in the case of loading a file
            // from GUI.
            history.add("addc " + store.getClassStore().get(0).getName());

            StringsCompleter classes = new StringsCompleter(store.getClassList());
            String[] str = reader.getHistory().get(reader.getHistory().last()).split(" ");

            boolean hasFields = false;
            // Check if the class store has fields.
            for (UML.model.Class c : store.getClassStore()) {
                if (!c.getFields().isEmpty())
                    hasFields = true;
            }

            // Check if the class store has methods.
            boolean hasMethods = false;
            for (UML.model.Class c : store.getClassStore()) {
                if (!c.getMethods().isEmpty())
                    hasMethods = true;
            }

            
            // Make String completers for access and type.
            StringsCompleter accesses = new StringsCompleter(this.theAccesses);

            StringsCompleter types = new StringsCompleter(this.theTypes);

            if (str[0].equals("load") || (!hasFields && !hasMethods)) {
                completer = new TreeCompleter(node("addc"), node("renamec", node(classes)),
                        node("deletec", node(classes)), node("addf", node(classes, node(accesses, node(types)))),
                        node("addm", node(classes, node(accesses, node(types)))),
                        node("addr",
                                node(classes,
                                        node(classes,
                                                node(new StringsCompleter("Aggregation", "Composition",
                                                        "Generalization", "Realization"))))),
                        node("deleter", node(classes, node(classes))),
                        node("help", "exit", "showgui", "save", "load", "undo", "redo"),
                        node("display", node(classes)));
            } else if (!hasFields) {
                completer = new TreeCompleter(node("addc"), node("renamec", node(classes)),
                        node("deletec", node(classes)), node("addf", node(classes, node(accesses, node(types)))),
                        node("addm", node(classes, node(accesses, node(types)))),
                        node("renamem", node(classes, node(new StringsCompleter(getMethodNames(str[1]))))),
                        node("deletem", node(classes, node(new StringsCompleter(getMethodNames(str[1]))))),
                        node("changemt",
                                node(classes, node(new StringsCompleter(getMethodNames(str[1])), node(types)))),
                        node("changema",
                                node(classes, node(new StringsCompleter(getMethodNames(str[1])), node(accesses)))),
                        node("addp", node(classes, node(new StringsCompleter(getMethodNames(str[1]))))),
                        node("deletep",
                                node(classes,
                                        node(new StringsCompleter(getMethodNames(str[1])),
                                                node(new StringsCompleter(getParameters(str[1])))))),
                        node("addr",
                                node(classes,
                                        node(classes,
                                                node(new StringsCompleter("Aggregation", "Composition",
                                                        "Generalization", "Realization"))))),
                        node("deleter", node(classes, node(classes))),
                        node("help", "exit", "showgui", "save", "load", "undo", "redo", "displayr"),
                        node("display", node(classes)));
            } else if (!hasMethods) {
                completer = new TreeCompleter(node("addc"), node("renamec", node(classes)),
                        node("deletec", node(classes)), node("addf", node(classes, node(accesses, node(types)))),
                        node("addm", node(classes, node(accesses, node(types)))),
                        node("renamef", node(classes, node(new StringsCompleter(getFieldNames(str[1]))))),
                        node("deletef", node(classes, node(new StringsCompleter(getFieldNames(str[1]))))),
                        node("changeft", node(classes, node(new StringsCompleter(getFieldNames(str[1])), node(types)))),
                        node("changefa",
                                node(classes, node(new StringsCompleter(getFieldNames(str[1])), node(accesses)))),
                        node("addr",
                                node(classes,
                                        node(classes,
                                                node(new StringsCompleter("Aggregation", "Composition",
                                                        "Generalization", "Realization"))))),
                        node("deleter", node(classes, node(classes))),
                        node("help", "exit", "showgui", "save", "load", "undo", "redo", "displayr"),
                        node("display", node(classes)));
            } else {
                completer = new TreeCompleter(node("addc"), node("renamec", node(classes)),
                        node("deletec", node(classes)), node("addf", node(classes, node(accesses, node(types)))),
                        node("renamef", node(classes, node(new StringsCompleter(getFieldNames(str[1]))))),
                        node("deletef", node(classes, node(new StringsCompleter(getFieldNames(str[1]))))),
                        node("changeft", node(classes, node(new StringsCompleter(getFieldNames(str[1])), node(types)))),
                        node("changefa",
                                node(classes, node(new StringsCompleter(getFieldNames(str[1])), node(accesses)))),
                        node("addm", node(classes, node(accesses, node(types)))),
                        node("renamem", node(classes, node(new StringsCompleter(getMethodNames(str[1]))))),
                        node("deletem", node(classes, node(new StringsCompleter(getMethodNames(str[1]))))),
                        node("changemt",
                                node(classes, node(new StringsCompleter(getMethodNames(str[1])), node(types)))),
                        node("changema",
                                node(classes, node(new StringsCompleter(getMethodNames(str[1])), node(accesses)))),
                        node("addp", node(classes, node(new StringsCompleter(getMethodNames(str[1]))))),
                        node("deletep",
                                node(classes,
                                        node(new StringsCompleter(getMethodNames(str[1])),
                                                node(new StringsCompleter(getParameters(str[1])))))),
                        node("addr",
                                node(classes,
                                        node(classes,
                                                node(new StringsCompleter("Aggregation", "Composition",
                                                        "Generalization", "Realization"))))),
                        node("deleter", node(classes, node(classes))),
                        node("help", "exit", "showgui", "save", "load", "undo", "redo", "displayr"),
                        node("display", node(classes)));
            }
        }

        //Make the new reader based on the new completer.
        reader = LineReaderBuilder.builder().terminal(terminal).history(history).completer(completer).parser(parser)
                .variable(LineReader.MENU_COMPLETE, true).build();

        reader.option(LineReader.Option.COMPLETE_IN_WORD, true);
        reader.option(LineReader.Option.RECOGNIZE_EXACT, true);
        reader.option(LineReader.Option.CASE_INSENSITIVE, true);
        reader.option(LineReader.Option.AUTO_REMOVE_SLASH, true);
    }
}