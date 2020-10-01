package UML.model;

public class Parameter extends Formal 
{
    /**
     * Contructs a new parameter object.
     */
    public Parameter(String type, String name) throws IllegalArgumentException 
    {
        super(type, name);
    }

    public boolean equals(Object other) {
        boolean result = false;
        if(this == other) {
            result = true;
        }
        else if (other == null) {
            result = false;
        }
        else if(!(other instanceof Parameter)) { 
            result = false; 
        }
        else {
            Parameter object = (Parameter) other;
            if(object.getName().equals(this.getName()) && object.getType().equals(this.getType())) {
                result = true;
            }
        }
        return result;
    }
}

