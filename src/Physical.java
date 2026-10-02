import java.util.Random;

public class Physical implements Award {
    private static final Random RANDOM = new Random();

    private String lastPrize;

    public Physical() {
        lastPrize = "";
    }

    public String pickPrize() {
        int prizeNumber = RANDOM.nextInt(Phrases.PRIZES.length);
        lastPrize = Phrases.PRIZES[prizeNumber];
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
