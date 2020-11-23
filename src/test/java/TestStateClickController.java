import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Before;
import static org.junit.Assert.assertNotNull;
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
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TestStateClickController 
{
    @Spy
    Store s = new Store();

    @Mock
    View v;

    @InjectMocks Controller c;

    @Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEventListener() 
    {
        c.createClass("Test");

        StateClickController subjectUnderTest = new StateClickController(s,v,c);
        
        subjectUnderTest.actionPerformed(new ActionEvent(new Object(), 2, "Undo"));
        assertFalse(c.getStore().getClassStore().contains(new UML.model.Class("Test")));
        subjectUnderTest.actionPerformed(new ActionEvent(new Object(), 2, "Redo"));
        assertTrue(c.getStore().getClassStore().contains(new UML.model.Class("Test")));
        subjectUnderTest.actionPerformed(new ActionEvent(new Object(), 2, "CLI"));
        assertTrue(c.getCurrentView() instanceof View);
    }
}
