import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Before;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.MockitoAnnotations;
import UML.views.*;
import UML.controllers.*;
import UML.model.*;

import java.util.ArrayList;

public class ControllerTest 
{
    
    @Spy
    private Store store = new Store();

    @Mock
    private View view;

    @InjectMocks private Controller controller;

    @Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testCreateClass()
    {
        controller.createClass("test");
        //Make sure correct class is in store.
        assertFalse((controller.getStore().getClassStore().isEmpty()));
        assertEquals(controller.getStore().getClassStore().get(0).getName(), "test");

        //Should not add class.
        controller.createClass("test");
        assertEquals(controller.getStore().getClassStore().size(), 1);

        //Should not add class if name is empty or contains space.
        controller.createClass("test space");
        controller.createClass("");
        assertEquals(controller.getStore().getClassStore().size(), 1);
    }

    @Test
    public void testDeleteClass()
    {
        controller.createClass("test");
        controller.deleteClass("test1");
        //Class should still exist.
        assertEquals(controller.getStore().getClassStore().size(), 1);

        controller.deleteClass("test");
        //Now class should be gone.
        assertEquals(controller.getStore().getClassStore().size(), 0);
    }

    @Test
    public void testRenameClass()
    {
        controller.createClass("test");
        controller.renameClass("test","newTest");
        //Name should have changed.
        assertEquals(controller.getStore().getClassStore().get(0).getName(), "newTest");
        controller.renameClass("newTest","name change");
        //Name should still remain the same
        assertEquals(controller.getStore().getClassStore().get(0).getName(), "newTest");

        controller.renameClass("newTest","");
        //Name should still remain the same
        assertEquals(controller.getStore().getClassStore().get(0).getName(), "newTest");
    }

    @Test
    public void testCreateField()
    {
        controller.createClass("test");
        controller.createField("wrongName", "type", "name", "public");
        //Test class should not have the field.
        assertFalse(controller.getStore().getClassStore().get(0).getFields().contains(new Field("type", "name", "public")));

        controller.createField("test", "type", "name", "public");
        //Test class should have the field.
        assertTrue(controller.getStore().getClassStore().get(0).getFields().contains(new Field("type", "name", "public")));

        //Spaces in names and blank names should not be allowed.
        controller.createField("test", "type space", "name", "public");
        controller.createField("test", "", "name", "public");
        controller.createField("test", "type", "name space", "public");
        controller.createField("test", "type", "", "public");
        //None of those fields should have been added.
        assertEquals(controller.getStore().getClassStore().get(0).getFields().size(), 1);
    }
    
    @Test
    public void testDeleteField()
    {
        controller.createClass("test");
        controller.createField("test", "type","name","private" );
        controller.deleteField("wrongClass", "name");
        //No field should be deleted
        assertTrue(controller.getStore().getClassStore().get(0).getFields().contains(new Field("type", "name", "private")));
        controller.deleteField("test", "name");
        assertEquals(controller.getStore().getClassStore().get(0).getFields().size(), 0);
    }

    @Test
    public void testRenameField()
    {
        controller.createClass("test");
        controller.createField("test", "type", "name", "public");
        
        //with the wrong class name.
        controller.renameField("wrongName", "name", "newName");
        assertTrue(controller.getStore().getClassStore().get(0).getFields().contains(new Field("type", "name", "public")));
        
        //Wrong field name.
        controller.renameField("test", "name1", "newName");
        assertTrue(controller.getStore().getClassStore().get(0).getFields().contains(new Field("type", "name", "public")));

        //Do the correct rename.
        controller.renameField("test", "name", "newName");
        assertTrue(controller.getStore().getClassStore().get(0).getFields().contains(new Field("type", "newName", "public")));

        //Spaces and empty Strings.a//Do the correct rename.
        controller.renameField("test", "name", "");
        controller.renameField("test", "name", "new name");
        assertTrue(controller.getStore().getClassStore().get(0).getFields().contains(new Field("type", "newName", "public")));
        
    }

    @Test
    public void testChangeFieldType()
    {
        controller.createClass("test");
        controller.createField("test", "type", "name", "private");

        //Wrong name, shouldn't change anything.
        controller.changeFieldType("wrongName", "name", "String");
        //Test class should have the field.
        assertTrue(controller.getStore().getClassStore().get(0).getFields().contains(new Field("type", "name", "private")));

        //Wrong name, shouldn't change anything.
        controller.changeFieldType("test", "name", "String");
        //Test class should have the field.
        assertTrue(controller.getStore().getClassStore().get(0).getFields().contains(new Field("String", "name", "private")));
        assertFalse(controller.getStore().getClassStore().get(0).getFields().contains(new Field("type", "name", "private")));

        //Nothing should chnage when changing to empty or type with spaces.
        controller.changeFieldType("wrongName", "name", "");
        controller.changeFieldType("wrongName", "name", "a String");
        assertTrue(controller.getStore().getClassStore().get(0).getFields().contains(new Field("String", "name", "private")));
    }

