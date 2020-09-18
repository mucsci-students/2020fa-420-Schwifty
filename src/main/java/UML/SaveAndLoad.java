
/*
    Author: Chris
    Date: 09/17/2020
    Purpose: This is a static class with static methods. Handles the saving and 
    loading of user created classes using simple JSON. 
*/
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class SaveAndLoad
{
    public static void save(String fileName, ArrayList<Class> classesToSave)
    {
        //Store an arraylist of JSONObjects to be written to the file
        ArrayList<JSONObject> classesToBeSaved = new ArrayList<JSONObject>();
        //Build the JSONObject from the elements of a class
        for(Class aClass : classesToSave)
        {
            //Get the deails about the class
            JSONObject classDetails = new JSONObject();
            Map<String,RelationshipType> relationToOthers = aClass.getRelationshipsToOther();
            JSONArray relationsToToBeAdded = new JSONArray();
            for (Map.Entry<String, RelationshipType> relation : relationToOthers.entrySet()) 
            {
                String relationship = relation.getKey() + " " + relation.getValue();
                relationsToToBeAdded.add(relationship);
            }
            //Add the JSONArray to the JSONObject
            classDetails.put("RelationshipToOthers",relationsToToBeAdded);

            Map<String,RelationshipType> relationFromOthers = aClass.getRelationshipsFromOther();
            JSONArray relationsFromToBeAdded = new JSONArray();
            for (Map.Entry<String, RelationshipType> relation : relationFromOthers.entrySet()) 
            {
                String relationship = relation.getKey() + " " + relation.getValue();
                relationsFromToBeAdded.add(relationship);
            }
            //Add the JSONArray to the JSONObject
            classDetails.put("RelationshipFromOthers",relationsFromToBeAdded);

            //Get the current class name and put it in the JSONObject
            String className = aClass.getName();
            classDetails.put("ClassName", className);
                        
            Set<Attribute> attributes = aClass.getAttributes();
            //Create a JSONArray of the attributes to be stored
            JSONArray attributesToBeAdded = new JSONArray();
            for (Attribute attr : attributes) 
            {
                //Get the current attribute and put in the JSONObject
                String nameAndType = attr.getType() + " " + attr.getName();
                attributesToBeAdded.add(nameAndType);
            }
            //Add the JSONArray to the JSONObject
            classDetails.put("Attributes", attributesToBeAdded);
            classesToBeSaved.add(classDetails);
        }
        //Attempt to write the json data to passed in file name. IOExcetion on failure. 
        try (FileWriter fw = new FileWriter(fileName))
        {
            BufferedWriter bw = new BufferedWriter(fw);
            for (JSONObject obj : classesToBeSaved) 
            {  
                bw.write(obj.toJSONString());
                bw.write("\n");
            }
            bw.close();
            fw.flush();
            fw.close();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    

    public static void load()
    {

    }
}