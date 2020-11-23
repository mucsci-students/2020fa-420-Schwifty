package UML.controllers;

import java.util.ArrayList;
import UML.model.RelationshipType;
public interface IController 
{
    //Class methods.
    void createClass(String name);
    void deleteClass(String name);
    void renameClass(String oldName, String newName);
 
    //Field methods.
    void createField(String className, String type, String name, String access);
    void renameField(String className, String oldName, String newName);
    void deleteField(String className, String name);
    void changeFieldType(String className, String name, String newType);
    void changeFieldAccess(String className, String name, String newAccess);
    
    //Method methods.
    void createMethod(String className, String type, String name, ArrayList<String> params, String access);
    void deleteMethod(String className, String type, String name, ArrayList<String> params, String access);
    void renameMethod(String className, String type, String name, ArrayList<String> params, String access, String newName);
    void changeMethodType(String className, String type, String name, ArrayList<String> params, String access, String newType);
    void changeMethodAccess(String className, String type, String name, ArrayList<String> params, String access, String newAccess);

    //Parameter methods.
    void addParameter(String className, String methodType, String methodName, ArrayList<String> params, String access, String paramType, String paramName);
    void deleteParameter(String className, String methodType, String methodName, ArrayList<String> params, String access,  String paramType, String paramName);
    
    //Relationship methods.
    void addRelationship(String from, String to, RelationshipType relation);
    void deleteRelationship(String from, String to);
}



