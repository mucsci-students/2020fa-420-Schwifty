/*
    Author: Tyler, Cory, Dominic, Drew, 
    Date: 09/08/2020
    Purpose: 
*/
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Class {
    //Holds the options for relationships between classes.
    enum RelationshipType 
    {
        ASSOCIATION, AGGREGATION, GENERALIZATION, COMPOSITION
    }
    //The name of the class object.
    private String name;
    //A set containing the attributes of a class object.
    private Set<Attribute> attributes;
    //The relationships from this class object to another.
    private Map<String, RelationshipType> relationshipsToOther;
    //The relationships from another class object to this one.
    private Map<String, RelationshipType> relationshipsFromOther;



    /**
     * Constructs a class object that takes in a parameter for the name of the class.
     */
     public Class(String name) 
     {
        this.name = name;
        this.attributes = new HashSet<Attribute>();
        this.relationshipsToOther = new HashMap<String, RelationshipType>();
        this.relationshipsFromOther = new HashMap<String, RelationshipType>();
     }

    /**
     * Returns the name of the class object.
     */
    public String getName() 
    {
        return this.name;
    }

    /**
     * Returns a set of the attributes of the class object.
     */
    public Set<Attribute> getAttributes() 
    {
        return this.attributes;
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
     * Changes the name of the class object.
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * Adds an attribute the the class object.
     */
    public boolean addAttribute(String type, String name) 
    {
        //If name is found, return false...atrribute already created
        for (Attribute a : attributes)
        {
            if (a.getName().equals(name))
            {
                return false;
            }
        }
        Attribute newAttr = new Attribute(name, type);
        attributes.add(newAttr);
        return true;
    }

    /**
     * Deletes an attribute from the class object.
     */
    public boolean deleteAttribute(String name) 
    {
        //TODO:
        for (Attribute a : attributes)
        {
            if (a.getName().equals(name))
            {
                attributes.remove(a);
                return true;
            }
        }
        return false;
    }

    /**
     * Renames an attribute of the class object.
     */
    public boolean renameAttribute(String oldName, String newName, String newType) 
    {
        //TODO:
        for (Attribute a : attributes)
        {
            if (a.getName().equals(oldName))
            {
                a.setName(newName);
                return true;
            }
        }
        return false;
    }
    //TODO: Add ability to change the type of an atrribute...also add that to GUI.

    /**
     * Adds a relationship from this class object to another.
     */
    public boolean addRelationshipToOther(RelationshipType relation, Class aClass) 
    {
        //Remember to add the relationship for the other class too.
        relationshipsToOther.put(aClass.name, relation);
        aClass.relationshipsFromOther.put(this.name, relation);
        return true;
    }

    /**
     * Adds a relationship from another class object to this one.
     */
    public boolean addRelationshipFromOther(RelationshipType relation, Class aClass) 
    {
        //Remember to add the relatinship for the other class too.
        relationshipsFromOther.put(aClass.name, relation);
        aClass.relationshipsToOther.put(this.name, relation);
        return true;
    }

    /**
     * Deletes a relationship from this class object to another.
     */
    public boolean deleteRelationshipToOther(RelationshipType relation, Class aClass) 
    {
        boolean removedToOther = relationshipsToOther.remove(aClass.name, relation);
        boolean removedFromOther = aClass.relationshipsFromOther.remove(this.name, relation);
        //Remember to delete the relatinship for the other class too.
        return removedToOther && removedFromOther;
    }

    /**
     * Deletes a relationship from another class object to this one.
     */
    public boolean deleteRelationshipFromOther(RelationshipType relation, Class aClass) 
    {
        boolean removedFromOther = relationshipsFromOther.remove(aClass.name, relation);
        boolean removedToOther = aClass.relationshipsToOther.remove(this.name, relation);
        //Remember to delete the relatinship for the other class too.
        return removedFromOther && removedToOther;
    }

    /**
     * Returns true if two class object are equal and false otehrwise.
     */
    public boolean equals(Object other) 
    {
        boolean result = false;
        if(this == other) {
            result = true;
        }
        else if (other == null) {
            result = false;
        }
        else if(!(other instanceof Class)) { 
            result = false; 
        }
        else {
            Class object = (Class) other;
            if(object.getName().equals(this.name) &&
             object.attributes.equals(this.attributes) &&
             object.relationshipsToOther.equals(this.relationshipsToOther) &&
             object.relationshipsFromOther.equals(this.relationshipsFromOther)) {
                result = true;
             }
        }
        return result;
    }

    /**
     * Returns a string representation of a class object.    
     */
    public String toString()
    {
        String result = "";
        result += "Class name: " + this.name + "\n";
        result += "------------------------------";
        result += "Attribute Names: " + attributes.toString() + "\n";
        result += "------------------------------";
        result += "Relationships To Others: \n" + relationshipsToOther.toString() + "\n";
        result += "Relationships From Others: \n" + relationshipsFromOther.toString() + "\n";
        return result;
    }
}