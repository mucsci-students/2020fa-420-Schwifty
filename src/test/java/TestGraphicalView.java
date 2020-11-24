import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.Before;

import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.MockitoAnnotations;
import UML.views.*;
import UML.controllers.*;
import UML.model.*;


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
