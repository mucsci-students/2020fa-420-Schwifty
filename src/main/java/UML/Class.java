
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
import java.util.Map;

//Holds the options for relationships between classes.
enum RelationshipType {
    ASSOCIATION, AGGREGATION, GENERALIZATION, COMPOSITION
}

public class Class {

    // The name of the class object.
    private String name;
    // A set containing the fields of a class object.
    private Set<Field> fields;
    // The relationships from this class object to another.
    private Map<String, RelationshipType> relationshipsToOther;
    // The relationships from another class object to this one.
    private Map<String, RelationshipType> relationshipsFromOther;
    // A set containing the class's methods.
    private Set<Method> methods;

    /**
     * Constructs a class object that takes in a parameter for the name of the
     * class.
     */
    public Class(String name) throws IllegalArgumentException {
        // Don't allow empty string/only whitespace
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("The class name cannot be blank.");
        }
        if (name.contains(" ")){
            throw new IllegalArgumentException("The class name cannot cantain a space.");
        }
        this.name = name;
        this.fields = new HashSet<Field>();
        this.relationshipsToOther = new HashMap<String, RelationshipType>();
        this.relationshipsFromOther = new HashMap<String, RelationshipType>();
        this.methods = new HashSet<Method>();
    }

    /**
     * Returns the name of the class object.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a set of the fields of the class object.
     */
    public Set<Field> getFields() {
        return this.fields;
    }

    public Set<Method> getMethods() {
        return this.methods;
    }

    /**
     * Returns the relationships from this class object to another.
     */
    public Map<String, RelationshipType> getRelationshipsToOther() {
        return this.relationshipsToOther;
    }

    /**
     * Returns the relationships from another class object to this one.
     */
    public Map<String, RelationshipType> getRelationshipsFromOther() {
        return this.relationshipsFromOther;
    }

    /**
     * Changes the name of the class object.
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("The class name cannot be blank.");
        }
        if (name.contains(" ")) {
            throw new IllegalArgumentException("The class name cannot contains spaces.");
        }
        this.name = name;
    }

    /**
     * Adds a field the the class object.
     */
    public boolean addField(String type, String name) throws IllegalArgumentException {
        // If name is found, return false...atrribute already created
        for (Field f : fields) {
            if (f.getName().equals(name)) {
                return false;
            }
        }
        Field newField = new Field(type, name);
        fields.add(newField);
        return true;
    }

    /**
     * Deletes a field from the class object.
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
     * Renames a field of the class object.
     */
    public boolean renameField(String oldName, String newName) throws IllegalArgumentException 
    {
        for (Field a : fields) {
            if (a.getName().equals(oldName)) {
                // Must remove and add so that the Field has correct hashcode
                String type = a.getType();
                this.fields.remove(a);
                Field toAdd = new Field(type, newName);
                this.fields.add(toAdd);
                return true;
            }
        }
        return false;
    }
    // TODO: Add ability to change the type of an atrribute...also add that to GUI.

    /**
     * Add parameter for parameters??
     */
    public boolean addMethod(String type, String name) throws IllegalArgumentException 
    {
        // If name is found, return false...atrribute already created
        for (Method m : methods) {
            if (m.getName().equals(name)) {
                return false;
            }
        }
        Method newMethod = new Method(type, name);
        methods.add(newMethod);
        return true;
    }

    /**
     * Add params and type to determine which to delete?
     */
    public boolean deleteMethod(String type, String name, ArrayList<Parameter> params) 
    {
        Method method = new Method(type, name, params);
        return methods.remove(method);
    }
    
    public boolean renameMethod(String type, String oldName, ArrayList<Parameter> params, String newName) throws IllegalArgumentException
    {
        if(newName.conatins(" "))
            throw new IllegalArgumentException("A method name cannot contain a space.");
        if(newName.trim().isEmpty())
            throw new IllegalArgumentException("A method name cannot be empty.");

        Method method = new Method(type, oldName, params);
        boolean deleted = methods.remove(method);
        if(deleted) 
        {
            Method newMethod = new Method(type, newName, params);
            methods.add(newMethod);
        }
        return deleted;
    }//package parameter

    /**
     * Adds a relationship from this class object to another.
     */
    public boolean addRelationshipToOther(RelationshipType relation, Class aClass) throws IllegalArgumentException {
        // If relationship with a class and itself, throw exception.
        if (this.equals(aClass)) {
            throw new IllegalArgumentException("A class cannot have a relationship with itself.");

        }
        // If relationship already exists between the two classes, don't overwrite.
        if (!relationshipsToOther.containsKey(aClass.name)) {
            relationshipsToOther.put(aClass.name, relation);
            aClass.relationshipsFromOther.put(this.name, relation);
            return true;
        }
        return false;
    }

    /**
     * Adds a relationship from another class object to this one.
     */
    public boolean addRelationshipFromOther(RelationshipType relation, Class aClass) throws IllegalArgumentException {
        if (this.equals(aClass)) {
            throw new IllegalArgumentException("A class cannot have a relationship with itself.");

        }
        // If relationship already exists between the two classes, don't overwrite.
        if (!relationshipsFromOther.containsKey(aClass.name)) {
            relationshipsFromOther.put(aClass.name, relation);
            aClass.relationshipsToOther.put(this.name, relation);
            return true;
        }
        return false;
    }

    /**
     * Deletes a relationship from this class object to another.
     */
    public boolean deleteRelationshipToOther(RelationshipType relation, Class aClass) {
        boolean removedToOther = relationshipsToOther.remove(aClass.name, relation);
        boolean removedFromOther = aClass.relationshipsFromOther.remove(this.name, relation);
        return removedToOther && removedFromOther;
    }

    /**
     * Deletes a relationship from another class object to this one.
     */
    public boolean deleteRelationshipFromOther(RelationshipType relation, Class aClass) {
        boolean removedFromOther = relationshipsFromOther.remove(aClass.name, relation);
        boolean removedToOther = aClass.relationshipsToOther.remove(this.name, relation);
        return removedFromOther && removedToOther;
    }

    /**
     * Returns true if two class object are equal and false otehrwise.
     */
    public boolean equals(Object other) {
        boolean result = false;
        if (this == other) {
            result = true;
        } else if (other == null) {
            result = false;
        } else if (!(other instanceof Class)) {
            result = false;
        } else {
            Class object = (Class) other;
            boolean attEqual = true;
            // Check if both sets contain the same objects since equals() won't work
            for (Field att : this.fields) {
                if (!object.fields.contains(att)) {
                    attEqual = false;
                }
            }
            for (Field att : object.fields) {
                if (!this.fields.contains(att)) {
                    attEqual = false;
                }
            }
            if (object.name.equals(this.name) && attEqual
                    && object.relationshipsToOther.equals(this.relationshipsToOther)
                    && object.relationshipsFromOther.equals(this.relationshipsFromOther)) {
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
        result += "\n------------------------------\n";
        result += "Relationships To Others: \n" + relationshipsToOther.toString();
        result += "Relationships From Others: \n" + relationshipsFromOther.toString();
        return result;
    }
}