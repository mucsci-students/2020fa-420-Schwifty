import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import java.util.ArrayList;
import java.io.File;
import java.util.Set;
import java.util.HashSet;
import UML.model.Store;
import UML.model.Class;
import UML.model.Field;
import UML.model.Method;
import UML.model.Parameter;
import UML.model.RelationshipType;
import UML.views.*;
import UML.controllers.*;

public class StoreTest {
    
    @Test
    public void testAddClass()
    {
        //create a store.
        Store store = new Store();

        //Add test classes
        store.addClass("Test");
        store.addClass("Test2");
        
        //Should not allow adding class of same name.
        assertFalse(store.addClass("Test"));

        //Validate they are in the class list
        assertTrue(store.getClassList().contains("Test"));
        assertTrue(store.getClassList().contains("Test2"));
    }

    @Test
    public void testDeleteClass()
    {
        //create a store.
        Store store = new Store();
        
        //Add a class to the store
        store.addClass("Test");

        //Shoudl return false if class not deleted.
        assertFalse(store.deleteClass("wrongName"));

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

        //Rename class that doesn't exist returns false.
        assertFalse(store.renameClass("wrongName", "newName"));
        
        //rename that class in the store
        store.renameClass("Test", "NewTest");

        //Test that new name is in the store
        assertTrue(store.getClassList().contains("NewTest"));

        //Ensure the old name is not
        assertFalse(store.getClassList().contains("Test"));

        //Renaming class to name that exist should return false.
        store.addClass("doNotUseThisName");
        assertFalse(store.renameClass("NewTest", "doNotUseThisName"));

        //Add relationships to test renaming classes with relationships.
        store.addClass("SecondTest");
        store.addClass("Extra");

        store.addRelationship("NewTest", "SecondTest", RelationshipType.AGGREGATION);
        store.addRelationship("Extra", "NewTest", RelationshipType.GENERALIZATION);
        store.addRelationship("Extra", "SecondTest", RelationshipType.COMPOSITION);

        store.renameClass("NewTest", "TestAgain");
        assertTrue(store.getClassList().contains("TestAgain"));

        //Check that class still has its relationships.
        Class theClass = store.findClass("TestAgain");

        assertTrue(theClass.getRelationshipsFromOther().containsKey("Extra"));
        assertTrue(theClass.getRelationshipsToOther().containsKey("SecondTest"));

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
        store.addField("TestClass","int", "num", "public");
        
        //Find the test class in the store
        Class testClass = store.findClass("TestClass");
        
        //get the fields from the test class
        ArrayList<String> stringFields = store.getFieldList(testClass.getFields());
        
        //Test the correct thing was added.
        assertTrue(stringFields.contains("+ int num"));
    }

    @Test
    public void testGetFieldList()
    {
        //create a store.
        Store store = new Store();
        
        //create a field. 
        Field f = new Field("int", "num", "public"); 
        
        //create a field set. 
        Set<Field> fieldSet = new HashSet<Field>();
        fieldSet.add(f); 
        
        //get field list as ArrayList.
        ArrayList<String> stringFields = store.getFieldList(fieldSet);
        
        //Test that an ArrayList was returned with field int num. 
        assertTrue(stringFields.contains("+ int num")); 
    }
    
    @Test
    public void testDeleteField()
    {
        //create a store.
        Store store = new Store();
        
        //add a class to the store.
        store.addClass("Test");
        //add a field to that class.
        store.addField("Test", "int", "num", "public");
        //Delete that field.
        store.deleteField("Test", "num");

        //get the test class from the store
        Class testClass = store.findClass("Test");
        Set<Field> fields = testClass.getFields();
        
        //Validate the field has been removed. 
        assertFalse(store.getFieldList(fields).contains("int num public"));
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
        store.addField("Test", "int", "num", "public");

        //rename num field in Test class.
        store.renameField("Test", "num", "num2");

        //checks if the field has been renamed.  
        assertTrue(store.getFieldList(test.getFields()).contains("+ int num2"));

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
        store.addField("Test", "int", "name", "public");

        //changes the type of a field in the test class.
        store.changeFieldType("Test", "name", "String");

        //checks if the field's type was changed. 
        assertTrue(store.getFieldList(test.getFields()).contains("+ String name"));
    }

