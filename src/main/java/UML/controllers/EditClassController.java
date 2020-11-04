package UML.controllers;

import UML.views.*;
import UML.model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditClassController implements ActionListener {

    private Store store;
    private View view;
    private Controller controller;

    public EditClassController(Store s, View v, Controller c) {
        this.view = v;
        this.store = s;
        this.controller = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    
}
