/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 09/10/2020
    Purpose: To start the program.
 */
import UML.model.Store;
import UML.views.View;
import UML.views.GraphicalView;
import UML.views.CommandlineView;
import UML.controllers.Controller;
import UML.controllers.InterfaceChoice;
import UML.controllers.CLI;
import java.util.Scanner;

public class UMLEditor 
{
    /**
     * The main method of the program.
     */
    public static void main(String[] args)
    {
        /**
        Scanner console = new Scanner(System.in);
        Store s = new Store();
        CommandlineView v = new CommandlineView();
        Controller c = new Controller(s, v);
        //Opens the CLI version of the app.
        CLI cli = new CLI(s, v, c, console);
        */
        InterfaceChoice ic = new InterfaceChoice();
    }
}

