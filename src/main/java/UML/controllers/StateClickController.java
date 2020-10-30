package UML.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import UML.model.Class;
import UML.model.Store;
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
            controller.undo();
        }
        else if(cmd.equals("Redo"))
        {
            controller.redo();
        }
    }
    
}
