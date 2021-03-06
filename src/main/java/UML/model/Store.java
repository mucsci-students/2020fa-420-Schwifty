package UML.model;

/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Stores all the classes and saved files being used in the UML diagram editor.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import java.util.Map;

public class Store implements Cloneable, IStore 
{

	// An array list to store the current classes.
	private ArrayList<Class> classStore;
	// The currently loaded file. Null if there is none.
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

	public void setClassStore(ArrayList<Class> newStore) 
	{
		this.classStore = newStore;
	}

	// ================================================================================================================================================
	// Getters
	// ===============================================================================================================================================

	/**
	 * Returns ArrayList of classes in the store.
	 */
	public ArrayList<Class> getClassStore() 
	{
		return this.classStore;
	}

	/**
	 * Makes and returns a combo box fill with the created classes.
	 */
	// Chnage name to say that it returns array of strings not classes.
	public ArrayList<String> getClassList() 
	{
		ArrayList<String> names = new ArrayList<String>();

		for (Class aClass : classStore) {
			names.add(aClass.getName());
		}
		return names;
	}

	/**
	 * Takes in a set of fields that are contained in a class and returns them as an
	 * array list of strings.
	 */
	public ArrayList<String> getFieldList(Set<Field> fieldsFromClass) {
		ArrayList<String> fields = new ArrayList<String>();

		for (Field field : fieldsFromClass) {
			fields.add(field.toString());
		}
		return fields;
	}

	/**
	 * Takes in a Set of methods from a class and returns a string of each of the
	 * classes methods. Returned as a ArrayList<String> to be worked with in the
	 * view.
	 */
	public ArrayList<String> getMethodList(Set<Method> methods) {
		ArrayList<String> methodsToReturn = new ArrayList<String>();

		for (Method m : methods) {
			methodsToReturn.add(m.toString());
		}

		return methodsToReturn;

	}

	// ================================================================================================================================================
	// Setters
	// ================================================================================================================================================



	// ********************************************************************************************************************
	// ********************************************************************************************************************
	// None of the below methods for modyifying classes can be called by the
	// controller unless the class in question exists.
	// ********************************************************************************************************************
	// ********************************************************************************************************************

	// ================================================================================================================================================
	// Class methods
	// ================================================================================================================================================

	/**
	 * Adds a class to the store.
	 */
	@Override
	public boolean addClass(String name) throws IllegalArgumentException {
		Class temp = findClass(name);
		if (temp == null) {
			Class newClass = new Class(name);
			classStore.add(newClass);
			return true;
		}
		return false;
	}

	/**
	 * Deletes a class from the store.
	 */
	@Override
	public boolean deleteClass(String name) {
		Class temp = findClass(name);
		if (findClass(name) != null) {
			// Delete relationships before deleting class.
			removeRelationships(temp);
			classStore.remove(temp);
			return true;
		}
		return false;
	}

	/**
	 * Renames a class in the store.
	 */
	@Override
	public boolean renameClass(String oldName, String newName) throws IllegalArgumentException 
	{
		if (findClass(newName) == null && findClass(oldName) != null) {
			Class temp = findClass(oldName);
			Map<String, RelationshipType> from = temp.getRelationshipsFromOther();
			for (Map.Entry<String, RelationshipType> relate : from.entrySet()) {
				Class c = findClass(relate.getKey());
				Map<String, RelationshipType> inner = c.getRelationshipsToOther();
				for (Map.Entry<String, RelationshipType> relate2 : inner.entrySet()) {
					if (relate2.getKey().equals(oldName)) {
						inner.remove(relate2.getKey());
						inner.put(newName, relate2.getValue());
					}
				}
			}
			Map<String, RelationshipType> to = temp.getRelationshipsToOther();
			for (Map.Entry<String, RelationshipType> relate : to.entrySet()) {
				Class c = findClass(relate.getKey());
				Map<String, RelationshipType> inner = c.getRelationshipsFromOther();
				for (Map.Entry<String, RelationshipType> relate2 : inner.entrySet()) {
					if (relate2.getKey().equals(oldName)) {
						inner.remove(relate2.getKey());
						inner.put(newName, relate2.getValue());
					}
				}
			}
			temp.setName(newName);
			return true;
		}
		return false;
	}

	// ================================================================================================================================================
	// Field methods
	// ================================================================================================================================================

	/**
	 * Adds a field to a class in the store.
	 */
	@Override
	public boolean addField(String className, String type, String name, String access) throws IllegalArgumentException {
		Class classToAddAttrTo = findClass(className);
		return classToAddAttrTo.addField(type, name, access);
	}

