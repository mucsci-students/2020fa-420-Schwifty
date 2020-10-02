package UML.controllers;
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
     *          methods[ Method: [{
     *                                type 
     *                                name
     *                                params[] 
     *                            }]
     *                  ]
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
    public static File save(String fileName, ArrayList<Class> classesToSave) throws IOException
    {
        //the json file
        File jsonFile;
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

            for (Field f : fields) 
            {
                //Get the current fields and put in the JSONObject
                String nameAndType = f.getType() + " " + f.getName();
                fieldsToBeAdded.add(nameAndType);
            }
            classDetails.put("Fields", fieldsToBeAdded);

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            Set<Method> methods = aClass.getMethods();
            //Create a JSONArray of the fields to be stored
            JSONArray methodsToBeAdded = new JSONArray();

            //Add the methods to the json object
            for (Method m : methods) 
            {
                //Get the current fields and put in the JSONObject
                String methodName = m.getName();
                String methodType = m.getType();
                ArrayList<String> params  = store.getMethodParamString(methodName, methodType);
                // {returnType Name {type name, type name, ...  }}
                JSONArray paramsToAdd = new JSONArray();
                JSONObject methObject = new JSONObject();
                for(String p : params)
                {
                    JSONObject par = new JSONObject();
                    par.put("Parameter", p);
                    paramsToAdd.add(p);
                }
                methObject.put("Params", paramsToAdd);
                methodsToBeAdded.add(m);
                classDetails.put("Methods", methodName);
            }
            
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //Add the relations to others
            Map<String,RelationshipType> relationToOthers = aClass.getRelationshipsToOther();
            
            JSONArray relationsTo = new JSONArray();

            for (Map.Entry<String, RelationshipType> relation : relationToOthers.entrySet()) 
            {
                String relationship = relation.getValue() + " " + relation.getKey();
                relationsTo.add(relationship);
            }
            //Add the JSONArray to the JSONObject
            classDetails.put("RelationshipToOthers",relationsTo);

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //Add the relations from others
            Map<String,RelationshipType> relationFromOthers = aClass.getRelationshipsFromOther();
            
            JSONArray relationsFrom = new JSONArray();
            
            for (Map.Entry<String, RelationshipType> relation : relationFromOthers.entrySet()) 
            {
                String relationship = relation.getValue() + " " + relation.getKey();
                relationsFrom.add(relationship);
            }
            
            //Add the JSONArray to the JSONObject
            classDetails.put("RelationshipFromOthers", relationsFrom);

            //Add the JSONArray to the JSONObject
            
            classesToBeSaved.add(classDetails);
        }
        toBeSaved.put("Classes",classesToBeSaved);
        //Attempt to write the json data to passed in file name. IOExcetion on failure. 
        //Append the .json format to name
        //ensures we are not adding it if we don't need to
        if(!fileName.contains(".json"))
        {
            fileName += ".json";
        }

        jsonFile = new File(fileName);
        FileWriter fw = new FileWriter(fileName);
        fw.write(toBeSaved.toJSONString());
        fw.flush();
        fw.close();
  
        return jsonFile;
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    
    public static File load(String fileName, ArrayList<Class> classStore) throws IOException, ParseException
    {
        JSONParser parser = new JSONParser();
        
        //Get the json object from the parser
        JSONObject obj = (JSONObject)parser.parse(fileName);
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
                Class relatedClass = store.findClass(className);
                aClass.addRelationshipToOther(RelationshipType.valueOf(relationship[0]), relatedClass);
            }

            //Get the relationships to other class and add it to the correct class
            JSONArray jsonRelationFromOthers = (JSONArray)jobct.get("RelationshipFromOthers");
            it = jsonRelationFromOthers.iterator();
            while(it.hasNext())
            {
                String[] relationship = it.next().split(" ");
                String className = relationship[1];
                Class relatedClass = store.findClass(className);
                aClass.addRelationshipFromOther(RelationshipType.valueOf(relationship[0]), relatedClass);
            }
            index++;
        }
        return new File("File.txt");
    }
}