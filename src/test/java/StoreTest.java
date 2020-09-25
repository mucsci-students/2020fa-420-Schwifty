import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Test;

public class StoreTest {
    
    @Test
    public void testGetClassList()
    {

    }

    @Test
    public void testGetFieldList()
    {
        
    }

    @Test
    public void testAddClass()
    {
        
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
    public void testAddField()
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

    @Test
    public void testChangeFieldType()
    {
        
    }

    @Test
    public void testAddMethod()
    {
        
    }

    @Test
    public void testDeleteMethod()
    {
        
    }

    @Test
    public void testRenameMethod()
    {
        
    }

    @Test
    public void testChangeMethodType()
    {
        
    }

    @Test
    public void testAddParam()
    {
        
    }

    @Test
    public void testDeleteParam()
    {
        
    }

    @Test
    public void testAddRelationship()
    {
        
    }

    @Test
    public void testDeleteRelationship()
    {
        
    }

    @Test
    public void testRemoveRelationships()
    {
        
    }

    @Test
    public void testFindClass()
    {
        Store store = new Store();
        Class aClass = new Class("name")
        store.addClass(aClass);
        //The store object should be able to return the class of a given name.
        assertEquals(aClass, store.findClass("name"));
        //Should return null when that class isn't found.
        assertEquals(null, store.findClass("name2"));
    }

    @Test
    public void testFindMethod()
    {
        Store store = new Store();
        Class aClass = new Class("name");
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method aMethod = new Method("int", "testMethod");
        //The store object should be able to return the method of a given name.
        assertEquals(aMethod, store.findMethod("name", "int", "testMethod", params));
        //Should return null when that class isn't found.
        assertEquals(null, store.findMethod("name", "int", "TestMethod", params));
    }
}
