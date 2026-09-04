import java.util.Scanner;

public class Turn {
    private static final int WIN_AMOUNT = 1000;
    private static final int LOSE_AMOUNT = 200;
    private final Scanner input;

    public Turn(Scanner input) {
        this.input = input;
    }

    public boolean takeTurn(Players player, Hosts host) {
        int guess = readGuess(player, host);
        int target = Numbers.getRandomNum();

        if (guess == target) {
            player.setMoney(player.getMoney() + WIN_AMOUNT);

            System.out.println("Congratulations, you guessed the number!");
            System.out.println("You win " + Players.formatCurrency(WIN_AMOUNT));
            System.out.println(player);
            return true;
        }

        if (guess > target) {
            System.out.println("I'm sorry. That guess was too high.");
        } else {
            System.out.println("I'm sorry. That guess was too low.");
        }

        player.setMoney(player.getMoney() - LOSE_AMOUNT);
        System.out.println("You lose " + Players.formatCurrency(LOSE_AMOUNT));
        System.out.println(player);
        return false;
    }

    private int readGuess(Players player, Hosts host) {
        while (true) {
            System.out.println(host.getFirstname() + " says \""
                    + getPlayerDisplayName(player)
                    + ", enter your guess for my random number between 0 and 100\"");
            String line = input.nextLine();

            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private String getPlayerDisplayName(Players player) {
        String lastName = player.getLastname();
        if (lastName == null || lastName.isBlank()) {
            return player.getFirstname();
        }
        return player.getFirstname() + " " + lastName;
    }
}
