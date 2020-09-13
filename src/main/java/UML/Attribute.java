/*
    Author: 
    Date: 09/08/2020
    Purpose: 
*/
public class Attribute {
    //The name of the attribute object.
    private String name;
    //The type of the attribute object.
    private String type;

    /**
     * Constructs an attribute object.
     */
    public Attribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Returns the name of the attribute object.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the type of the attribute object.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Changes the name of the attribute object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Changes the name of the attribute object.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns true if two attribute object are equal and false otehrwise.
     */
    public boolean equals(Object other) {
        boolean result = false;
        if(this == other) {
            result = true;
        }
        else if (other == null) {
            result = false;
        }
        else if(!(other instanceof Attribute)) { 
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