package UML.model;
/*
    Author: Tyler, Cory, Dominic, Drew, Chris. 
    Date: 09/08/2020
    Purpose: This class defines how classes the user creates will function. A class 
    needs at least a name to be created and allows for the addition of attributes that are
    stored in a set. Relationships to the this class, as well as relationships to other classes
    are stored in maps. 
*/
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.awt.Dimension;


public class Class {

    // The name of the class object.
    private String name;
    // A set containing the fields of a class object.
    private Set<Field> fields;
    // The relationships from this class object to another.
    private ConcurrentHashMap<String, RelationshipType> relationshipsToOther;
    // The relationships from another class object to this one.
    private ConcurrentHashMap<String, RelationshipType> relationshipsFromOther;
    // A set containing the class's methods.
    private Set<Method> methods;
    //The position of the class to be used in the GUI.
    Dimension location;


    /**
     * Constructs a class object that takes in a parameter for the name of the
     * class.
     */
    public Class(String name) throws IllegalArgumentException 
    {
        // Don't allow empty string/only whitespace
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("The class name cannot be blank.");
        }
        if (name.contains(" ")) {
            throw new IllegalArgumentException("The class name cannot cantain a space.");
        }
        this.name = name;
        this.fields = new HashSet<Field>();
        this.relationshipsToOther = new ConcurrentHashMap<String, RelationshipType>();
        this.relationshipsFromOther = new ConcurrentHashMap<String, RelationshipType>();
        this.methods = new HashSet<Method>();
        this.location = new Dimension(0, 0);
    }


//================================================================================================================================================
//Getters.
//================================================================================================================================================

    
    /**
     * Returns the name of the class object.
     */
    public String getName() 
    {
        return this.name;
    }

    /**
     * Returns a set of the fields of the class object.
     */
    public Set<Field> getFields() 
    {
        return this.fields;
    }

    /**
     * Returns a set of the methods of the class object.
     */
    public Set<Method> getMethods() 
    {
        return this.methods;
    }

    /**
     * Returns the relationships from this class object to another.
     */
    public Map<String, RelationshipType> getRelationshipsToOther() 
    {
        return this.relationshipsToOther;
    }

    /**
     * Returns the relationships from another class object to this one.
     */
    public Map<String, RelationshipType> getRelationshipsFromOther() 
    {
        return this.relationshipsFromOther;
    }

    /**
     * Returns location associated with the class.
     */
    public Dimension getLocation()
    {
        return location;
    }


//================================================================================================================================================
//Setters
//================================================================================================================================================


    /**
     * Changes the name of the class object.
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("The class name cannot be blank.");
        }
        if (name.contains(" ")) 
        {
            throw new IllegalArgumentException("The class name cannot contains spaces.");
        }
        this.name = name;
    }

    /**
     * Sets the location associated with the class.
     */
    public void setLocation(Dimension d)
    {
        this.location = d;
    }


