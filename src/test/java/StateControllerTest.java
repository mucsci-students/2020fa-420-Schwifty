
import UML.controllers.StateController;
import UML.model.Store;
import java.util.Stack;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertNotNull;
public class StateControllerTest 
{ 
    /**
     * Test both constructors
     */
    @Test
    public void testStateController()
    {
        Store s = new Store();
        Stack<Store> redoStack = new Stack<Store>();
        Stack<Store> undoStack = new Stack<Store>();

        StateController sc = new StateController(s);
        StateController sc2 = new StateController(s, undoStack, redoStack);

        assertNotNull(sc);
        assertNotNull(sc2);
    }

    @Test
    public void testGetUndoStack()
    {
        Store s = new Store();

        StateController sc = new StateController(s);

        Stack<Store> undoStack = sc.getUndoStack();

        assertNotNull(undoStack);
        assertTrue(undoStack.empty());
    }

    @Test
    public void testSetUndoStack()
    {
        Store s = new Store();
        Store cloneOne = (Store)s.clone();
        StateController sc = new StateController(s);

        Stack<Store> undoStack = new Stack<Store>();
        undoStack.push(cloneOne);
        sc.setUndoStack(undoStack);

        Stack<Store> returnedStack = sc.getUndoStack();
        Store returnedClone = returnedStack.pop();

        assertNotNull(returnedStack);
        assertEquals(undoStack, returnedStack);
        assertEquals(cloneOne, returnedClone);
    }

    @Test
    public void testGetRedoStack()
    {
        Store s = new Store();

        StateController sc = new StateController(s);

        Stack<Store> redoStack = sc.getRedoStack();

        assertNotNull(redoStack);
        assertTrue(redoStack.empty());
    }

    @Test
    public void testSetRedoStack()
    {
        Store s = new Store();
        Store cloneOne = (Store)s.clone();
        StateController sc = new StateController(s);

        Stack<Store> redoStack = new Stack<Store>();
        redoStack.push(cloneOne);
        sc.setRedoStack(redoStack);

        Stack<Store> returnedStack = sc.getRedoStack();
        assertFalse(returnedStack.empty());
        Store returnedClone = returnedStack.pop();

        assertNotNull(returnedStack);
        assertEquals(redoStack, returnedStack);
        assertEquals(cloneOne, returnedClone);
    }

    @Test
    public void testGetCurrentState()
    {
        Store s = new Store();
        StateController sc = new StateController(s);

        Store returnedCurrentState = sc.getCurrentState();

        assertNotNull(returnedCurrentState);
        assertEquals(s, returnedCurrentState);
    }

    @Test
    public void testSetCurrentState()
    {
        Store s = new Store();
        Store newStore = new Store();

        StateController sc = new StateController(s);
        Store returnedCurrentStateFirst = sc.getCurrentState();
        assertNotNull(returnedCurrentStateFirst);
        assertEquals(s, returnedCurrentStateFirst);

        sc.setCurrentState(newStore);

        Store returnedCurrentStateSecond = sc.getCurrentState();
        assertNotNull(returnedCurrentStateSecond);
        assertEquals(newStore, returnedCurrentStateSecond);
    }

    @Test
    public void testAddStateToRedo()
    {
        Store s = new Store();
        Store newStore = new Store();

        StateController sc = new StateController(s);
        sc.addStateToRedo(newStore);
        Stack<Store> redoStack = sc.getRedoStack();
        assertNotNull(redoStack);
        
        Store returned = redoStack.pop();
        assertEquals(newStore, returned);
    }

    @Test
    public void testAddStateToUndo()
    {
        Store s = new Store();
        Store newStore = new Store();

        StateController sc = new StateController(s);
        sc.addStateToUndo(newStore);
        Stack<Store> undoStack = sc.getUndoStack();
        assertNotNull(undoStack);
        
        Store returned = undoStack.pop();
        assertEquals(newStore, returned);
    }

    @Test
    public void testUndo()
    {
        int amtToTest = 10;
        Store s = new Store();
        StateController sc = new StateController(s);

        ArrayList<Store> stores = getListOfStores(amtToTest);
        for(Store store : stores)
        {
            sc.addStateToUndo(store);
        }

        //Test that currentState = s;
        assertEquals(s, sc.getCurrentState());
        
        //The undo stack should not be empty
        assertFalse(sc.getUndoStack().empty());

        for(int i = 0; i < amtToTest; i++)
        {
            Store returnedStore = sc.Undo();
            assertNotNull(returnedStore);
            assertEquals(returnedStore, sc.getCurrentState());
        }

        assertTrue(sc.getUndoStack().empty());
    }

    @Test
    public void testRedo()
    {
        int amtToTest = 10;
        Store s = new Store();
        StateController sc = new StateController(s);

        ArrayList<Store> stores = getListOfStores(amtToTest);

        for(Store store : stores)
        {
            sc.addStateToRedo(store);
        }

        //Test that currentState = s;
        assertEquals(s, sc.getCurrentState());
        
        //The undo stack should not be empty
        assertFalse(sc.getRedoStack().empty());

        for(int i = 0; i < amtToTest; i++)
        {
            Store returnedStore = sc.Redo();
            assertNotNull(returnedStore);
            assertEquals(returnedStore, sc.getCurrentState());
        }

        assertTrue(sc.getUndoStack().empty());
    }

    @Test
    public void testClearUndo()
    {
        int amtToTest = 10;
        Store s = new Store();
        StateController sc = new StateController(s);

        ArrayList<Store> stores = getListOfStores(amtToTest);
        
        for(Store store : stores)
        {
            sc.addStateToUndo(store);
        }
        assertFalse(sc.getUndoStack().empty());
        sc.clearUndo();
        assertTrue(sc.getUndoStack().empty());
    }

    @Test
    public void testClearRedo()
    {
        int amtToTest = 10;
        Store s = new Store();
        StateController sc = new StateController(s);

        ArrayList<Store> stores = getListOfStores(amtToTest);
        
        for(Store store : stores)
        {
            sc.addStateToRedo(store);
        }
        assertFalse(sc.getRedoStack().empty());
        sc.clearRedo();
        assertTrue(sc.getRedoStack().empty());
    }

    private ArrayList<Store> getListOfStores(int amtOfStores)
    {
        ArrayList<Store> stores = new ArrayList<Store>();
        for(int i = 0; i < amtOfStores; i++)
        {
            stores.add(new Store());
        }

        return stores;
    }
}
