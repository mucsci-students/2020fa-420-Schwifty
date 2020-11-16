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
import java.util.ArrayList;
import java.awt.event.ActionListener;

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

    @Mock
    Store store;
    @Mock
    Controller controller;

    @Spy
    CommandlineView view = new CommandlineView();

    @Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    }

    
    @Test
    public void testUpdateClass()
    {
        view.createClass("Test", 0, 0);
        outContent.reset();
        view.updateClass("Test", "NotATest");
        assertEquals("Updated class:\n" + "NotATest", outContent.toString().trim());
        verify(view).createClass("Test", 0, 0);
        verify(view).updateClass("Test", "NotATest");
        reset(view);
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
        outContent.reset();
        view.addRelationship("Test", "Test2", "Realization");
        assertEquals("Added " + "Realization" + " between " + "Test"+ " and " + "Test2", outContent.toString().trim());
        verify(view).addRelationship("Test", "Test2", "Realization");
    }

    @Test
    public void testDeleteRelationship()
    {
        view.createClass("Test", 0, 0);
        view.createClass("Test2", 0, 0);
        view.addRelationship("Test", "Test2", "Realization");
        outContent.reset();
        view.deleteRelationship("Test", "Test2");
        assertEquals("Deleted relationship between " + "Test" + " and " + "Test2", outContent.toString().trim());

        verify(view).deleteRelationship("Test", "Test2");
    }

    @Test
    public void testHelp()
    {

        view.showHelp();
        String result = "";
        result += "exit:                                                                                                       Close CLI\n";
        result += "help:                                                                                                       Show all options\n";
        result += "display:                                                                                                    Display all classes\n";
        result += "showgui:                                                                                                    Displays the GUI\n";
        result += "addc [class]:                                                                                               Create a class\n";
        result += "renamec [oldName] [newName]:                                                                                Rename a class\n";
        result += "deletec [class]:                                                                                            Delete a class\n";
        result += "addf [class] [type] [name] [public or private or protected]:                                                Create a field\n";
        result += "renamef [className] [oldName][newName]:                                                                     Rename a field\n";
        result += "deletef [class] [FieldName]:                                                                                Delete a field\n";
        result += "changefa [class] [fieldName] [public or private or protected]:                                              Change field access\n";
        result += "changeft [class] [fieldName] [newType]:                                                                     Change field type\n";
        result += "addm [class] [methodType] [methodName] [[paramType] [paramName] ...]:                                       Add a method\n";
        result += "renamem [class] [methodType] [oldMethodName] [[ParamType] [paramName] ...] [newMethodName]:                 Rename a method\n";
        result += "deletem [class] [methodType] [methodName] [[paramType] [paramName] ...]:                                    Delete a method\n";
        result += "changema [class] [methodType] [methodName] [[paramType] [paramName] ...] [public or private or protected]:  Change method access\n";
        result += "changemt [class] [methodType] [methodName] [[paramType] [paramName] ...] [newType]:                         Change method type\n";
        result += "addr [classFrom] [classTo] [relateType]:                                                                    Add a relationship\n";
        result += "deleter [classFrom] [classTo]:                                                                              Delete a relationship\n";
        result += "save [fileName]:                                                                                            Saves to passed in file name\n";
        result += "load [fileName]:                                                                                            Loads the passed in file name\n";
        result += "display [(Optional) className]:                                                                             Displays a class\n";
        result += "display [className]:                                                                                        Displays a class\n";
        result += "undo:                                                                                                       Reverts to a previous state\n";
        result += "redo:                                                                                                       Restores the latest undo\n";
        assertEquals(outContent.toString(), result);
    }

    @Test
    public void testDsiplay()
    {
        view.display("Test String\nnew line");
        assertEquals("Test String\nnew line", outContent.toString().trim());
        verify(view).display("Test String\nnew line");
    }
    

    @Test
    public void testShowError()
    {
        view.showError("404 cat meme");
        assertEquals("404 cat meme", outContent.toString().trim());
        verify(view).showError("404 cat meme");
    }

    @Test
    public void testSave()
    {
        view.save();
        assertEquals("Your file has been saved.", outContent.toString().trim());
        verify(view).save();
    }

    @Test
    public void testLoad()
    {
        view.load();
        assertEquals("Your file has been loaded.", outContent.toString().trim());
        verify(view).load();
    }


    @Test
    public void testExit()
    {
        view.exit();
        assertEquals("Closing application.", outContent.toString().trim());
        verify(view).exit();
    }

    @Test
    public void testStart()
    {
        view.start();
        String result = "";
        result += "  _____      _             _  __ _\n";
        result += " /  ___|    | |           (_)/ _| |\n";
        result += " \\ `--.  ___| |____      ___| |_| |_ _   _\n";
        result += "  `--. \\/ __| '_ \\ \\ /\\ / / |  _| __| | | |\n";
        result += " /\\__/ / (__| | | \\ V  V /| | | | |_| |_| |\n";
        result += " \\____/ \\___|_| |_|\\_/\\_/ |_|_|  \\__|\\__, |\n";
        result += "                                      __/ |\n";
        result += "                                     |___/ \n";
        assertEquals(outContent.toString(), result);
    }

    @Test
    public void testAddListener() 
    {
        ClassClickController test = new ClassClickController(store, view, controller);
        view.addListener(test);
        assertEquals("", outContent.toString().trim());
        verify(view).addListener(test);
    }

    @Test
    public void testAddListener2() 
    {
        MouseClickAndDragController test = new MouseClickAndDragController(store, view, controller);
        view.addListener(test, "Text");
        assertEquals("", outContent.toString().trim());
        verify(view).addListener(test, "Text");
    }

    @Test
    public void testGetLoc() {
        view.getLoc("String");
        assertEquals("", outContent.toString().trim());
        verify(view).getLoc("String");
    }

    @Test
    public void testGetRelationships() {
        view.getRelationships();
        assertEquals("", outContent.toString().trim());
        verify(view).getRelationships();
    }

    @Test
    public void testGetMainWindow() {
        view.getMainWindow();
        assertEquals("", outContent.toString().trim());
        verify(view).getMainWindow();
    }

    @Test
    public void testGetPanels() {
        view.getPanels();
        assertEquals("", outContent.toString().trim());
        verify(view).getPanels();
    }

    @Test
    public void testGetChoiceFromUser() 
    {
        view.getChoiceFromUser("Hi I am a message", "So am I", new ArrayList<String>());
        assertEquals("", outContent.toString().trim());
        verify(view).getChoiceFromUser("Hi I am a message", "So am I", new ArrayList<String>());
    }

    @Test
    public void testGetInputFromUser() 
    {
        view.getInputFromUser("Hi I am a message");
        assertEquals("", outContent.toString().trim());
        verify(view).getInputFromUser("Hi I am a message");
    }

    @Test
    public void testAddListeners() 
    {
        ActionListener test = new ClassClickController(store, view, controller);
        view.addListeners(test, test, test);
        assertEquals("", outContent.toString().trim());
        verify(view).addListeners(test, test, test);
    }

    @Test
    public void testAddPanelListener() 
    {
        ActionListener test = new ClassClickController(store, view, controller);
        view.addPanelListener(test, "Text");
        assertEquals("", outContent.toString().trim());
        verify(view).addPanelListener(test, "Text");
    }

    @Test
    public void testSetGUIInvisible()
    {
        view.setGUIInvisible();
        assertEquals("", outContent.toString().trim());
        verify(view).setGUIInvisible();
    }

    @Test
    public void testSetGUIVisible()
    {
        view.setGUIVisible();
        assertEquals("", outContent.toString().trim());
        verify(view).setGUIVisible();
    }
}

