/*
    Author: Chris, Dominic, Tyler, Cory and Drew
    Date: 09/17/2020
    Purpose: Test the saving and loading of a class.
    * {Classes: [
    *      {
    *          ClassName: name
    *          fields[]
    *          relationTo []
    *          relationFrom []
    *      }
    *      {
    *          ClassName: name
    *          fields[]
    *          relationTo []
    *          relationFrom []
    *      }
    * ]} 
*/
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
     * {"Classes":[{"RelationshipToOthers":["ASSOCIATION TestTwo"],"RelationshipFromOthers":["ASSOCIATION TestTwo"],"ClassName":"Test","Fields":["int num"]},
     * {"RelationshipToOthers":["ASSOCIATION Test"],"RelationshipFromOthers":["ASSOCIATION Test"],"ClassName":"TestTwo","Fields":["string aStr"]}]}
     */
    public void testSave()
    {
        Class testClass = new Class("Test");
        testClass.addField("int", "num");

        Class testClassTwo = new Class("TestTwo");
        testClassTwo.addField("string", "aStr");
        testClass.addRelationshipToOther(RelationshipType.ASSOCIATION, testClassTwo);
        testClass.addRelationshipFromOther(RelationshipType.ASSOCIATION, testClassTwo);
        testClassTwo.addRelationshipToOther(RelationshipType.ASSOCIATION, testClass);
        testClassTwo.addRelationshipFromOther(RelationshipType.ASSOCIATION, testClass);

        ArrayList<Class> arrList = new ArrayList<Class>();
        arrList.add(testClass);
        arrList.add(testClassTwo);
        File testFile = new File("JSONTest.json");
        SaveAndLoad.save(testFile, arrList);
        assertTrue(testFile.exists());
    }

    //Test that the JSON data being saved correctly
    /**
     * Expected: {"Classes":[{"RelationshipToOthers":["ASSOCIATION TestTwo"],"RelationshipFromOthers":["ASSOCIATION TestTwo"],"ClassName":"Test","Fields":["int num"]},
     * {"RelationshipToOthers":["ASSOCIATION Test"],"RelationshipFromOthers":["ASSOCIATION Test"],"ClassName":"TestTwo","Fields":["string aStr"]}]}
     */
    @Test
    public void testLoadedData()
    {
        ArrayList<Class> classStore = new ArrayList<Class>();
        File testFile = new File("JSONTest.json");
        SaveAndLoad.load(testFile, classStore);
        String[] classNameTests = {"Test","TestTwo"};

        //Create the test fields
        Field attrTest = new Field("int", "num");
        Field attrTestTwo = new Field("string", "aStr");
        Field[] attrTestArray = {attrTest, attrTestTwo};
        String[] relationName = {"TestTwo", "Test"};

        RelationshipType[] rType = {RelationshipType.ASSOCIATION, RelationshipType.ASSOCIATION};
        int counter = 0;
        for(Class aClass : classStore)
        {
            assertEquals(classNameTests[counter], aClass.getName());
            Set<Field> classFields = aClass.getFields();
            for(Field attr : classFields)
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