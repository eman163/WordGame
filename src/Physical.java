import java.util.Random;

public class Physical implements Award {
    private final String[] prizes = {
            "a cruise for two",
            "a vacation to Tahiti",
            "a ski trip to the Alps",
            "a safari adventure in Africa",
            "a spa retreat in Bali",
            "a year of gas"
    };
    private final Random random = new Random();

    public int getRandomPrize() {
        return random.nextInt(prizes.length);
    }

    @Override
    public int displayWinnings(Players player, boolean isCorrect) {
        int prizeIndex = getRandomPrize();
        String prize = prizes[prizeIndex];

        if (isCorrect) {
            return 0;
        }

        System.out.println(player.getDisplayName() + " lost. You could have won " + prize + ".");
        return 0;
    }
}

