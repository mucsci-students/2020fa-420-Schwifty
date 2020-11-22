import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
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
import java.util.Map;
import javax.swing.JPanel;
import java.util.ArrayList;

public class TestGraphicalView 
{

    @Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    }
    
    @Spy
    Store s = new Store();

    @Mock
    GraphicalView gv;

    @InjectMocks Controller c;

    @Test
    public void testGUIStart()
    {
        gv.start();
        verify(gv).start();
        gv.makeWindow();
        verify(gv, atLeastOnce()).makeWindow();
        gv.createMenu();
        verify(gv, atLeastOnce()).createMenu();
    }

    @Test
    public void testCreateClass()
    {
        c.createClass("Test");
        UML.model.Class dasClass = new UML.model.Class("Test");
        verify(gv).createClass(dasClass.toString(),0,0);
        verify(gv, atLeastOnce()).getPanels();
    }

    @Test
    public void testDeleteClass()
    {
        c.createClass("Test");
        UML.model.Class dasClass = new UML.model.Class("Test");
        verify(gv).createClass(dasClass.toString(),0,0);
        verify(gv, atLeastOnce()).getPanels();
        c.deleteClass("Test");
        verify(gv).deleteClass(dasClass.toString());
    }
    @Test
    public void testSaveAndLoad()
    {
        gv.save();
        verify(gv).save();

        gv.load();
        verify(gv).load();
    }

    @Test
    public void testShowError()
    {
        c.createClass("Fa il ed");
        verify(gv).showError("The class name cannot cantain a space.");
    }

    @Test
    public void testAddRelationship()
    {
        //test adding a relationship
        //Add classes and a relationship between then in the controller
        c.createClass("Test1");
        c.createClass("Test2");
        c.addRelationship("Test1", "Test2", UML.model.RelationshipType.GENERALIZATION);
        //Get the created classes from the store
        UML.model.Class chungusTest = (UML.model.Class)c.getStore().getClassStore().get(0);
        UML.model.Class biggerChungusTest = (UML.model.Class)c.getStore().getClassStore().get(1);
        
        //Verify that happens in the view
        verify(gv).addRelationship(chungusTest.toString(), biggerChungusTest.toString(), "GENERALIZATION");
    }
}
