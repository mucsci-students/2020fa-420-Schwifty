package UML.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.FlowLayout;

import UML.views.*;
import UML.model.*;

public class DeleteRelationshipController implements ActionListener {

    private Store store;
    private View view;
    private Controller controller;

    public DeleteRelationshipController(Store s, View v, Controller c) {
        this.view = v;
        this.store = s;
        this.controller = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String cmd = e.getActionCommand();
        String[] cmdArr = cmd.split(" ");
        String className = cmdArr[1];

        String classTo = "";

        //Get class from storage.
        UML.model.Class theClass = store.findClass(className);
        
        boolean valid = false;
        while(!valid)
        {   
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            //Allow deleting of relationships that this class has with others.
            Object[] to = theClass.getRelationshipsToOther().keySet().toArray();
            Object[] from = theClass.getRelationshipsFromOther().keySet().toArray();
            Object[] relationships = new String[to.length + from.length];
            System.arraycopy(to, 0, relationships, 0, to.length);
            System.arraycopy(from, 0, relationships, to.length, from.length);
            JComboBox toBox = new JComboBox(relationships);
            panel.add(toBox);
            
            int result = JOptionPane.showConfirmDialog(null, panel,
                "Delete Relationship", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                //Get to String.
                classTo = (String) ((JComboBox)panel.getComponent(0)).getSelectedItem();

                controller.deleteRelationship(className, classTo);
                return;
            }
            //Cancel.
            else if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
            {
                return;
            }
            else
                valid = true;
        }
    }
}
