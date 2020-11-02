package UML.controllers;

/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 11/01/2020
    Purpose: Creates the action listeners for the state buttons.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UML.model.Store;
import UML.views.CommandlineView;
import UML.views.View;

public class StateClickController implements ActionListener {

    private Store store;
    private View view;
    private Controller controller;

    public StateClickController(Store s, View v, Controller c) {
        this.view = v;
        this.store = s;
        this.controller = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd.equals("Undo"))
        {
            //Undo one action.
            controller.undo();
        }
        else if(cmd.equals("Redo"))
        {
            //Redo one action.
            controller.redo();
        }
        else if(cmd.equals("CLI"))
        {
            //Set the gui invisible when we switch to the CLI view.
            controller.setGUIInvisible();
            View v = new CommandlineView();
            Controller c = new Controller(store, v);
            CLI cli = new CLI(store, v, c);
        }
    }
    
}
