import java.util.ArrayList;

public class Class {
    private String name;
    private ArrayList<Attribute> attributes;
    private ArrayList<Class> aggregation;
    private ArrayList<Class> composition;
    private ArrayList<Class> association;
    private ArrayList<Class> generalization;

    //TODO: Make getters
    public String getName() {
        return this.name;
    }

    public ArrayList<Attribute> getAttributes() {
        return this.attributes;
    }
    
    public ArrayList<Class> getAggregation() {
        return this.aggregation;
    }

    public ArrayList<Class> getComposition() {
        return this.composition;
    }

    public ArrayList<Class> getAssociation() {
        return this.association;
    }
    
    public ArrayList<Class> getGeneralization() {
        return this.generalization;
    }




    //TODO: Make setters
    public void setName(String name) {
        this.name = name;
    }


}
