package linkeddata;

import java.util.List;
public class Person extends Entity {

    String first;
    String last;
    String middle;
    List<String> roles;
    List<Entity> related;

    public Person(String label, String iri, String first, String last, String middle, List<String> role, List<Entity> related) {
        super(label, iri);
        this.first = first;
        this.last = last;
        this.middle = middle;
        this.roles = role;
        this.related = related;
    }

    public String getType() {return "Person";}

    public void addRoles(List<String> roles) {
        for (String role : roles) {
            if (!roles.contains(role)) this.roles.add(role);
        }
    }

    public void addRelated(List<Entity> items) {
        for (Entity i : items){
            this.related.add(i);
        }
    }

    public String toText() {
        String text = label;
        for (String role : roles) {
            text += " has role " + role;
        }
        for (Entity rel : related) {
            if (rel != null && rel.getType().equals("Subject")) text += " related to " + rel.toText();
        }
        Collection collection = relatedCollection();
        if (collection != null) text += " is part of " + collection.toText();
        return text;
    }

    public Collection relatedCollection() {
        for (Entity rel : related) {
            Collection coll = rel.relatedCollection();
            if (coll != null) return coll;
        }
        return null;
    }
}