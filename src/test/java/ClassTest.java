import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    public void testGetAttributes() 
    {
        Class test = new Class("Test");
        test.addAttribute("attribute", "int");
        Set set = new HashSet<Attribute>();
        set.add(new Attribute("attribute", "int"));
        assertEquals(set, test.getAttributes());
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
        
    }
    
    @Test
    public void testAddAttribute() 
    {

    }

    @Test
    public void testDeleteAttribute() 
    {

    }

    @Test
    public void testRenameAttribute() 
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