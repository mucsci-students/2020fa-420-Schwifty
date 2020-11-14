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
import java.util.Stack;

public class CLI {

    private Store store;
    private View view;
    private Controller controller;
    private Terminal terminal;
    private LineReader reader;
    private Completer completer;
    private History history;
    private Parser parser;

    public CLI(Store s, View v, Controller c) {
        store = new Store();
        this.store = s;
        this.view = v;
        this.controller = c;
        v.start();

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

        completer = new TreeCompleter(node("addc", "exit", "help", "showgui", "load"));

        // Build the reader and it's options
        reader = LineReaderBuilder.builder().terminal(terminal).history(history).completer(completer).parser(parser)
                .variable(LineReader.MENU_COMPLETE, true).build();

        reader.option(LineReader.Option.COMPLETE_IN_WORD, true);
        reader.option(LineReader.Option.RECOGNIZE_EXACT, true);
        reader.option(LineReader.Option.CASE_INSENSITIVE, true);
        reader.option(LineReader.Option.AUTO_REMOVE_SLASH, true);
        // unset default tab behavior
        reader.unsetOpt(LineReader.Option.INSERT_TAB);
        history.attach(reader);

        // Make sure the correct tab completions are used.
        makeReader();

        boolean go = true;
        while (go) {
            try {
                // Reads the line from the user
                String readLine = reader.readLine(">", "", (MaskingCallback) null, null);
                readLine = readLine.trim();
                // Writer back completions to console.
                terminal.writer().println(readLine);
                terminal.flush();
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
    private void showGUI() {
        if (controller.getGUIExists()) {
            view.setGUIVisible();
            try {
                terminal.close();
            } catch (Exception e) {

            }
        } else {
            try {
                controller.save("toLoad");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Store s = new Store();
            GraphicalView v = new GraphicalView();
            StateController stateController = controller.getStateController();
            Stack<Store> undoState = stateController.getUndoStack();
            Stack<Store> redoState = stateController.getRedoStack();
            Store currentState = stateController.getCurrentState();
            Controller c = new Controller(s, v);
            StateController state = new StateController(currentState, undoState, redoState);
            v.start();
            c.addListeners();
            try {
                terminal.close();
                c.load("toLoad.JSON");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
        }
    }

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

    /**
     * Adds a field to a class in the store.
     */
    private void addField(String[] args) {
        if (args.length == 5 && store.findClass(args[1]) != null) {
            controller.createField(args[1], args[3], args[4], args[2]);
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
            view.load();
            makeReader();
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
        System.out.println("THICCC BOY");
                                                                                                                                                          
        System.out.println("                                         ,z,              ,z,             ");   
        System.out.println("                                         ,+i              *xi             ");                     
        System.out.println("                                         :MW:             #n+             ");                     
        System.out.println("                                         `Mxx`           `nnz`            ");                     
        System.out.println("                                         `MnW*           :nnn.            ");                     
        System.out.println("                                         `MnMx`         `*nnn.            ");                     
        System.out.println("                                         ;MnnM,         `#nnn:             ");                    
        System.out.println("                                        `*MxnMi         `znnn*`           ");                     
        System.out.println("                                         #xxxxz.        ,nnnn+`            ");                    
        System.out.println("                                        `xxnzxM;        ixn++#`           ");                     
        System.out.println("                                        `nnnn#x#,      `#nn+in,           ");                     
        System.out.println("                                        `xnnxixx:      .nnni;x:           ");                     
        System.out.println("                                        `xnnx;zM;`     :xnni:x:          ");                      
        System.out.println("                                        ,xnnxi+x#,     +nnn;:n;             ");                   
        System.out.println("                                        iMnnxiixx:    `nnnn::zi           ");                     
        System.out.println("                                        ;Mnnxi;nx:    ,nnnn::##             ");                   
        System.out.println("                                        iMnnn*:zx:    :xnnn;:+z            ");                    
        System.out.println("                                        ixnnn*:#xi    *nnnn::+z`        ");                       
        System.out.println("                                       ixnnn*:+x#`   #nnnz::*n.           ");                    
        System.out.println("                                       *xnnn*:inn`  `nnnnz::ix.          ");                     
        System.out.println("                                       ixnnn*:;nx`  .xnnnn::ix,         ");                      
        System.out.println("                                       *xnnn+::zx,  ,xnnnz,,;x,        ");                       
        System.out.println("                                       ixnnn+::zx:  :xnznz,,:x,         ");                      
        System.out.println("                                       ixnnn+,:#xi  ,xnnnz:::x:           ");                    
        System.out.println("                                       ;xnnn+::+x*  ,xnnnz::;x:        ");                       
        System.out.println("                                       ixnnn#:,*xz` *xnnnz::;x:           ");                    
        System.out.println("                                       :xnnn#::*xz` ixnznz::;x:          ");                     
        System.out.println("                                       ,xnnn#,:in#  ;xnnnz,:ix,       ");                        
        System.out.println("                                       ,xnnnz::;x#` :xnnn#,,ix,        ");                       
        System.out.println("                                       ,xnnnz::inz` .xnzn#:,+x,     ");                          
        System.out.println("                                       .xnnnz::;nx. .xnnn#:,#x,      ");                         
        System.out.println("                                       `nnnnz::;nx. .xnnn+::zx,    ");                           
        System.out.println("                                       `znnnn::;nx. `xnnn*:;nn.      ");                         
        System.out.println("                                        *xnnn;:;nx. `xnnn*:ixz      ");                          
        System.out.println("                                        :xnnni,;nx` `xnzni,+x+       ");                         
        System.out.println("                                        .nnzn*,:nx` `xnnni,#xi     ");                           
        System.out.println("                                          #nnn#,;nx. `nnnn::zx,    ");                            
        System.out.println("                                         ixnnn:;nx. .zxnn:;xn,     ");                           
        System.out.println("                                          :xnnn;inx. `#xnz:*xn`   ");                             
        System.out.println("                                          `nnnn#*nx:`;#xnz:#x#`   ");                             
        System.out.println("                                           ixnnnznx;;zzxnn:nMi`   ");                             
        System.out.println("                                          .xnnnxnx+#MnMxnznx:    ");                             
        System.out.println("                                          `nnnnnnx#xMxxnnnxn;   ");                              
        System.out.println("                                           ixnnnnxnxxnxMMMM#*   ");                              
        System.out.println("                                           .xnnnnMnnnnnxnnMz*`  ");                              
        System.out.println("                                           ,xnnnnxx@MnnnxnxWx:.      ");                         
        System.out.println("                                            .nxnnxnWMnnnnnnnW@M:         ");                      
        System.out.println("                                            ,nnnnnxWnnnnnnnnMnxni`         ");                    
        System.out.println("                                           ixnnnnxMnnnnnnznnnnx#.        ");                     
        System.out.println("                                            znnnnnnxnnnnnnnnnnnnz.          ");                   
        System.out.println("                                          .nnnnnnnnnnnnnznnnnnnM:          ");                   
        System.out.println("                                          ,xnnnnnnnnxnnnnnnnnnxx#`                 ");           
        System.out.println("                                         ,xnnnnznnnxxxnnnnznnxnn.                    ");        
        System.out.println("                                          ,xnnnnnnnnxnnxnnzznnnnx;`                  ");         
        System.out.println("                                         ,xnznnnnnnxnzznnznnnz+x+.                     ");      
        System.out.println("                                          ,xnznnnnnzx#n,znnnznMx#*:                  ");         
        System.out.println("                                          ,nzznnnnnnn*W;+nnzzzMM##i,.                  ");       
        System.out.println("                                          ,xznnzzzzzn+x*;nnnznnni:+#+:                  ");      
        System.out.println("                                         ;nz#i;::::*#+::nnzznn#,,;#M+                    ");    
        System.out.println("                                          ;n*:,.,.,,;;,i+nnnnn#;:ii:n*`                ");       
        System.out.println("                                          *n;.,.,,,,,i*;;;;znz*:*ii;+:                   ");     
        System.out.println("                                         ##,..,..,,;;;i*i::z#:,+*ii+*`                  ");     
        System.out.println("                                        .z:......,,,::;+#*,:i.;;,,.*i                   ");     
        System.out.println("                                         .#:,..,,,,:*nxxM#:;*ii*,.,,*;                   ");     
        System.out.println("                                        :zi..,.,,.,:,nxxx#:,,.;...:#;                   ");     
        System.out.println("                                         ;n+;,....,,.,+xxx*`,,,;...:ii                   ");     
        System.out.println("                                         ;n#+:......,.;zzz#*+ii,....;*                   ");     
        System.out.println("                                         *nzii,.......,#++++i,.,...,,*                  ");      
        System.out.println("                                         `#nz+,i,,......,###i,..,....:*                  ");      
        System.out.println("                                         ,nzz#.,,.....,i.,::.,;......,i                  ");      
        System.out.println("                                      `:znzz#,.,,.,.,.;i:,,;i,.,..,.:+                   ");     
        System.out.println("                                     `;nnzzz+,,....,.,,,;i;:,.,.,...:+`                  ");     
        System.out.println("                                    .+nzzzzz*......,.......,..,,....:+`                  ");     
        System.out.println("                                   ,znzzzzzzi,,...,...,;...,..;,.,.,;n:                  ");     
        System.out.println("                                 `*nzzzzzzzz;,,.,...,..;i:.,:i:.....:n#.                 ");     
        System.out.println("                                .znzzzzzzzz#,,,.,,,.....,;i;;,.....,;zn*.                 ");    
        System.out.println("                              `;nnzzzzzzzzz+,,.....,,.,......,....,,,zzn*`               ");     
        System.out.println("                             .+nnzzzzzzzzzzi.,....,,..............,,.+zzni`              ");     
        System.out.println("                            ,znnzzzzzzzzzzz:,..,....,.......,....,..,;zzzn;               ");    
        System.out.println("                          `innzzzznnzzzzzz+,,...,.,.........,...,.,..,#zzzx;`             ");    
        System.out.println("                          ;nnzzzzzzzzzzzzz:................,......,,,.;zzzzn:              ");   
        System.out.println("                        ;nzzzzzzzzzzzzzz+,,.......,..................,+zzznn:             ");   
        System.out.println("                         :nnzzzzzzzzzzzzzz;,...,.,......,.....,.........;nzzznz,           ");    
        System.out.println("                       :nzzzzzzzzznznnnn*.,......,......,.,.,...,...,.,,+zzzzn#.            ");  
        System.out.println("                      .nnzzzznzzzznnnxn+,,.,...........................,,zzzzzn+`            "); 
        System.out.println("                      `#nnzzzzzzzznxzzz#:,,............................,.,*zzzzzn;           ");  
        System.out.println("                     inzzzzzzzzznnzzzz;,.................,.........,....,:zzzzzzn:           "); 
        System.out.println("                    ,nnzzzzzzzznnzzzz;..,..................`............,.+zzzzzn#`           ");
        System.out.println("                   `+nzzzzzzzznnzzzz*,.....,.,............................:zzzzznni           ");
        System.out.println("                   ,nzzzzzzzznnzzzz+,..............,,...................,..#zzzzzzz:          ");
        System.out.println("                   *zzzzzzzzzxzzzz#:......................................,izzzzzzn+`         ");
        System.out.println("                  `zzzzzzzzzxnznzzi,....,..............................,...:zzzzzzzn;         ");
        System.out.println("                   ,nzz+i*+znnzzzz#,..,.................................,....zzzzzzzn+`        ");
        System.out.println("                   ;n#,````,zzzzzz;...................................,.....,#zznz##zn,        ");
        System.out.println("                   i#.:i;;:.:zz#z+,..........................................*z+:;;;:;*`       ");
        System.out.println("                   ;;*,i,:.:inzzz:..........,...............................,i+:i,i,+ii:       ");
        System.out.println("                  `;*i.;:.```+zz*,.........,........,.....................;:,**:``,:i:zi       ");
        System.out.println("                  .*.i`::.```.+z;.........,..............................;,,:,````,:i.*i`      ");
        System.out.println("                  :i`;`:,``````:;i,...................,..........,.......;`.``````:::,;*,      ");
        System.out.println("                 `i.`:`,.````````.:.......................,..............;`.``````,``:,ii      ");
        System.out.println("                 `i.`:```````````::.....,................................,i.`,,``````:`;i      ");
        System.out.println("                `i``.```````.+:;:.................................`.......:;i:````````i*      ");
        System.out.println("                 `i``````````.*,.............................................;.````````z;      ");
        System.out.println("                `i.````` ```.;..........,...,........................,....,.i``````..,z`      ");
        System.out.println("                 ;, `````` ``;.............................................::```:``:`*+       ");
        System.out.println("                 ,i``````````:.........................................,...;```,``,,.n.       ");
        System.out.println("                 `;,``  `,,``.:........,....,.............................,;``:.`,,`++        ");
        System.out.println("                  .i`````.:,``;,.............................,...........,.i,;,`,:`*z.      ");  
        System.out.println("                   ,i.`.```:``.:........................`,..,..............;i*,i;:zx;        "); 
        System.out.println("                  `+#.,,` `:``;...............,...........,,..,.......,....:+nn##M*      ");    
        System.out.println("                   `#zziii.`*;i,.........................,................,.i;.,           ");   
        System.out.println("                    +zzznzzz*,...........,.................,.,..`.....,....,*.          ");      
        System.out.println("                   izzzzzzz*..............................,...............,*            ");     
        System.out.println("                   ,nzzzzzz*..........................................,.,,i,         ");        
        System.out.println("                   `#zzzzzz#,.................,.....................,.....*`        ");         
        System.out.println("                    inzzzzz#,.,..`........................,.....,..,..,..;;       ");           
        System.out.println("                   .nzzzzzz;...........................................,*`       ");           
        System.out.println("                    +zzzzzz+..............,............................:*         ");          
        System.out.println("                      :nzzzzz#:...`.`.......,............................*.      ");             
        System.out.println("                      `#zzzzzzi.........................................:i,      ");             
        System.out.println("                       ;nzzzzz#,..................................,....,*..     ");              
        System.out.println("                       `#zzzzzzi......................................,+ii;.    ");              
        System.out.println("                       ,nzzzzz#:..........,..........................*;,,:+.      ");           
        System.out.println("                          *zzzzzz#,..................................,i;::;i*;.`   ");            
        System.out.println("                         `#zzzzzz+,................................:*i:,...,*:.   ");            
        System.out.println("                           .zzzzzzz+,.............................:ii,,,:i,..,+,   ");            
        System.out.println("                           .#nzzzzz+,........................,:i*;.....,.i:.,;;    ");           
        System.out.println("                  `      ``,+zzzzzz#;..,.................,,i**:,.........i.,:i   " );            
        System.out.println("              `:iiiii#nzzz##+nnnzzzz#*:..........,,:,:;iii;,..........,.,i,:+,    ");            
        System.out.println("             ,*:....,.*nzzzzzzznnzzzzzz+;,,;ii+##+**i;:.,.....,.....,,,,:+i*.    ");             
        System.out.println("             `*,,::;;.,.inzzzzzzzzzzzzzzzz++i...:;iiii;:::,::,,,,,,,,,:ii*,`   ");                
        System.out.println("            ,*;:,,.....,;zzzzzzzzzzzzzzzi,+.      `...,,,:;:;::::;;;;:.`    ");                  
        System.out.println("            .*,.....,;;i;.;zzzzzzzzzzzzi,:+.  ");                                                 
        System.out.println("            *:.....:;...:,.:#zzzzzz#*;,:i*.   ");                                                 
        System.out.println("            *...,.,i.........,:::;,,:;*i.     ");                                                 
        System.out.println("           *,,...i...,.....,..,:;**;,``   ");                                                    
        System.out.println("            ;;....i.........,:i*i:.`   ");                                                        
        System.out.println("            `i;,.,i..,....,i*i,`` ");                                                             
        System.out.println("            `;iii+:,,,,;*i,`` ");                                                                
        System.out.println("               ```,iiii;;.`" );                                                                  
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

    /**
     * Displays a specified class.
     */
    private void display(String[] args) {
        if (args.length == 1) {
            String classes = "";
            for (UML.model.Class c : store.getClassStore()) {
                classes += c.toString() + "\n";
            }
            view.display(classes);
        } else {
            UML.model.Class aClass = store.findClass(args[1]);
            if (aClass == null) {
                view.showError("Class does not exist");
            } else {
                view.display(aClass.toString());
            }
        }
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
            StringsCompleter accesses = new StringsCompleter("public", "private", "protected");

            StringsCompleter types = new StringsCompleter("boolean", "char", "double", "float", "int", "short",
                    "size_t", "String", "unsigned");

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
                        node("help", "exit", "showgui", "save", "load", "undo", "redo"),
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
                        node("help", "exit", "showgui", "save", "load", "undo", "redo"),
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
                        node("help", "exit", "showgui", "save", "load", "undo", "redo"),
                        node("display", node(classes)));
            }
        }
        reader = LineReaderBuilder.builder().terminal(terminal).history(history).completer(completer).parser(parser)
                .variable(LineReader.MENU_COMPLETE, true).build();

        reader.option(LineReader.Option.COMPLETE_IN_WORD, true);
        reader.option(LineReader.Option.RECOGNIZE_EXACT, true);
        reader.option(LineReader.Option.CASE_INSENSITIVE, true);
        reader.option(LineReader.Option.AUTO_REMOVE_SLASH, true);
    }
}