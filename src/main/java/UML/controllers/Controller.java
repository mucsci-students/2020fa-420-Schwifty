package UML.controllers;

import UML.model.Class;
import UML.model.Store;
import UML.views.*;
import java.util.ArrayList;
import java.awt.event.*;

public class Controller 
{
    // Store the model
    private Store store;
    // Store the view
    private View view;

    /**
     * Contructs a controller object.  Assigns action listeners to the correct buttons.
     */
    public Controller(Store store, View view) 
    {
        this.store = store;
        this.view = view;
        addListeners();
    }

    /**
     * Creates a class in the UML diagram.
     */
    public void createClass(String name) 
    {
        boolean temp = store.addClass(name);
        if (temp)
            view.createClass(name);
        else
            view.showError("Class could not be created");
    }

    /**
     * Deletes a class from the UML diagram.
     */
    public void deleteClass(String name) 
    {
        boolean temp = store.deleteClass(name);
        if (temp)
            view.deleteClass(name);
        else
            view.showError("Class could not be deleted");
    }

    /**
     * Renames a class in the UML diagram.
     */
    public void renameClass(String oldName, String newName) throws IllegalArgumentException
    {
        Class oldClass = findClass(oldName);
        String oldClassStr = oldClass.toString();
        boolean temp = store.renameClass(oldName, newName);
        sendToView(temp, "Class", "renamed", newName, oldClassStr);
    }

    /**
     * Creates a field for a given class.
     */
    public void createField(String className, String type, String name) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.addField(className, type, name);
        sendToView(temp, "Field", "created", className, oldClassStr);
    }

    /**
     * Deletes a field from a given class.
     */
    public void deleteField(String className, String name) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.deleteField(className, name);
        sendToView(temp, "Field", "deleted", className, oldClassStr);
    }

    /**
     * Renames a field in a given class.
     */
    public void renameField(String className, String oldName, String newName) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.renameField(className, oldName, newName);
        sendToView(temp, "Field", "renamed", className, oldClassStr);
    }

    /**
     * Chnages the type of a field in a given class.
     */
    public void changeFieldType(String className, String fieldName, String newType) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.changeFieldType(className, newType, fieldName);
        sendToView(temp, "Field", "re-typed", className, oldClassStr);  
    }
    
    /**
     * Creates a method in a given class.
     */
    public void createMethod(String className, String returnType, String methodName, ArrayList<String> params)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.addMethod(className, returnType, methodName, params);
        sendToView(temp, "Method", "added", className, oldClassStr);
    }

    /**
     * Deletes a method from a given class.
     */
    public void deleteMethod(String className, String returnType, String methodName, ArrayList<String> params)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.deleteMethod(className, returnType, methodName, params);
        sendToView(temp, "Method", "deleted", className, oldClassStr);
    }

    /**
     * Renames a method in a given class.
     */
    public void renameMethod(String className, String returnType, String methodName, ArrayList<String> params, String newName)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.renameMethod(className, returnType, methodName, params, newName);
        sendToView(temp, "Method", "renamed", className, oldClassStr);
    }

    public void addRelationship()
    {

    }

    /**
     * Saves a UML diagram file.
     */
    public void save(String fileName)
    {
        File currentFile = SaveAndLoad.save(fileName, store.getClassStore());
        store.setCurrentLoadedFile(currentFile);
    }

    /**
     * Loads a UML diagram File.
     */
    public void load(String fileName)
    {
        File currentFile = SaveAndLoad.load(fileName, store.getClassStore());
        store.setCurrentLoadedFile(currentFile);
        ArrayList<String> toStrings = new ArrayList<String>();
        for(Class c : store.getClassStore())
        {
            toStrings.add(c.toString());
        }
        view.display(toStrings);
    }

    /**
     * Sends the correct information back to the view.  Can call for updating class or showing error.
     */
    private void sendToView(boolean canSend, String objectType, String action, String className, String oldClassStr) 
    {
        if (canSend) 
        {
            String newClassStr = store.findClass(className).toString();
            view.updateClass(oldClassStr, newClassStr);
        } 
        else
        {
            String error = objectType + " could not be " + action;
            view.showError(error);
        }
    }

    /**
     * Returns class if it exists, otherwise it throws an exception.
     */
    private Class findClass(String className)
    {
        Class aClass = store.findClass(className);
        if (aClass == null)
            throw new IllegalArgumentException("Class does not exist");
        return aClass;
    }
}