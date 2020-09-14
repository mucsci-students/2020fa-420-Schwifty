/*
    Author: 
    Date: 09/08/2020
    Purpose: 
*/
import java.util.Set;
import javafx.util.Pair;

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
    private ArrayList<Pair <RelationshipType, Class>> relationshipsToOther;
    //The relationships from another class object to this one.
    private ArrayList<Pair <RelationshipType, Class>> relationshipsFromOther;



    /**
     * Constructs a class object that takes in a parameter for the name of the class.
     */
     public Class(String name) 
     {
         this.name = name;
         this.attributes = new Set<Attribute>();
         this.relationshipsToOther = new ArrayList<Pair <RelationshipType, Class> >();
         this.relationshipsFromOther = new ArrayList<Pair <RelationshipType, Class> >();
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
    public ArrayList<Pair <RelationshipType, Class>> getRelationshipsToOther() {
        return this.relationshipsToOther;
    }

    /**
     * Returns the relationships from another class object to this one.
     */
    public ArrayList<Pair <RelationshipType, Class>> getRelationshipsFromOther() {
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
        for (Atrribute a : attributes)
        {
            if a.getName().equals(name)
            {
                return false;
            }
        }
        Atrribute newAttr = new Attribute(name, type);
        attributes.add(name, type);
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
            if a.getName().equals(name)
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
            if a.getName().equals(oldName)
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
    public boolean addRelationshipToOther(RelationshipType relation, Class theClass) {
        //Remember to add the relationship for the other class too.
        return true;
    }

    /**
     * Adds a relationship from another class object to this one.
     */
    public boolean addRelationshipFromOther(RelationshipType relation, Class theClass) {
        //Remember to add the relatinship for the other class too.
        return true;
    }

    /**
     * Deletes a relationship from this class object to another.
     */
    public boolean deleteRelationshipToOther(RelationshipType relation, Class theClass) {
        //Remember to delete the relatinship for the other class too.
        return true;
    }

    /**
     * Deletes a relationship from another class object to this one.
     */
    public boolean deleteRelationshipFromOther(RelationshipType relation, Class theClass) {
        //Remember to delete the relatinship for the other class too.
        return true;
    }

    /**
     * Returns true if two class object are equal and false otehrwise.
     */
    public boolean equals(Object other) {
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
             object.getAttributes().equals(this.attributes) &&
             object.getRelationshipsToOther().equals(this.relationshipsToOther) &&
             object.getRelationshipsFromOther().equals(this.relationshipsFromOther)) {
                result = true;
             }
        }
        return result;
    }

    public String toString()
    {

    }
}