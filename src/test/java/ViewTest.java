import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Before;
import org.junit.After;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.MockitoAnnotations;
import UML.views.*;
import UML.controllers.*;
import UML.model.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ViewTest 
{

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() 
    {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() 
    {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    @Spy
    CommandlineView view = new CommandlineView();


    @Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateClass()
    {
        view.createClass("Test", 0, 0);
        assertEquals("New class:\n" + "Test", outContent.toString().trim());
        view.createClass("Te st", 0, 0);
        verify(view).createClass("Test", 0, 0);
        verify(view).createClass("Te st", 0, 0);
        
        reset(view);
    }

    @Test
    public void testUpdateClass()
    {
        view.createClass("Test", 0, 0);
        outContent.reset();
        view.updateClass("Test", "NotATest");
        assertEquals("Updated class:\n" + "NotATest", outContent.toString().trim());
        verify(view).updateClass("Test", "NotATest");
        reset(view);
    }

    @Test
    public void testDeleteClass()
    {
        view.createClass("Test", 0, 0);
        outContent.reset();
        view.deleteClass("Test");
        assertEquals("Class deleted", outContent.toString().trim());
        verify(view).deleteClass("Test");
        reset(view);
    }

    @Test
    public void testAddRelationship()
    {
        view.createClass("Test", 0, 0);
        view.createClass("Test2", 0, 0);

        view.addRelationship("test", "test2", "REALIZATION");

        verify(view).addRelationship("test", "test2", "REALIZATION");
    }

    @Test
    public void testDeleteRelationship()
    {
        view.createClass("Test", 0, 0);
        view.createClass("Test2", 0, 0);
        view.addRelationship("test", "test2", "REALIZATION");

        view.deleteRelationship("Test", "Test2");

        verify(view).deleteRelationship("Test", "Test2");
    }
}
