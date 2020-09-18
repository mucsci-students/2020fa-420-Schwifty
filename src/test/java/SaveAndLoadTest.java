import java.util.ArrayList;
import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class SaveAndLoadTest
{
    Class testClass = new Class("Test");

    @Test
    /**
     * Test file: JSONTest.json
     * Output to file: {"ClassName":"TestTwo","Attributes":"type name"}
     */
    public void testSave()
    {
        testClass.addAttribute("int", "num");

        Class testClassTwo = new Class("TestTwo");
        testClass.addAttribute("type", "name");

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

    }
}