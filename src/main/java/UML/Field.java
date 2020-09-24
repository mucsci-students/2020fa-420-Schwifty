

public class Field extends Formal 
{   
    /**
     * Contructs a new field object.
     */
    public Field(String type, String name) throws IllegalArgumentException 
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