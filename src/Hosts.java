import java.util.Scanner;

public class Hosts extends Person {
    public Hosts(String name) {
        super(name);
    }

    public void randomizeNum(String phrase) {
        Phrases.setGamePhrase(phrase);
    }

    public void choosePhrase(String phrase) {
        Phrases.setGamePhrase(phrase);
    }

    public void setGamePhrase(String phrase) {
        Phrases.setGamePhrase(phrase);
    }

    public void promptForPhrase(Scanner scanner) {
        while (true) {
            System.out.println(getDisplayName() + ": Enter a phrase for the players to guess:");
            String phrase = scanner.nextLine();

            if (phrase.isBlank()) {
                System.out.println("Please enter a non-empty phrase.");
                continue;
            }

            Phrases.setGamePhrase(phrase);
            break;
        }
    }
}
