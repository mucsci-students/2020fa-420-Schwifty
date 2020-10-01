package UML.model;
/*
    Author: Chris
    Date: 09/17/2020
    Purpose: This is a static class with static methods. Handles the saving and 
    loading of user created classes using simple JSON. 
    JSON files will be saved in the following format which is a JSONObject that is an array
    of jsonobjects representing classes and there values.
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
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.util.HashSet;
import java.util.Iterator;


public class SaveAndLoad
{
    //To save a file with proper format
    //A single JSONObject should be written to file..i.e the object should look like the following:
    /*
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
    public static void save(File fileName, ArrayList<Class> classesToSave)
    {
        JSONObject toBeSaved = new JSONObject();

        //Store an arraylist of JSONObjects to be written to the file
        ArrayList<JSONObject> classesToBeSaved = new ArrayList<JSONObject>();

        
        //Build the JSONObject from the elements of a class
        for(Class aClass : classesToSave)
        {
            //Get the deails about the class
            JSONObject classDetails = new JSONObject();

            //Get the current class name and put it in the JSONObject
            String className = aClass.getName();
            classDetails.put("ClassName", className);
            
                                    
            Set<Field> fields = aClass.getFields();
            //Create a JSONArray of the fields to be stored
            JSONArray fieldsToBeAdded = new JSONArray();

            for (Field attr : fields) 
            {
                //Get the current fields and put in the JSONObject
                String nameAndType = attr.getType() + " " + attr.getName();
                fieldsToBeAdded.add(nameAndType);
            }
            classDetails.put("Fields", fieldsToBeAdded);

            Map<String,RelationshipType> relationToOthers = aClass.getRelationshipsToOther();
            JSONArray relationsToToBeAdded = new JSONArray();

            for (Map.Entry<String, RelationshipType> relation : relationToOthers.entrySet()) 
            {
                String relationship = relation.getValue() + " " + relation.getKey();
                relationsToToBeAdded.add(relationship);
            }
            //Add the JSONArray to the JSONObject
            classDetails.put("RelationshipToOthers",relationsToToBeAdded);

            Map<String,RelationshipType> relationFromOthers = aClass.getRelationshipsFromOther();
            JSONArray relationsFromToBeAdded = new JSONArray();

            for (Map.Entry<String, RelationshipType> relation : relationFromOthers.entrySet()) 
            {
                String relationship = relation.getValue() + " " + relation.getKey();
                relationsFromToBeAdded.add(relationship);
            }
            //Add the JSONArray to the JSONObject
            classDetails.put("RelationshipFromOthers",relationsFromToBeAdded);

            //Add the JSONArray to the JSONObject
            
            classesToBeSaved.add(classDetails);
        }
        toBeSaved.put("Classes",classesToBeSaved);
        //Attempt to write the json data to passed in file name. IOExcetion on failure. 
        //Append the .json format to name
        
        String jsonFileName = fileName.getName();

        //ensures we are not adding it if we don't need to
        if(!jsonFileName.contains(".json"))
        {
            jsonFileName += ".json";
        }
        
        try (FileWriter fw = new FileWriter(jsonFileName))
        {
            fw.write(toBeSaved.toJSONString());
            fw.flush();
            fw.close();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Failed to save " + jsonFileName, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void load(File fileName, ArrayList<Class> classStore)
    {
        JSONParser parser = new JSONParser();
        
        try (FileReader fr = new FileReader(fileName.getName()))
        {
            //Get the json object from the parser
            JSONObject obj = (JSONObject)parser.parse(fr);
            //Get the classes array inside that json object
            JSONArray classes = (JSONArray)obj.get("Classes");

            for(Object jsonObject : classes)
            {
                //Get the current json object, lots of casting ahead.
                JSONObject jobct = (JSONObject)jsonObject;
                String className = (String)jobct.get("ClassName");
                //The class to be created
                Class aClass = new Class(className);
                classStore.add(aClass);
            }
            int index = 0;
            for (Object jsonObject : classes) 
            {
                JSONObject jobct = (JSONObject)jsonObject;
                Class aClass = classStore.get(index);
                JSONArray jsonAttr = (JSONArray)jobct.get("Fields");
                Iterator<String> it = jsonAttr.iterator();
                while(it.hasNext())
                {
                    String[] attr = it.next().split(" ");
                    aClass.addField(attr[0], attr[1]);
                }
                
                //Get the relationships to other class and add it to the correct class
                JSONArray jsonRelationToOthers = (JSONArray)jobct.get("RelationshipToOthers");
                it = jsonRelationToOthers.iterator();
                while(it.hasNext())
                {
                    String[] relationship = it.next().split(" ");
                    String className = relationship[1];
                    Class relatedClass = findClass(className, classStore);
                    aClass.addRelationshipToOther(RelationshipType.valueOf(relationship[0]), relatedClass);
                }

                //Get the relationships to other class and add it to the correct class
                JSONArray jsonRelationFromOthers = (JSONArray)jobct.get("RelationshipFromOthers");
                it = jsonRelationFromOthers.iterator();
                while(it.hasNext())
                {
                    String[] relationship = it.next().split(" ");
                    String className = relationship[1];
                    Class relatedClass = findClass(className, classStore);
                    aClass.addRelationshipFromOther(RelationshipType.valueOf(relationship[0]), relatedClass);
                }
                index++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "File " + fileName.getName() + " failed to load. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Could not parse JSON file! Please ensure you selected the correct file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
       /**
    * Finds an element in the storage and returns it. Returns null if nothing found.
    */
   private static Class findClass(String name, ArrayList<Class> classStore)
   {
       for (Class aClass : classStore) 
       {
           if(aClass.getName().equals(name))
           {
               return aClass;
           }
       }
       return null;
   }
}