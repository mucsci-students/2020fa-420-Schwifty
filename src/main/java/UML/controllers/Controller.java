package UML.controllers;

/*
    Author: Chris, Tyler, Drew, Dominic, Cory. 
    Date: 09/24/2020
    Purpose: Main controller for the UML application. 
 */
import UML.model.RelationshipType;
import UML.model.Class;
import UML.model.Store;
import UML.views.*;
import java.util.ArrayList;
import java.io.File;
import java.util.Map;
import java.util.Stack;
import javax.swing.JPanel;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import UML.controllers.MouseClickAndDragController;

import java.awt.Dimension;

public class Controller 
{
    // Store the model
    private Store store;
    // Store the view
    private View view;
    // The stack of all states to undo to.
    private Stack<File> undo;
    // The stack of all states to redo to.
    private Stack<File> redo;  
    // The file representing the current state.
    private File currentState;
    // The state change count for unique file names.
    private int count;
    // True if a GUI exists already, false otherwise.
    private boolean GUIExists;

    /**
     * Contructs a controller object.  Assigns action listeners to the correct buttons.
     */
    public Controller(Store store, View view) 
    {
        this.store = store;
        this.view = view;
        GUIExists = false;
        count = 0;
        String current = "" + count;
        currentState = new File(current);
        try
        {
            save(currentState.getName());
        }
        catch(Exception e)
        {
            view.showError("ERROR");
        }
        undo = new Stack<File>();
        redo = new Stack<File>();
    }


//================================================================================================================================================
//Getters
//================================================================================================================================================


/**
 * Returns the value of GUIExists.
 */
public boolean getGUIExists()
{
    return GUIExists;
}


//================================================================================================================================================
//Setters
//================================================================================================================================================


/**
 * Sets the value of GUIExists to true.
 */
public void setGUIExists()
{
    GUIExists = true;
}

/**
 * Sets GUI to be invisible.
 */
public void setGUIInvisible()
{
    view.setGUIInvisible();
}

/**
 * Sets GUI to be invisible.
 */
public void setGUIVisible()
{
    view.setGUIVisible();
}

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
            //view.createClass(classStr, 0, 0);
            stateChange(); 
        }
    }
    
    /**
     * Deletes a class from the store and view.
     */
    public void deleteClass(String name) 
    {
        Class aClass = findClass(name);
        String classStr = aClass.toString();
        boolean temp = store.deleteClass(name);
        stateChange(); 
        /**
        if (temp)
        {
            view.deleteClass(classStr);
            int counter = 0;
            for(Class c : store.getClassStore())
            {
                view.updateClass(oldClassStrings.get(counter), c.toString());
                counter++;
            }
        }
        else
            view.showError("Class could not be deleted");
        */
    }

    /**
     * Renames a class in the UML diagram.
     */
    public void renameClass(String oldName, String newName) throws IllegalArgumentException
    {
        Class oldClass = findClass(oldName);
        String oldClassStr = oldClass.toString();
        boolean temp = store.renameClass(oldName, newName);
        stateChange(); 
    }

    
//================================================================================================================================================
//Field methods
//================================================================================================================================================


    /**
     * Creates a field for a given class.
     */
    public void createField(String className, String type, String name, String access) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.addField(className, type, name, access);
        stateChange();
        //sendToView(temp, "Field", "created", className, oldClassStr);
    }

    /**
     * Deletes a field from a given class.
     */
    public void deleteField(String className, String name) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.deleteField(className, name);
        stateChange();
    }

    /**
     * Renames a field in a given class.
     */
    public void renameField(String className, String oldName, String newName) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.renameField(className, oldName, newName);
        stateChange();
    }

    /**
     * Chnages the type of a field in a given class.
     */
    public void changeFieldType(String className, String fieldName, String newType) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.changeFieldType(className, newType, fieldName);
        stateChange();
    }

    /**
     * Changes access type of a field.
     */
    public void changeFieldAccess(String className, String fieldName, String access) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.changeFieldAccess(className, fieldName, access);
        stateChange(); 
    }

//================================================================================================================================================
//Method methods
//================================================================================================================================================


    /**
     * Creates a method in a given class.
     */
    public void createMethod(String className, String returnType, String methodName, ArrayList<String> params, String access)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.addMethod(className, returnType, methodName, params, access);
        stateChange(); 
    }

    /**
     * Deletes a method from a given class.
     */
    public void deleteMethod(String className, String returnType, String methodName, ArrayList<String> params, String access)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.deleteMethod(className, returnType, methodName, params, access);
        stateChange(); 
    }

    /**
     * Renames a method in a given class.
     */
    public void renameMethod(String className, String returnType, String methodName, ArrayList<String> params, String access, String newName)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.renameMethod(className, returnType, methodName, params, access, newName);
        stateChange(); 
    }

    /**
     * Changes the type of a given method.
     */
    public void changeMethodType(String className, String oldType, String methodName, ArrayList<String> params, String access, String newType)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.changeMethodType(className, oldType, methodName, params, access, newType);
        stateChange(); 
    }

    /**
     * Changes the access type of a given method.
     */
    public void changeMethodAccess(String className, String type, String name, ArrayList<String> params, String access, String newAccess)
    {
        Class aClass = findClass(className);
        String oldClassStr = aClass.toString();
        boolean temp = store.changeMethodAccess(className, type, name, params, access, newAccess);
        stateChange(); 
    }


//================================================================================================================================================
//Parameter methods
//================================================================================================================================================


