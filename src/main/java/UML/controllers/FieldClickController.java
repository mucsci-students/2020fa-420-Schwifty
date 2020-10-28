package UML.controllers;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Creates the action listeners for the field and method buttons.
 */
import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import UML.model.Class;
import UML.model.Store;
import UML.views.View;


public class FieldClickController implements ActionListener {
    private Store store;
    private View view;
    private Controller controller;

    public FieldClickController(Store s, View v, Controller c) {
        this.view = v;
        this.store = s;
        this.controller = c;
    }

    public void actionPerformed(ActionEvent e) {
        // Create a drop down list of created classes.
        ArrayList<String> classList = store.getClassList();
        String cmd = e.getActionCommand();
        String[] cmdArr = cmd.split(" ");
        String className = cmdArr[1];

        if (cmdArr[0].equals("CreateField")) {
            String type = handleExceptions("Type: ", "Inavlid field name");
            String name = handleExceptions("Name: ", "Invalid field name");
            
            ArrayList<String> accessTypes = new ArrayList<String>();
            accessTypes.add("public");
            accessTypes.add("private");
            accessTypes.add("protected");

            String access = view.getChoiceFromUser("New access level", "New access level", accessTypes);
            // Add field to the class using received params.
            controller.createField(className, type, name, access);

        } else if (cmdArr[0].equals("DeleteField")) {
            // Get class from storage.
            Class classToDeleteFrom = store.findClass(className);

            // Get the atrributes to be placed in a combo box.
            ArrayList<String> fieldList = store.getFieldList(classToDeleteFrom.getFields());

            // Get field to delete.
            String field = view.getChoiceFromUser("Delete this atrribute", "Delete atrribute", fieldList);
            String[] f = field.split(" ");
            controller.deleteField(className, f[2]);

        } else if (cmdArr[0].equals("RenameField")) {
            // Get class from storage.
            Class classToRenameFrom = store.findClass(className);

            // Get a list of atrributes from the class.
            ArrayList<String> fieldList = store.getFieldList(classToRenameFrom.getFields());

            // Get field to rename.
            String field = view.getChoiceFromUser("Rename this field", "Rename atrribute", fieldList);

            // Open text input for new name.
            String newField = handleExceptions("New field name: ", "Invalid field name");
            // Old field. 
            String[] att = field.split(" ");
            // Rename the field.
            controller.renameField(className, att[2], newField);

            
        } else if (cmdArr[0].equals("ChangeFieldType")) {
            // Get class from storage.
            Class classToChangeTypeFrom = store.findClass(className);

            // Get a list of atrributes from the class.
            ArrayList<String> fieldList = store.getFieldList(classToChangeTypeFrom.getFields());

            // Get field to change it's type.
            String field = view.getChoiceFromUser("Change this field's type", "Change Field Type", fieldList);

            // get the new type from the user and let the store know it can change.
            String newType = handleExceptions("New field type: ", "Invalid field type");
            String[] att = field.split(" ");
            controller.changeFieldType(className, newType, att[1]);

        } else if (cmdArr[0].equals("CreateMethod")) {
            // Get the access level from the user.
            ArrayList<String> accessTypes = new ArrayList<String>();
            accessTypes.add("public");
            accessTypes.add("private");
            accessTypes.add("protected");

            String access = view.getChoiceFromUser("New access level", "New access level", accessTypes);

            // Get Type from user.
            String returnType = handleExceptions("Return Type: ", "Invalid return type");
            // Get name from user.
            String name = handleExceptions("Method Name: ", "Invalid method name");
            // Get number of parameters from user.
            boolean valid = false;
            int paramNum = getNumberFromInput(view.getInputFromUser("Number of Parameters: "));


            while(!valid)
            {
                if(paramNum < 0)
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Specify a nonnegative number of parameters.", "Error", JOptionPane.ERROR_MESSAGE);
                    paramNum = getNumberFromInput(view.getInputFromUser("Number of Parameters: "));
                }
                else
                {
                    valid = true;
                }
            }

            ArrayList<String> params = new ArrayList<String>();

            for (int count = 0; count < paramNum; ++count) {
                String paramType = handleExceptions("Parameter Type: ", "Invalid parameter type");
                String paramName = handleExceptions("Parameter Name: ", "Invalid parameter name");
                params.add(paramType + " " + paramName);
            }
            // Add field to the class using received params.
            controller.createMethod(className, returnType, name, params, access);
        } 
        else if (cmdArr[0].equals("DeleteMethod")) {
            // Get a list of methods to show user in a combo box.
            ArrayList<String> methods = store.getMethodList(store.findClass(className).getMethods());
            // Get their choice
            String methodString = view.getChoiceFromUser("Delete method", "DeleteMethod", methods);
            //Get the needed componets from the method string
            String[] splitStr = methodString.split(" ");
            String access = splitStr[0];
            String returnType = splitStr[1];
            String methodName = splitStr[2];
            
            controller.deleteMethod(className, returnType, methodName, store.getMethodParamString(className, methodString), access);
        } 
        else if (cmdArr[0].equals("RenameMethod")) {
            ArrayList<String> methodList = store.getMethodList(store.findClass(className).getMethods());
            String methodString = view.getChoiceFromUser("Rename this method", "Rename method", methodList);

            String newMethod = handleExceptions("Enter new method name: ", "Invalid method name");
            
            String[] splitStr = methodString.split(" ");
            String access = splitStr[1];
            String returnType = splitStr[2];
            String methodName = splitStr[3];
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

            controller.renameMethod(className, returnType, methodName, store.getMethodParamString(className, methodString), accessString, newMethod);
        }
        else if(cmdArr[0].equals("ChangeFieldAccess"))
        {
            // Get class from storage.
            Class aClass = store.findClass(className);

            // Get a list of atrributes from the class.
            ArrayList<String> fieldList = store.getFieldList(aClass.getFields());

            // Get field to rename.
            String field = view.getChoiceFromUser("Rename this field", "Rename atrribute", fieldList);
            String[] fieldString = field.split(" ");
            String fieldName = fieldString[2];

            ArrayList<String> accessTypes = new ArrayList<String>();
            accessTypes.add("public");
            accessTypes.add("private");
            accessTypes.add("protected");

            String newAccess = view.getChoiceFromUser("New access level", "New access level", accessTypes);
            controller.changeFieldAccess(className, fieldName, newAccess);
        }
        else if(cmdArr[0].equals("ChangeMethodAccess"))
        {
            ArrayList<String> methodList = store.getMethodList(store.findClass(className).getMethods());
            String methodString = view.getChoiceFromUser("Rename method access", "Method access", methodList);

            String[] splitStr = methodString.split(" ");
            String accessChar = splitStr[1];
            String accessString = "";
            if(accessChar.equals("+"))
            {
                accessString = "public";
            }
            else if(accessChar.equals("-"))
            {
                accessString = "private";
            }
            else if(accessChar.equals("*"))
            {
                accessString = "protected";
            }
            String returnType = splitStr[2];
            String methodName = splitStr[3];
            ArrayList<String> accessTypes = new ArrayList<String>();
            accessTypes.add("public");
            accessTypes.add("private");
            accessTypes.add("protected");

            String newAccess = view.getChoiceFromUser("New access level", "New access level", accessTypes);

            controller.changeMethodAccess(className, returnType, methodName, store.getMethodParamString(className, methodString), accessString, newAccess);
        }
        else if(cmdArr[0].equals("ChangeMethodType"))
        {
            ArrayList<String> methodList = store.getMethodList(store.findClass(className).getMethods());
            String methodString = view.getChoiceFromUser("Change this method's type", "Change method type", methodList);

            String newMethod = handleExceptions("Enter new method type: ", "Invalid method type");
            
            String[] splitStr = methodString.split(" ");
            String access = splitStr[1];
            String returnType = splitStr[2];
            String methodName = splitStr[3];
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

            controller.changeMethodType(className, returnType, methodName, store.getMethodParamString(className, methodString), accessString, newMethod);
        }
    }

    private int getNumberFromInput(String input) 
    {
        int paramNum;
        // Ensure we get a number, defaults to zero on bad input.
        try 
        {
            paramNum = Integer.parseInt(input);
        }
        catch (NumberFormatException e) 
        {
            paramNum = 0;
        }
        return paramNum;
    }

    /**
	 * Prevents exceptions given exceptional behavior.
	 */
	private String handleExceptions(String prompt, String error)
    {
        boolean canCreate = false;
        String name = view.getInputFromUser(prompt);
			while(!canCreate)
			{
				if(name.trim().equals("") || name.contains(" "))
				{
					view.showError(error);
					name = view.getInputFromUser(prompt);
					canCreate = !(name.trim().equals("") || name.contains(" "));
				}
				else
					canCreate = true;
            }
        return name;
    }
}
