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
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params);
        //Test attribute's name should equal "name".
        assertEquals("name", test.getName());
    }

    @Test
    public void testGetType() 
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params);
        //Test attribute's type should equal "type".
        assertEquals("type", test.getType());
    }

    @Test
    public void testSetName() 
    {
        //Simple name change.
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params);
        test.setName("newName");
        assertEquals("newName", test.getName());
        //Change to empty string/white spaces.
        Method test2 = new Method("type", "name", params);
        //Don't allow empty/whitespace name.
        assertThrows(IllegalArgumentException.class, () -> {
            test.setName(" ");
        });
    }

    @Test
    public void testSetType() 
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        //Simple type change.
        Method test = new Method("type", "name", params);
        test.setType("newType");
        assertEquals("newType", test.getType());
        //Change to empty string/white spaces.
        Method test2 = new Method("type", "name", params);
        //Don't allow empty/whitespace type.
        assertThrows(IllegalArgumentException.class, () -> {
            test2.setType(" ");
        });
    }

    @Test
    public void testEquals() 
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        ArrayList<Parameter> params1 = new ArrayList<Parameter>();
        params.add(new Parameter("double", "number"));
        params1.add(new Parameter("double", "number"));
        //Equals method works for equal attributes.
        Method test1 = new Method("type", "name", params);
        Method test2 = new Method("type", "name", params1);
        assertTrue(test1.equals(test2));
        test1.addParam(new Parameter("int", "num"));
        test2.addParam(new Parameter("int", "num"));
        //Methods should be equal.
        assertTrue(test1.equals(test2));
        ArrayList<Parameter> params2 = new ArrayList<Parameter>();
        ArrayList<Parameter> params3 = new ArrayList<Parameter>();
        Method testA = new Method("type", "name", params2);
        Method testB = new Method("type", "name", params3);
        testA.addParam(new Parameter("String", "name"));
        testB.addParam(new Parameter("String", "name2"));
        assertFalse(testA.equals(testB));
        //Equals method does not work for unequal attributes.
        //Deiffrent names.
        Method test3 = new Method("type", "name", params);
        Method test4 = new Method("type", "name1", params1);
        assertFalse(test3.equals(test4));
        //Different types.
        Method test5 = new Method("type", "name", params);
        Method test6 = new Method("type1", "name", params1);
        assertFalse(test5.equals(test6));
    }

    @Test
    public void testToString() 
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        params.add(new Parameter("String", "param"));
        Method test = new Method("type", "name", params);
        test.addParam(new Parameter("int", "num"));
        assertEquals("Parameters: String : param | int : num | \nMethod: type : name", test.toString());
    }

    @Test
    public void testSetAndGetParams() 
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params);
        ArrayList<Parameter> list = new ArrayList<Parameter>();
        list.add(new Parameter("String", "hello"));
        list.add(new Parameter("int", "num"));
        test.setParams(list);
        assertEquals(list, test.getParams());
    }

    @Test
    public void testAddParam()
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params);
        assertEquals(0, test.getParams().size());
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
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params);
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

