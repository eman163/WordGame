import java.util.Scanner;

public class GamePlay {
    private static final int PLAYER_COUNT = 3;
    private final Players[] currentPlayers = new Players[PLAYER_COUNT];

    public static void main(String[] args) {
        GamePlay gamePlay = new GamePlay();
        gamePlay.startGame();
    }

    private void startGame() {
        Scanner scanner = new Scanner(System.in);

        Hosts host = new Hosts("Quisqueya");
        host.randomizeNum();

        for (int i = 0; i < currentPlayers.length; i++) {
            System.out.println("Player " + (i + 1) + ": What is your first name?");
            String firstName = scanner.nextLine();

            System.out.println("Player " + (i + 1) + ": Would you like to enter a last name? Leave blank if not.");
            String lastName = scanner.nextLine();

            if (lastName.isEmpty()) {
                currentPlayers[i] = new Players(firstName);
            } else {
                currentPlayers[i] = new Players(firstName, lastName);
            }
        }

        playLoop(scanner, host);

        scanner.close();
    }

    private void playLoop(Scanner scanner, Hosts host) {

        Turn turn = new Turn(scanner);
        boolean keepPlaying = true;

        while (keepPlaying) {
            boolean correct = false;
            int playerIndex = 0;

            while (!correct) {
                correct = turn.takeTurn(currentPlayers[playerIndex], host);
                playerIndex = (playerIndex + 1) % currentPlayers.length;
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
