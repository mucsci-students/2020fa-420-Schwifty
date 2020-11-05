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
import java.awt.event.ActionListener;

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
            //Save to get the initial state of the UML editor.
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
        if (temp)
        {   
            stateChange(); 
        }
    }
    
    /**
     * Deletes a class from the store and view.
     */
    public void deleteClass(String name) 
    {
        Class aClass = findClass(name);
        if(aClass != null)
        {
            boolean temp = store.deleteClass(name);
            if(temp)
            {
                stateChange();
            }
            else
            {
                view.showError("Class could not be deleted");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
    }

    /**
     * Renames a class in the UML diagram.
     */
    public void renameClass(String oldName, String newName) throws IllegalArgumentException
    {
        Class oldClass = findClass(oldName);
        if(oldClass != null)
        {
            boolean temp = store.renameClass(oldName, newName);
            if(temp)
            {
                stateChange();
            }
            else
            {
                view.showError("Class could not be renamed");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
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
        if(aClass != null)
        {
            boolean temp = store.addField(className, type, name, access);
            if(temp)
            {
                stateChange();
            }
            else
            {
                view.showError("Field could not be created");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
    }

    /**
     * Deletes a field from a given class.
     */
    public void deleteField(String className, String name) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            boolean temp = store.deleteField(className, name);
            if(temp)
            {
                stateChange();
            }
            else
            {
                view.showError("Field could not be deleted");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
    }

    /**
     * Renames a field in a given class.
     */
    public void renameField(String className, String oldName, String newName) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            boolean temp = store.renameField(className, oldName, newName);
            if(temp)
            {
                stateChange();
            }
            else
            {
                view.showError("Field could not be renamed");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
    }

    /**
     * Chnages the type of a field in a given class.
     */
    public void changeFieldType(String className, String fieldName, String newType) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            boolean temp = store.changeFieldType(className, fieldName, newType);
            if(temp)
            {
                stateChange();
            }
            else
            {
                view.showError("Field type could not be changed");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
    }

    /**
     * Changes access type of a field.
     */
    public void changeFieldAccess(String className, String fieldName, String access) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            boolean temp = store.changeFieldAccess(className, fieldName, access);
            if(temp)
            {
                stateChange();
            }
            else
            {
                view.showError("Field access could not be changed");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
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
        if(aClass != null)
        {
            boolean temp = store.addMethod(className, returnType, methodName, params, access);
            if(temp)
            {
                stateChange();
            }
            else
            {
                view.showError("Method could not be created");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
    }

    /**
     * Deletes a method from a given class.
     */
    public void deleteMethod(String className, String returnType, String methodName, ArrayList<String> params, String access)
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            boolean temp = store.deleteMethod(className, returnType, methodName, params, access);
            if(temp)
            {
                stateChange();
            }
            else
            {
                view.showError("Method could not be deleted");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
    }

    /**
     * Renames a method in a given class.
     */
    public void renameMethod(String className, String returnType, String methodName, ArrayList<String> params, String access, String newName)
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            boolean temp = store.renameMethod(className, returnType, methodName, params, access, newName);
            if(temp)
            {
                stateChange(); 
            }
            else
            {
                view.showError("Method could not be renamed");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
        
    }

    /**
     * Changes the type of a given method.
     */
    public void changeMethodType(String className, String oldType, String methodName, ArrayList<String> params, String access, String newType)
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            boolean temp = store.changeMethodType(className, oldType, methodName, params, access, newType);
            if(temp)
            {
                stateChange();
            }

            else
            {
                view.showError("Method type could not be changed");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
    }

    /**
     * Changes the access type of a given method.
     */
    public void changeMethodAccess(String className, String type, String name, ArrayList<String> params, String access, String newAccess)
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            boolean temp = store.changeMethodAccess(className, type, name, params, access, newAccess);
            if(temp)
            {
                stateChange(); 
            }
            else
            {
                view.showError("Method access could not be changed");
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
    }


//================================================================================================================================================
//Parameter methods
//================================================================================================================================================


/**
 * Adds an parameter to a given method.
 */
public void addParameter(String className, String methodType, String methodName, ArrayList<String> params, String access, String paramType, String paramName)
{
    //Implemet in sprint 4.
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
    //Implemet in sprint 4.
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
        try{
            boolean temp = store.addRelationship(from, to, relation);
            //If the relationship could not be deleted, give the user and error and do not change the state.
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
        boolean temp = store.deleteRelationship(fromOld.getName(), toOld.getName());

        //If the relationship could not be deleted, give the user and error and do not change the state.
        if(!temp || fromOld == null || toOld == null)
            view.showError("Relationship could not be deleted.  Make sure both classes exist or check that there is an existing relationships between those classes.");
        else
        {
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
     * Load selected file.  Used for undo and redo.
     */
    public void load(String fileName) throws IOException, ParseException
    {
        //If there are panels on the GUI, get red of them to prep for the new load.
        if(view.getPanels() != null)
        {
            for(Map.Entry<String, JPanel> entry : view.getPanels().entrySet())
            {
                view.deleteClass(entry.getKey());
            }
        }
        //Also clear the class store.
        store.getClassStore().clear();
        
        //Load the specified file in the store.
        SaveAndLoad sl = new SaveAndLoad(store, view, this);
        File currentFile = sl.load(fileName);
        store.setCurrentLoadedFile(currentFile);

        ActionListener[] listeners = new ActionListener[7];

        //Create Listeners
        listeners[0] = new CreateFieldController(store, view, this);
        listeners[1] = new EditFieldController(store, view, this);
        listeners[2]= new CreateMethodController(store, view, this);
        listeners[3] = new EditMethodController(store, view, this);
        listeners[4] = new CreateRelationshipController(store, view, this);
        listeners[5] = new DeleteRelationshipController(store,view, this);
        listeners[6] = new EditClassController(store, view, this);

        //Add the approprate panels and listeners to the view.
        for(Class c : store.getClassStore())
        {
            view.createClass(c.toString(), (int)c.getLocation().getWidth(), (int)c.getLocation().getHeight());
            view.addListener(new MouseClickAndDragController(store, view, this), c.toString());
            for(int count = 0; count < 7; count++)
            {
                view.addPanelListener(listeners[count], c.toString());
            }
        }
        //Add relationships to the view.
        for(Class c : store.getClassStore())
        {
            for(Map.Entry<String, RelationshipType> entry : c.getRelationshipsToOther().entrySet())
            {
                view.addRelationship(c.toString(), store.findClass(entry.getKey()).toString(), entry.getValue().toString());
            }
        }
    }

    /**
     * Loads a selected file, but is not meant to be used for undo and redo.
     */
    public void loadFromMenu(String fileName) throws IOException, ParseException
    {
        load(fileName);
        //This is the load that is used for normal loading so:
        //Increment the count, push the old file onto the undo stack.
        count++;
        undo.push(currentState);

        //Create a new file to save and set it as the currrent file.
        String toSave = "" + count;
        currentState = new File(toSave);
        save(toSave);
        //Load the file like a normal file now.
        load(toSave + ".json");
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
     * Adds action listeners (used in GUIView for the menu bar).
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
            //When we change the state, we must add the old state to the undo state and increment the total number of stored states.
            count++;
            undo.push(currentState);
            String toSave = "" + count;
            currentState = new File(toSave);
            //Save the new state and load it.
            save(toSave);
            load(toSave + ".json");
        }
        catch(Exception e)
        {
            //This should never occur.
            view.showError("ERROR");
        }
    }

    /**
     * Does a redo.
     */
    public void redo()
    {
        //If the redo stack is empty, tell the user they cannot perform a redo.
        if(redo.isEmpty())
            view.showError("Cannot redo");
        else
        {
            try
            {
                //Add the current file to the undo stack so that we can revisit it.
                undo.push(currentState);
                save(currentState.getName());
                //Set the current state to be the first state on the redo stack.
                currentState = redo.pop();
                load(currentState.getName() + ".json");
            }
            catch(Exception e)
            {
                //This should never occur.
                view.showError("ERROR");
            }
        }
    }


     /**
      * Does an undo.
      */
      public void undo()
      {
          //If the undo stack is empty, tell the user they cannot perform a undo.
        if(undo.isEmpty())
            view.showError("Cannot undo");
        else
        {
            try
            {
                //Add the current file to the redo stack so that we can revisit it.
                redo.push(currentState);
                save(currentState.getName());
                //Set the current state to be the first state on the undo stack.
                currentState = undo.pop();
                load(currentState.getName() + ".json");
            }
            catch (Exception e)
            {
                //This should never occur.
                view.showError("ERROR");
            }
        }
      }
}