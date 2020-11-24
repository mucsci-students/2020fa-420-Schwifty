package UML.controllers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UML.views.*;
import UML.model.*;

public class CreateRelationshipController implements ActionListener {

    private Store store;
    private View view;
    private Controller controller;

    public CreateRelationshipController(Store s, View v, Controller c) {
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
        String relateType;
        
        boolean valid = false;
        while(!valid)
        {   
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            
            JComboBox toBox = new JComboBox(store.getClassList().toArray());
            panel.add(toBox);
            
            //Create sStrings to represent relationships.
            String[] relations ={ "AGGREGATION", "GENERALIZATION", "COMPOSITION", "REALIZATION"};
        
            JComboBox relationshipBox = new JComboBox(relations);
            panel.add(relationshipBox);
            
            int result = JOptionPane.showConfirmDialog(null, panel,
                "Create Relationship", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                //Get to String.
                classTo = (String) ((JComboBox)panel.getComponent(0)).getSelectedItem();

                relateType = (String) ((JComboBox)panel.getComponent(1)).getSelectedItem();
                controller.addRelationship(className, classTo, RelationshipType.valueOf(relateType));
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
