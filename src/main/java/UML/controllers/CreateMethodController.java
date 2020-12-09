package UML.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.util.ArrayList;

import UML.views.*;
import UML.model.*;

public class CreateMethodController implements ActionListener  {

    private Store store;
    private View view;
    private Controller controller;

    /**
     * Constructor for CreateMethodConstroller.
     */
    public CreateMethodController(Store s, View v, Controller c) {
        this.view = v;
        this.store = s;
        this.controller = c;
    }

    /**
     * Creates a window for the user to create a method.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
        String[] cmdArr = cmd.split(" ");
        String className = cmdArr[1];

        String access = "";
        String type = "";
        String name = "";
        String param = "";

        //The ArrayList to be used for parameters later.
        ArrayList<String> params = new ArrayList<String>();
        
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

            JTextField paramArea = new JTextField(15);
            panel.add(paramArea);
            

            int result = JOptionPane.showConfirmDialog(null, panel,
                "Create method", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                //Get access String.
                access = (String) ((JComboBox)panel.getComponent(0)).getSelectedItem();

                //Get type String.
                type = (String) ((JTextField) panel.getComponent(1)).getText();

                //Get name String.
                name = (String) ((JTextField) panel.getComponent(2)).getText();

                //Get param String.
                param = (String) ((JTextField) panel.getComponent(3)).getText();
            }
            //Cancel.
            else if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
            {
                return;
            }

            //Set boolean for parameters being okay.
            boolean okay = true;

            if(!param.equals(""))
            {
                //Add the params to an array list to call the controller method.
                String[] theParams = param.split(",");
                // type" "name," "type" "name," "...
                // type" "name,type" "name,...
                for(int count = 0; count < theParams.length; count++)
                {
                    String[] typeAndName = theParams[count].trim().split(" ");
                    if(typeAndName.length != 2)
                    {
                        okay = false;
                        params.clear();
                        break;
                    }
                    String temp = typeAndName[0] +  " " + typeAndName[1];
                    params.add(temp);
                }
            }

            //Handle exceptional cases.    
            if(type.trim().equals("") || type.contains(" ") || name.trim().equals("") || name.contains(" "))
            {
                view.showError("Type and name must not contain spaces or be blank.");
            }
            else if(!okay)
            {
                view.showError("A parameter should be a type followed by a name.");               
            }
            else
                valid = true;
        }

        //Create the field.
        controller.createMethod(className, type, name, params, access);
	}
    
}
