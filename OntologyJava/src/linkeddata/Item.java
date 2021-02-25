package linkeddata;

public class Item extends Entity {

    Collection isPartOf;
    String coverage;
    String date;
    String description;
    String identifier;
    String rights;
    Subject subject;
    String title;
    String type;
    String mediaType;

    public Item(String label, String iri, Collection isPartOf, String coverage, String date, String desc, String id,
                String rights, Subject subject, String title, String type, String mediaType) {
        super(label, iri);
        this.isPartOf = isPartOf;
        this.coverage = coverage;
        this.date = date;
        this.description = desc;
        this.identifier = id;
        this.rights = rights;
        this.subject = subject;
        this.title = title;
        this.type = type;
        this.mediaType = mediaType;
    }

    public String getType() {return "Item";}

    public String toText() {
        String text = label;
        text += " described as " + description;
        text += " covers " + coverage;
        text += " date " + date;
        text += " has subject " + subject.toText();
        text += " of type " + type;
        text += " of type " + mediaType;
        return text;
    }

    public Collection relatedCollection() {
        return isPartOf;
    }

}