	/**
	 * Deletes a field from a class in the store.
	 */
	@Override
	public boolean deleteField(String className, String name) {
		Class classToDeleteFrom = findClass(className);
		return classToDeleteFrom.deleteField(name);
	}

	/**
	 * Renames a field from a class in the store. Returns false if field cannot be
	 * added.
	 */
	@Override
	public boolean renameField(String className, String oldName, String newName) throws IllegalArgumentException {
		Class toBeRenamed = findClass(className);
		return toBeRenamed.renameField(oldName, newName);
	}

	/**
	 * Changes the type of a field of a class in the store.
	 */
	@Override
	public boolean changeFieldType(String className, String name, String newType) throws IllegalArgumentException {
		// Class always exists if this method is called; guarenteed by controller.
		Class toChange = findClass(className);
		return toChange.changeFieldType(name, newType);
	}

	/**
	 * Changes the access type of a field.
	 */
	@Override
	public boolean changeFieldAccess(String className, String fieldName, String access) {
		Class toChange = findClass(className);
		return toChange.changeFieldAccess(fieldName, access);
	}

	// ================================================================================================================================================
	// Method methods
	// ================================================================================================================================================

	/**
	 * Adds method to a class in the store.
	 */
	@Override
	public boolean addMethod(String className, String type, String name, ArrayList<String> params, String access)
			throws IllegalArgumentException {
		Class toAdd = findClass(className);

		ArrayList<Parameter> newParams = new ArrayList<Parameter>();

		for (String param : params) {
			String[] splitStr = param.split(" ");
			Parameter newParam = new Parameter(splitStr[0].trim(), splitStr[1].trim());
			newParams.add(newParam);
		}

		return toAdd.addMethod(type, name, newParams, access);
	}

	/**
	 * Deletes a method from a class in the store.
	 */
	@Override
	public boolean deleteMethod(String className, String type, String name, ArrayList<String> params, String access) {
		Class toDelete = findClass(className);

		ArrayList<Parameter> newParams = new ArrayList<Parameter>();

		for (String param : params) {
			String[] splitStr = param.split(" ");
			Parameter newParam = new Parameter(splitStr[0], splitStr[1]);
			newParams.add(newParam);
		}

		return toDelete.deleteMethod(type, name, newParams, access);
	}

	/**
	 * Renames method of a class in the store.
	 */
	@Override
	public boolean renameMethod(String className, String type, String oldName, ArrayList<String> params, String access,
			String newName) throws IllegalArgumentException {
		Class toRename = findClass(className);

		ArrayList<Parameter> newParams = new ArrayList<Parameter>();

		for (String param : params) {
			String[] splitParam = param.split(" ");
			Parameter newParam = new Parameter(splitParam[0].trim(), splitParam[1].trim());
			newParams.add(newParam);
		}

		return toRename.renameMethod(type, oldName, newParams, access, newName);
	}

	/**
	 * Chnages the return type of a method of a class in the store.
	 */
	@Override
	public boolean changeMethodType(String className, String oldType, String methodName, ArrayList<String> params,
			String access, String newType) {
		Class aClass = findClass(className);

		ArrayList<Parameter> newParams = new ArrayList<Parameter>();

		for (String param : params) {
			String[] splitParam = param.split(" ");
			Parameter newParam = new Parameter(splitParam[0].trim(), splitParam[1].trim());
			newParams.add(newParam);
		}

		return aClass.changeMethodType(oldType, methodName, newParams, access, newType);
	}

	/**
	 * Changes the access type of a method of a classs in this store.
	 */
	@Override
	public boolean changeMethodAccess(String className, String type, String methodName, ArrayList<String> params,
			String access, String newAccess) {
		Class aClass = findClass(className);

		ArrayList<Parameter> newParams = new ArrayList<Parameter>();

		for (String param : params) {
			String[] splitParam = param.split(" ");
			Parameter newParam = new Parameter(splitParam[0].trim(), splitParam[1].trim());
			newParams.add(newParam);
		}

		return aClass.changeMethodAccess(type, methodName, newParams, access, newAccess);
	}

	// ================================================================================================================================================
	// Parameter methods
	// ================================================================================================================================================

	/**
	 * Add parameter to a method of a class in the store.
	 */
	@Override
	public boolean addParam(String className, String methodType, String methodName, ArrayList<String> params,
			String access, String paramType, String paramName) throws IllegalArgumentException {
		ArrayList<Parameter> theParams = new ArrayList<Parameter>();

		for (String p : params) {
			String[] splitStr = p.split(" ");
			Parameter newParam = new Parameter(splitStr[0].trim(), splitStr[1].trim());
			theParams.add(newParam);
		}

		Class theClass = findClass(className);
		return theClass.addParameter(methodType, methodName, theParams, access, paramType, paramName);
	}

