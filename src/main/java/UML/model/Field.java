package UML.model;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Defines the structure of a field and methods that act upon it.
 */
public class Field extends Formal 
{   
    //Stores public +, private -, or protected *
    char access;
    
    /**
     * Contructs a new field object.
     */
    public Field(String type, String name, String access) throws IllegalArgumentException 
    {
        super(type, name);
        this.access = getCharFromString(access);
    }

    /**
     * Sets access to specified char given a String.  Defaults to '+'.
     */
    public boolean setAccess(String access)
    {
        boolean isSet;
        if(access.equals("public"))
        {
            temp = '+';
            isSet = true;
        }
        else if(access.equals("private"))
        {
            temp = '-';
            isSet = true;
        }
        else if(access.equals("protected"))
        {
            temp = '*';
            isSet = true;
        }
        else
            isSet = false;
        return isSet;
    }

    /**
     * Returns a char that represents the access of this field.
     * public:    +
     * private:   -
     * protected: *
     */
    public char getAccessChar()
    {
        return this.access;
    }

    /**
     * Returns string representation of access char.
     */
    public String getAccessString()
    {
        String result = "";
        if(access == '+')
            result = "public";
        else if(access == '-')
            result = "private";
        else if(access == '*')
            result = "protected";
        else
            result = "public";

        return result;
    }    
    /**
     * 
     * Returns a char for the requested level of access of this field
     * public:    +
     * private:   - 
     * protected: *
     */
    private char getCharFromString(String accessString)
    {
        char temp;
        if(accessString.equals("public"))
            temp = '+';
        else if(accessString.equals("private"))
            temp = '-';
        else if(accessString.equals("protected"))
            temp = '*';
        //Default to public.
        else
            temp = '+';
        
        return temp;
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
            if(object.getName().equals(this.getName()) && object.getType().equals(this.getType()) && object.getAccess() == this.getAccess()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Returns a string reppresentation of a formal parameter.
     */
    @Override
    public String toString()
    {
        return this.getAccess() + " " + this.getType() + " " + this.getName();
    }
} 