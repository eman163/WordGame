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
        while (true) {
            String guess = readGuess(player, host);

            if (guess.length() != 1) {
                System.out.println("Please enter one letter at a time.");
                continue;
            }

            char guessChar = guess.charAt(0);
            if (!Character.isLetter(guessChar)) {
                System.out.println("Please enter a letter, not a number or symbol.");
                continue;
            }

            try {
                boolean isCorrect = Phrases.findLetters(guess);

                Award award = isCorrect ? new Money() : (random.nextBoolean() ? new Money() : new Physical());
                int amountChange = award.displayWinnings(player, isCorrect);
                player.setMoney(player.getMoney() + amountChange);

                if (isCorrect) {
                    System.out.println(player.getDisplayName() + " won " + Players.formatCurrency(amountChange));
                } else {
                    System.out.println(player);
                }

                return isCorrect;
            } catch (MultipleLettersException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private String readGuess(Players player, Hosts host) {
        System.out.println(host.getDisplayName() + ": " + player.getDisplayName()
                + ", enter your guess for my phrase (one letter at a time)");
        return input.nextLine().trim();
    }
}
