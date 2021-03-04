package linkeddata;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class Ontology {

    private Map<String, BibSeries> bibSeries;
    private Map<String, Collection> collections;
    private Map<String, Event> events;
    private Map<String, Item> items;
    private Map<String, Name> names;
    private Map<String, Person> people;
    private Map<String, Place> places;
    private Map<String, Series> series;
    private Map<String, Subject> subjects;

    public Ontology(){
        bibSeries = new HashMap<>();
        collections = new HashMap<>();
        events = new HashMap<>();
        items = new HashMap<>();
        names = new HashMap<>();
        people = new HashMap<>();
        places = new HashMap<>();
        series = new HashMap<>();
        subjects = new HashMap<>();
    }

    public void addObject(String label, BibSeries obj) { bibSeries.put(label, obj); }
    public void addObject(String label, Collection obj) { collections.put(label, obj); }
    public void addObject(String label, Event obj) { events.put(label, obj); }
    public void addObject(String label, Item obj) { items.put(label, obj); }
    public void addObject(String label, Name obj) { names.put(label, obj); }
    public void addObject(String label, Person obj) { people.put(label, obj); }
    public void addObject(String label, Place obj) { places.put(label, obj); }
    public void addObject(String label, Series obj) { series.put(label, obj); }
    public void addObject(String label, Subject obj) { subjects.put(label, obj); }

    public Collection getCollection(String label) { return collections.get(label); }
    public Person getPerson(String label) { return people.get(label); }

    public boolean personExists(String label) { return (people.keySet().contains(label)); }
    public boolean subjectExists(String label) { return (subjects.keySet().contains(label)); }

    public Item getItem(String label) {
        if (items.get(label) != null) return items.get(label);
        else {
            for (String s : items.keySet()) {
                if (s.contains(label)) return items.get(s);
                else if (s.contains(label.replace("\"", "\'"))) return items.get(s);
            }
            return null;
        }
    }

    public Subject getSubject(String label) {
        return subjects.get(label);
    }

    public Map<String, Set<Person>> sortPeopleByCollection() {
        Set<Person> none = new HashSet<>();
        Set<Person> becker = new HashSet<>();
        Set<Person> belfer = new HashSet<>();
        Set<Person> koppel = new HashSet<>();

        for (String label : people.keySet()) {
            if (label.equals("Unknown")) continue;
            Person p = people.get(label);
            Collection c = p.relatedCollection();
            if (c == null) none.add(p);
            else if (c.getLabel().contains("Becker")) becker.add(p);
            else if (c.getLabel().contains("Belfer")) belfer.add(p);
            else if (c.getLabel().contains("Koppel")) koppel.add(p);
        }

        Map<String, Set<Person>> sets = new HashMap<>();
        sets.put("becker", becker);
        sets.put("belfer", belfer);
        sets.put("koppel", koppel);
        sets.put("none", none);

        return sets;
    }

    List<String> readMatchedPeople() {
        List<String> people = new ArrayList<>();

        try {
            FileReader file = new FileReader("matched_names.txt");
            BufferedReader buff = new BufferedReader(file);

            String name = buff.readLine();
            while (name != null) {
                people.add(name);
                name = buff.readLine();
            }

            Collections.sort(people);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return people;
    }

    void writePersonSet(String fileName, Set<Person> set) {

        List<String> matchedPeople = readMatchedPeople();

        try {
            FileWriter file = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(file);
            for (Person p : set) {
                String label = p.getLabel();
                if (matchedPeople.contains(label)) writer.write(people.get(label).toText() + "\n");
            }
            writer.close();
            file.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void peopleTextToFile() {

        Map<String, Set<Person>> collection_sets = sortPeopleByCollection();

        //writePersonSet("becker_people.txt", collection_sets.get("becker"));
        //System.out.println("Successfully wrote Becker.");

        //writePersonSet("belfer_people.txt", collection_sets.get("belfer"));
        //System.out.println("Successfully wrote Belfer.");

        writePersonSet("koppel_people.txt", collection_sets.get("koppel"));
        System.out.println("Successfully wrote Koppel.");

        //writePersonSet("no_match_people.txt", collection_sets.get("none"));
        //System.out.println("Successfully wrote No Match.");

    }



}
