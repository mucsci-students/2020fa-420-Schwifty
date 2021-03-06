package UML.controllers;

import UML.views.*;
import UML.model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.JTextField;

public class CreateFieldController implements ActionListener 
{
    private Store store;
    private View view;
    private Controller controller;

    /**
     * Constructor for CreateFieldController. 
     */
    public CreateFieldController(Store s, View v, Controller c) {
        this.view = v;
        this.store = s;
        this.controller = c;
    }

    /**
     * Creates a window for the user to input a field. 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String[] cmdArr = cmd.split(" ");
        String className = cmdArr[1];

        String access = "";
        String type = "";
        String name = "";
        
        boolean valid = false;
        while(!valid)
        {   
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            String[] accessTypes = new String[3];
            accessTypes[0] = "public";
            accessTypes[1] = "private";
            accessTypes[2] = "protected";

            JComboBox accessBox = new JComboBox(accessTypes);
            panel.add(accessBox);
            
            JTextField typeArea = new JTextField(12);
            panel.add(typeArea);

            JTextField nameArea = new JTextField(12);
            panel.add(nameArea);
            

            int result = JOptionPane.showConfirmDialog(null, panel,
                "Create field", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                //Get access String.
                access = (String) ((JComboBox)panel.getComponent(0)).getSelectedItem();

                //Get type String.
                type = (String) ((JTextField) panel.getComponent(1)).getText();

                //Get name String.
                name = (String) ((JTextField) panel.getComponent(2)).getText();

            }
            //Cancel.
            else if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
            {
                return;
            }

            //Handle exceptional cases.    
            if(type.trim().equals("") || type.contains(" ") || name.trim().equals("") || name.contains(" "))
            {
                view.showError("Type and name must not contain spaces or be blank.");
            }
            else
                valid = true;
        }

        //Create the field.
        controller.createField(className, type, name, access);
    }
}