	/**
	 * Deletes parameter of a method of a class in the store.
	 */
	@Override
	public boolean deleteParam(String className, String methodType, String methodName, ArrayList<String> params,
			String access, String paramType, String paramName) {
		ArrayList<Parameter> theParams = new ArrayList<Parameter>();
		for (String p : params) {
			String[] splitStr = p.split(" ");
			Parameter toBeDeleted = new Parameter(splitStr[0].trim(), splitStr[1].trim());
			theParams.add(toBeDeleted);
		}

		Class theClass = findClass(className);
		return theClass.deleteParameter(methodType, methodName, theParams, access, paramType, paramName);
	}

	// ================================================================================================================================================
	// Relationship methods
	// ================================================================================================================================================

	/**
	 * Adds a relationship between two classes in the store.
	 */
	@Override
	public boolean addRelationship(String classFrom, String classTo, RelationshipType relation) {
		Class class1 = findClass(classFrom);
		Class class2 = findClass(classTo);
		if (class1 == null || class2 == null)
			return false;
		else
			return class1.addRelationshipToOther(relation, class2);
	}

	/**
	 * Deletes a relationship between two classes in the store.
	 */
	@Override
	public boolean deleteRelationship(String classFrom, String classTo) {
		Class class1 = findClass(classFrom);
		Class class2 = findClass(classTo);
		if (class1 == null || class2 == null)
			return false;
		else {
			RelationshipType relation = class1.getRelationshipsToOther().get(classTo);
			return class1.deleteRelationshipToOther(relation, class2);
		}
	}

	/**
	 * Removes relevant relationships when classes are deleted.
	 */
	@Override
	public void removeRelationships(Class aClass) {
		// Find the classes that the class in question has a relationship with
		Map<String, RelationshipType> tempTo = aClass.getRelationshipsToOther();
		Map<String, RelationshipType> tempFrom = aClass.getRelationshipsFromOther();
		for (Map.Entry<String, RelationshipType> entry : tempTo.entrySet()) {
			// Go to those classes and get rid of relationships to and from the class in
			// question.
			Class temp = findClass(entry.getKey());
			temp.deleteRelationshipFromOther(entry.getValue(), aClass);
		}
		for (Map.Entry<String, RelationshipType> entry : tempFrom.entrySet()) {
			// Go to those classes and get rid of relationships to and from the class in
			// question.
			Class temp = findClass(entry.getKey());
			temp.deleteRelationshipToOther(entry.getValue(), aClass);
		}
	}

	// ================================================================================================================================================
	// Find methods
	// ================================================================================================================================================

	/**
	 * Finds a class in the storage and returns it. Returns null if nothing found.
	 */
	public Class findClass(String name) {
		for (Class aClass : classStore) {
			if (aClass.getName().equals(name)) {
				return aClass;
			}
		}
		return null;
	}

	/**
	 * Finds a method in a class in the storage and returns it. Returns null if
	 * nothing found.
	 */
	public Method findMethod(String className, String methodType, String methodName, ArrayList<Parameter> params, String access) {
		Method newMethod = new Method(methodType, methodName, params, access);
		Class foundClass = findClass(className);
		for (Method method : foundClass.getMethods()) {
			if (method.equals(newMethod)) {
				return method;
			}
		}
		return null;
	}

	// ===============================================================================================================================================
	// Others
	// ===============================================================================================================================================

	/**
	 * Remove a method from a class based on the method string. Takes in a class
	 * name from which the method is to be removed and the method toString result.
	 * Parse of string is based on toString from Method String result = ""; result
	 * += "Method: "; result += this.getType() + " " + this.getName(); result += "(
	 * ";
	 * 
	 * for(Parameter p : this.getParams()) { result += p.toString() + ", "; } result
	 * += " )"; return result;
	 */
	public boolean removeMethodByString(Set<Method> methods, String methodToBeDeleted, String className) {
		for (Method m : methods) {
			if (m.toString().equals(methodToBeDeleted)) {
				ArrayList<String> arr = new ArrayList<String>();
				for (Parameter p : m.getParams()) {
					arr.add(p.getType() + " " + p.getName());
				}
				return deleteMethod(className, m.getType(), m.getName(), arr, m.getAccessString());
			}
		}
		return false;
	}

