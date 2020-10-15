package UML.model;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Defines the structure of a method and methods that act upon it.
 */
import java.util.ArrayList;

public class Method extends Formal 
{
    //The parameters of the method.
    private ArrayList<Parameter> params;
    //Stores public +, private -, or protected *
    private char access;

    /**
     * Contructs a method object.
     */
    public Method(String type, String name, ArrayList<Parameter> params, String access) throws IllegalArgumentException
    {
        super(type, name);
        this.params = params;
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
            this.access = '+';
            isSet = true;
        }
        else if(access.equals("private"))
        {
            this.access = '-';
            isSet = true;
        }
        else if(access.equals("protected"))
        {
            this.access = '*';
            isSet = true;
        }
        else
            isSet = false;
        return isSet;
    }

    /**
     * Returns a char that represents the access of this method.
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
     * Returns a char for the requested level of access of this method.
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
     * Returns an ArrayList of the method's parameters.
     */
    public ArrayList<Parameter> getParams() 
    {
        return this.params;
    }
    
    /**
     * Sets params to given array list of Parameter.
     */
    public void setParams(ArrayList<Parameter> parameters)
    {
        this.params = parameters;
    }
    /**
     * Adds a parameter to the method's ArrayList of parameters.
     */
    public boolean addParam(Parameter param) throws IllegalArgumentException
    {
        if(param.getName().trim().isEmpty())
            throw new IllegalArgumentException("The parameter name cannot be blank.");
        
        if(param.getType().trim().isEmpty()) 
            throw new IllegalArgumentException("The parameter type cannot be blank.");
            
        if(param.getName().contains(" ")) 
            throw new IllegalArgumentException("The parameter name cannot contain a space.");

        if(param.getType().contains(" "))
            throw new IllegalArgumentException("The parameter type cannot contain a space.");
        for(Parameter p : params)
        {
            if(p.getName().equals(param.getName()))
                return false;
        }

        if(!this.params.contains(param))
        {
            this.params.add(param);
            return true;
        }
        return false;
    }
    
    /**
     * Deletes a parameter from the method's ArrayList of parameters.
     */    
    public boolean deleteParam(Parameter param) 
    {
        return this.params.remove(param);
    }

    /**
     * Returns true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if(this == other) {
            result = true;
        }
        else if (other == null) {
            result = false;
        }
        else if(!(other instanceof Method)) { 
            result = false; 
        }
        else {
            Method object = (Method) other;
            boolean equal = true;
            //Check if both ArrayLists contain the same objects since equals() won't work
            for(Parameter att : this.params) {
                if(!object.params.contains(att)) {
                    equal = false;
                }
            }
            for(Parameter att : object.params) {
                if(!this.params.contains(att)) {
                    equal = false;
                }
            }
            if(object.getName().equals(this.getName()) && object.getType().equals(this.getType()) && object.getAccessChar() == this.getAccessChar() && equal) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Returns a string representation of a method.
     */
    @Override
    public String toString() 
    {
        String result = "";
        result += "Method: ";
        result += this.getAccessChar() + " " + this.getType() + " " + this.getName();
        result += " ( ";

        for(int counter = 0; counter < params.size() - 1; counter++)
        {
            result += params.get(counter).toString() + " , ";
        }
        if(params.size() > 0)
            result += params.get(params.size() - 1).toString();
        result += " )";
        return result;
    }

    @Override
    public int hashCode()
    {
        return this.getName().hashCode() + this.getType().hashCode() + this.getParams().hashCode() + this.getAccessString().hashCode();
    }
}
