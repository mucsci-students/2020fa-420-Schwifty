
import UML.controllers.StateController;
import UML.model.Store;
import java.util.Stack;

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

    }

    @Test
    public void testGetCurrentState()
    {

    }

    @Test
    public void testSetCurrentState()
    {

    }

    @Test
    public void testAddStateToRedo()
    {

    }

    @Test
    public void testAddStateToUndo()
    {

    }

    @Test
    public void testUndo()
    {

    }

    @Test
    public void testRedo()
    {

    }

    @Test
    public void testClearUndo()
    {

    }

    @Test
    public void testClearRedo()
    {

    }
}
