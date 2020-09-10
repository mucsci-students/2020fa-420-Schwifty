import java.util.Set;
import java.util.MultiMap;

public class Class {
    enum RelationshipType 
    {
        ASSOCIATION, AGGREGATION, GENERALIZATION, COMPOSITION
    }
    private String name;
    //Use sets and maps so we don't have to deal with duplicates.
    private Set<Attribute> attributes;
    private MultiMap<RelationshipType, Class> relationshipsToOther;
    private MultiMap<RelationshipType, Class> relationshipsFromOther;





    public String getName() {
        return this.name;
    }

    public Set<Attribute> getAttributes() {
        return this.attributes;
    }

    public Map<RelationshipType, Class> getRelationshipsToOther() {
        return this.relationshipsToOther;
    }

    public Map<RelationshipType, Class> getRelationshipsFromOther() {
        return this.relationshipsFromOther;
    }


    public void setName(String name) {
        this.name = name;
    }

    public boolean addAttrubute(Attribute theAttribute) {
        //TODO:
        return true;
    }

    public boolean deleteAttribute(Attribute theAttribute) {
        //TODO:
        return true;
    }

    public boolean renameAttribute(Attribute theAttribute, String newName, String newType) {
        //TODO:
        return true;
    }

    public boolean addRelationship(RelationshipType relation, Class theClass) {
        return true;
    }

    public boolean deleteRelationship(RelationshipType relation, Class theClass) {
        return true;
    }

    public boolean equals(Object other) {
        boolean result = false;
        if(this == other) {
            result = true;
        }
        else if (other == null) {
            result = false;
        }
        else if(!(other instanceof Class)) { 
            result = false; 
        }
        else {
            Class object = (Class) other;
            if(object.getName().equals(this.name) &&
             object.getAttributes().equals(this.attributes) &&
             object.getRelationshipsToOther().equals(this.relationshipsToOther) &&
             object.getRelationshipsFromOther().equals(this.relationshipsFromOther)) {
                result = true;
             }
        }
        return result;
    }
}