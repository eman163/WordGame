public class Money implements Award {
    private final int winAmount;
    private final int loseAmount;

    public Money() {
        this.winAmount = 1000;
        this.loseAmount = 200;
    }

    @Override
    public int displayWinnings(Players player, boolean isCorrect) {
        if (isCorrect) {
            System.out.println(player.getDisplayName() + " won " + Players.formatCurrency(winAmount));
            return winAmount;
        }

        System.out.println(player.getDisplayName() + " lost " + Players.formatCurrency(loseAmount));
        return -loseAmount;
    }
}

