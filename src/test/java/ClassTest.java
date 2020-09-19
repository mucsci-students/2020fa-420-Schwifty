import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassTest {

    @Test
    public void testGetName() 
    {
        Class test = new Class("Test");
        assertEquals("Test", test.getName());
    }

    @Test
    public void testBlankName() 
    {
        Class test = new Class("");
        assertEquals("", test.getName());
        //Change if not allowing user to enter blank class name
    }

    @Test
    public void testGetAttributes() 
    {
        Class test = new Class("Test");
        test.addAttribute("attribute", "int");
        Set set = new HashSet<Attribute>();
        set.add(new Attribute("attribute", "int"));
        assertEquals(set, test.getAttributes());
    }

    @Test
    public void testBlankAttr() 
    {
        Class test = new Class("Test");
        test.addAttribute("", "");
        Set set = new HashSet<Attribute>();
        set.add(new Attribute("", ""));
        assertEquals(set, test.getAttributes());
        //Change if not allowing user to enter blank attribute
    }
        
        

    @Test
    public void testGetRelationshipsToOther() 
    {
        Class class1 = new Class("class1");
        Class class2 = new Class("class2");
        Class.addRelationship(class1, class2, RelationshipType.ASSOCIATION);
        Map map = new HashMap<String, RelationshipType>();
        map.put("class2", RelationshipType.ASSOCIATION);
        assertEquals(map, class1.getRelationshipsToOther());
    }

    @Test
    public void testGetRelationshipsFromOther() 
    {
        Class class1 = new Class("class1");
        Class class2 = new Class("class2");
        Class.addRelationship(class1, class2, RelationshipType.ASSOCIATION);
        Map map = new HashMap<String, RelationshipType>();
        map.put("class1", RelationshipType.ASSOCIATION);
        assertEquals(map, class2.getRelationshipsFromOther());
    }

    @Test
    public void testSetName() 
    {
        Class test = new Class("name");
        test.setName("newName");
        assertEquals("newName", test.getName());
        //Throw exception for setting name to white space or empty?
        Class empty = new Class("empty");
        //Expect exception
        assertThrows(IllegalArgumentException.class, () -> {
            empty.setName("");
        });
    }
    
    @Test
    public void testAddAttribute() 
    {
        Class test = new Class("name");
        test.addAttribute("int", "att");
        test.addAttribute("String", "att2");
        assertTrue(test.getAttributes().contains(new Attribute("att", "int")));
        assertTrue(test.getAttributes().contains(new Attribute("att2", "String")));
        //Don't allow adding empty attribute (should do this in attribute to not allow creating or setting)
        //Expect exception when calling test.addAttribute("", "  ")
        assertThrows(IllegalArgumentException.class, () -> {
            test.addAttribute("", "   ");
        });
    }
    

    @Test
    public void testDeleteAttribute() 
    {
        Class test = new Class("name");
        //When attribute doesn't exist
        assertFalse(test.deleteAttribute("name"));
        test.addAttribute("int", "att");
        test.addAttribute("String", "att2");
        assertTrue(test.deleteAttribute("att"));
        assertFalse(test.getAttributes().contains(new Attribute("att", "int")));
        assertTrue(test.getAttributes().contains(new Attribute("att2", "String")));
    }

    @Test
    public void testRenameAttribute() 
    {
        Class test = new Class("name");
        test.addAttribute("int", "att");
        test.renameAttribute("att", "newAtt");
        assertTrue(test.getAttributes().contains(new Attribute("newAtt", "int")));
        //Name that doesn't exist
        assertFalse(test.renameAttribute("att", "att2"));
        //Rename to empty ecpect exception
        assertThrows(IllegalArgumentException.class, () -> {
            test.renameAttribute("newAtt", " ");
        });
    }

    @Test
    public void testAddRelationshipToOther() 
    {
        Class test = new Class("name");
        Class test2 = new Class("name2");
        Map map = new HashMap<String, RelationshipType>();
        Map map2 = new HashMap<String, RelationshipType>();
        test.addRelationshipToOther(RelationshipType.ASSOCIATION, test2);
        map.put("name2", RelationshipType.ASSOCIATION);
        map2.put("name", RelationshipType.ASSOCIATION);
        assertEquals(map, test.getRelationshipsToOther());
        assertEquals(map2, test2.getRelationshipsFromOther());
        assertTrue(test.getRelationshipsFromOther().isEmpty());
        assertTrue(test2.getRelationshipsToOther().isEmpty());
        //Add case for adding one to the same class
    }

    @Test
    public void testAddRelationshipFromOther() 
    {
        Class test = new Class("name");
        Class test2 = new Class("name2");
        Map map = new HashMap<String, RelationshipType>();
        Map map2 = new HashMap<String, RelationshipType>();
        test.addRelationshipFromOther(RelationshipType.ASSOCIATION, test2);
        map.put("name2", RelationshipType.ASSOCIATION);
        map2.put("name", RelationshipType.ASSOCIATION);
        assertEquals(map, test.getRelationshipsFromOther());
        assertEquals(map2, test2.getRelationshipsToOther());
        assertTrue(test.getRelationshipsToOther().isEmpty());
        assertTrue(test2.getRelationshipsFromOther().isEmpty());
        //Add case for adding one to the same class
    }

    //Replace with versions of to and from
    @Test
    public void testDeleteRelationship() 
    {
        Class test1 = new Class("test");
        Class test2 = new Class("test2");
        Class.addRelationship(test1, test2, RelationshipType.ASSOCIATION);
        Map map = new HashMap<String, RelationshipType>();
        map.put("test2", RelationshipType.ASSOCIATION);
        //Line below
        map.remove("class2");
        Class.deleteRelationship(test, test2, ASSOCIATION);
        assertEquals(map, class2.getRelationshipsFromOther());
    }
    
    @Test
    public void testDeleteRelationshipToOther() 
    {
        Class test = new Class("name");
        Class test2 = new Class("name2");
        assertFalse(test.deleteRelationshipToOther(RelationshipType.ASSOCIATION, test2));
        test.addRelationshipToOther(RelationshipType.ASSOCIATION, test2);
        test.deleteRelationshipToOther();
        assertTrue(test.getRelationshipsToOther().isEmpty());
        assertTrue(test2.getRelationshipsFromOther().isEmpty());
    }


    @Test
     public void testDeleteRelationshipFromOther() 
    {
        Class test = new Class("name");
        Class test2 = new Class("name2");
        assertFalse(test.deleteRelationshipFromOther(RelationshipType.ASSOCIATION, test2));
        test.addRelationshipFromOther(RelationshipType.ASSOCIATION, test2);
        test.deleteRelationshipFromOther();
        assertTrue(test.getRelationshipsFromOther().isEmpty());
        assertTrue(test2.getRelationshipsToOther().isEmpty());
    }


    @Test
    public void testEquals() 
    {
        Class test1 = new Class("name");
        test1.addAttribute("attribute", "type");
        Class test2 = new Class("name");
        test2.addAttribute("attribute", "type");
        Class extra = new Class("extra");
        Class.addRelationship(test1, extra, RelationshipType.ASSOCIATION);
        Class.addRelationship(test2, extra, RelationshipType.ASSOCIATION);
        Class extra1 = new Class("extra1");
        Class.addRelationship(extra1, test1, RelationshipType.AGGREGATION);
        Class.addRelationship(extra1, test2, RelationshipType.AGGREGATION);
        assertTrue(test1.equals(test2));
    }

    @Test
    public void testToString() 
    {
        Class test1 = new Class("name");
        test1.addAttribute("attribute", "type");
        Class extra = new Class("extra");
        Class extra2 = new Class("extra2");
        Class.addRelationship(test1, extra, RelationshipType.ASSOCIATION);
        Class.addRelationship(extra2, test1, RelationshipType.ASSOCIATION);
        assertEquals("Class Name: name\n------------------------------\nAttribute Names: attribute : type\n"
        + "------------------------------\nRelationships To Others: {extra=ASSOCIATION}\n"
        + "Relationships From Others: {extra2=ASSOCIATION}\n",test1.toString());
    }


}