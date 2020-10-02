import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import UML.model.Field;

public class FieldTest {

    @Test
    public void testGetName() 
    {
        Field test = new Field("type", "name");
        //Test field's name should equal "name".
        assertEquals("name", test.getName());
    }

    @Test
    public void testGetType() 
    {
        Field test = new Field("type", "name");
        //Test field's type should equal "type".
        assertEquals("type", test.getType());
    }

    @Test
    public void testSetName() 
    {
        //Simple name change.
        Field test = new Field("type", "name");
        test.setName("newName");
        assertEquals("newName", test.getName());
        //Change to empty string/white spaces.
        Field test2 = new Field("type", "name");
        //Don't allow empty/whitespace name.
        assertThrows(IllegalArgumentException.class, () -> {
            test.setName(" ");
        });
    }

    @Test
    public void testSetType() 
    {
        //Simple type change.
        Field test = new Field("type", "name");
        test.setType("newType");
        assertEquals("newType", test.getType());
        //Change to empty string/white spaces.
        Field test2 = new Field("type", "name");
        //Don't allow empty/whitespace type.
        assertThrows(IllegalArgumentException.class, () -> {
            test2.setType(" ");
        });
    }

    @Test
    public void testEquals() 
    {
        //Equals method works for equal attributes.
        Field test1 = new Field("type", "name");
        Field test2 = new Field("type", "name");
        assertTrue(test1.equals(test2));
        //Equals method does not work for unequal attributes.
        //Deiffrent names.
        Field test3 = new Field("type", "name");
        Field test4 = new Field("type", "name1");
        assertFalse(test3.equals(test4));
        //Different types.
        Field test5 = new Field("type", "name");
        Field test6 = new Field("type1", "name");
        assertFalse(test5.equals(test6));
    }

     @Test
    public void testToString() 
    {
        Field test = new Field("type", "name");
        //assertEquals("type : name", test.toString().equals("type : name"));
        assertEquals("type : name", test.toString());
    }
}
