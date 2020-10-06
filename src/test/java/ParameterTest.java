import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import UML.model.Parameter;


public class ParameterTest {
    
    @Test
    public void testGetName() 
    {
        Parameter test = new Parameter("type", "name");
        //Test attribute's name should equal "name".
        assertEquals("name", test.getName());
    }

    @Test
    public void testGetType() 
    {
        Parameter test = new Parameter("type", "name");
        //Test attribute's type should equal "type".
        assertEquals("type", test.getType());
    }

    @Test
    public void testSetName() 
    {
        //Simple name change.
        Parameter test = new Parameter("type", "name");
        test.setName("newName");
        assertEquals("newName", test.getName());
        //Change to empty string/white spaces.
        Parameter test2 = new Parameter("type", "name");
        //Don't allow empty/whitespace name.
        assertThrows(IllegalArgumentException.class, () -> {
            test.setName(" ");
        });
    }

    @Test
    public void testSetType() 
    {
        //Simple type change.
        Parameter test = new Parameter("type", "name");
        test.setType("newType");
        assertEquals("newType", test.getType());
        //Change to empty string/white spaces.
        Parameter test2 = new Parameter("type", "name");
        //Don't allow empty/whitespace type.
        assertThrows(IllegalArgumentException.class, () -> {
            test2.setType(" ");
        });
    }

    @Test
    public void testEquals() 
    {
        //Equals method works for equal attributes.
        Parameter test1 = new Parameter("type", "name");
        Parameter test2 = new Parameter("type", "name");
        assertTrue(test1.equals(test2));
        //Equals method does not work for unequal attributes.
        //Deiffrent names.
        Parameter test3 = new Parameter("type", "name");
        Parameter test4 = new Parameter("type", "name1");
        assertFalse(test3.equals(test4));
        //Different types.
        Parameter test5 = new Parameter("type", "name");
        Parameter test6 = new Parameter("type1", "name");
        assertFalse(test5.equals(test6));
    }

     @Test
    public void testToString() 
    {
        Parameter test = new Parameter("type", "name");
        //assertEquals("type : name", test.toString().equals("type : name"));
        assertEquals("type name", test.toString());
    }
}
