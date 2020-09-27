import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;


public class Store {
    
    private ArrayList<Class> classStore;
    private File currentLoadedFile;

	public Store() 
	{
        classStore = new ArrayList<Class>();
        currentLoadedFile = null;
	}

	/**
	 * Returns the currently loaded file, if there is one. 
	 */
	public File getCurrentLoadedFile()
	{
		return this.currentLoadedFile;
	}

	/**
	 * Setter for the current loaded file. 
	 */
	public void setCurrentLoadedFile(File fileToSet)
	{
		this.currentLoadedFile = fileToSet;
	}
    /** 
	 * Makes and returns a combo box fill with the created classes.
	 */
    //Chnage name to say that it returns array of strings not classes.
	public ArrayList<String> getClassList()
	{
		ArrayList<String> names = new ArrayList<String>();

		for(Class aClass : classStore)
		{
			names.add(aClass.getName());
		}
		return names;
    }
    
    /**
	 * Takes in a set of fields that are contained in a 
	 * class and returns them as an array list of strings.
	 */
	public ArrayList<String> getFieldList(Set<Field> fieldsFromClass)
	{
		ArrayList<String> fields = new ArrayList<String>();

		for(Field field : fieldsFromClass)
		{
			String type = field.getType();
			String name = field.getName();
			fields.add(type + " " + name);
		}
		return fields;
    }
	
	/**
	 * Takes in a Set of methods from a class and returns a string of each
	 * of the classes methods. Returned as a ArrayList<String> to be worked with
	 * in the view. 
	 */
	public ArrayList<String> getMethodList(Set<Method> methods)
	{
		ArrayList<String> methodsToReturn = new ArrayList<String>();

		for(Method m : methods)
		{
			methodsToReturn.add(m.toString());
		}

		return methodsToReturn;
	}
	
	/**
	 * Adds a class to the store.
	 */
	public boolean addClass(String name) throws IllegalArgumentException
	{
		Class temp = findClass(name);
			if(temp == null) 
			{
				Class newClass = new Class(name);
				classStore.add(newClass);
				return true;
			}
		return false;
	}

	/**
	 * Deletes a class from the store.
	 */
	public boolean deleteClass(String name)
	{
		Class temp = findClass(name);
		if(findClass(name) != null)
		{
			//Delete relationships before deleting class.
			removeRelationships(temp);
			classStore.remove(temp);
			return true;
		}
		return false;
	}

	/**
	 * Renames a class in the store.
	 */
	public boolean renameClass(String oldName, String newName) throws IllegalArgumentException
	{
		if(findClass(newName) == null)
		{
			Class temp = findClass(oldName);
			temp.setName(newName);
			return true;
		}
		return false;
	}

	/**
	 * Adds a field to a class in the store.
	 */
	public void addField(String className, String type, String name) throws IllegalArgumentException
	{
		Class classToAddAttrTo = findClass(className);
		classToAddAttrTo.addField(type, name);
	}

	/**
	 * Deletes a field from a class in the store.
	 */
	public void deleteField(String className, String name) 
	{
		Class classToDeleteFrom = findClass(className);		
		classToDeleteFrom.deleteField(name);
	}

	/**
	 * Renames a field from a class in the store.
	 */
	public void renameField(String className, String oldName, String newName) throws IllegalArgumentException
	{
		Class toBeRenamed = findClass(className);
		toBeRenamed.renameField(oldName, newName);
	}

	/**
	 * Changes the type of a field of a class in the store.
	 */
	public void changeFieldType(String className, String newType, String name) throws IllegalArgumentException
	{
		Class toChange = findClass(className);
		toChange.changeFieldType(newType, name);
	}


	/**
	 * Adds method to a class in the store.
	 */
	public void addMethod(String className, String type, String name, ArrayList<String> params) throws IllegalArgumentException
	{
		Class toAdd = findClass(className);
		
		ArrayList<Parameter> newParams = new ArrayList<Parameter>();

		for(String param : params)
		{
			String[] splitStr = param.split(" ");
			Parameter newParam = new Parameter(splitStr[0], splitStr[1]);
			newParams.add(newParam);
		}

		toAdd.addMethod(type, name, newParams);
	}

