import java.util.Scanner;

public class GamePlay {
    private static Person person;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    System.out.println("Enter your first name: ");
    String firstname = scanner.nextLine();
    System.out.println("Would you like to to enter a last name? (y/n): ");
    String answer = scanner.nextLine();

    if (answer.equalsIgnoreCase("y")) {
        System.out.println("Enter your last name: ");
        String lastname = scanner.nextLine();
        person = new Person(firstname, lastname);
    }else {
        person = new Person(firstname);
    }

    String playerName = person.getFirstname();

    if (!person.getLastname().isEmpty()) {
        playerName += " " + person.getLastname();
    }
    Numbers numbers = new Numbers();
    numbers.generateNumber();

    boolean correct = false;

    while (!correct) {
        System.out.println(playerName + ", enter your guess: ");
        int guess = scanner.nextInt();

        correct = numbers.compareNumbers(guess);
    }
    scanner.close();
    }

}
