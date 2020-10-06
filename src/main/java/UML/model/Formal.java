package UML.model;

abstract class Formal {

    //The type of a formal parameter.
    private String type;
    //The name of a formal parameter.
    private String name;
    /**
     * Constructs a Formal object.
     */
    public Formal(String type, String name) throws IllegalArgumentException{
        if(name.trim().isEmpty()) {
            throw new IllegalArgumentException("The attribute name cannot be blank.");
        }
        if(type.trim().isEmpty()) {
            throw new IllegalArgumentException("The attribute type cannot be blank.");
        }
        if(name.contains(" ")) {
            throw new IllegalArgumentException("The attribute name cannot contain a space.");
        }
        if(type.contains(" ")) {
            throw new IllegalArgumentException("The attribute type cannot contain a space.");
        }
        this.type = type;
        this.name = name;
    }

    /**
     * Returns the name of the formal parameter.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the type of the formal parameter.
     */
    public String getType() {
        return this.type;
    }

     /**
     * Changes the name of the formal parameter object.
     */
    public void setName (String name) throws IllegalArgumentException{
        if(name.trim().isEmpty()) {
            throw new IllegalArgumentException("The name type cannot be blank.");
        }
        this.name = name;
    }

    /**
     * Changes the name of the formal parameter object.
     */
    public void setType(String type) throws IllegalArgumentException{
        if(type.trim().isEmpty()) {
            throw new IllegalArgumentException("The attribute type cannot be blank.");
        }
        this.type = type;
    }

    /**
     * Returns true if two formal parameters object are equal and false otehrwise.
     */
    public boolean equals(Object other) {
        boolean result = false;
        if(this == other) {
            result = true;
        }
        else if (other == null) {
            result = false;
        }
        else if(!(other instanceof Formal)) { 
            result = false; 
        }
        else {
            Formal object = (Formal) other;
            if(object.getName().equals(this.getName()) && object.getType().equals(this.getType())) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Returns a string reppresentation of a formal parameter.
     */
    public String toString()
    {
        return this.type + " " + this.name;
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
