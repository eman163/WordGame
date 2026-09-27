import java.util.Random;

public class Turn {
    private final Random random;

    public Turn() {
        random = new Random();
    }

    public TurnResult takeTurn(Players player, String guess) {
        if (guess == null) {
            return new TurnResult(false, false, "Turn cancelled.");
        }

        guess = guess.trim();

        if (guess.length() != 1) {
            return new TurnResult(false, false, "Please enter one letter at a time.");
        }

        if (!Character.isLetter(guess.charAt(0))) {
            return new TurnResult(false, false, "Please enter a letter, not a number or symbol.");
        }

        if (!Phrases.hasPhrase()) {
            return new TurnResult(false, false, "No phrase has been set yet.");
        }

        try {
            boolean foundLetter = Phrases.findLetters(guess);
            String message = buildMessage(player, foundLetter);
            boolean roundWon = Phrases.isSolved();
            return new TurnResult(true, roundWon, message);
        } catch (MultipleLettersException exception) {
            return new TurnResult(false, false, exception.getMessage());
        }
    }

    private String buildMessage(Players player, boolean foundLetter) {
        String message;

        if (foundLetter) {
            Money moneyPrize = new Money();
            int moneyWon = moneyPrize.displayWinnings(player, true);
            player.setMoney(player.getMoney() + moneyWon);

            message = "Good guess!";
            message = message + "\nPhrase: " + Phrases.getPlayingPhrase();
            message = message + "\n" + player.getDisplayName() + " won " + Players.formatCurrency(moneyWon) + ".";
        } else {
            if (random.nextBoolean()) {
                Money moneyPrize = new Money();
                int moneyLost = moneyPrize.displayWinnings(player, false);
                player.setMoney(player.getMoney() + moneyLost);

                message = "Sorry, that letter is not in the phrase.";
                message = message + "\nPhrase: " + Phrases.getPlayingPhrase();
                message = message + "\n" + player.getDisplayName() + " lost " + Players.formatCurrency(Math.abs(moneyLost)) + ".";
            } else {
                Physical physicalPrize = new Physical();
                physicalPrize.displayWinnings(player, false);

                message = "Sorry, that letter is not in the phrase.";
                message = message + "\nPhrase: " + Phrases.getPlayingPhrase();
                message = message + "\n" + player.getDisplayName() + " lost. You could have won "
                        + physicalPrize.getLastPrize() + ".";
            }
        }

        message = message + "\nCurrent Money: " + Players.formatCurrency(player.getMoney());

        if (Phrases.isSolved()) {
            message = message + "\nCongratulations, you solved the phrase and won " + Phrases.getWinPrize() + "!";
        }

        return message;
    }

    public static class TurnResult {
        private final boolean valid;
        private final boolean roundWon;
        private final String message;

        public TurnResult(boolean valid, boolean roundWon, String message) {
            this.valid = valid;
            this.roundWon = roundWon;
            this.message = message;
        }

        public boolean isValid() {
            return valid;
        }

        public boolean isRoundWon() {
            return roundWon;
        }

        public String getMessage() {
            return message;
        }
    }
}
