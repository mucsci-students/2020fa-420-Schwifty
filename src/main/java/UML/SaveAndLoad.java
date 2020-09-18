
/*
    Author: Chris
    Date: 09/17/2020
    Purpose: This is a static class with static methods. Handles the saving and 
    loading of user created classes using simple JSON. 
*/
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class SaveAndLoad
{
    public static void save(String fileName, ArrayList<Class> classesToSave)
    {
        //Get the deails abou
        JSONObject classDetails = new JSONObject();

        //Build the JSONObject from the elements of a class
        for(Class aClass : classesToSave)
        {
            //Get the current class name and put it in the JSONObject
            String className = aClass.getName();
            classDetails.put("ClassName", className);
            Set<Attribute> attributes = aClass.getAttributes();
            for (Attribute attr : attributes) 
            {
                //Get the current attribute and put in the JSONObject
                String type = attr.getType();
                String attrName = attr.getName();
                //TODO: Find a better way to do this. 
                classDetails.put("Attributes", type + " " + attrName);                
            }
            Map<String,RelationshipType> relationToOthers = aClass.getRelationshipsToOther();  
            for (Map.Entry<String, RelationshipType> relation : relationToOthers.entrySet()) 
            {
                classDetails.put("RelationTo",relation.getKey());
                classDetails.put("RelationType",relation.getValue());
            }

            Map<String,RelationshipType> relationFromOthers = aClass.getRelationshipsFromOther();
            for (Map.Entry<String, RelationshipType> relation : relationFromOthers.entrySet()) 
            {
                classDetails.put("RelationFrom",relation.getKey());
                classDetails.put("RelationType",relation.getValue());
            }
            //Attempt to write the json data to passed in file name. IOExcetion on failure. 
            try (FileWriter file = new FileWriter(fileName))
            {
                file.write(classDetails.toJSONString());
                file.flush();
                file.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    

    public static void load()
    {

    }
}