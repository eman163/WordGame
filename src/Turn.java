import java.util.Random;
import java.util.Scanner;

public class Turn {
    private final Scanner input;
    private final Random random;

    public Turn(Scanner input) {
        this.input = input;
        this.random = new Random();
    }

    public boolean takeTurn(Players player, Hosts host) {
        int guess = readGuess(player, host);
        boolean isCorrect = Numbers.compareNumbers(guess);

        Award award = random.nextBoolean() ? new Money() : new Physical();
        int amountChange = award.displayWinnings(player, isCorrect);
        player.setMoney(player.getMoney() + amountChange);
        System.out.println(player);

        return isCorrect;
    }

    private int readGuess(Players player, Hosts host) {
        while (true) {
            System.out.println(host.getDisplayName() + ": " + player.getDisplayName()
                    + ", enter your guess for my random number between 0 and 100");

            String line = input.nextLine();

            try {
                int guess = Integer.parseInt(line.trim());
                if (guess < 0 || guess > 100) {
                    System.out.println(", enter your guess for my random number between 0 and 100");
                    continue;
                }
                return guess;
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }
}