    @Test
    public void testChangeFieldAccess()
    {
        //changeFieldAccess(String className, String fieldName, String access)
        controller.createClass("test");
        controller.createField("test", "type", "name", "private");
        //Access type should not change
        controller.changeFieldAccess("wrongName", "name", "public");
        assertTrue(controller.getStore().getClassStore().get(0).getFields().contains(new Field("type", "name", "private")));
        //Change should now occur
        controller.changeFieldAccess("test", "name", "protected");
        assertTrue(controller.getStore().getClassStore().get(0).getFields().contains(new Field("type", "name", "protected")));
    }

    @Test
    public void testCreateMethod()
    {
        controller.createClass("test");
        ArrayList<String> params = new ArrayList<String>();
        ArrayList<Parameter> realParams = new ArrayList<Parameter>();
        realParams.add(new Parameter("pType", "pName"));
        params.add("pType pName");
        controller.createMethod("wrongName", "type", "name", params, "protected");
        //Wrong name, nothing should change.
        assertFalse(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "protected")));

        controller.createMethod("test", "type", "name", params, "protected");
        //Method should exist.
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "protected")));
        
        //Spaces or empty Strings nevr allowed.
        controller.createMethod("test", "type space", "name", params, "protected");
        controller.createMethod("test", "", "name", new ArrayList<String>(), "protected");
        controller.createMethod("test", "type", "name space", new ArrayList<String>(), "protected");
        controller.createMethod("test", "type", "", params, "protected");
        assertEquals(controller.getStore().getClassStore().get(0).getMethods().size(), 1);
    }

    @Test
    public void testDeleteMethod()
    {
        controller.createClass("test");
        ArrayList<String> params = new ArrayList<String>();
        params.add("pType pName");
        ArrayList<Parameter> realParams = new ArrayList<Parameter>();
        realParams.add(new Parameter("pType", "pName"));
        controller.createMethod("test", "type", "name", params, "protected");

        controller.deleteMethod("wrongName", "type", "name", params, "protected");
        //Wrong class name, change nothing.
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "protected")));

        controller.deleteMethod("test", "type", "name", params, "protected");
        //Should be deleted now
        assertFalse(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "protected")));
        assertEquals(controller.getStore().getClassStore().get(0).getMethods().size(), 0);
    }

    @Test
    public void testRenameMethod()
    {
        controller.createClass("test");
        ArrayList<String> params = new ArrayList<String>();
        params.add("pType pName");
        ArrayList<Parameter> realParams = new ArrayList<Parameter>();
        realParams.add(new Parameter("pType", "pName"));
        controller.createMethod("test", "type", "name", params, "protected");

        controller.renameMethod("wrongName", "type", "name", params, "protected", "newName");
        //Wrong class name, change nothing.
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "protected")));
        assertEquals(controller.getStore().getClassStore().get(0).getMethods().size(), 1);
        
        //Correct name change.
        controller.renameMethod("test", "type", "name", params, "protected", "newName");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "newName", realParams, "protected")));
        assertEquals(controller.getStore().getClassStore().get(0).getMethods().size(), 1);

        //Nothing should change when using wrong type, name, access, or params.
        controller.renameMethod("test", "wrongType", "name", params, "protected", "name2");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "newName", realParams, "protected")));

        controller.renameMethod("test", "type", "wrongName", params, "protected", "name3");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "newName", realParams, "protected")));

        controller.renameMethod("test", "type", "name", params, "public", "name4");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "newName", realParams, "protected")));

        controller.renameMethod("test", "type", "name", new ArrayList<String>(), "public", "name5");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "newName", realParams, "protected")));
    }
    
    @Test
    public void testChangeMethodType()
    {
        controller.createClass("test");
        ArrayList<String> params = new ArrayList<String>();
        params.add("pType pName");
        ArrayList<Parameter> realParams = new ArrayList<Parameter>();
        realParams.add(new Parameter("pType", "pName"));
        controller.createMethod("test", "type", "name", params, "protected");

        //Wrong class name, change nothing
        controller.changeMethodType("wrongName", "type", "name", params, "protected", "aType");
        assertFalse(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("atype", "name", realParams, "protected")));
        
        //Should change now.
        controller.changeMethodType("test", "type", "name", params, "protected", "newType");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("newType", "name", realParams, "protected")));

        //Nothing should change when using wrong type, name, access, or params.
        controller.changeMethodType("test", "wrongType", "name", params, "protected", "type2");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("newType", "name", realParams, "protected")));

        controller.changeMethodType("test", "type", "wrongName", params, "protected", "type3");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("newType", "name", realParams, "protected")));

        controller.changeMethodType("test", "type", "name", params, "public", "type4");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("newType", "name", realParams, "protected")));

        controller.changeMethodType("test", "type", "name", new ArrayList<String>(), "public", "type5");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("newType", "name", realParams, "protected")));
    }

    @Test
    public void testChangeMethodAccess()
    {
        controller.createClass("test");
        ArrayList<String> params = new ArrayList<String>();
        params.add("pType pName");
        ArrayList<Parameter> realParams = new ArrayList<Parameter>();
        realParams.add(new Parameter("pType", "pName"));
        controller.createMethod("test", "type", "name", params, "protected");

        //Wrong class name, change nothing
        controller.changeMethodAccess("wrongName", "type", "name", params, "protected", "public");
        assertFalse(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));
        
        //Should change now
        controller.changeMethodAccess("test", "type", "name", params, "protected", "public");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));

        //Nothing should change when using wrong type, name, access, or params.
        controller.changeMethodAccess("test", "wrongType", "name", params, "public", "private");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));

        controller.changeMethodAccess("test", "type", "wrongName", params, "public", "private");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));

        controller.changeMethodAccess("test", "type", "name", params, "protected", "private");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));

        controller.changeMethodAccess("test", "type", "name", new ArrayList<String>(), "public", "private");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));
    }

    @Test
    public void testAddParameter()
    {
        controller.createClass("test");
        
        ArrayList<String> params = new ArrayList<String>();
        params.add("pType pName");
        
        ArrayList<Parameter> realParams = new ArrayList<Parameter>();
        realParams.add(new Parameter("pType", "pName"));
        realParams.add(new Parameter("newType", "newName"));
        
        controller.createMethod("test", "type", "name", params, "public");

        //Wrong class name, shouldn't chnage anything.
        controller.addParameter("wrongName", "type", "name", params, "public", "newType", "newName");
        assertFalse(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));

        //Now the parameter should be added.
        controller.addParameter("test", "type", "name", params, "public", "newType", "newName");
        //assertEquals(controller.getStore().getClassStore().get(0).getMethods().contains(m), true);
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));


        ArrayList<String> params2 = new ArrayList<String>();
        params2.add("pType pName");
        params2.add("newType newName");
        
        //Nothing should change when the specified method is not correct.
        controller.addParameter("test", "wrongType", "name", params2, "public", "otherType", "otherName");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));

        controller.addParameter("test", "type", "newName", params2, "public", "otherType", "otherName");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));

        controller.addParameter("test", "type", "name", new ArrayList<String>(), "public", "otherType", "otherName");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));

        controller.addParameter("test", "wrongType", "name", params2, "private", "otherType", "otherName");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", realParams, "public")));
    }

    @Test
    public void testDeleteParameter()
    {
        controller.createClass("test");
        ArrayList<String> params = new ArrayList<String>();
        params.add("pType pName");
        ArrayList<Parameter> realParams = new ArrayList<Parameter>();
        realParams.add(new Parameter("pType", "pName"));
        
        controller.createMethod("test", "type", "name", params, "protected");    
        //Test if the param has been removed

        //Wrong class name.
        controller.deleteParameter("wrongName", "type", "name", params, "protected", "pType", "pName");
        assertFalse(controller.getStore().getClassStore().get(0).getMethods().contains(new Method ("type", "name", new ArrayList<Parameter>(), "protected")));

        //Should delete param.
        controller.deleteParameter("test", "type", "name", params, "protected", "pType", "pName");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method("type", "name", new ArrayList<Parameter>(), "protected")));
        
        //Add in a new set of params.
        controller.addParameter("test", "type", "name", new ArrayList<String>(), "protected", "newType", "newName");
        
        ArrayList<Parameter> realParams2 = new ArrayList<Parameter>();
        realParams2.add(new Parameter("newType", "newName"));
        ArrayList<String> params2 = new ArrayList<String>();
        params2.add("newType newName");

        //Other wrong parameters.
        controller.deleteParameter("test", "wrongType", "name", params2, "protected", "newType", "newName");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method ("type", "name", realParams2, "protected")));

        controller.deleteParameter("test", "type", "wrongName", params2, "protected", "newType", "newName");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method ("type", "name", realParams2, "protected")));

        controller.deleteParameter("test", "type", "name", new ArrayList<String>(), "protected", "newType", "newName");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method ("type", "name", realParams2, "protected")));

        controller.deleteParameter("test", "type", "name", params2, "private", "newType", "newName");
        assertTrue(controller.getStore().getClassStore().get(0).getMethods().contains(new Method ("type", "name", realParams2, "protected")));

    }

    @Test
    public void testAddRelationship()
    {
        controller.createClass("test");
        controller.createClass("test2");
        
        //test to see if classes can form relationships
        controller.addRelationship("test", "test2", RelationshipType.REALIZATION);  
        assertTrue(controller.getStore().getClassStore().get(0).getRelationshipsToOther().containsValue(RelationshipType.REALIZATION));
        
        //test to see if classes can have more than one relationship
        controller.createClass("test3");
        controller.addRelationship("test2", "test3", RelationshipType.GENERALIZATION);  
        assertTrue(controller.getStore().getClassStore().get(1).getRelationshipsToOther().containsValue(RelationshipType.GENERALIZATION));
    }

    @Test
    public void testDeleteRelationship()
    {
        controller.createClass("test");
        controller.createClass("test2");

        controller.addRelationship("test", "test2", RelationshipType.REALIZATION);
        controller.deleteRelationship("test","test2");
        
        assertFalse(controller.getStore().getClassStore().get(0).getRelationshipsToOther().containsValue(RelationshipType.REALIZATION));
    }
}
