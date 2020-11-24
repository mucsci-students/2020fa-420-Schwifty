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
import UML.controllers.StateController;

import java.awt.Dimension;

public class Controller implements IController
{
    // Store the model
    private Store store;
    // Store the view
    private View view;
    //The state controller that handles undo and redo
    private StateController stateController;
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

        //get the initial state of the UML editor.
        stateController = new StateController(this.store);
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

public View getCurrentView()
{
    return this.view;
}
/**
 * Returns the current store.
 */
public Store getStore()
{
    return this.store;
}
//================================================================================================================================================
//Setters
//================================================================================================================================================

public void setStore(Store newStore)
{
    this.store = newStore;
}

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

/**
 * Gets the state controller
 */
public StateController getStateController()
{
    return this.stateController;
}
//================================================================================================================================================
//Class methods
//================================================================================================================================================


    /**
     * Creates a class in the UML diagram.
     */
    @Override
    public void createClass(String name) 
    {
        try
        {
            stateChange();
            boolean temp = store.addClass(name);  
            if (temp)
            {   
                prepGUI();
                rebuild();
            }
            else
            {
                view.showError("Class could not be created");
            }
        }
        catch (Exception e)
        {
            view.showError(e.getMessage());
        }
    }
    
    /**
     * Deletes a class from the store and view.
     */
    @Override
    public void deleteClass(String name) 
    {
        Class aClass = findClass(name);
        if(aClass != null)
        {
            try
            {
                stateChange();
                store.deleteClass(name);  
                
                String oldString = aClass.toString();
                view.deleteClass(oldString);
            }
            catch (Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void renameClass(String oldName, String newName) throws IllegalArgumentException
    {
        Class oldClass = findClass(oldName);
        if(oldClass != null)
        {
            try
            {
                stateChange();    
                boolean temp = store.renameClass(oldName, newName); 
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Class could not be renamed");
                }
            }
            catch(Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void createField(String className, String type, String name, String access) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            try
            {
                stateChange();
                boolean temp = store.addField(className, type, name, access);
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Field could not be created");
                }
            }
            catch(Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void deleteField(String className, String name) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            try 
            {
                stateChange();
                boolean temp = store.deleteField(className, name); 
            
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Field could not be deleted");
                }
            }
            catch(Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void renameField(String className, String oldName, String newName) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            try 
            {
                stateChange();
                boolean temp = store.renameField(className, oldName, newName);   
            
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Field could not be renamed");
                }
            }
            catch (Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void changeFieldType(String className, String fieldName, String newType) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {  
            try
            {
                stateChange();
                boolean temp = store.changeFieldType(className, fieldName, newType);
            
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Field type could not be changed");
                }
            }
            catch(Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void changeFieldAccess(String className, String fieldName, String access) throws IllegalArgumentException
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            try
            {
                stateChange();
                boolean temp = store.changeFieldAccess(className, fieldName, access); 
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Field access could not be changed");
                }
            }
            catch(Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void createMethod(String className, String returnType, String methodName, ArrayList<String> params, String access)
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            try
            {
                stateChange();
                boolean temp = store.addMethod(className, returnType, methodName, params, access);
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Method could not be created");
                }
            }
            catch(Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void deleteMethod(String className, String returnType, String methodName, ArrayList<String> params, String access)
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            try
            {
                stateChange();
                boolean temp = store.deleteMethod(className, returnType, methodName, params, access);
            
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Method could not be deleted");
                }
            }
            catch (Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void renameMethod(String className, String returnType, String methodName, ArrayList<String> params, String access, String newName)
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            try
            {
                stateChange();
                boolean temp = store.renameMethod(className, returnType, methodName, params, access, newName);
             
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Method could not be renamed");
                }
            }
            catch (Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void changeMethodType(String className, String oldType, String methodName, ArrayList<String> params, String access, String newType)
    {
        Class aClass = findClass(className);
        if(aClass != null)
        {
            try
            {
                stateChange();
                boolean temp = store.changeMethodType(className, oldType, methodName, params, access, newType);
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }

                else
                {
                    view.showError("Method type could not be changed");
                }
            }
            catch(Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void changeMethodAccess(String className, String type, String name, ArrayList<String> params, String access, String newAccess)
    {
        Class aClass = findClass(className);
        stateChange();
        if(aClass != null)
        {
            try
            {
                stateChange();
                boolean temp = store.changeMethodAccess(className, type, name, params, access, newAccess);
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Method access could not be changed");
                }
            }
            catch(Exception e)
            {
                view.showError(e.getMessage());
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
    @Override
    public void addParameter(String className, String methodType, String methodName, ArrayList<String> params, String access, String paramType, String paramName)
    {
        //Implemet in sprint 4.
        Class aClass = findClass(className);
        if(aClass != null)
        {
            try
            {
                stateChange();
                boolean temp = store.addParam(className, methodType, methodName, params, access, paramType, paramName);
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Parameter could not be added");
                }
            }
            catch(Exception e)
            {
                view.showError(e.getMessage());
            }
        }   
        else
        {
            view.showError("Class does not exist");
        }
    }

    /**
     * Deletes an parameter from a given method.
     */
    @Override
    public void deleteParameter(String className, String methodType, String methodName, ArrayList<String> params, String access,  String paramType, String paramName)
    {
        //Implemet in sprint 4.
        Class aClass = findClass(className);
        if(aClass != null)
        {
            try 
            {    
                stateChange();
                boolean temp = store.deleteParam(className, methodType, methodName, params, access, paramType, paramName);
                if(temp)
                {
                    prepGUI();
                    rebuild();
                }
                else
                {
                    view.showError("Method access could not be changed");
                }
            }
            catch (Exception e) 
            {
                view.showError(e.getMessage());
            }
        }
        else
        {
            view.showError("Class does not exist");
        }
    }

    
//================================================================================================================================================
//Relationship methods
//================================================================================================================================================

    /**
     * Adds a relationship between two classes.
     */
    @Override
    public void addRelationship(String from, String to, RelationshipType relation)
    {   
        Class fromOld = findClass(from);
        Class toOld = findClass(to);
        try
        {
            stateChange();
            boolean temp = store.addRelationship(from, to, relation);
            //If the relationship could not be deleted, give the user and error and do not change the state.
            if(!temp || fromOld == null || toOld == null)
                view.showError("Relationship could not be created.  Make sure both classes exist or check that there is no existing relationships between those classes.");
            else 
            { 
                prepGUI();
                rebuild();
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
    @Override
    public void deleteRelationship(String from, String to)
    {
        Class fromOld = findClass(from);
        Class toOld = findClass(to);
        try
        {
            stateChange();
            boolean temp = store.deleteRelationship(fromOld.getName(), toOld.getName());
            //If the relationship could not be deleted, give the user and error and do not change the state.
            if(!temp || fromOld == null || toOld == null)
                view.showError("Relationship could not be deleted.  Make sure both classes exist or check that there is an existing relationships between those classes.");
            else
            { 
                prepGUI();
                rebuild();
            }
        } 
        catch(Exception e)
        {
            view.showError(e.getMessage());
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
        stateChange();
        prepGUI();
        //Also clear the class store.
        store.getClassStore().clear();
        //Load the specified file in the store.
        SaveAndLoad sl = new SaveAndLoad(store, view, this);
        File currentFile = sl.load(fileName);
        store.setCurrentLoadedFile(currentFile);
        rebuild();
    }

    /**
     * Returns class if it exists, otherwise it throws an exception.
     */
    private Class findClass(String className)
    {
        Class aClass = store.findClass(className);
        if (aClass == null)
            return null;
        else
            return aClass;
    }

    /**
     * Adds action listeners (used in GUIView for the menu bar).
     */
    public void addListeners()
    {
        view.addListeners(new FileClickController(store, view, this), new ClassClickController(store, view, this), new StateClickController(store, view, this));
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
        //When we change the state, we must add the old state to the undo state and increment the total number of stored states.
        stateController.getRedoStack().clear();
        stateController.addStateToUndo((Store)this.store.clone());
    }

    /**
     * Does a redo.
     */
    public void redo()
    {
        Stack<Store> redoStack = stateController.getRedoStack();
        //If the redo stack is empty, tell the user they cannot perform a redo.
        if(redoStack.isEmpty())
            view.showError("Cannot redo");
        else
        {
            stateController.addStateToUndo((Store)this.store.clone());
            //stateChange();
            this.store = stateController.Redo();
            prepGUI();
            rebuild();
        }
    }

     /**
      * Does an undo.
      */
      public void undo()
      {
        Stack<Store> undoStack = stateController.getUndoStack();
        //If the undo stack is empty, tell the user they cannot perform a undo.
        if(undoStack.isEmpty())
            view.showError("Cannot undo");
        else
        {
            stateController.addStateToRedo((Store)this.store.clone());
            this.store = stateController.Undo();
            prepGUI();
            rebuild();
        }
      }

      private void rebuild()
      {
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

      private void prepGUI()
      {
          //If there are panels on the GUI, get red of them to prep for the new load.
          if(view.getPanels() != null)
          {
              for(Map.Entry<String, JPanel> entry : view.getPanels().entrySet())
              {
                  view.deleteClass(entry.getKey());
              }
          }
      }
}