import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import java.util.ArrayList;

public class MethodTest {
    
    @Test
    public void testGetName() 
    {
        Method test = new Method("type", "name");
        //Test attribute's name should equal "name".
        assertEquals("name", test.getName());
    }

    @Test
    public void testGetType() 
    {
        Method test = new Method("type", "name");
        //Test attribute's type should equal "type".
        assertEquals("type", test.getType());
    }

    @Test
    public void testSetName() 
    {
        //Simple name change.
        Method test = new Method("type", "name");
        test.setName("newName");
        assertEquals("newName", test.getName());
        //Change to empty string/white spaces.
        Method test2 = new Method("type", "name");
        //Don't allow empty/whitespace name.
        assertThrows(IllegalArgumentException.class, () -> {
            test.setName(" ");
        });
    }

    @Test
    public void testSetType() 
    {
        //Simple type change.
        Method test = new Method("type", "name");
        test.setType("newType");
        assertEquals("newType", test.getType());
        //Change to empty string/white spaces.
        Method test2 = new Method("type", "name");
        //Don't allow empty/whitespace type.
        assertThrows(IllegalArgumentException.class, () -> {
            test2.setType(" ");
        });
    }

    @Test
    public void testEquals() 
    {
        //Equals method works for equal attributes.
        Method test1 = new Method("type", "name");
        Method test2 = new Method("type", "name");
        assertTrue(test1.equals(test2));
        test1.addParam(new Parameter("int", "num"));
        test2.addParam(new Parameter("int", "num"));
        assertTrue(test1.equals(test2));
        test1.addParam(new Parameter("String", "name"));
        test2.addParam(new Parameter("String", "name2"));
        assertFalse(test1.equals(test2));
        //Equals method does not work for unequal attributes.
        //Deiffrent names.
        Method test3 = new Method("type", "name");
        Method test4 = new Method("type", "name1");
        assertFalse(test3.equals(test4));
        //Different types.
        Method test5 = new Method("type", "name");
        Method test6 = new Method("type1", "name");
        assertFalse(test5.equals(test6));
    }

    @Test
    public void testToString() 
    {
        Method test = new Method("type", "name");
        test.addParam(new Parameter("String", "param"));
        test.addParam(new Parameter("int", "num"));
        assertEquals("Parameters: String : param | int : num | \nMethod: type : name", test.toString());
    }

    @Test
    public void testSetAndGetParams() 
    {
        Method test = new Method("type", "name");
        ArrayList<Parameter> list = new ArrayList<Parameter>();
        list.add(new Parameter("String", "hello"));
        list.add(new Parameter("int", "num"));
        test.setParams(list);
        assertEquals(list, test.getParams());
    }

    @Test
    public void testAddParam()
    {
        Method test = new Method("type", "name");
        test.addParam(new Parameter("int", "param"));
        assertTrue(!test.getParams().isEmpty());
        assertTrue(test.getParams().contains(new Parameter("int", "param")));
        test.addParam(new Parameter("int", "param"));
        assertEquals(1, test.getParams().size());
        test.addParam(new Parameter("String", "test"));
        assertTrue(test.getParams().contains(new Parameter("int", "param")));
        assertTrue(test.getParams().contains(new Parameter("String", "test")));
    }

    @Test
    public void testDeleteParam()
    {
        Method test = new Method("type", "name");
        assertFalse(test.deleteParam(new Parameter("int", "param")));
        test.addParam(new Parameter("int", "param"));
        test.addParam(new Parameter("String", "name"));
        test.deleteParam(new Parameter("int", "param"));
        assertTrue(!test.getParams().isEmpty());
        assertTrue(!test.getParams().contains(new Parameter("int", "param")));
        test.deleteParam(new Parameter("String", "name"));
        assertTrue(test.getParams().isEmpty());
    }
}

