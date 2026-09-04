import java.util.Scanner;

public class GamePlay {
    private static Players player;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Hosts host = new Hosts("Quesqueya");
        host.randomizeNum();

        System.out.println("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Would you like to enter a last name? (y/n): ");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter your last name: ");
            String lastname = scanner.nextLine();
           player = new Players(firstName,lastname);
        } else {
          player = new Players(firstName);
        }
        Turn turn = new Turn();
        boolean keepPlaying = true;
        while (keepPlaying)
        {
            boolean correct = false;

            while (!correct) {
                correct = turn.takeTurn(player,host);
            }
            System.out.println("\nWould you like to play again? (y/n)");
            String playAgain = scanner.nextLine();

            if (playAgain.equalsIgnoreCase("y")) {
                host.randomizeNum();
            }else {
                keepPlaying = false;
            }
        }
        System.out.println("\nThanks for playing");
        System.out.println(player);

        scanner.close();
    }

}