/**
 * Adds an parameter to a given method.
 */
public void addParameter(String className, String methodType, String methodName, ArrayList<String> params, String access, String paramType, String paramName)
{
    Class aClass = findClass(className);
    String oldClassStr = aClass.toString();
    boolean temp = store.addParam(className, methodType, methodName, params, access, paramType, paramName);
    stateChange(); 
}


/**
 * Deletes an parameter from a given method.
 */
public void deleteParameter(String className, String methodType, String methodName, ArrayList<String> params, String access,  String paramType, String paramName)
{
    Class aClass = findClass(className);
    String oldClassStr = aClass.toString();
    boolean temp = store.deleteParam(className, methodType, methodName, params, access, paramType, paramName);
    stateChange(); 
}

    
//================================================================================================================================================
//Relationship methods
//================================================================================================================================================

    /**
     * Adds a relationship between two classes.
     */
    public void addRelationship(String from, String to, RelationshipType relation)
    {   
        Class fromOld = findClass(from);
        Class toOld = findClass(to);
        //Get toStrings from each string.
        String fromOldStr = fromOld.toString();
        String toOldStr = toOld.toString();
        try{
            boolean temp = store.addRelationship(from, to, relation);
            if(!temp || fromOld == null || toOld == null)
                view.showError("Relationship could not be created.  Make sure both classes exist or check that there is no existing relationships between those classes.");
            else 
            {
                stateChange(); 
                
            }
        }
        catch(IllegalArgumentException e)
        {
            view.showError(e.getMessage());
        }
    }

    /**
     * Deletes a relationship between two classes.
     */
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
            //sendToView(temp, "Relationship", "deleted", from, fromOldStr);
            //sendToView(temp, "Relationship", "deleted", to, toOldStr);
            stateChange(); 
        }
    }

    /**
     * Saves a UML diagram file.
     */
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
        if(view.getPanels() != null)
        {
            for(Map.Entry<String, JPanel> entry : view.getPanels().entrySet())
            {
                view.deleteClass(entry.getKey());
            }
        }
        store.getClassStore().clear();
        
        SaveAndLoad sl = new SaveAndLoad(store, view, this);
        File currentFile = sl.load(fileName);
        store.setCurrentLoadedFile(currentFile);
        
        for(Class c : store.getClassStore())
        {
            view.createClass(c.toString(), (int)c.getLocation().getWidth(), (int)c.getLocation().getHeight());
            view.addListener(new MouseClickAndDragController(store, view, this), c.toString());
            view.addPanelListener(new FieldClickController(store, view, this), c.toString());
        }
        for(Class c : store.getClassStore())
        {
            for(Map.Entry<String, RelationshipType> entry : c.getRelationshipsToOther().entrySet())
            {
                view.addRelationship(c.toString(), store.findClass(entry.getKey()).toString(), entry.getValue().toString());
            }
        }
    }

    public void loadFromMenu(String fileName) throws IOException, ParseException
    {
        for(Map.Entry<String, JPanel> entry : view.getPanels().entrySet())
        {
            view.deleteClass(entry.getKey());
        }
        store.getClassStore().clear();
        
        SaveAndLoad sl = new SaveAndLoad(store, view, this);
        File currentFile = sl.load(fileName);
        store.setCurrentLoadedFile(currentFile);
        for(Class c : store.getClassStore())
        {
            view.createClass(c.toString(), (int)c.getLocation().getWidth(), (int)c.getLocation().getHeight());
            view.addListener(new MouseClickAndDragController(store, view, this), c.toString());
            view.addPanelListener(new FieldClickController(store, view, this), c.toString());
        }
        for(Class c : store.getClassStore())
        {
            for(Map.Entry<String, RelationshipType> entry : c.getRelationshipsToOther().entrySet())
            {
                view.addRelationship(c.toString(), store.findClass(entry.getKey()).toString(), entry.getValue().toString());
            }
        }
        count++;
        undo.push(currentState);
        String toSave = "" + count;
        currentState = new File(toSave);
        save(toSave);
        load(toSave + ".json");
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
        view.addListeners(new FileClickController(store, view, this), new ClassClickController(store, view, this), new StateClickController(store, view, this), new RelationshipClickController(store, view, this));
    }

    /**
     * Adds listener for interface choice.
     */
    public void addListener()
    {
        view.addListener(new InterfaceChoiceClickController(store, view, this));
    }


    /**
     * Changes the state of the model and view
     */
    private void stateChange()
    {
        try
        {
            count++;
            undo.push(currentState);
            String toSave = "" + count;
            currentState = new File(toSave);
            save(toSave);
            load(toSave + ".json");
        }
        catch(Exception e)
        {
            view.showError("ERROR");
        }
    }

    /**
     * Does a redo.
     */
    public void redo()
    {
        if(redo.isEmpty())
            view.showError("Cannot redo");
        else
        {
            try
            {
                undo.push(currentState);
                save(currentState.getName());
                currentState = redo.pop();
                load(currentState.getName() + ".json");
            }
            catch(Exception e)
            {
                view.showError("ERROR");
            }
        }
    }


     /**
      * Does an undo.
      */
      public void undo()
      {
        if(undo.isEmpty())
            view.showError("Cannot undo");
        else
        {
            try
            {
                redo.push(currentState);
                save(currentState.getName());
                currentState = undo.pop();
                load(currentState.getName() + ".json");
            }
            catch (Exception e)
            {
                view.showError("ERROR");
            }
        }
      }
}