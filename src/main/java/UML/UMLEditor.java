
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
import UML.controllers.CLI;

public class UMLEditor 
{
    public static void main(String[] args)
    {
        //Store s = new Store();
        //CommandlineView v = new CommandlineView();
        //Controller c = new Controller(s, v);
        //CLI cli = new CLI(args, s, v, c);
        interfaceChange();
    }

    public static void interfaceChange()
    {
        Store s = new Store();
        GraphicalView v = new GraphicalView();
        Controller c = new Controller(s, v);
        v.start();
        c.addListeners();
    }
}

