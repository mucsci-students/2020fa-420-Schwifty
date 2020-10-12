package UML.model;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Defines the structure of a field and methods that act upon it.
 */
public class Field extends Formal 
{   
    /**
     * Contructs a new field object.
     */
    public Field(String type, String name) throws IllegalArgumentException 
    {
        super(type, name);
    }

    /**
     * Returns true if the objects are equal, false otherwise.
     */
    public boolean equals(Object other) {
        boolean result = false;
        if(this == other) {
            result = true;
        }
        else if (other == null) {
            result = false;
        }
        else if(!(other instanceof Field)) { 
            result = false; 
        }
        else {
            Field object = (Field) other;
            if(object.getName().equals(this.getName()) && object.getType().equals(this.getType())) {
                result = true;
            }
        }
        return result;
    }
} 