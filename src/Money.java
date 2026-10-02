public class Money implements Award {
    private static final int WIN_AMOUNT = 1000;
    private static final int LOSE_AMOUNT = 200;

    public int getWinAmount() {
        return WIN_AMOUNT;
    }

    public int getLoseAmount() {
        return LOSE_AMOUNT;
    }

    @Override
    public int displayWinnings(Players player, boolean isCorrect) {
        if (isCorrect) {
            return WIN_AMOUNT;
        }

        return -LOSE_AMOUNT;
    }
}