//================================================================================================================================================
//Field methods
//================================================================================================================================================


    /**
     * Adds a field the the class object.  If the field name is already used, return false.
     */
    public boolean addField(String type, String name, String access) throws IllegalArgumentException 
    {
        // If name is found, return false...atrribute already created
        for (Field f : fields) 
        {
            if (f.getName().equals(name)) 
            {
                return false;
            }
        }
        Field newField = new Field(type, name, access);
        fields.add(newField);
        return true;
    }

    /**
     * Deletes a field from the class object.  If the field isn't found, return false.
     */
    public boolean deleteField(String name) {
        for (Field f : fields) {
            if (f.getName().equals(name)) {
                fields.remove(f);
                return true;
            }
        }
        return false;
    }

    /**
     * Renames a field of the class object.  If it cannot be ranmed, return false.
     */
    public boolean renameField(String oldName, String newName) throws IllegalArgumentException 
    {
        for (Field a : fields) 
        {
            //If the new name name already exists, return false.
            if (a.getName().equals(newName)) 
            {
                return false;
            }
        }
        for (Field f : fields) {
            if (f.getName().equals(oldName)) 
            {
                // Must remove and add so that the Field has correct hashcode.
                String type = f.getType();
                String access = f.getAccessString();
                this.fields.remove(f);
                Field toAdd = new Field(type, newName, access);
                this.fields.add(toAdd);
                return true;
            }
        }
        //If the field wasn't found, return false.
        return false;
    }

    /**
     * Changes the type of a field of the class object.  If the field cannot be found, return false.
     */
    public boolean changeFieldType(String newType, String name) throws IllegalArgumentException 
    {
        for (Field f : fields) 
        {
            if (f.getName().equals(name)) 
            {
                // Must remove and add so that the Field has correct hashcode
                String access = f.getAccessString();
                Field toAdd = new Field(newType, name, access);
                this.fields.remove(f);
                this.fields.add(toAdd);
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the field access to the new requested access level.
     */
    public boolean changeFieldAccess(String fieldName, String newAccessString)
    {
        for(Field f : fields)
        {
            if (f.getName().equals(fieldName))
            {
                // Must remove and add so that the Field has correct hashcode
                String type = f.getType();
                Field toAdd = new Field(type, fieldName, newAccessString);
                this.fields.remove(f);
                this.fields.add(toAdd);
                return true;
            }
        }
        return false;
    }


//================================================================================================================================================
//Method methods
//================================================================================================================================================



    /**
     * Adds method with parameters.  If the method cannot be added, return false.
     */
    public boolean addMethod(String type, String name, ArrayList<Parameter> params, String access) throws IllegalArgumentException 
    {
        Method newMethod = new Method(type, name, params, access);
        for (Method m : methods) 
        {
            // If the number of parameters doesn't match, no need to check if the parameters are equal.
            if ((params.size() == m.getParams().size())) 
            {
                //If the return type and method name are equal, check if parameters are equal.
                if (m.getType().equals(newMethod.getType()) && m.getName().equals(newMethod.getName())) 
                {
                    boolean typesMatch = true;
                    //Iterate through parameters to check if they are equal.
                    for (int count = 0; count < params.size(); count++) 
                    {
                        if (!params.get(count).getType().equals(m.getParams().get(count).getType())) 
                        {
                            typesMatch = false;
                        }
                    }
                    //If the method to be added is exactly equal to another, don't add and return false.
                    if (typesMatch)
                        return false;
                }
            }
        }
        methods.add(newMethod);
        return true;
    }

    /**
     * Deletes a method from the class.  If it is not deleted, return false.
     */
    //////////Can rewrite in one line as long as method.equals() works correctly.
    public boolean deleteMethod(String type, String name, ArrayList<Parameter> params, String access) 
    {
        Method method = new Method(type, name, params, access);
        for (Method m : methods) 
        {
            if (m.getType().equals(type) && m.getName().equals(name) && m.getParams().equals(params)) 
            {
                //This should always be true in this case.
                return methods.remove(method);
            }
        }
        return false;
    }

    /**
     * Renames method with parameters.  If it cannot be renamed, return false.
     */
    public boolean renameMethod(String type, String oldName, ArrayList<Parameter> params, String access, String newName) throws IllegalArgumentException 
    {
        Method methodNew = new Method(type, newName, params, access);
        for (Method m : methods) 
        {
            //If that exact method already exists, return false.
            if (m.equals(methodNew)) 
            {
                return false;
            }
        }
        Method method = new Method(type, oldName, params, access);
        boolean deleted = methods.remove(method);
        if (deleted) 
        {
            addMethod(type, newName, params, access);
        }
        //Returns false if method wasn't found in methods, true if it was found and renamed.
        return deleted;
    }

    /**
     * Changes the return type of a method.  If it cannot be changed, return false.
     */
    public boolean changeMethodType(String oldType, String methodName, ArrayList<Parameter> params, String access, String newType) throws IllegalArgumentException 
    {
        Method methodNew = new Method(newType, methodName, params, access);
        for (Method m : methods) 
        {
            //If that exact method already exists, return false.
            if (m.equals(methodNew)) 
            {
                return false;
            }
        }
        Method method = new Method(oldType, methodName, params, access);
        boolean deleted = methods.remove(method);
        if (deleted) 
        {
            addMethod(newType, methodName, params, access);
        }
        //Returns false if method wasn't found in methods, true if it was found and retyped.
        return deleted;
    }
    /**
     * Changes the access type of a method.  If it cannot be changed, return false.
     */
    public boolean changeMethodAccess(String type, String methodName, ArrayList<Parameter> params, String access, String newAccess) throws IllegalArgumentException 
    {
        if(!newAccess.equals("public") && !newAccess.equals("private") && !newAccess.equals("protected"))
            return false;
        Method methodNew = new Method(type, methodName, params, newAccess);
        for (Method m : methods) 
        {
            //If that exact method already exists, return false.
            if (m.equals(methodNew)) 
            {
                return false;
            }
        }
        Method method = new Method(type, methodName, params, access);
        boolean deleted = methods.remove(method);
        if (deleted) 
        {
            addMethod(type, methodName, params, newAccess);
        }
        return deleted;
    }


//================================================================================================================================================
//Relationship methods
//================================================================================================================================================



    /**
     * Adds a relationship from this class object to another.  Returns false if the relationship couldn't be created.
     */
    public boolean addRelationshipToOther(RelationshipType relation, Class aClass) throws IllegalArgumentException 
    {
        // If trying to create a relationship with a class and itself, throw exception.
        if (this.equals(aClass)) 
        {
            throw new IllegalArgumentException("A class cannot have a relationship with itself.");

        }
        // If relationship already exists between the two classes, don't overwrite.
        if (!relationshipsToOther.containsKey(aClass.name) && !relationshipsFromOther.containsKey(aClass.name)) 
        {
            relationshipsToOther.put(aClass.name, relation);
            aClass.relationshipsFromOther.put(this.name, relation);
            return true;
        }
        //This will occur if there is already a relationship between the two classes.
        return false;
    }

    /**
     * Adds a relationship from another class object to this one.  Returns false if the relationship couldn't be created.
     */
    public boolean addRelationshipFromOther(RelationshipType relation, Class aClass) throws IllegalArgumentException 
    {
        // If trying to create a relationship with a class and itself, throw exception.
        if (this.equals(aClass)) 
        {
            throw new IllegalArgumentException("A class cannot have a relationship with itself.");
        }
        // If relationship already exists between the two classes, don't overwrite.
        if (!relationshipsFromOther.containsKey(aClass.name) && !relationshipsToOther.containsKey(aClass.name)) 
        {
            relationshipsFromOther.put(aClass.name, relation);
            aClass.relationshipsToOther.put(this.name, relation);
            return true;
        }
        //This will occur if there is already a relationship between the two classes.
        return false;
    }

    /**
     * Deletes a relationship from this class object to another.  Returns false if a relationship cannot be deleted.
     */
    public boolean deleteRelationshipToOther(RelationshipType relation, Class aClass) 
    {
        //Remove relationship this class has to another.
        boolean removedToOther = relationshipsToOther.remove(aClass.name, relation);
        //Remove a relationship another class has from this one.
        boolean removedFromOther = aClass.relationshipsFromOther.remove(this.name, relation);
        //True if both cases above were true.
        return removedToOther && removedFromOther;
    }

    /**
     * Deletes a relationship from another class object to this one.  Returns false if a relationship cannot be deleted.
     */
    public boolean deleteRelationshipFromOther(RelationshipType relation, Class aClass) 
    {
        //Remove a relationship this class has from another.
        boolean removedFromOther = relationshipsFromOther.remove(aClass.name, relation);
        //Remove relationship another class has to this one.
        boolean removedToOther = aClass.relationshipsToOther.remove(this.name, relation);
        //True if both cases above were true.
        return removedFromOther && removedToOther;
    }


//================================================================================================================================================
//Other methods
//================================================================================================================================================
    /**
     * Returns true if two class object are equal and false otehrwise.
     */
    public boolean equals(Object other) 
    {
        boolean result = false;
        if (this == other) 
        {
            result = true;
        } else if (other == null) 
        {
            result = false;
        } else if (!(other instanceof Class)) 
        {
            result = false;
        } else {
            Class object = (Class) other;
            boolean fieldEqual = true;
            // Check if both sets contain the same objects since equals() won't work
            for (Field att : this.fields) 
            {
                if (!object.fields.contains(att)) 
                {
                    fieldEqual = false;
                }
            }
            for (Field att : object.fields) 
            {
                if (!this.fields.contains(att)) 
                {
                    fieldEqual = false;
                }
            }
            boolean methodEqual = true;
            // Check if both sets contain the same objects since equals() won't work
            for (Method att : this.methods) 
            {
                if (!object.methods.contains(att)) 
                {
                    methodEqual = false;
                }
            }
            for (Method att : object.methods) 
            {
                if (!this.methods.contains(att)) 
                {
                    methodEqual = false;
                }
            }
            if (object.name.equals(this.name) && fieldEqual && methodEqual
                    && object.relationshipsToOther.equals(this.relationshipsToOther)
                    && object.relationshipsFromOther.equals(this.relationshipsFromOther)) 
            {
                result = true;
            }
        }
        return result;
    }

    /**
     * Returns a string representation of a class object.
     */
    public String toString() {
        String result = "";
        result += "Class name: " + this.name + "\n";
        result += "------------------------------\n";
        result += "Field Names: \n";
        if (!fields.isEmpty()) {
            for (Field field : fields) {
                result += field.toString();
                result += "\n";
            }
        }
        result += "------------------------------\n";

        result += "Methods:  \n";
        if (!methods.isEmpty()) {
            for (Method m : methods) {
                result += m.toString();
                result += "\n";
            }
        }
        result += "\n------------------------------\n";
        result += "Relationships To Others: \n" + relationshipsToOther.toString() + "\n";
        result += "Relationships From Others: \n" + relationshipsFromOther.toString();
        return result;
    }
}