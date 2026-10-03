public class Hosts extends Person {
    public Hosts(String name) {
        super(name);
    }

    public void setGamePhrase(String phrase) {
        Phrases.setGamePhrase(phrase);
    }
}
