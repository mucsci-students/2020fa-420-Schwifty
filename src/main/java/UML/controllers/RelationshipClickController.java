package UML.controllers;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Creates the action listeners for the relationship buttons.
 */
import java.awt.event.*;
import java.util.ArrayList;

import UML.model.Store;
import UML.model.RelationshipType;

import UML.views.*;

public class RelationshipClickController implements ActionListener {
    private Store store;
    private View view;
    private Controller controller;

    public RelationshipClickController(Store store, View v, Controller c) {
        this.view = v;
        this.store = store;
        this.controller = c;
    }

    public void actionPerformed(ActionEvent e) 
    {
        String cmd = e.getActionCommand();
        if (cmd.equals("Realization"))
         {
            // Creates two dialog boxes to get the classes to add the relationship to or from.
            ArrayList<String> classList = store.getClassList();

            String buildRelateOne = view.getChoiceFromUser("Choose first class", "Realization", classList);

            String buildRelateTwo = view.getChoiceFromUser("Choose second class", "Realization", classList);

            // Change add relationship.
            controller.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.REALIZATION);

        } else if (cmd.equals("Aggregation")) {
            ArrayList<String> classList = store.getClassList();

            // Creates two dialog boxes to get the classes to add the relationship to or
            // from.
            String buildRelateOne = view.getChoiceFromUser("Choose first class", "Aggregation", classList);

            String buildRelateTwo = view.getChoiceFromUser("Choose second class", "Aggregation", classList);

            controller.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.AGGREGATION);

        } else if (cmd.equals("Composition")) {
            // creates two dialog boxes to get the classes to add the relationship to or
            // from.
            ArrayList<String> classList = store.getClassList();

            String buildRelateOne = view.getChoiceFromUser("Choose first class", "Composition", classList);

            String buildRelateTwo = view.getChoiceFromUser("Choose second class", "Composition", classList);

            controller.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.COMPOSITION);

        } else if (cmd.equals("Generalization")) {
            // Creates two dialog boxes to get the classes to add the relationship to or from.
            ArrayList<String> classList = store.getClassList();

            String buildRelateOne = view.getChoiceFromUser("Choose first class", "Generalization", classList);

            String buildRelateTwo = view.getChoiceFromUser("Choose first class", "Generalization", classList);

            controller.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.GENERALIZATION);

        } 
        else if (cmd.equals("DeleteRelationship")) 
        {
            // Create a dialog box with two dropdowns of available classes.
            ArrayList<String> classList = store.getClassList();

            String buildRelateOne = view.getChoiceFromUser("Choose first class", " Class", classList);

            String buildRelateTwo = view.getChoiceFromUser("Choose Second class", " Class", classList);

            controller.deleteRelationship(buildRelateOne, buildRelateTwo);
       }
    }
}
