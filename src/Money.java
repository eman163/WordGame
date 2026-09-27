public class Money implements Award {
    private final int winAmount;
    private final int loseAmount;

    public Money() {
        winAmount = 1000;
        loseAmount = 200;
    }

    public int getWinAmount() {
        return winAmount;
    }

    public int getLoseAmount() {
        return loseAmount;
    }

    @Override
    public int displayWinnings(Players player, boolean isCorrect) {
        if (isCorrect) {
            return winAmount;
        }

        return -loseAmount;
    }
}
