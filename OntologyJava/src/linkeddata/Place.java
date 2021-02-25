package linkeddata;

public class Place extends Entity {

    public Place (String label, String iri) {
        super(label, iri);
    }

    public String getType() {return "Place";}

}
