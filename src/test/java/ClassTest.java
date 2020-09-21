/*
    Author: Chris, Dominic, Tyler, Cory and Drew
    Date: 09/10/2020
    Purpose: Runs tests on the public methods in the class class.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class ClassTest {

    @Test
    public void testGetName() 
    {
        Class test = new Class("Test");
        //The test class's name should equal "Test".
        assertEquals("Test", test.getName());
    }

    @Test
    public void testGetAttributes() 
    {
        Class test = new Class("Test");
        test.addAttribute("int", "attribute");
        //The class's call to get attributes is nonempty.
        assertTrue(!test.getAttributes().isEmpty());
        Attribute att = new Attribute("attribute", "int");
        //The class's call to get attributes should comtain the added attribute.
        assertTrue(test.getAttributes().contains(att));
    }

    @Test
    public void testGetRelationshipsFromOther() 
    {
        Class class1 = new Class("class1");
        Class class2 = new Class("class2");
        class1.addRelationshipFromOther(RelationshipType.ASSOCIATION, class2);
        Map map = new HashMap<String, RelationshipType>();
        map.put("class2", RelationshipType.ASSOCIATION);
        //Make sure expected map is the same as the actual returned map.
        assertEquals(map, class1.getRelationshipsFromOther());
    }

    @Test
    public void testGetRelationshipsToOther() 
    {
        Class class1 = new Class("class1");
        Class class2 = new Class("class2");
        class1.addRelationshipToOther(RelationshipType.ASSOCIATION, class2);
        Map map = new HashMap<String, RelationshipType>();
        map.put("class2", RelationshipType.ASSOCIATION);
        //Make sure expected map is the same as the actual returned map.
        assertEquals(map, class1.getRelationshipsToOther());
    }

    @Test
    public void testSetName() 
    {
        Class test = new Class("name");
        test.setName("newName");
        assertEquals("newName", test.getName());
        //Setting name to whitespace should thorw exception.
        Class empty = new Class("empty");
        assertThrows(IllegalArgumentException.class, () -> {
            empty.setName("");
        });
    }
    
    @Test
    public void testAddAttribute() 
    {
        Class test = new Class("aName");
        test.addAttribute("int", "att");
        test.addAttribute("String", "att2");
        Set<Attribute> testSet = test.getAttributes();
        Attribute attrTest = new Attribute("att", "int");
        Attribute attrTestTwo = new Attribute("att2", "String");
        //The class should contain the added attributes.
        assertTrue(test.getAttributes().contains(attrTest));
        assertTrue(test.getAttributes().contains(attrTestTwo));
        //Don't allow adding whitespace/empty strings.
        assertThrows(IllegalArgumentException.class, () -> {
            test.addAttribute("", "   ");
        });
    }
    

    @Test
    public void testDeleteAttribute() 
    {
        Class test = new Class("name");
        //When attribute doesn't exist.
        assertFalse(test.deleteAttribute("name"));
        test.addAttribute("int", "att");
        test.addAttribute("String", "att2");
        test.deleteAttribute("att");
        test.deleteAttribute("att2");
        //Set should be empty.
        assertTrue(test.getAttributes().isEmpty());
    }

    @Test
    public void testRenameAttribute() 
    {
        Class test = new Class("name");
        test.addAttribute("int", "att"); 
        test.renameAttribute("att", "newAtt");
        Attribute newAtt = new Attribute("newAtt", "int");
        assertTrue(test.getAttributes().contains(newAtt));
        //Renaming an attribute name that doesn't exist should return false.
        assertFalse(test.renameAttribute("att", "att2"));
        //Rename to empty or whitespace expects exception.
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
        //Should have the correctly oriented relationship in both classes.
        assertEquals(map, test.getRelationshipsToOther());
        assertEquals(map2, test2.getRelationshipsFromOther());
        //Should not have incorrectly oriented relationships in either class.
        assertTrue(test.getRelationshipsFromOther().isEmpty());
        assertTrue(test2.getRelationshipsToOther().isEmpty());
        //Don't allow replacing the key's value.
        assertFalse(test.addRelationshipToOther(RelationshipType.AGGREGATION, test2));
        

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
        //Should have the correctly oriented relationship in both classes.
        assertEquals(map, test.getRelationshipsFromOther());
        assertEquals(map2, test2.getRelationshipsToOther());
        //Should not have incorrectly oriented relationships in either class.
        assertTrue(test.getRelationshipsToOther().isEmpty());
        assertTrue(test2.getRelationshipsFromOther().isEmpty());
        //Don't allow replacing the key's value.
        assertFalse(test.addRelationshipFromOther(RelationshipType.AGGREGATION, test2));
        
    }

    @Test
    public void testDeleteRelationshipToOther() 
    {
        Class test = new Class("name");
        Class test2 = new Class("name2");
        //Deleting from empty should return false.
        assertFalse(test.deleteRelationshipToOther(RelationshipType.ASSOCIATION, test2));
        test.addRelationshipToOther(RelationshipType.ASSOCIATION, test2);
        test.deleteRelationshipToOther(RelationshipType.ASSOCIATION, test2);
        //Relationship should be gone from both classes.
        assertTrue(test.getRelationshipsToOther().isEmpty());
        assertTrue(test2.getRelationshipsFromOther().isEmpty());
    }

    @Test
     public void testDeleteRelationshipFromOther() 
    {
        Class test = new Class("name");
        Class test2 = new Class("name2");
        //Deleting from empty should return false.
        assertFalse(test.deleteRelationshipFromOther(RelationshipType.ASSOCIATION, test2));
        test.addRelationshipFromOther(RelationshipType.ASSOCIATION, test2);
        test.deleteRelationshipFromOther(RelationshipType.ASSOCIATION, test2);
        //Relationship should be gone from both classes
        assertTrue(test.getRelationshipsFromOther().isEmpty());
        assertTrue(test2.getRelationshipsToOther().isEmpty());
    }

    @Test
    public void testEquals() 
    {
        Class test1 = new Class("name");
        test1.addAttribute("int", "5");
        Class test2 = new Class("name");
        test2.addAttribute("int", "5");
        Class extra = new Class("extra");
        test1.addRelationshipToOther(RelationshipType.ASSOCIATION, extra);
        test2.addRelationshipToOther(RelationshipType.ASSOCIATION, extra);
        Class extra1 = new Class("extra1");
        test1.addRelationshipFromOther(RelationshipType.AGGREGATION, extra1);
        test2.addRelationshipFromOther(RelationshipType.AGGREGATION, extra1);
        //Tests that equals works for two classes that have had equal items added.
        assertTrue(test1.equals(test2));
    }

    @Test
    public void testToString() 
    {
        Class test1 = new Class("name");
        test1.addAttribute("attribute", "type");
        Class extra = new Class("extra");
        Class extra2 = new Class("extra2");
        Class test2 = test1;
        test1.addRelationshipToOther(RelationshipType.ASSOCIATION, extra);
        extra2.addRelationshipFromOther(RelationshipType.ASSOCIATION, test1);
        Set<Attribute> testSet = test1.getAttributes();
        String result = "";
        //Tests that the strings are equal.
        assertEquals(test1.toString(), test2.toString());
    }
}