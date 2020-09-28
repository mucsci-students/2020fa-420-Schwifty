import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class StoreTest {
    
    @Test
    public void testAddClass()
    {
        //create a store.
        Store store = new Store();

        //Add test classes
        store.addClass("Test");
        store.addClass("Test2");

        //Validate they are in the class list
        assertTrue(store.getClassList().contains("Test"));
        assertTrue(store.getClassList().contains("Test2"));
    }

    @Test
    public void testGetClassList()
    {
        //create a store.
        Store store = new Store();
        //Add test classes
        store.addClass("Test");
        store.addClass("Test2");

        ArrayList<String> testStore = new ArrayList<String>();
        testStore.add("Test");
        testStore.add("Test2");
        assertTrue(testStore.equals(store.getClassList()));
    }
    @Test
    public void testAddField()
    {
        //create a store.
        Store store = new Store();
        
        //add a class and a field to the store
        store.addClass("TestClass");
        store.addField("TestClass","int", "num");
        
        //Find the test class in the store
        Class testClass = store.findClass("TestClass");
        
        //get the fields from the test class
        ArrayList<String> stringFields = store.getFieldList(testClass.getFields());
        
        //Test the correct thing was added.
        assertTrue(stringFields.contains("int num"));
    }

    @Test
    public void testGetFieldList()
    {
        //create a store.
        Store store = new Store();
        
        //create a field. 
        Field f = new Field("int", "num"); 
        
        //create a field set. 
        Set<Field> fieldSet = new HashSet<Field>();
        fieldSet.add(f); 
        
        //get field list as ArrayList.
        ArrayList<String> stringFields = store.getFieldList(fieldSet);
        
        //Test that an ArrayList was returned with field int num. 
        assertTrue(stringFields.contains("int num")); 
    }
    
    @Test
    public void testDeleteField()
    {
        //create a store.
        Store store = new Store();
        
        //add a class to the store.
        store.addClass("Test");
        //add a field to that class.
        store.addField("Test", "int", "num");
        //Delete that field.
        store.deleteField("Test", "num");

        //get the test class from the store
        Class testClass = store.findClass("Test");
        Set<Field> fields = testClass.getFields();
        
        //Validate the field has been removed. 
        assertFalse(store.getFieldList(fields).contains("int num"));
    }
    
    @Test
    public void testRenameField()
    {
        //create a store.
        Store store = new Store();
        
        //add class to store.
        store.addClass("Test");
        
        //find the test class.
        Class test = store.findClass("Test");
        
        //add field to Test class.
        store.addField("Test", "int", "num");

        //rename num field in Test class.
        store.renameField("Test", "num", "num2");

        //checks if the field has been renamed.  
        assertTrue(store.getFieldList(test.getFields()).contains("int num2"));

    }

    @Test
    public void testChangeFieldType()
    {
        //create a store.
        Store store = new Store(); 

        //adds a test class to store.
        store.addClass("Test");

        //finds the test class in store.
        Class test = store.findClass("Test");

        //adds a field to the test class.
        store.addField("Test", "int", "name");

        //changes the type of a field in the test class.
        store.changeFieldType("Test", "String", "name");

        //checks if the field's type was changed. 
        assertTrue(store.getFieldList(test.getFields()).contains("String name"));
    }
    
    @Test
    public void testDeleteClass()
    {
        //create a store.
        Store store = new Store();
        
        //Add a class to the store
        store.addClass("Test");

        //remove that class
        store.deleteClass("Test");

        //verify the class has been removed
        assertFalse(store.getClassList().contains("Test"));
    }

    @Test
    public void testRenameClass()
    {
        //create a store.
        Store store = new Store();   
        
        //Add a new class to the store
        store.addClass("Test");
        
        //rename that class in the store
        store.renameClass("Test", "NewTest");

        //Test that new name is in the store
        assertTrue(store.getClassList().contains("NewTest"));

        //Ensure the old name is not
        assertFalse(store.getClassList().contains("Test"));
    }

    @Test
    public void testAddMethod()
    {
        //create a store.
        Store store = new Store(); 
        
        //Create a class
        store.addClass("Test");
        
        //Create params for method      
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        
        //Add a method to the store
        store.addMethod("Test", "int", "testMethod", params);

        //Create a test method
        Method m = new Method("int", "testMethod", params);

        //get the list of methods from the class
        Set<Method> classMethods = store.findClass("Test").getMethods();
        
        //Ensure classMethods contains the one we added.
        assertTrue(classMethods.contains(m));
    }

    @Test
    public void testDeleteMethod()
    {
        //create a store.
        Store store = new Store();         
        
        //add a test class to store.
        store.addClass("Test");
        
        //find the test class in store.
        Class test = store.findClass("Test");
        
        //create an ArrayList of params for a method.
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        
        //add method to test class.
        store.addMethod("Test", "int", "testMethod", params);
        
        //delete method to test class.
        store.deleteMethod("Test", "int", "testMethod", params);
        
        //get the set of methods from test class.
        Set<Method> classMethods = test.getMethods();
        
        //create a method with same parameters as when we added a method to test class.
        Method m = new Method("int", "testMethod", params);

        //check to see is the method has been deleted.
        assertFalse(classMethods.contains(m));

                
    }

    @Test
    public void testRenameMethod()
    {
        //create a store.
        Store store = new Store(); 
        
        //Create a class
        store.addClass("Test");
                
        //Create params for method      
        ArrayList<Parameter> params = new ArrayList<Parameter>();
                
        //Add a method to the store
        store.addMethod("Test", "int", "testMethod", params);
        
        //Rename that method
        store.renameMethod("Test", "int", "testMethod", params, "testMethodTwo");

        //Create a test method
        Method m = new Method("int", "testMethodTwo", params);
        
        //get the list of methods from the class
        Set<Method> classMethods = store.findClass("Test").getMethods();
                
        //Ensure classMethods contains the one we added.
        assertTrue(classMethods.contains(m));
        
    }

    @Test
    public void testChangeMethodType()
    {
        //create a store.
        Store store = new Store();
        
        //add a test class to store.
        store.addClass("Test");
        
        //Create an ArrayList of Parameters for adding a method.
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        
        //add a method to test class.
        store.addMethod("Test", "int", "testMethod", params);
        
        //change the return type of testMethod.
        store.changeMethodType("Test", "int", "testMethod", params, "String");
        
        //get a set of methods from the test class.
        Set<Method> classMethods = store.findClass("Test").getMethods();
        
        //made a method that should be the updated method.
        Method m = new Method("String", "testMethod", params);
        
        //check to see if the return type of the method has changed. 
        assertTrue(classMethods.contains(m));

        //Should throw an exception for empty type name.
        assertThrows(IllegalArgumentException.class, () -> {
            store.changeMethodType("Test", "String", "testMethod", params, " ");
        });
    }

    @Test
    public void testAddParam()
    {
        //create a store.
        Store store = new Store();
        store.addClass("Test");
        //Create an ArrayList of Parameters for adding a method.
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        
        //Add a method
        store.addMethod("Test", "int", "testMethod", params);

        //add a param to that method
        store.addParam("Test", "int", "testMethod",  params, "String", "str");

        //Test that the param was added
        Method m = store.findMethod("Test", "int", "testMethod", params);

        //Get the actual params
        ArrayList<Parameter> actualParams = m.getParams();

        //Make a test param
        Parameter param = new Parameter("String", "str");
        
        //That test param should be there, test it. 
        assertTrue(actualParams.contains(param));
        
    }

    @Test
    public void testDeleteParam()
    {
        //create store.
        Store store = new Store();
        
        //add a test class to store.
        store.addClass("Test");

        //create ArrayList of parameters for creating a method.
        ArrayList<Parameter> params = new ArrayList<Parameter>();

        //add method to test class.
        store.addMethod("Test", "int", "testMethod", params);

        //add parameter to test method.
        store.addParam("Test", "int", "testMethod", params, "int", "param");

        //delete parameter from test method.
        store.deleteParam("Test", "int", "testMethod", params, "int", "param");

        //find method we deleted from.
        Method m = store.findMethod("Test", "int", "testMethod", params);

        //get ArrayList of parameters from test method.
        ArrayList<Parameter> paramList = m.getParams();
        
        //make a test parameter.
        Parameter p = new Parameter("int", "param");
        
        //test parameter should not be in ArrayList.
        assertFalse(paramList.contains(p));
                
    }

    @Test
    public void testAddRelationship()
    {
        //create store.
        Store store = new Store();
        Store store1 = new Store();
        //add a test class to store.
        store.addClass("Test");
        store.addClass("Test1");
        //create ArrayList of parameters for creating a method.
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        //add method to test class.
        store.addMethod("Test", "int", "testMethod", params);
        store.addMethod("Test1", "String", "method", params);
        //Shouldn't allow relationship between class and itself.
        assertThrows(IllegalArgumentException.class, () -> {
            store.addRelationship("Test", "Test", RelationshipType.ASSOCIATION);
        });
        store.addRelationship("Test", "Test1", RelationshipType.ASSOCIATION);
        //Should be rtelationships in Test's to, and Test1's from.
        assertTrue(store.findClass("Test").getRelationshipsToOther().containsKey("Test1"));
        assertTrue(store.findClass("Test1").getRelationshipsFromOther().containsKey("Test"));
    }

    @Test
    public void testDeleteRelationship()
    {
        //create store.
        Store store = new Store();
        Store store1 = new Store();
        //add a test class to store.
        store.addClass("Test");
        store.addClass("Test1");
        //create ArrayList of parameters for creating a method.
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        //add method to test class.
        store.addMethod("Test", "int", "testMethod", params);
        store.addMethod("Test1", "String", "method", params);
        //Should return false when there is no relationship to delete.
        //assertFalse(store.deleteRelationship("Test", "Test1"));
        store.addRelationship("Test", "Test1", RelationshipType.ASSOCIATION);
        store.deleteRelationship("Test", "Test1");
        //Neither class should contain relationships now.
        assertTrue(store.findClass("Test").getRelationshipsToOther().isEmpty());
        assertTrue(store.findClass("Test").getRelationshipsFromOther().isEmpty());
        assertTrue(store.findClass("Test1").getRelationshipsToOther().isEmpty());
        assertTrue(store.findClass("Test1").getRelationshipsFromOther().isEmpty());
    }

    @Test
    public void testRemoveRelationships()
    {
        //create store.
        Store store = new Store();
        
        //add a test class to store.
        store.addClass("Test");

        //create ArrayList of parameters for creating a method.
        ArrayList<Parameter> params = new ArrayList<Parameter>();

        //add method to test class.
        store.addMethod("Test", "int", "testMethod", params);
    }

    @Test
    public void testFindClass()
    {
        Store store = new Store();
        Class aClass = new Class("name");
        store.addClass(aClass.getName());
        //The store object should be able to return the class of a given name.
        assertEquals(aClass, store.findClass("name"));
        //Should return null when that class isn't found.
        assertEquals(null, store.findClass("name2"));
    }

    @Test
    public void testFindMethod()
    {
        Store store = new Store();
        store.addClass("name");
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        Method aMethod = new Method("int", "testMethod", params);
        store.addMethod("name", "int", "testMethod", params);
        //The store object should be able to return the method of a given name.
        assertEquals(aMethod, store.findMethod("name", "int", "testMethod", params));
        //Should return null when that class isn't found.
        assertEquals(null, store.findMethod("name", "int", "TestMethod", params));
    }

    @Test
    public void testRemoveMethodByString()
    {

    }

    @Test
    public void testRenameMethodByString()
    {
        
    }
}
