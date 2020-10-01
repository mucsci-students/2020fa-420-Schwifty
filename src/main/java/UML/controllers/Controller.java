package UML.controllers;

import UML.model.Class;
import UML.model.Store;
import UML.views.*;
import java.util.ArrayList;

public class Controller 
{
    // Store the model
    private Store store;
    // Store the view
    private View view;

    public Controller(Store store, View view) 
    {
        this.store = store;
        this.view = view;
        view.addListeners();
    }

    public void createClass(String name) 
    {
        boolean temp = store.addClass(name);
        if (temp)
            view.createClass(name);
        else
            view.showError("Class could not be created");
    }

    public void deleteClass(String name) 
    {
        boolean temp = store.deleteClass(name);
        if (temp)
            view.deleteClass(name);
        else
            view.showError("Class could not be deleted");
    }

    public void renameClass(String oldName, String newName) throws IllegalArgumentException
    {
        Class oldClass = findClass(oldName);
        String oldClassStr = oldClass.toString();
        boolean temp = store.renameClass(oldName, newName);
        sendToView(temp, "Class", "renamed", newName, oldClassStr);
    }

    public void createField(String className, String type, String name) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.addField(className, type, name);
        sendToView(temp, "Field", "created", className, oldClassStr);
    }

    public void deleteField(String className, String name) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.deleteField(className, name);
        sendToView(temp, "Field", "deleted", className, oldClassStr);
    }

    public void renameField(String className, String oldName, String newName) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.renameField(className, oldName, newName);
        sendToView(temp, "Field", "renamed", className, oldClassStr);
    }

    public void changeFieldType(String className, String fieldName, String newType) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.changeFieldType(className, newType, fieldName);
        sendToView(temp, "Field", "re-typed", className, oldClassStr);  
    }
    
    public void createMethod(String className, String returnType, String methodName, ArrayList<String> params)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.addMethod(className, returnType, methodName, params);
        sendToView(temp, "Method", "added", className, oldClassStr);
    }

    public void deleteMethod(String className, String returnType, String methodName, ArrayList<String> params)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.deleteMethod(className, returnType, methodName, params);
        sendToView(temp, "Method", "deleted", className, oldClassStr);
    }

    public void renameMethod(String className, String returnType, String methodName, ArrayList<String> params, String newName)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.renameMethod(className, returnType, methodName, params, newName);
        sendToView(temp, "Method", "renamed", className, oldClassStr);
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

    public void addListeners()
    {
        view.addListeners(new FileClickController(store, view), new ClassClickController(store, view), new FieldClickController(store, view), new RelationshipClickController(store, view));
    }
}
