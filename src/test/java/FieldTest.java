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
        Field test = new Field("type", "name", "public");
        //Test field's name should equal "name".
        assertEquals("name", test.getName());
    }

    @Test
    public void testGetType() 
    {
        Field test = new Field("type", "name", "public");
        //Test field's type should equal "type".
        assertEquals("type", test.getType());
    }

    @Test
    public void testGetAccessString()
    {
        Field test = new Field("type", "name", "private");
        //Test field's access type should be private.
        assertEquals("private", test.getAccessString());

        Field test1 = new Field("type", "name", "nonsense");
        //Test field's access type should default to public.
        assertEquals("public", test1.getAccessString());
    }

    @Test
    public void getAccessChar()
    {
        Field test = new Field("type", "name", "private");
        //Test field's access type should be -.
        assertEquals('-', test.getAccessChar());

        Field test1 = new Field("type", "name", "nonsense");
        //Test field's access type should default to +.
        assertEquals('+', test1.getAccessChar());
    }

    @Test
    public void testSetName() 
    {
        //Simple name change.
        Field test = new Field("type", "name", "public");
        test.setName("newName");
        assertEquals("newName", test.getName());
        //Don't allow empty/whitespace name.
        assertThrows(IllegalArgumentException.class, () -> {
            test.setName(" ");
        });
    }

    @Test
    public void testSetType() 
    {
        //Simple type change.
        Field test = new Field("type", "name", "private");
        test.setType("newType");
        assertEquals("newType", test.getType());
        //Change to empty string/white spaces.
        Field test2 = new Field("type", "name", "private");
        //Don't allow empty/whitespace type.
        assertThrows(IllegalArgumentException.class, () -> {
            test2.setType(" ");
        });
    }

    @Test
    public void testSetAccess()
    {
        Field test = new Field("type", "name", "protected");
        test.setAccess("public");
        //Test that the string and char are set as expected.
        assertEquals("public", test.getAccessString());
        assertEquals('+', test.getAccessChar());
        //Illegal type should not allow setting
        assertFalse(test.setAccess("nonsense"));
        //The field should not have changed.
        assertEquals('+', test.getAccessChar());

        //Test setting access for private and protected.
        test.setAccess("private");
        assertEquals('-', test.getAccessChar());
        test.setAccess("protected");
        assertEquals('*', test.getAccessChar());
    }

    @Test
    public void testEquals() 
    {
        //Equals method works for equal attributes.
        Field test1 = new Field("type", "name", "protected");
        Field test2 = new Field("type", "name", "protected");
        assertTrue(test1.equals(test2));
        //Equals method does not work for unequal attributes.
        //Deiffrent names.
        Field test3 = new Field("type", "name", "protected");
        Field test4 = new Field("type", "name1", "protected");
        assertFalse(test3.equals(test4));
        //Different types.
        Field test5 = new Field("type", "name", "public");
        Field test6 = new Field("type1", "name", "public");
        assertFalse(test5.equals(test6));
        //Different access types.
        Field test7 = new Field("type", "name", "public");
        Field test8 = new Field("type", "name", "private");
        assertFalse(test7.equals(test8));
    }

    @Test
    public void testToString() 
    {
        Field test = new Field("type", "name", "public");
        //assertEquals("type : name", test.toString().equals("type : name"));
        assertEquals("+ type name", test.toString());
    }
}