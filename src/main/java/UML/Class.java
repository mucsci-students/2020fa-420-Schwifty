
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
enum RelationshipType 
{
    ASSOCIATION, AGGREGATION, GENERALIZATION, COMPOSITION
} 

public class Class 
{

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
     public Class(String name) throws IllegalArgumentException
     {
        //Don't allow empty string/only whitespace
        if(name.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("The class name cannot be blank.");
        }
        if(name.contains(" ")) 
        {
            throw new IllegalArgumentException("The class name cannot cantain a space.");
        }
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
    public void setName(String name) throws IllegalArgumentException
    {
        if(name.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("The class name cannot be blank.");
        }
        if(name.contains(" ")) 
        {
            throw new IllegalArgumentException("The class name cannot contains spaces.");
        }
        this.name = name;
    }

    /**
     * Adds an attribute the the class object.
     */
    public boolean addAttribute(String type, String name) throws IllegalArgumentException
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
    public boolean renameAttribute(String oldName, String newName) throws IllegalArgumentException
    {
        for (Attribute a : attributes)
        {
            if (a.getName().equals(oldName))
            {
                //Must remove and add so that the Attribute has correct hashcode
                String type = a.getType();
                this.attributes.remove(a);
                Attribute toAdd = new Attribute(newName, type);
                this.attributes.add(toAdd);
                return true;
            }
        }
        return false;
    }
    //TODO: Add ability to change the type of an atrribute...also add that to GUI.

    /**
     * Adds a relationship from this class object to another.
     */
    public boolean addRelationshipToOther(RelationshipType relation, Class aClass) throws IllegalArgumentException
    {
        //If relationship with a class and itself, throw exception.
        if (this.equals(aClass)) 
        {
            throw new IllegalArgumentException("A class cannot have a relationship with itself.");
        
        }
        //If relationship already exists between the two classes, don't overwrite.
        if(!relationshipsToOther.containsKey(aClass.name))
        {
            relationshipsToOther.put(aClass.name, relation);
            aClass.relationshipsFromOther.put(this.name, relation);
            return true;
        }
        return false;
    }

    /**
     * Adds a relationship from another class object to this one.
     */
    public boolean addRelationshipFromOther(RelationshipType relation, Class aClass) throws IllegalArgumentException
    {
        if (this.equals(aClass)) 
        {
            throw new IllegalArgumentException("A class cannot have a relationship with itself.");
        
        }
        //If relationship already exists between the two classes, don't overwrite.
        if(!relationshipsFromOther.containsKey(aClass.name))
        {
            relationshipsFromOther.put(aClass.name, relation);
            aClass.relationshipsToOther.put(this.name, relation);
            return true;
        }
        return false;
    }

    /**
     * Deletes a relationship from this class object to another.
     */
    public boolean deleteRelationshipToOther(RelationshipType relation, Class aClass) 
    {
        boolean removedToOther = relationshipsToOther.remove(aClass.name, relation);
        boolean removedFromOther = aClass.relationshipsFromOther.remove(this.name, relation);
        return removedToOther && removedFromOther;
    }

    /**
     * Deletes a relationship from another class object to this one.
     */
    public boolean deleteRelationshipFromOther(RelationshipType relation, Class aClass) 
    {
        boolean removedFromOther = relationshipsFromOther.remove(aClass.name, relation);
        boolean removedToOther = aClass.relationshipsToOther.remove(this.name, relation);
        return removedFromOther && removedToOther;
    }

    /**
     * Returns true if two class object are equal and false otehrwise.
     */
    public boolean equals(Object other) 
    {
        boolean result = false;
        if(this == other) 
        {
            result = true;
        }
        else if (other == null) 
        {
            result = false;
        }
        else if(!(other instanceof Class)) 
        { 
            result = false; 
        }
        else {
            Class object = (Class) other;
            boolean attEqual = true;
            //Check if both sets contain the same objects since equals() won't work
            for(Attribute att : this.attributes) 
            {
                if(!object.attributes.contains(att)) 
                {
                    attEqual = false;
                }
            }
            for(Attribute att : object.attributes) 
            {
                if(!this.attributes.contains(att)) 
                {
                    attEqual = false;
                }
            }
            if(object.name.equals(this.name) &&
             attEqual &&
             object.relationshipsToOther.equals(this.relationshipsToOther) &&
             object.relationshipsFromOther.equals(this.relationshipsFromOther)) 
            {
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
        result += "------------------------------\n";
        result += "Attribute Names: \n"; // + attributes.get(0).getName() + "\n";
        if(!attributes.isEmpty())
        {
            for (Attribute attribute : attributes) 
            {
                result += attribute.toString();
                result += "\n";
            }
        }
        result += "\n------------------------------";
        result += "Relationships To Others: \n" + relationshipsToOther.toString();
        result += "Relationships From Others: \n" + relationshipsFromOther.toString();
        return result;
    }
}