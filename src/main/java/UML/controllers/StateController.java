package UML.controllers;
/*
    Authors: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 11/06/2020
    Purpose: Manages the state to allow the user to redo/undo
 */
import java.util.Stack;
import UML.model.Class;
import UML.model.Store;

 public class StateController
 {
    // The stack of all states to undo to.
    private Stack<Store> undoStack;
    // The stack of all states to redo to.
    private Stack<Store> redoStack;  
    // The file representing the current state.
    private Store currentState;

    /**
     * Default constructor
     */
    public StateController(Store s)
    {
        this.currentState = s;
        undoStack = new Stack<Store>();
        redoStack = new Stack<Store>();
    }
    /**
     * 
     */
    public StateController(Store s, Stack<Store> undo, Stack<Store> redo)
    {
        currentState = s;
        undoStack = undo;
        redoStack = redo;
    }

    //Getters
    public Stack<Store> getUndoStack() {
        return undoStack;
    }

    public Stack<Store> getRedoStack() {
        return redoStack;
    }

    public Store getCurrentState() {
        return currentState;
    }

    //Setters
    public void setRedoStack(Stack<Store> redoStack) {
        this.redoStack = redoStack;
    }
    
    public void setCurrentState(Store currentState) {
        this.currentState = currentState;
    }

    public void setUndoStack(Stack<Store> undoStack) {
        this.undoStack = undoStack;
    }

    public void addStateToRedo(Store state)
    {
        redoStack.push(state);
    }

    public void addStateToUndo(Store state)
    {
        undoStack.push(state);
    }

    /**
     * Pops the undo stack and sets the current state. Returns poped state.
     */
    public Store Undo()
    {
        Store newState = undoStack.pop();
        currentState = newState;
        return newState;
    }

    /**
     * Pops the redo stack and sets the current state. Returns poped state.
     */
    public Store Redo()
    {
        Store newState = redoStack.pop();
        currentState = newState;
        return newState;
    }

    /**
     * Clears the undo stack. 
     */
    public void clearUndo()
    {
        stackClear(redoStack);
    }

    /**
     * Clears the redo stack. 
     */
    public void clearRedo()
    {
        stackClear(redoStack);
    }

    /**
     * Clears the passed in stack
     */
    private void stackClear(Stack<Store> stack)
    {
        while(!stack.empty())
        {
            stack.pop();
        }
    }
 }