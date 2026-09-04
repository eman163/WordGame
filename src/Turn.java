import java.util.Scanner;

public class Turn {

    private final int WIN_AMOUNT = 200;
    private final int LOSE_AMOUNT = 100;

    public boolean takeTurn(Players player, Hosts host) {

        Scanner input = new Scanner(System.in);

        System.out.print(host.getFirstname()
                + ": " + player.getFirstname() + player.getLastname()
                + ", enter your guess for my random number between 0 and 100: ");

        int guess = input.nextInt();
        int target = Numbers.getRandomNum();

        if (guess == target) {
            player.setMoney(player.getMoney() - WIN_AMOUNT);

            System.out.println("Congratulations! " + player.getFirstname() + " guessed correctly and wins!");

            System.out.println(player);

            return true;
        } else {

            player.setMoney(player.getMoney() - LOSE_AMOUNT);

            System.out.println(player);

            if (guess > target) {
                System.out.println("Your guess was too high. ");
            } else {
                System.out.println("Your guess was too low. ");
            }
            return false;
        }
    }
}
