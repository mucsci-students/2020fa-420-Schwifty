package UML.model;

import java.util.ArrayList;
import UML.model.RelationshipType;

public interface IStore 
{
    //Class methods.
    boolean addClass(String name);
    boolean deleteClass(String name);
    boolean renameClass(String oldName, String newName);
 
    //Field methods.
    boolean addField(String className, String type, String name, String access);
    boolean renameField(String className, String oldName, String newName);
    boolean deleteField(String className, String name);
    boolean changeFieldType(String className, String name, String newType);
    boolean changeFieldAccess(String className, String name, String newAccess);
    
    //Method methods.
    boolean addMethod(String className, String type, String name, ArrayList<String> params, String access);
    boolean deleteMethod(String className, String type, String name, ArrayList<String> params, String access);
    boolean renameMethod(String className, String type, String name, ArrayList<String> params, String access, String newName);
    boolean changeMethodType(String className, String type, String name, ArrayList<String> params, String access, String newType);
    boolean changeMethodAccess(String className, String type, String name, ArrayList<String> params, String access, String newAccess);

    //Parameter methods.
    boolean addParam(String className, String methodType, String methodName, ArrayList<String> params, String access, String paramType, String paramName);
    boolean deleteParam(String className, String methodType, String methodName, ArrayList<String> params, String access,  String paramType, String paramName);
    
    //Relationship methods.
    boolean addRelationship(String classFrom, String classTo, RelationshipType relation);
    boolean deleteRelationship(String classFrom, String classTo);
    void removeRelationships(Class aClass);
}
