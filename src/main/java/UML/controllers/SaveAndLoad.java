package UML.controllers;
/*
    Author: Chris, Tyler, Drew.
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import java.util.Iterator;

import UML.views.View;

import UML.model.Class;
import UML.model.Field;
import UML.model.Method;
import UML.model.Store;
import UML.model.RelationshipType;


public class SaveAndLoad
{

    private static Store store;
    
    private View view;

    private Controller controller;

    public SaveAndLoad(Store s, View v, Controller c)
    {
        this.store = s;
        this.view = v;
        this.controller = c;
    }

    //To save a file with proper format
    //A single JSONObject should be written to file..i.e the object should look like the following:
    /*
     * {Classes: [
     *      {
     *          ClassName: name
     *          fields[]
     *          methods[ type name [type name type name ...]]
     *          relationTo []
     *          relationFrom []
     *      }
     *      {
     *          ClassName: name
     *          fields[]
     *          methods[ type name [type name type name ...]]
     *          relationTo []
     *          relationFrom []
     *      }
     * ]} 
    */
    public File save(String fileName) throws IOException, FileNotFoundException
    {
        //the json file
        File jsonFile;
        //build the json object to be saved
        JSONObject toBeSaved = buildObjectToSave();
        //Write that to a file
        jsonFile = writeToFile(fileName, toBeSaved);
  
        return jsonFile;
    }

    public File load(String fileName) throws IOException, ParseException
    {
        File fileToLoad = new File(fileName);
        try
        {
            FileReader fr = new FileReader(fileToLoad);
            JSONParser parser = new JSONParser();
            //Get the json object from the parser
            JSONObject obj = (JSONObject)parser.parse(fr);
            

            //Get the classes array inside that json object
            JSONArray classes = (JSONArray)obj.get("Classes");

            //tell the main controller to create all the classes we need in the store
            Object[] className = classObjectsToStore(classes);
        
            int i = 0;
            for (Object jsonObject : classes) 
            {
                loadFields(jsonObject, (String)className[i]);
                loadMethods(jsonObject, (String)className[i]);
                loadRelationsTo(jsonObject, (String)className[i]);
                loadRelationsFrom(jsonObject, (String)className[i]);
                i++;
            }  
        }
        catch(ParseException p)
        {
            p.printStackTrace();
        }
        return fileToLoad;
    }

    private Object[] classObjectsToStore(JSONArray classes)
    {
        Object[] className = new Object[classes.size()];
        int i = 0;
        for(Object jsonObject : classes)
        {
            //Get the current json object, lots of casting ahead.
            JSONObject jobct = (JSONObject)jsonObject;
            className[i] = (String)jobct.get("ClassName");
            //create the class from the name
            controller.createClass((String)className[i]);
            i++;
        }

        return className;
    }

    /**
     * Gets the JSON array of fields to be added to the class details object. 
     */
    private JSONArray getFieldArray(Class aClass)
    {
        Set<Field> fields = aClass.getFields();
        //Create a JSONArray of the fields to be stored
        JSONArray fieldsToBeAdded = new JSONArray();

        for (Field f : fields) 
        {
            //Get the current fields and put in the JSONObject
            String nameAndType = f.getAccessChar() + " " + f.getType() + " " + f.getName();
            fieldsToBeAdded.add(nameAndType);
        }

        return fieldsToBeAdded;
    }
    /**
     * Get the array of methods to be added to the class details object
     */
    private JSONArray getMethodArray(Class aClass)
    {
        Set<Method> methods = aClass.getMethods();
        //Create a JSONArray of the fields to be stored
        JSONArray methodsToBeAdded = new JSONArray();

        //Add the methods to the json object
        for (Method m : methods) 
        {
            JSONObject methObject = new JSONObject();
            //Get the current fields and put in the JSONObject
            String access = String.valueOf(m.getAccessChar());
            String methodName = m.getName();
            String methodType = m.getType();
            ArrayList<String> params  = store.getMethodParamString(aClass.getName(), m.toString());

            String methodString = access + " " + methodType + " " + methodName + "[ ";
            for(String s : params)
            {
                methodString += s;
                methodString += " ";
            }
            methodString += "]";
            methodsToBeAdded.add(methodString);
        }
        return methodsToBeAdded;
    }
    /**
     * Gets the JSON array of relationships to other classes to be added to the class details object. 
     */
    private JSONArray getRelationToArray(Class aClass)
    {
        //Add the relations to others
        Map<String,RelationshipType> relationToOthers = aClass.getRelationshipsToOther();
            
        JSONArray relationsTo = new JSONArray();
 
        for (Map.Entry<String, RelationshipType> relation : relationToOthers.entrySet()) 
        {
            String relationship = relation.getValue() + " " + relation.getKey();
            relationsTo.add(relationship);
        }
        
        return relationsTo;
    }
    /**
     * Gets the JSON array of relationships from other classes to be added to the class details object. 
     */
    private JSONArray getRelationFromArray(Class aClass)
    {
        Map<String,RelationshipType> relationFromOthers = aClass.getRelationshipsFromOther();
            
        JSONArray relationsFrom = new JSONArray();
        
        for (Map.Entry<String, RelationshipType> relation : relationFromOthers.entrySet()) 
        {
            String relationship = relation.getValue() + " " + relation.getKey();
            relationsFrom.add(relationship);
        }
        return relationsFrom;
    }
    /**
     * Writes the json contents to file
     */
    private File writeToFile(String fileName, JSONObject toBeSaved) throws IOException
    {
        //Attempt to write the json data to passed in file name. IOExcetion on failure. 
        //Append the .json format to name
        //ensures we are not adding it if we don't need to
        if(!fileName.contains(".json"))
        {
            fileName += ".json";
        }

        File jsonFile = new File(fileName);
        FileWriter fw = new FileWriter(jsonFile);
        fw.write(toBeSaved.toJSONString());
        fw.flush();
        fw.close();

        return jsonFile;
    }

    /**
     * Builds the json object to be written to file
     */
    private JSONObject buildObjectToSave()
    {
        ArrayList<Class> classesToSave = store.getClassStore();
        JSONObject toBeSaved = new JSONObject();

        //Store an arraylist of JSONObjects to be written to the file
        ArrayList<JSONObject> classesToBeSaved = new ArrayList<JSONObject>();
        
        //Build the JSONObject from the elements of a class
        for(Class aClass : classesToSave)
        {
            //Get the deails about the class    
            JSONObject classDetails = new JSONObject();
            
            //Add the relations from others
            JSONArray relationsFrom = getRelationFromArray(aClass);
            classDetails.put("RelationshipFromOthers", relationsFrom);

            //Add the relations to other  
            JSONArray relationsTo = getRelationToArray(aClass);
            classDetails.put("RelationshipToOthers",relationsTo);

            //Create a JSONArray of the fields to be stored
            JSONArray methodsToBeAdded = getMethodArray(aClass);
            classDetails.put("Methods", methodsToBeAdded);

            //Get the field array and add it to class details.
            JSONArray fieldsToBeAdded = getFieldArray(aClass);
            classDetails.put("Fields", fieldsToBeAdded);

            //Get the current class name and put it in the JSONObject
            String className = aClass.getName();
            classDetails.put("ClassName", className); 

            //Add the JSONArray to the JSONObject
            classesToBeSaved.add(classDetails);
        }
        toBeSaved.put("Classes",classesToBeSaved);

        return toBeSaved;
    }

    /**
     * Loads fields from the JSON file.
     */
    private void loadFields(Object jsonObject, String className)
    {
        JSONObject jobct = (JSONObject)jsonObject;
        Class aClass = store.findClass(className);
        JSONArray jsonAttr = (JSONArray)jobct.get("Fields");
        Iterator<String> it = jsonAttr.iterator();

        while(it.hasNext())
        {
            String[] field = it.next().split(" ");
            aClass.addField(field[1], field[2], field[0]);
        }
    }

    /**
     * Loads methods from the JSON file.
     */
    private void loadMethods(Object jsonObject, String className)
    {
        JSONObject jobct = (JSONObject)jsonObject;
        Class aClass = store.findClass(className);

        JSONArray jsonMethods = (JSONArray)jobct.get("Methods");
        Iterator<String> it = jsonMethods.iterator();
        
        while(it.hasNext())
        {
            String nextElement = it.next().replace("[", "");
            String[] methodString = nextElement.split(" ");
            String access = methodString[0];
            String type = methodString[1];
            String name = methodString[2];
            //void testMethod ( int num )
            //void testMethod int num 
            ArrayList<String> params = new ArrayList<String>();
            
            for(int count = 3; count < methodString.length - 1; count += 2)
            {
                params.add(methodString[count] + " " + methodString[count + 1]);    
            }
            store.addMethod(className, type, name, params, access);
        }
    }

    /**
     * Loads the relationships to other classes from the JSON file.
     */
    private void loadRelationsTo(Object jsonObject, String className)
    {
        JSONObject jobct = (JSONObject)jsonObject;
        //Get the relationships to other class and add it to the correct class
        JSONArray jsonRelationToOthers = (JSONArray)jobct.get("RelationshipToOthers");
        Iterator<String> it = jsonRelationToOthers.iterator();
        Class aClass = store.findClass(className);
        while(it.hasNext())
        {
            String[] relationship = it.next().split(" ");
            String relatedClassName = relationship[1];
            Class relatedClass = store.findClass(relatedClassName);
            aClass.addRelationshipToOther(RelationshipType.valueOf(relationship[0]), relatedClass);
        }
    }

    /**
     * Loads relationships from other classes from the JSON file.
     */
    private void loadRelationsFrom(Object jsonObject, String className)
    {
        JSONObject jobct = (JSONObject)jsonObject;
        //Get the relationships to other class and add it to the correct class
        JSONArray jsonRelationFromOthers = (JSONArray)jobct.get("RelationshipFromOthers");
        Iterator<String> it = jsonRelationFromOthers.iterator();
        Class aClass = store.findClass(className);
        while(it.hasNext())
        {
            String[] relationship = it.next().split(" ");
            String relatedClassName = relationship[1];
            Class relatedClass = store.findClass(relatedClassName);
            relatedClass.addRelationshipToOther(RelationshipType.valueOf(relationship[0]), aClass);
        }
    }
}