    @Test
    public void testChangeFieldAccess()
    {
        //create a store.
        Store store = new Store(); 

        //adds a test class to store and adds a field.
        store.addClass("Test");
        store.addField("Test", "int", "name", "public");

        Class test = store.findClass("Test");
        //Access should be changed to whatever is specified, and defaulted to public for nonsense.
        store.changeFieldAccess("Test", "name", "private");
        assertTrue(test.getFields().contains(new Field("int", "name", "private")));

        store.changeFieldAccess("Test", "name", "protected");
        assertTrue(test.getFields().contains(new Field("int", "name", "protected")));

        store.changeFieldAccess("Test", "name", "nonsense");
        assertTrue(test.getFields().contains(new Field("int", "name", "public")));
    }

    @Test
    public void testAddMethod()
    {
        //create a store.
        Store store = new Store(); 
        
        //Create a class
        store.addClass("Test");
        
        //Create params for method      
        ArrayList<String> params = new ArrayList<String>();
        ArrayList<Parameter> params2 = new ArrayList<Parameter>();
        
        //Add a method to the store
        store.addMethod("Test", "int", "testMethod", params, "private");

        //Create a test method
        Method m = new Method("int", "testMethod", params2, "private");

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
        ArrayList<String> params = new ArrayList<String>();
        params.add("pType pName");
        ArrayList<Parameter> params2 = new ArrayList<Parameter>();
        params2.add(new Parameter("pType", "pName"));
        
        //add method to test class.
        store.addMethod("Test", "int", "testMethod", params, "public");
        
        //delete method to test class.
        store.deleteMethod("Test", "int", "testMethod", params, "public");
        
        //get the set of methods from test class.
        Set<Method> classMethods = test.getMethods();
        
        //create a method with same parameters as when we added a method to test class.
        Method m = new Method("int", "testMethod", params2, "public");

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
        ArrayList<String> params = new ArrayList<String>();
        params.add("pType pName");
        ArrayList<Parameter> params2 = new ArrayList<Parameter>();
        params2.add(new Parameter("pType", "pName"));
                
        //Add a method to the store
        store.addMethod("Test", "int", "testMethod", params, "private");
        
        //Rename that method
        store.renameMethod("Test", "int", "testMethod", params, "private", "testMethodTwo");

        //Create a test method
        Method m = new Method("int", "testMethodTwo", params2, "private");
        Method m2 = new Method("int", "testMethod", params2, "private");
        
        //get the list of methods from the class
        Set<Method> classMethods = store.findClass("Test").getMethods();
                
        //Ensure classMethods contains the one we added.
        assertTrue(classMethods.contains(m));
        assertFalse(classMethods.contains(m2));

        //Throw excpetion for name that is blank or has space.
        assertThrows(IllegalArgumentException.class, () -> {
            store.renameMethod("Test", "int", "testMethod", params, "private", " ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            store.renameMethod("Test", "int", "testMethod", params, "private", "a space");
        });
    }

    @Test
    public void testChangeMethodType()
    {
        //create a store.
        Store store = new Store();
        
        //add a test class to store.
        store.addClass("Test");
        
        //Create an ArrayList of Parameters for adding a method.
        ArrayList<String> params = new ArrayList<String>();
        params.add("pType pName");
        ArrayList<Parameter> params2 = new ArrayList<Parameter>();
        params2.add(new Parameter("pType", "pName"));
        
        //add a method to test class.
        store.addMethod("Test", "int", "testMethod", params, "private");
        
        //change the return type of testMethod.
        store.changeMethodType("Test", "int", "testMethod", params, "private", "String");
        
        //get a set of methods from the test class.
        Set<Method> classMethods = store.findClass("Test").getMethods();
        
        //made a method that should be the updated method.
        Method m = new Method("String", "testMethod", params2, "private");
        
        //check to see if the return type of the method has changed. 
        assertTrue(classMethods.contains(m));

        //Should throw an exception for empty type name or name with space.
        assertThrows(IllegalArgumentException.class, () -> {
            store.changeMethodType("Test", "String", "testMethod", params, "private", " ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            store.changeMethodType("Test", "String", "testMethod", params, "private", "a space");
        });
    }

    @Test
    public void testChangeMethodAccess()
    {
        //create a store.
        Store store = new Store();
        
        //add a test class to store.
        store.addClass("Test");
        
        //Create an ArrayList of Parameters for adding a method.
        ArrayList<String> params = new ArrayList<String>();
        params.add("pType pName");
        ArrayList<Parameter> params2 = new ArrayList<Parameter>();
        params2.add(new Parameter("pType", "pName"));
        
        //add a method to test class.
        store.addMethod("Test", "int", "testMethod", params, "private");
        
        //change the return type of testMethod.
        store.changeMethodAccess("Test", "int", "testMethod", params, "private", "protected");
        
        //get a set of methods from the test class.
        Set<Method> classMethods = store.findClass("Test").getMethods();
        
        //made a method that should be the updated method.
        Method m = new Method("int", "testMethod", params2, "protected");
        
        //check to see if the access type of the method has changed. 
        assertTrue(classMethods.contains(m));

    }

    @Test
    public void testAddParam()
    {
        //create a store.
        Store store = new Store();
        store.addClass("Test");
        //Create an ArrayList of Parameters for adding a method.
        ArrayList<String> params = new ArrayList<String>();
        ArrayList<Parameter> params2 = new ArrayList<Parameter>();
        params2.add(new Parameter("String", "str"));
        
        //Add a method
        store.addMethod("Test", "int", "testMethod", params, "private");

        //add a param to that method
        store.addParam("Test", "int", "testMethod", params, "private", "String", "str");

        //Test that the param was added
        Method m = store.findMethod("Test", "int", "testMethod", params2, "private");

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
        ArrayList<String> params = new ArrayList<String>();
        params.add("int param");

        //add method to test class.
        store.addMethod("Test", "int", "testMethod", params, "protected");

        //add parameter to test method.
        store.addParam("Test", "int", "testMethod", params, "protected", "int", "param");

        //delete parameter from test method.
        store.deleteParam("Test", "int", "testMethod", params, "protected", "int", "param");

        //find method we deleted from.
        ArrayList<Parameter> params3 = new ArrayList<Parameter>();
        Method m = store.findMethod("Test", "int", "testMethod", params3, "protected");

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
        //add a test class to store.
        store.addClass("Test");
        store.addClass("Test1");
        //create ArrayList of parameters for creating a method.
        ArrayList<String> params = new ArrayList<String>();
        //add method to test class.
        store.addMethod("Test", "int", "testMethod", params, "protected");
        store.addMethod("Test1", "String", "method", params, "protected");

        //Shouldn't allow relationship between class and itself.
        assertThrows(IllegalArgumentException.class, () -> {
            store.addRelationship("Test", "Test", RelationshipType.REALIZATION);
        });
        store.addRelationship("Test", "Test1", RelationshipType.REALIZATION);

        //Should be rtelationships in Test's to, and Test1's from.
        assertTrue(store.findClass("Test").getRelationshipsToOther().containsKey("Test1"));
        assertTrue(store.findClass("Test1").getRelationshipsFromOther().containsKey("Test"));

        //Adding relationship when a class doesn't exist should return false.
        assertFalse(store.addRelationship("Test", "NoExist", RelationshipType.REALIZATION));
        assertFalse(store.addRelationship("NoExist", "Test", RelationshipType.REALIZATION));
    }

    @Test
    public void testDeleteRelationship()
    {
        //create store.
        Store store = new Store();
        //add a test class to store.
        store.addClass("Test");
        store.addClass("Test1");
        //create ArrayList of parameters for creating a method.
        ArrayList<String> params = new ArrayList<String>();
        //add method to test class.
        store.addMethod("Test", "int", "testMethod", params, "protected");
        store.addMethod("Test1", "String", "method", params, "protected");
        //Should return false when there is no relationship to delete.
        //assertFalse(store.deleteRelationship("Test", "Test1"));
        store.addRelationship("Test", "Test1", RelationshipType.REALIZATION);

        //Deleting relationship when a class doesn't exist should return false.
        assertFalse(store.deleteRelationship("Test", "NoExist"));
        assertFalse(store.deleteRelationship("NoExist", "Test"));

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
        store.addClass("Test1");
        store.addClass("Extra1");
        store.addClass("Extra2");
        //Add fields to the classes.
        store.addField("Test", "int", "num", "public");
        store.addField("Test1", "int", "number", "protected");
        //create ArrayList of parameters for creating a method.
        ArrayList<String> params = new ArrayList<String>();
        //add methods to classes.
        store.addMethod("Test", "int", "testMethod", params, "protected");
        store.addMethod("Test1", "int", "methodTest", params, "private");
        store.addRelationship("Test", "Test1", RelationshipType.GENERALIZATION);
        store.addRelationship("Extra1", "Test", RelationshipType.REALIZATION);
        store.addRelationship("Extra2", "Test", RelationshipType.REALIZATION);
        store.removeRelationships(store.findClass("Test"));
        //Relationship should no longer exist.
        assertTrue(store.findClass("Test").getRelationshipsToOther().size() == 0);
        assertTrue(store.findClass("Test1").getRelationshipsFromOther().size() == 0);
        assertTrue(store.findClass("Extra1").getRelationshipsToOther().size() == 0);
        assertTrue(store.findClass("Extra2").getRelationshipsToOther().size() == 0);
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
        ArrayList<String> params = new ArrayList<String>();
        ArrayList<Parameter> params2 = new ArrayList<Parameter>();
        Method aMethod = new Method("int", "testMethod", params2, "protected");
        store.addMethod("name", "int", "testMethod", params, "protected");
        //The store object should be able to return the method of a given name.
        assertEquals(aMethod, store.findMethod("name", "int", "testMethod", params2, "protected"));
        //Should return null when that class isn't found.
        assertEquals(null, store.findMethod("name", "int", "TestMethod", params2, "private"));
    }

    
    @Test
    public void testRemoveMethodByString()
    {
        Store store = new Store();
        store.addClass("name");
        ArrayList<String> params = new ArrayList<String>();
        params.add("type name");
        ArrayList<Parameter> realParams = new ArrayList<Parameter>();
        realParams.add(new Parameter("type", "name"));

        store.addMethod("name", "int", "num", params, "private");

        //Non-existen method => false.
        assertFalse(store.removeMethodByString(store.findClass("name").getMethods(), "Method: + wrong wrongAgain", "name"));

        //Should return true if method deleted.
        assertTrue(store.removeMethodByString(store.findClass("name").getMethods(), store.findMethod("name", "int", "num", realParams, "private").toString(), "name"));
        //Method should no longer exist.
        assertEquals(null, store.findMethod("name", "int", "num", realParams, "private"));
    }

    @Test
    public void testRenameMethodByString()
    {
        Store store = new Store();
        store.addClass("name");
        ArrayList<String> params = new ArrayList<String>();
        params.add("type name");
        ArrayList<Parameter> realParams = new ArrayList<Parameter>();
        realParams.add(new Parameter("type", "name"));
        store.addMethod("name", "int", "num", params, "private");

        //Non-existen method => false.
        assertFalse(store.renameMethodByString(store.findClass("name").getMethods(), "Method: + wrong wrongAgain", "name", "newName", "private"));

        //Should return true if method renamed.
        assertEquals(true,store.renameMethodByString(store.findClass("name").getMethods(), store.findMethod("name", "int", "num", realParams, "private").toString(), "name", "newName", "private"));
        assertEquals(null, store.findMethod("name", "int", "num", realParams, "private"));
        assertEquals("- int newName ( type name )",store.findMethod("name", "int", "newName", realParams, "private").toString());
    }

    @Test
    public void testGetSetClassStore()
    {
        //Create store.
        Store store = new Store();
        //Add two classes to store. 
        store.addClass("name");
        store.addClass("name2");
        
        //Create ArrayList of classes.
        ArrayList<Class> classAL = new ArrayList<Class>();
        //Add class to classAL.
        Class testClass = new Class("test");
        classAL.add(testClass);

        store.setClassStore(classAL);
        //Store's classStore should have been changed.         
        assertTrue(store.getClassStore().equals(classAL));
        
    }

    @Test
    public void testGetMethodList()
    {
        //Create store.
        Store store = new Store(); 

        //Add class to store.
        store.addClass("name");

        //Add methods to class in store.
        ArrayList<String> params = new ArrayList<String>();
        store.addMethod("name", "int", "m1", params, "private");
        store.addMethod("name", "int", "m2", params, "private");

        //Get methods in store.
        ArrayList<Parameter> findParams = new ArrayList<Parameter>();
        Method m1 = store.findMethod("name", "int", "m1", findParams, "private");
        Method m2 = store.findMethod("name", "int", "m2", findParams, "private");

        //Get class in store.
        Class test = store.findClass("name");
        //Get class's Set of Methods. 
        Set<Method> methods = test.getMethods();
                
        //Test ArrayList of Method toStrings.
        ArrayList<String> testM = new ArrayList<String>();
        testM.add(m1.toString());
        testM.add(m2.toString());

        //store Method List equals testM.
        assertTrue(store.getMethodList(methods).equals(testM));

    }

    @Test
    public void testGetMethodParamString ()
    {
        Store s = new Store();
        s.addClass("test");
        ArrayList<String> params = new ArrayList<String>();
        params.add("int num");
        ArrayList<Parameter> p = new ArrayList<Parameter>();
        p.add(new Parameter ("int", "num"));
        s.addMethod("test", "int", "name", params, "public");
        Method m = s.findMethod("test", "int", "name", p, "public");
        ArrayList<String> paramList = s.getMethodParamString("test", m.toString());

        //The moethod should return an ArrayList with the correct param.
        assertEquals(paramList, params);

        assertFalse(params.equals(s.getMethodParamString("test", "Oops the wrnmg string")));

    }

    @Test
    public void testClone()
    {
        Store s = new Store();
        s.addClass("Test1");
        s.addClass("Test2");
        s.setCurrentLoadedFile(new File("TestFile"));
        Store testStore = (Store)s.clone();

        //Make sure clone and original have same content.
        assertEquals(s.getClassList(), testStore.getClassList());
        assertEquals(s.getCurrentLoadedFile(), testStore.getCurrentLoadedFile());
    }

    @Test
    public void testGetCurrentLoadedFile()
    {
        Store s = new Store();
        View v = new CommandlineView();
        Controller c = new Controller(s, v);
        try
        {
            c.save("test");
        }
        catch(Exception e)
        {

        }
        try
        {
            c.load("test");
        }
        catch(Exception e)
        {

        }
        File file = s.getCurrentLoadedFile();
        assertEquals(file.getName(), "test.json");
    }

    @Test
    public void testStringOfClasses()
    {
        //Test we get equal strings for separate stores (toString for each class tested in class).
        Store s = new Store();
        s.addClass("Test1");
        s.addField("Test1", "fType", "fName", "public");
        s.addClass("Test2");
        s.addMethod("Test2", "mType", "mName", new ArrayList<String>(), "private");
        s.addRelationship("Test1", "Test2", RelationshipType.AGGREGATION);

        Store s2 = new Store();
        s2.addClass("Test1");
        s2.addField("Test1", "fType", "fName", "public");
        s2.addClass("Test2");
        s2.addMethod("Test2", "mType", "mName", new ArrayList<String>(), "private");
        s2.addRelationship("Test1", "Test2", RelationshipType.AGGREGATION);

        assertEquals(s.stringOfClasses(), s2.stringOfClasses());
    }

    @Test
    public void testEquals()
    {
        Store s = new Store();
        s.addClass("Test1");
        s.addField("Test1", "fType", "fName", "public");
        s.addMethod("Test1", "mType", "mName", new ArrayList<String>(), "private");

        Store s2 = new Store();
        s2.addClass("Test1");
        s2.addField("Test1", "fType", "fName", "public");
        s2.addMethod("Test1", "mType", "mName", new ArrayList<String>(), "private");

        //The above two stores should be equal.
        assertTrue(s.equals(s2));
               
        //Stores should equal themselves.
        assertTrue(s.equals(s));

        //Not equal when a store is null.
        assertFalse(s.equals(null));
        
        Store s3 = new Store();
        s3.addClass("Test1");
        s3.addField("Test1", "fType", "fName", "public");
        s3.addMethod("Test1", "mType", "mName", new ArrayList<String>(), "public");
        //Classes are not equal.
        assertFalse(s.equals(s3));
        assertFalse(s3.equals(s));

        s.setCurrentLoadedFile(new File("test.json"));

        //If one of the files is null then not equal.
        assertFalse(s.equals(s2));
        assertFalse(s2.equals(s));
        
        s2.setCurrentLoadedFile(new File("WRONG.json"));

        //Different files should return false.
        assertFalse(s.equals(s2));
        assertFalse(s2.equals(s));

        s2.setCurrentLoadedFile(new File("test.json"));

        //Same files means true.
        assertTrue(s.equals(s2));

        //Different type means false.
        assertFalse(s.equals("DUMBSTRIUNG"));

    }

}