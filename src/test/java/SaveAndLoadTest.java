import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import org.junit.Test;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SaveAndLoadTest
{

    @Test
    /**
     * Test file: JSONTest.json
     * Output to file: 
     * {"RelationshipToOthers":[],"RelationshipFromOthers":[],"ClassName":"Test","Attributes":["int num","type name"]}
     * {"RelationshipToOthers":[],"RelationshipFromOthers":[],"ClassName":"TestTwo","Attributes":[]}
     */
    public void testSave()
    {
        Class testClass = new Class("Test");
        testClass.addAttribute("int", "num");

        Class testClassTwo = new Class("TestTwo");
        testClassTwo.addAttribute("string", "aStr");
        Class.addRelationship(testClass, testClassTwo, RelationshipType.ASSOCIATION);


        ArrayList<Class> arrList = new ArrayList<Class>();
        arrList.add(testClass);
        arrList.add(testClassTwo);
        SaveAndLoad.save("JSONTest.json", arrList);
        File testFile = new File("JSONTest.json");
        assertTrue(testFile.exists());
    }

    //Test that the JSON data being saved correctly
    /**
     * Expected: {"ClassName":"TestTwo","Attributes":"type name"}
     */
    @Test
    public void testLoadedData()
    {
        ArrayList<Class> classStore = new ArrayList<Class>();
        SaveAndLoad.load("JSONTest.json", classStore);
        String[] classNameTests = {"Test","TestTwo"};

        //Create the test attributes
        Attribute attrTest = new Attribute("num", "int");
        Attribute attrTestTwo = new Attribute("aStr", "string");
        Attribute[] attrTestArray = {attrTest, attrTestTwo};
        String[] relationName = {"TestTwo", "Test"};
        RelationshipType[] rType = {RelationshipType.ASSOCIATION, RelationshipType.ASSOCIATION};
        int counter = 0;
        for(Class aClass : classStore)
        {
            assertEquals(classNameTests[counter], aClass.getName());
            Set<Attribute> classAttributes = aClass.getAttributes();
            for(Attribute attr : classAttributes)
            {
                assertEquals(attrTestArray[counter], attr);
            }

            Map<String, RelationshipType> relationToOthers = aClass.getRelationshipsToOther();

            Map<String, RelationshipType> relationFromOthers = aClass.getRelationshipsFromOther();

            for (Map.Entry<String, RelationshipType> relation : relationToOthers.entrySet()) 
            {
                String className = relation.getKey();
                RelationshipType type = relation.getValue();
                assertEquals(relationName[counter], className);
                assertEquals(rType[counter], type);
            }

            for (Map.Entry<String, RelationshipType> relation : relationFromOthers.entrySet()) 
            {
                String className = relation.getKey();
                RelationshipType type = relation.getValue();
                assertEquals(relationName[counter], className);
                assertEquals(rType[counter], type);
            }
            counter++;
        }
    }
}