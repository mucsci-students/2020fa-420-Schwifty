import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Before;

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

public class ControllerTest 
{
    


    @Spy
    private Store store = new Store();

    @Mock
    private View view;

    @InjectMocks private Controller controller;

    @Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testCreateClass()
    {
        controller.createClass("test");
        //Make sure correct class is in store.
        assertFalse((controller.getStore().getClassStore().isEmpty()));
        assertEquals(controller.getStore().getClassStore().get(0).getName(), "test");
    }

    @Test
    public void testDeleteClass()
    {
        controller.createClass("test");
        controller.deleteClass("test");
    }

    @Test
    public void testRenameClass()
    {
        
    }

    @Test
    public void testCreateField()
    {
        
    }
    
    @Test
    public void testDeleteField()
    {
        
    }

    @Test
    public void testRenameField()
    {
        
    }



}