	/**
	 * Rename a method from a class based on the method string. Takes in a class
	 * name from which the method is to be removed and the method toString result.
	 */
	public boolean renameMethodByString(Set<Method> methods, String methodToBeRenamed, String className, String newName, String access) {
		// Loop through the returned methods to find the method to be renamed.
		for (Method m : methods) {
			if (m.toString().equals(methodToBeRenamed)) {
				// ArrayList of param strings
				ArrayList<String> paramStrings = new ArrayList<String>();
				// Generate an ArrayList of strings to be passed into the rename method.
				ArrayList<Parameter> params = m.getParams();
				for (Parameter p : params) {
					paramStrings.add(p.getType() + " " + p.getName());
				}
				// rename the method and break out.
				return renameMethod(className, m.getType(), m.getName(), paramStrings, m.getAccessString(), newName);
			}
		}
		return false;
	}

	// ===============================================================================================================================================

	/**
	 * Returns an array list with the toStrings
	 */
	public ArrayList<String> stringOfClasses() {
		ArrayList<String> result = new ArrayList<String>();
		for (Class c : classStore) {
			result.add(c.toString());
		}
		return result;
	}

	// ===============================================================================================================================================

	/**
	 * Creates an ArrayList<String> of a methods parameters from className and methodToString.
	 */
	public ArrayList<String> getMethodParamString(String className, String methodString) {
		Class aClass = findClass(className);
		Set<Method> methods = aClass.getMethods();
		ArrayList<String> params = new ArrayList<String>();
		for (Method m : methods) {
			if (m.toString().equals(methodString))
				for (Parameter p : m.getParams()) {
					params.add(p.getType() + " " + p.getName());
				}
		}
		return params;
	}

	/**
	 * Returns a deep copied clone of the Store object to the caller. 
	 */
	@Override
	public Object clone() 
	{
		//The clone
		Store clone = new Store();
		clone.setCurrentLoadedFile(this.getCurrentLoadedFile());
		//will hold the new copy of the classStore
		ArrayList<Class> storeCopy = clone.getClassStore();
		for(Class c : classStore)
		{
			//For each class, add all relevant field.
			//Make new class (use name and location).
			Class aClass = new Class(c.getName());
			aClass.setLocation(c.getLocation());
			//Add fields.
			Set<Field> fields = c.getFields();
			for (Field f : fields)
			{
				aClass.addField(f.getType(), f.getName(), f.getAccessString());	
			}
			
			//Add methods.
			Set<Method> methods = c.getMethods();
			for (Method m : methods) 
			{
				String access = m.getAccessString();
				String methodName = m.getName();
				String methodType = m.getType();
				ArrayList<Parameter> params  = m.getParams();
				aClass.addMethod(methodType, methodName, params, access);
			}
			//Add the class to the store.
			storeCopy.add(aClass);
		}
		//Add all relationships in.
		for(Class aClass : this.getClassStore())
		{
			Class newClass = clone.findClass(aClass.getName());
			//Add the relations to others.
			Map<String,RelationshipType> relationToOthers = aClass.getRelationshipsToOther();
	
			for (Map.Entry<String, RelationshipType> relation : relationToOthers.entrySet()) 
			{
				newClass.addRelationshipToOther(relation.getValue(), clone.findClass(relation.getKey()));
			}
			//Add the relations from otehrs
			Map<String,RelationshipType> relationFromOthers = aClass.getRelationshipsFromOther();

			for (Map.Entry<String, RelationshipType> relation : relationFromOthers.entrySet()) 
			{
				newClass.addRelationshipFromOther(relation.getValue(), clone.findClass(relation.getKey()));
			}
		}
		return clone;
	}

	/**
	* Returns whether or not a store object is equal to this store.
	*/
	public boolean equals(Object other)
	{
		boolean result = false;
		if(this == other)
		{
			result = true;
		}
		else if(other == null)
		{
			result = false;
		}
		else if (!(other instanceof Store))
		{
			result = false;
		}
		else
		{
			Store object = (Store)other;
			boolean classesEqual = true;
			for(Class c : classStore)
			{
				if(!object.classStore.contains(c))
				{
					classesEqual = false;
				}
			}
			for(Class c : object.classStore)
			{
				if(!classStore.contains(c))
				{
					classesEqual = false;
				}
			}

			boolean filesMatch;
			if(currentLoadedFile == null && object.currentLoadedFile == null)
			{
				filesMatch = true;
			}
			else if(currentLoadedFile == null && object.currentLoadedFile != null || currentLoadedFile != null && object.currentLoadedFile == null)
			{
				filesMatch = false;
			}
			else if(!currentLoadedFile.equals(object.currentLoadedFile))
			{
				filesMatch = false;
			}
			else
			{
				filesMatch = true;
			}
			
			if(classesEqual && filesMatch)
			{
				result = true;
			}
		}
		return result;
	}
}