	/**
	 * Deletes a method from a class in the store.
	 */
	public void deleteMethod(String className, String type, String name, ArrayList<Parameter> params)
	{
		Class toDelete = findClass(className);
		toDelete.deleteMethod(type, name, params);
	}

	/**
     * Renames method of a class in the store.
     */
	public void renameMethod(String className, String type, String oldName, ArrayList<Parameter> params, String newName) throws IllegalArgumentException
	{
		Class toRename = findClass(className);
		toRename.renameMethod(type, oldName, params, newName);
	}

	/**
     * Chnages the return type of a method of a class in the store.
     */
	public void changeMethodType(String className, String oldType, String methodName, ArrayList<Parameter> params, String newType)
	{
		Class aClass = findClass(className);
		aClass.changeMethodType(oldType, methodName, params, newType);
	}
	/**
	 * Add parameter to a method of a class in the store.
	 */
	public void addParam(String className, String methodType, String methodName, ArrayList<Parameter> params, String paramType, String paramName) throws IllegalArgumentException
	{
		Class theClass = findClass(className);
		Method theMethod = findMethod(theClass.getName(), methodType, methodName, params);
		theMethod.addParam(new Parameter(paramType, paramName));
	}

	/**
	 * Deletes parameter of a method of a class in the store.
	 */
	public void deleteParam(String className, String methodType, String methodName, ArrayList<Parameter> params, String paramType, String paramName)
	{
		Method theMethod = findMethod(className, methodType, methodName, params);
		theMethod.deleteParam(new Parameter(paramType, paramName));
	}
	
	/**
	 * Adds a relationship between two classes in the store.
	 */
	public void addRelationship(String classFrom, String classTo, RelationshipType relation)
	{
		Class class1 = findClass(classFrom);
		Class class2 = findClass(classTo);
		class1.addRelationshipToOther(relation, class2);
	}

	/**
	 * Deletes a relationship between two classes in the store.
	 */
	public void deleteRelationship(String classFrom, String classTo) {
		Class class1 = findClass(classFrom);
		Class class2 = findClass(classTo);
		RelationshipType relation = class1.getRelationshipsToOther().get(classTo);
		class1.deleteRelationshipToOther(relation, class2);
		class2.deleteRelationshipToOther(relation, class1);
	}

    /**
     * Removes relevant relationships when classes are deleted.
     */
	public void removeRelationships(Class aClass)
	{
		//Find the classes that the class in question has a relationship with
		Map<String, RelationshipType> tempTo = aClass.getRelationshipsToOther();
		Map<String, RelationshipType> tempFrom = aClass.getRelationshipsFromOther();
		for(Map.Entry<String, RelationshipType> entry : tempTo.entrySet()) 
		{
			//Go to those classes and get rid of relationships to and from the class in question.
			Class temp = findClass(entry.getKey());
			temp.deleteRelationshipFromOther(entry.getValue(), aClass);
		}
		for(Map.Entry<String, RelationshipType> entry : tempFrom.entrySet()) 
		{
			//Go to those classes and get rid of relationships to and from the class in question.
			Class temp = findClass(entry.getKey());
			temp.deleteRelationshipToOther(entry.getValue(), aClass);
		}
    }
    
    /**
	 * Finds a class in the storage and returns it. Returns null if nothing found.
	 */
	public Class findClass(String name)
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

	/**
	 * Finds a method in a class in the storage and returns it. Returns null if nothing found.
	 */
	public Method findMethod(String className, String methodType, String methodName, ArrayList<Parameter> params)
	{
		Method newMethod = new Method(methodType, methodName, params);
		Class foundClass = findClass(className);
		for(Method method : foundClass.getMethods())
		{
			if(method.equals(newMethod))
			{
				return method;
			}
		}
		return null;
	}

}