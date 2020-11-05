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

        String stringMethod = firstString.replace(" )", "");

        String[] methodSplit = stringMethod.split(" ");

        System.out.println(stringMethod);
        
        //Get selected access
        String accessString = getStringVersion(methodSplit[0]);

        //ArrayList of parameter Strings for new.
        ArrayList<String> params = new ArrayList<String>();

        //The ArrayList of Strings for deleting the old method.
        ArrayList<String> oldParams = new ArrayList<String>();
        
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
            JTextArea typeArea = new JTextArea(1, 12);
            typeArea.setText(methodSplit[2]);
            panel.add(typeArea);
            
            //Get the name Stirng.
            JTextArea nameArea = new JTextArea(1, 12);
            nameArea.setText(methodSplit[3]);
            panel.add(nameArea);

            //Get the parameters.
            JTextArea paramArea = new JTextArea(1, 15);
            String paramString = "";
            panel.add(paramArea);
            
            for (int i = 4; i < methodSplit.length - 1; i += 2)
            {
                paramString += methodSplit[i];
                paramString += " ";
                paramString += methodSplit[i + 1];

                //Add to the ArrayList for deleting the old method.
                String add = paramString;
                oldParams.add(add);

                if(i + 2 != methodSplit.length)
                    paramString += ",";
            }
            paramArea.setText(paramString);

            int result = JOptionPane.showConfirmDialog(view.getMainWindow(), panel,
                "Edit Method", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                //Get access String.
                access = (String) ((JComboBox)panel.getComponent(0)).getSelectedItem();
            
                //Get type String.
                type = (String) ((JTextArea) panel.getComponent(1)).getText();

                //Get name String.
                name = (String) ((JTextArea) panel.getComponent(2)).getText();

                param = (String) ((JTextArea) panel.getComponent(3)).getText();

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
        controller.deleteMethod(className, methodSplit[2], methodSplit[3], oldParams, methodSplit[methodSplit.length - 1]);
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
