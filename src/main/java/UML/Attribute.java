/*
    Author: Tyler, Cory, Dominic, Drew, Chris. 
    Date: 09/08/2020
    Purpose: To define an attribute that consist of a name and type. Added 
    classes as requested by the user. To construct an attribute a name and type must be provided. 
*/
public class Attribute 
{
    //The name of the attribute object.
    private String name;
    //The type of the attribute object.
    private String type;

    /**
     * Constructs an attribute object.
     */
    public Attribute(String name, String type) throws IllegalArgumentException
    {
        if(name.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("The attribute name cannot be blank.");
        }
        if(type.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("The attribute type cannot be blank.");
        }
        this.name = name;
        this.type = type;
    }

    /**
     * Returns the name of the attribute object.
     */
    public String getName() 
    {
        return this.name;
    }

    /**
     * Returns the type of the attribute object.
     */
    public String getType() 
    {
        return this.type;
    }

    /**
     * Changes the name of the attribute object.
     */
    public void setName (String name) throws IllegalArgumentException{
        if(name.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("The name type cannot be blank.");
        }
        this.name = name;
    }

    /**
     * Changes the name of the attribute object.
     */
    public void setType(String type) throws IllegalArgumentException{
        if(type.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("The attribute type cannot be blank.");
        }
        this.type = type;
    }

    /**
     * Returns true if two attribute object are equal and false otehrwise.
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
        else if(!(other instanceof Attribute)) 
        { 
            result = false; 
        }
        else {
            Attribute object = (Attribute) other;
            if(object.getName().equals(this.getName()) && object.getType().equals(this.getType())) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Returns a string reppresentation of an attribute.
     */
    public String toString()
    {
        return this.type + " : " + this.name;
    }

    /**
     * Overrides the hashcode.
     */
    @Override
    public int hashCode() 
    { 
          return this.name.hashCode() + this.type.hashCode();
    } 
}
