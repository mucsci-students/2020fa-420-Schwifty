package UML.controllers;

import UML.model.RelationshipType;
import UML.model.Class;
import UML.model.Store;
import UML.views.*;
import java.util.ArrayList;
import java.io.File;

import java.io.IOException;
import org.json.simple.parser.ParseException;


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
        //addListeners();
    }


//================================================================================================================================================
//Class methods
//================================================================================================================================================


//================================================================================================================================================
//Class methods
//================================================================================================================================================


    /**
     * Creates a class in the UML diagram.
     */
    public void createClass(String name) 
    {
        boolean temp = store.addClass(name);
        Class aClass = findClass(name);
        if (temp)
        {   
            String classStr = aClass.toString();
            view.createClass(classStr);
        }
        else  
        view.showError("Class could not be created");     
    }
    
    
    public void deleteClass(String name) 
    {
    
        Class aClass = findClass(name);
        boolean temp = store.deleteClass(name);
        if (temp)
        {
            String classStr = aClass.toString();
            view.deleteClass(classStr);
        }
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

    
//================================================================================================================================================
//Field methods
//================================================================================================================================================


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
    

//================================================================================================================================================
//Method methods
//================================================================================================================================================


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

    /**
     * Changes the type of a given method.
     */
    public void changeMethodType(String className, String oldType, String methodName, ArrayList<String> params, String newType)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.changeMethodType(className, oldType, methodName, params, newType);
        sendToView(temp, "Method", "re-typed", className, oldClassStr);
    }


 //================================================================================================================================================
//Parameter methods
//================================================================================================================================================


/**
 * Adds an parameter to a given method.
 */
public void addParameter(String className, String methodType, String methodName, ArrayList<String> params, String paramType, String paramName)
{
    Class aClass = findClass(className);
    String oldClassStr = aClass.toString();
    boolean temp = store.addParam(className, methodType, methodName, params, paramType, paramName);
    sendToView(temp, "Parameter", "added", className, oldClassStr);
}


/**
 * Deletes an parameter from a given method.
 */
public void deleteParameter(String className, String methodType, String methodName, ArrayList<String> params, String paramType, String paramName)
{
    Class aClass = findClass(className);
    String oldClassStr = aClass.toString();
    boolean temp = store.deleteParam(className, methodType, methodName, params, paramType, paramName);
    sendToView(temp, "Parameter", "deleted", className, oldClassStr);
}

    
//================================================================================================================================================
//Relationship methods
//================================================================================================================================================


    public void addRelationship(String from, String to, RelationshipType relation)
    {   
        Class fromOld = findClass(from);
        Class toOld = findClass(to);
        //Get toStrings from each string.
        String fromOldStr = fromOld.toString();
        String toOldStr = toOld.toString();
        
        boolean temp = store.addRelationship(from, to, relation);
        if(!temp || fromOld == null || toOld == null)
            view.showError("Relationship could not be created.  Make sure both classes exist or check that there is no existing relationships between those classes.");
        else 
            sendToView(temp, "Relationship", "added", from, fromOldStr);
            sendToView(temp, "Relationship", "added", to, toOldStr);
    }

    public void deleteRelationship(String from, String to)
    {
        Class fromOld = findClass(from);
        Class toOld = findClass(to);
        //Get toStrings from each string.
        String fromOldStr = fromOld.toString();
        String toOldStr = toOld.toString();

        if(fromOld == null || toOld == null)
            view.showError("Relationship could not be deleted.  Make sure both classes exist or check that there is an existing relationships between those classes.");
        else
        {
            boolean temp = store.deleteRelationship(fromOld.getName(), toOld.getName()); 
            sendToView(temp, "Relationship", "deleted", from, fromOldStr);
            sendToView(temp, "Relationship", "deleted", to, toOldStr);
        }
    }

    /**
     * Saves a UML diagram file.
     
        tr*/
    public void save(String fileName) throws IOException
    {
        SaveAndLoad sl = new SaveAndLoad(store, view, this);
        
        File currentFile = sl.save(fileName);
        store.setCurrentLoadedFile(currentFile);
    }

    /**
     * Load selected file.
     */
    public void load(String fileName) throws IOException, ParseException
    {
        SaveAndLoad sl = new SaveAndLoad(store, view, this);
        File currentFile = sl.load(fileName);
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

    /**
     * Adds action listeners (used in GUIView).
     */
    public void addListeners()
    {
        view.addListeners(new FileClickController(store, view, this), new ClassClickController(store, view, this), new FieldClickController(store, view, this), new RelationshipClickController(store, view, this));
    }

}