import java.util.ArrayList;

public class Method extends Formal 
{
    //The parameters of the method.
    private ArrayList<Parameter> params;

    /**
     * Contructs a method object.
     */
    public Method(String type, String name) throws IllegalArgumentException 
    {
        super(type, name);
        this.params = new ArrayList<Parameter>();
    }
    
    /**
     * Returns an ArrayList of the method's parameters.
     */
    public ArrayList<Parameter> getParams() 
    {
        return this.params;
    }

    /**
     * Sets the params ArrayList equal to the parameter.
     */
    public void setParams(ArrayList<Parameter> toSet) 
    {
        this.params = toSet;
    }
    
    /**
     * Adds a parameter to the method's ArrayList of parameters.
     */
    public void addParam(Parameter param) throws IllegalArgumentException
    {
        if(param.getName().trim().isEmpty())
            throw new IllegalArgumentException("The parameter name cannot be blank.");
        
        if(param.getType().trim().isEmpty()) 
            throw new IllegalArgumentException("The parameter type cannot be blank.");
            
        if(param.getName().contains(" ")) 
            throw new IllegalArgumentException("The parameter name cannot contain a space.");

        if(param.getType().contains(" "))
            throw new IllegalArgumentException("The parameter type cannot contain a space.");

        if(!this.params.contains(param))
            this.params.add(param);
    }
    
    /**
     * Deletes a parameter from the method's ArrayList of parameters.
     */    
    public boolean deleteParam(Parameter param) 
    {
        return this.params.remove(param);
    }

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
            if(object.getName().equals(this.getName()) && object.getType().equals(this.getType()) && equal) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString() 
    {
        String result = "Parameters: ";
        for(Parameter p : this.getParams())
        {
            result += p.toString() + " | ";
        }
        result += "\nMethod: ";
        result += this.getType() + " : " + this.getName();
        return result;
    }

    @Override
    public int hashCode()
    {
        return this.getName().hashCode() + this.getType().hashCode() + this.params.hashCode();
    }
}
