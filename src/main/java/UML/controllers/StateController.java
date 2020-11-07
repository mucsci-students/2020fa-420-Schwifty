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
        Store s = undoStack.pop();
        currentState = s;
        return s;
    }

    /**
     * Pops the redo stack and sets the current state. Returns poped state.
     */
    public Store Redo()
    {
        Store s = redoStack.pop();
        currentState = s;
        return s;
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