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

import org.json.simple.parser.ParseException;
import org.junit.Test;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import UML.model.Class;
import UML.model.Field;
import UML.model.Method;
import UML.model.RelationshipType;
import UML.views.CommandlineView;
import UML.controllers.SaveAndLoad;
import UML.controllers.*;
import UML.model.*;
import UML.views.*;

public class SaveAndLoadTest {
    @Test
    /**
     * Test file: JSONTest.json Output to file:
     * {"Classes":[{"RelationshipToOthers":["ASSOCIATION
     * TestTwo"],"RelationshipFromOthers":["ASSOCIATION
     * TestTwo"],"ClassName":"Test","Fields":["int num"]},
     * {"RelationshipToOthers":["ASSOCIATION
     * Test"],"RelationshipFromOthers":["ASSOCIATION
     * Test"],"ClassName":"TestTwo","Fields":["string aStr"]}]}
     */
    public void testSave() {
        Store s = new Store();
        CommandlineView v = new CommandlineView();
        Controller c = new Controller(s, v);
        SaveAndLoad sl = new SaveAndLoad(s, v, c);

        Class testClass = new Class("Test");
        testClass.addField("int", "num");

        Class testClassTwo = new Class("TestTwo");
        testClassTwo.addField("string", "aStr");
        ArrayList<Parameter> params = new ArrayList();
        params.add(new Parameter("int","num"));

        testClass.addMethod("void", "testMethod", params);
        testClassTwo.addMethod("void", "testMethod", params);

        testClass.addRelationshipToOther(RelationshipType.REALIZATION, testClassTwo);

        ArrayList<Class> arrList = new ArrayList<Class>();
        arrList.add(testClass);
        arrList.add(testClassTwo);
        s.setClassStore(arrList);
        try {
            File testFile = sl.save("JSONTest.json");
            assertTrue(testFile.exists());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
    }

    // Test that the JSON data being saved correctly
    /**
     * Expected: {"Classes":[{"RelationshipToOthers":["ASSOCIATION
     * TestTwo"],"RelationshipFromOthers":["ASSOCIATION
     * TestTwo"],"ClassName":"Test","Fields":["int num"]},
     * {"RelationshipToOthers":["ASSOCIATION
     * Test"],"RelationshipFromOthers":["ASSOCIATION
     * Test"],"ClassName":"TestTwo","Fields":["string aStr"]}]}
     */
    @Test
    public void testLoadedData() {
        Store s = new Store();
        CommandlineView v = new CommandlineView();
        Controller c = new Controller(s, v);
        SaveAndLoad sl = new SaveAndLoad(s, v, c);

        try {
            sl.load("JSONTest.json");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ArrayList<Class> classStore = s.getClassStore();
        String[] classNameTests = {"Test","TestTwo"};
        String[] relatedClasses = {"TestTwo",""};
        //Create the test fields
        Field attrTest = new Field("int", "num");
        Field attrTestTwo = new Field("string", "aStr");
        Field[] attrTestArray = {attrTest, attrTestTwo};

        RelationshipType[] relationsTo = {RelationshipType.REALIZATION, null};
        RelationshipType[] relationsFrom = {null, RelationshipType.REALIZATION};

        int counter = 0;
        for(Class aClass : classStore)
        {
            assertEquals(classNameTests[counter], aClass.getName());
            Set<Field> classFields = aClass.getFields();
            for(Field attr : classFields)
            {
                assertEquals(attrTestArray[counter], attr);
            }

            Set<Method> methods = aClass.getMethods();

            ArrayList<Parameter> params = new ArrayList();
            params.add(new Parameter("int","num"));

            Method testMethod = new Method("void", "testMethod", params);
            for(Method m : methods)
            {
                assertEquals(testMethod, m);
            }

            Map<String, RelationshipType> relationToOthers = aClass.getRelationshipsToOther();

            Map<String, RelationshipType> relationFromOthers = aClass.getRelationshipsFromOther();

            for (Map.Entry<String, RelationshipType> relation : relationToOthers.entrySet()) 
            {
                String className = relation.getKey();
                RelationshipType type = relation.getValue();
                assertEquals(relatedClasses[counter], className);
                assertEquals(relationsTo[counter], type);
            }

            for (Map.Entry<String, RelationshipType> relation : relationFromOthers.entrySet()) 
            {
                String className = relation.getKey(); 
                RelationshipType type = relation.getValue();
                assertEquals(relatedClasses[counter], className);
                assertEquals(relationsFrom[counter], type);
            }
            counter++;
        }
    }
}