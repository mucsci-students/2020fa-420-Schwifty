package UML.controllers;
/**
    Author: Chris, Cory, Dominic, Drew, Tyler.
    Date: 09/24/2020
    Purpose: Provide the user a way to select between using the command line view
    or through the graphical interface. 
 */
import UML.model.*;
import UML.views.*;
import java.util.ArrayList;
import java.awt.event.*;


public class InterfaceChoiceClickController implements ActionListener
{
    private Store store;
    private View view;
    private Controller controller;

    public InterfaceChoiceClickController(Store s, View v, Controller c)
    {
        this.view = v;
        this.store = store;
        this.controller = c;
    }

    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();
        if(cmd.equals("OK"))
        {
            //if the choice is cli, load it. otherwise, load the gui. 
            if(view.getChoiceFromUser("", "", new ArrayList<String>()).equals("true"))
            {
                view.exit();
                Store s = new Store();
                CommandlineView v = new CommandlineView();
                Controller c = new Controller(s, v);
                //Opens the CLI version of the app.
                CLI cli = new CLI(s, v, c);
             }
            else
            {
                view.exit();
                Store s = new Store();
                GraphicalView v = new GraphicalView();
                Controller c = new Controller(s, v);
                //Opens the CLI version of the app.
                v.start();
                c.addListeners();
                controller.setGUIExists();
            }
        }
        else 
        {
            System.exit(0);
        }
    }
}
