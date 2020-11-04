package UML.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UML.views.*;
import UML.model.*;

public class EditMethodController implements ActionListener {

    private Store store;
    private View view;
    private Controller controller;

    public EditMethodController(Store s, View v, Controller c) {
        this.view = v;
        this.store = s;
        this.controller = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
    
}
