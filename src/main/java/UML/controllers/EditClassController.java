package UML.controllers;

import UML.views.*;
import UML.model.*;

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
    public void actionPerformed(ActionEvent e) 
    {
        String cmd = e.getActionCommand();
        String[] cmdArr = cmd.split(" ");
        String className = cmdArr[1];
        //Initialize string for new name.
        String name = "";
        
        boolean valid = false;
        while(!valid)
        {   
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            //Create the text area.
            JTextArea nameArea = new JTextArea(1, 12);
            nameArea.setText(className);
            panel.add(nameArea);

            Object[] options = { "OK", "Delete" };
            
            int result = JOptionPane.showOptionDialog(null, panel, "Edit Class",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

            //Delete class
            if(result == 1)
            {
                controller.deleteClass(className);
                return;
            }
            //Rename class.
            else if (result == 0) {
                //Get name String.
                name = (String) ((JTextArea) panel.getComponent(0)).getText();
            }
            //Handle canceling out.
            else if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
            {
                return;
            }

            //Handle exceptional cases.    
            if(name.trim().equals("") || name.contains(" "))
            {
                view.showError("Class name must not contain spaces or be blank.");
            }
            else
                valid = true;
        }

        //Rename the class using the controller method.
        controller.renameClass(className, name);
    }
}
    

