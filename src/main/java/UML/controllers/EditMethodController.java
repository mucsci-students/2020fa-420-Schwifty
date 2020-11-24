package UML.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        String cmd = e.getActionCommand();
        String[] cmdArr = cmd.split(" ");
        String className = cmdArr[1];

        String access = "";
        String type = "";
        String name = "";
        String param = "";

        //Get class from storage.
        UML.model.Class theClass = store.findClass(className);

        //Get the atrributes to be placed in a combo box.
        ArrayList<String> methodList = store.getMethodList(theClass.getMethods());

        //Get field to delete.
        //Has the form of (access char) (type) (name).
       
        String firstString = view.getChoiceFromUser("Edit this class", "Edit class", methodList).replace("( ", "");

        //Handle cancel.
        if(firstString == null)
            return;

        String stringMethod = firstString.replace(" )", "");

        String[] methodSplit = stringMethod.split(" ");

        //Get selected access
        String accessString = getStringVersion(methodSplit[0]);

        //ArrayList of parameter Strings for new.
        ArrayList<String> params = new ArrayList<String>();

        //The ArrayList of Strings for deleting the old method.
        ArrayList<String> oldParams = new ArrayList<String>();

        //Get params for old params.
        for (int i = 3; i < methodSplit.length - 1; i += 2)
        {
            String paramString = methodSplit[i];
            paramString += " ";
            paramString += methodSplit[i + 1];

            //Add to the ArrayList for deleting the old method.
            String add = paramString;
            oldParams.add(add);
        }
        
        boolean valid = false;
        while(!valid)
        {   
            //access
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            String[] accessTypes = new String[3];
            accessTypes[0] = "public";
            accessTypes[1] = "private";
            accessTypes[2] = "protected";

            //Get the acces String.
            JComboBox accessBox = new JComboBox(accessTypes);
            accessBox.setSelectedItem(accessString);
            panel.add(accessBox);
            
            //Get the type String.
            JTextField typeArea = new JTextField(12);
            typeArea.setText(methodSplit[1]);
            panel.add(typeArea);
            
            //Get the name Stirng.
            JTextField nameArea = new JTextField(12);
            nameArea.setText(methodSplit[2]);
            panel.add(nameArea);

            //Get the parameters.
            JTextField paramArea = new JTextField(15);
            //The string to add to the text area.
            String allParams = "";
            panel.add(paramArea);
            
            //Get params for setting text.
            for (int i = 3; i < methodSplit.length - 1; i += 2)
            {
                String paramString = methodSplit[i];
                paramString += " ";
                paramString += methodSplit[i + 1];

                if(i + 2 != methodSplit.length)
                {
                    paramString += ",";
                    i++;
                }
                allParams += paramString;
            }
            paramArea.setText(allParams);

            Object[] options = { "OK", "Delete" };
            
            int result = JOptionPane.showOptionDialog(null, panel, "Edit method",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

            if (result == 0) {
                //Get access String.
                access = (String) ((JComboBox)panel.getComponent(0)).getSelectedItem();
            
                //Get type String.
                type = (String) ((JTextField) panel.getComponent(1)).getText();

                //Get name String.
                name = (String) ((JTextField) panel.getComponent(2)).getText();

                param = (String) ((JTextField) panel.getComponent(3)).getText();

            }
            else if(result == 1)
            {
                controller.deleteMethod(className, methodSplit[1], methodSplit[2], oldParams, accessString);
                return;
            }
            //Handle canceling out.
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
                view.showError("Type and name must not contain spaces or be blank");
            }
            else if(!okay)
            {
                view.showError("A parameter should be a type followed by a name.");
            }
            else
                valid = true;
        }

        //Delete the old field and create the new corret one.
        controller.deleteMethod(className, methodSplit[1], methodSplit[2], oldParams, methodSplit[methodSplit.length - 1]);
        controller.createMethod(className, type, name, params, access);

    }
    
    /**
     * Gets the string access version from symbol version.
     */
    private String getStringVersion(String access)
    {
        String accessString = "";
        if(access.equals("+"))
        {
            accessString = "public";
        }
        else if(access.equals("-"))
        {
            accessString = "private";
        }
        else if(access.equals("*"))
        {
            accessString = "protected";
        }
        return accessString;
    }
}
