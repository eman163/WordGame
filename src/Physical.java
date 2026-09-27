import java.util.Random;

public class Physical implements Award {
    private static final String[] PRIZES = {
            "a cruise for two",
            "a vacation to Tahiti",
            "a ski trip to the Alps",
            "a safari adventure in Africa",
            "a spa retreat in Bali",
            "a year of gas"
    };

    private final Random random;
    private String lastPrize;

    public Physical() {
        random = new Random();
        lastPrize = "";
    }

    public String pickPrize() {
        int prizeNumber = random.nextInt(PRIZES.length);
        lastPrize = PRIZES[prizeNumber];
        return lastPrize;
    }

    public String getLastPrize() {
        return lastPrize;
    }

    @Override
    public int displayWinnings(Players player, boolean isCorrect) {
        if (!isCorrect) {
            pickPrize();
        }
        return 0;
    }
}
