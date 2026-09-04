import java.util.Scanner;

public class GamePlay {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Hosts host = new Hosts("Quisqueya");
        host.randomizeNum();

        System.out.println("What is your first name?");
        String firstName = scanner.nextLine();

        System.out.println("Would you like to enter a last name? Leave blank if not.");
        String lastName = scanner.nextLine();

        if (lastName.isEmpty()) {
            Players player = new Players(firstName);
            playLoop(scanner, host, player);
        } else {
            Players player = new Players(firstName, lastName);
            playLoop(scanner, host, player);
        }

        scanner.close();
    }

    private static void playLoop(Scanner scanner, Hosts host, Players player) {

        Turn turn = new Turn(scanner);
        boolean keepPlaying = true;

        while (keepPlaying) {
            boolean correct = false;

            while (!correct) {
                correct = turn.takeTurn(player, host);
            }

            System.out.println("Play another game? (y or n)");
            String playAgain = scanner.nextLine();

            if (playAgain.equalsIgnoreCase("y")) {
                host.randomizeNum();
            } else {
                keepPlaying = false;
            }
        }

    }

}
