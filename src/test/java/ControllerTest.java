import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import UML.views.*;
import UML.controllers.*;
import UML.model.*;

public class ControllerTest {
    
    @Test
    public void testCreateClass()
    {
        GraphicalView v = new GraphicalView();
        Store s = new Store();
        Controller c = new Controller(s, v);
        //Test this later.
    }

    @Test
    public void testDeleteClass()
    {
        
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
