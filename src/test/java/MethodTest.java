import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import java.util.ArrayList;
import UML.model.Method;
import UML.model.Parameter;

public class MethodTest {
    
    @Test
    public void testGetName() 
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params, "public");
        //Test attribute's name should equal "name".
        assertEquals("name", test.getName());
    }

    @Test
    public void testGetType() 
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params, "public");
        //Test attribute's type should equal "type".
        assertEquals("type", test.getType());
    }

    @Test
    public void testSetName() 
    {
        //Simple name change.
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params, "public");
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
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        //Simple type change.
        Method test = new Method("type", "name", params, "public");
        test.setType("newType");
        assertEquals("newType", test.getType());
        //Change to empty string/white spaces.
        Method test2 = new Method("type", "name", params, "public");
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
        Method test1 = new Method("type", "name", params, "public");
        Method test2 = new Method("type", "name", params1, "public");
        assertTrue(test1.equals(test2));
        test1.addParam(new Parameter("int", "num"));
        test2.addParam(new Parameter("int", "num"));
        //Methods should be equal.
        assertTrue(test1.equals(test2));
        ArrayList<Parameter> params2 = new ArrayList<Parameter>();
        ArrayList<Parameter> params3 = new ArrayList<Parameter>();
        Method testA = new Method("type", "name", params2, "public");
        Method testB = new Method("type", "name", params3, "public");
        testA.addParam(new Parameter("String", "name"));
        testB.addParam(new Parameter("String", "name2"));
        assertFalse(testA.equals(testB));
        //Equals method does not work for unequal attributes.
        //Deiffrent names.
        Method test3 = new Method("type", "name", params, "protected");
        Method test4 = new Method("type", "name1", params1, "protected");
        assertFalse(test3.equals(test4));
        //Different types.
        Method test5 = new Method("type", "name", params, "private");
        Method test6 = new Method("type1", "name", params1, "private");
        assertFalse(test5.equals(test6));
        //Different parameters.
        Method test7 = new Method("type", "name", params, "private");
        Method test8 = new Method("type", "name", params3, "private");
        assertFalse(test7.equals(test8));
        //Different access types.
        Method test9 = new Method("type", "name", params, "public");
        Method test10 = new Method("type", "name", params1, "private");
        assertFalse(test9.equals(test10));
    }

    @Test
    public void testToString() 
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>(); 
        params.add(new Parameter("String", "param"));
        Method test = new Method("type", "name", params, "public");
        test.addParam(new Parameter("int", "num"));
        //The toString() output should be equal to the string below.
        assertEquals("Method: + type name ( String param , int num )", test.toString());
    }

    @Test
    public void testSetAndGetParams() 
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params, "private");
        ArrayList<Parameter> list = new ArrayList<Parameter>();
        list.add(new Parameter("String", "hello"));
        list.add(new Parameter("int", "num"));
        test.setParams(list);
        //The parameter that is gotten should be equal to the created list.
        assertEquals(list, test.getParams());
    }

    @Test
    public void testAddParam()
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params, "protected");
        assertEquals(0, test.getParams().size());
        test.addParam(new Parameter("int", "param"));
        //The method should contain the added parameter and therefore not be empty.
        assertTrue(!test.getParams().isEmpty());
        assertTrue(test.getParams().contains(new Parameter("int", "param")));
        test.addParam(new Parameter("int", "param"));
        //The size should be one after adding a parameter.
        assertEquals(1, test.getParams().size());
        test.addParam(new Parameter("String", "test"));
        //The method should contaim the added parameters.
        assertTrue(test.getParams().contains(new Parameter("int", "param")));
        assertTrue(test.getParams().contains(new Parameter("String", "test")));
        //Should return false for duplicate name parameter.
        assertFalse(test.addParam(new Parameter("String", "param")));
    }

    @Test
    public void testDeleteParam()
    {
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method test = new Method("type", "name", params, "public");
        //Disallow deleting a parameter that doesn't exist.
        assertFalse(test.deleteParam(new Parameter("int", "param")));
        test.addParam(new Parameter("int", "param"));
        test.addParam(new Parameter("String", "name"));
        test.deleteParam(new Parameter("int", "param"));
        //There should exist parameters in the method.
        assertTrue(!test.getParams().isEmpty());
        //The method should not contain the deleted parameters.
        assertTrue(!test.getParams().contains(new Parameter("int", "param")));
        test.deleteParam(new Parameter("String", "name"));
        assertTrue(test.getParams().isEmpty());
    }

    @Test
    public void testSetandGetAccessChar()
    {

    }

    @Test
    public void testSetandGetAccessString()
    {
        
    }
}

