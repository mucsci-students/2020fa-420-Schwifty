
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 09/10/2020
    Purpose: To start the program.
 */
import UML.model.Store;
import UML.views.View;
import UML.views.GraphicalView;
import UML.views.InterfaceChoiceView;
import UML.views.CommandlineView;
import UML.controllers.Controller;
import UML.controllers.CLI;
import java.util.Scanner;

public class UMLEditor 
{
    /**
     * The main method of the program.
     */
    public static void main(String[] args)
    {
        Store s = new Store();
        InterfaceChoiceView v = new InterfaceChoiceView();
        Controller c = new Controller(s, v);
        //Adds the action lsiteners for the interface choice controller.
        c.addListener();
    }
}


