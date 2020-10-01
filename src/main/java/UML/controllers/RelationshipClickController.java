package UML.controllers;

import java.awt.event.*;

import UML.model.Class;
import UML.model.Field;
import UML.model.Method;
import UML.model.Parameter;
import UML.model.SaveAndLoad;
import UML.model.Store;

import UML.views.*;

public class RelationshipClickController implements ActionListener {
    private Store store;
    private View view;

    public RelationshipClickController(Store store, View v) {
        this.view = v;
        this.store = store;
    }

    public void actionPerformed(ActionEvent e) {
        // TODO: Prevent class from having a relationship to itself.
        String cmd = e.getActionCommand();
        if (cmd.equals("Association")) {
            // Creates two dialog boxes to get the classes to add the relationship to or
            // from.
            Object[] classList = store.getClassList().toArray();

            String buildRelateOne = getResultFromComboBox("Choose first class", "Association", classList);

            String buildRelateTwo = getResultFromComboBox("Choose second class", "Association", classList);

            // Add the relationship between the classes.
            Class class1 = store.findClass(buildRelateOne);
            Class class2 = store.findClass(buildRelateTwo);
            // Change add relationship.
            store.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.ASSOCIATION);
            // Create relationship between chosen two, a relationship from and to must be
            // made.
            updateDisplayRelationship(class1, class2);
        } else if (cmd.equals("Aggregation")) {
            Object[] classList = store.getClassList().toArray();

            // Creates two dialog boxes to get the classes to add the relationship to or
            // from.
            String buildRelateOne = getResultFromComboBox("Choose first class", "Aggregation", classList);

            String buildRelateTwo = getResultFromComboBox("Choose second class", "Aggregation", classList);

            // Add the relationship between the classes.
            Class class1 = store.findClass(buildRelateOne);
            Class class2 = store.findClass(buildRelateTwo);
            // Change add relationship.
            store.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.AGGREGATION);
            updateDisplayRelationship(class1, class2);
        } else if (cmd.equals("Composition")) {
            // creates two dialog boxes to get the classes to add the relationship to or
            // from.
            Object[] classList = store.getClassList().toArray();

            String buildRelateOne = getResultFromComboBox("Choose first class", "Composition", classList);

            String buildRelateTwo = getResultFromComboBox("Choose second class", "Composition", classList);

            // Add the relationship between the classes.
            Class class1 = store.findClass(buildRelateOne);
            Class class2 = store.findClass(buildRelateTwo);
            // Chnage add relationship
            store.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.COMPOSITION);
            // display relationship
            updateDisplayRelationship(class1, class2);
        } else if (cmd.equals("Generalization")) {
            // Creates two dialog boxes to get the classes to add the relationship to or
            // from.
            Object[] classList = store.getClassList().toArray();

            String buildRelateOne = getResultFromComboBox("Choose first class", "Generalization", classList);

            String buildRelateTwo = getResultFromComboBox("Choose first class", "Generalization", classList);
            // Add the relationship between the classes.
            Class class1 = store.findClass(buildRelateOne);
            Class class2 = store.findClass(buildRelateTwo);
            // Change add relationship.
            store.addRelationship(buildRelateOne, buildRelateTwo, RelationshipType.GENERALIZATION);
            // Display relationship.
            updateDisplayRelationship(class1, class2);
        } else if (cmd.equals("Delete Relationship")) {
            // TODO: Not working correctly, need to display the relationship to the user
            // Create a dialog box with two dropdowns of available classes.
            Object[] classList = store.getClassList().toArray();

            String buildRelateOne = getResultFromComboBox("Choose first class", " Class", classList);

            String buildRelateTwo = getResultFromComboBox("Choose Second class", " Class", classList);

            // Delete relationship between chosen two.
            Class class1 = store.findClass(buildRelateOne);
            Class class2 = store.findClass(buildRelateTwo);
            // Change deleteRelationship.
            store.deleteRelationship(buildRelateOne, buildRelateTwo);
            // TODO: Add a try catch if false is returned.
            // Delete relationship from display.
            updateDisplayRelationship(class1, class2);
        }
    }
}
