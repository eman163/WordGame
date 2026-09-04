public class Hosts extends Person {
    public Hosts(String name) {
        super(name);
    }

    public void randomizeNum() {
        Numbers numbers = new Numbers();
        numbers.generateNumber();
    }
}
