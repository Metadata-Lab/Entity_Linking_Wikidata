package linkeddata;

public class BibSeries extends Entity {

    public BibSeries(String label, String iri){
        super(label, iri);
    }

    public String getType() {return "Bibliographic Series";}

}
