import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import org.junit.Test;
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
    public void testSavedData()
    {
        ArrayList<Class> classStore = new ArrayList<Class>();
        SaveAndLoad.load("JSONTest.json", classStore);
        String[] classNameTests = {"Test","TestTwo"};

        //Create the test attributes
        Attribute attrTest = new Attribute("num", "int");
        Attribute attrTestTwo = new Attribute("aStr", "string");
        Attribute[] attrTestArray = {attrTest, attrTestTwo};

        int counter = 0;
        for(Class aClass : classStore)
        {
            assertEquals(classNameTests[counter], aClass.getName());
            Set<Attribute> classAttributes = aClass.getAttributes();
            for(Attribute attr : classAttributes)
            {
                assertEquals(attrTestArray[counter], attr);
            }
            
            counter++;
        }
    }
}