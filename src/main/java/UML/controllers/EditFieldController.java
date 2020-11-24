package UML.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import java.util.ArrayList;

import UML.model.Store;
import UML.views.*;

public class EditFieldController implements ActionListener
{
    private Store store;
	private View view;
	private Controller controller;

    /**
     * Constructor for EditFieldController.
     */
    public EditFieldController(Store s, View v, Controller c) 
    {
		this.view = v;
		this.store = s;
		this.controller = c;
	}

    /**
     * Creates a window for the user to edit a field.
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String cmd = e.getActionCommand();
        String[] cmdArr = cmd.split(" ");
        String className = cmdArr[1];

        String access = "";
        String type = "";
        String name = "";

        //Get class from storage.
        UML.model.Class theClass = store.findClass(className);

        //Get the atrributes to be placed in a combo box.
        ArrayList<String> fieldList = store.getFieldList(theClass.getFields());

        //Get field to delete.
        //Has the form of (access char) (type) (name).
       
        String field = view.getChoiceFromUser("Edit this class", "Edit class", fieldList);

        //Handle cancelling.
        if(field == null)
            return;

        String[] fieldSplit = field.split(" ");
        //Get selected access
        String accessString = getStringVersion(fieldSplit[0]);
        
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
            accessBox.setSelectedItem(accessString);
            panel.add(accessBox);
            
            JTextArea typeArea = new JTextArea(1, 12);
            typeArea.setText(fieldSplit[1]);
            panel.add(typeArea);

            JTextArea nameArea = new JTextArea(1, 12);
            nameArea.setText(fieldSplit[2]);
            panel.add(nameArea);
            
            Object[] options = { "OK", "Delete" };

            int result = JOptionPane.showOptionDialog(null, panel, "Edit Field",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
            
    
            if (result == 0) 
            {
                //Get access String.
                access = (String) ((JComboBox)panel.getComponent(0)).getSelectedItem();

                //Get type String.
                type = (String) ((JTextArea) panel.getComponent(1)).getText();

                //Get name String.
                name = (String) ((JTextArea) panel.getComponent(2)).getText();

            }
            //Delete field
            else if (result == 1)
            {
                controller.deleteField(className, fieldSplit[2]);
                return;
            }
            //Handle canceling out.
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

        //Delete the old field and create the new corret one.
        controller.deleteField(className, fieldSplit[2]);
        controller.createField(className, type, name, access);
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
