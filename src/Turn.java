import java.util.Random;

public class Turn {
    private final Random random;
    private boolean showPhysicalPrizeOnFirstWrongGuess;

    public Turn() {
        random = new Random();
        showPhysicalPrizeOnFirstWrongGuess = true;
    }

    public void startNewRound() {
        showPhysicalPrizeOnFirstWrongGuess = true;
    }

    public TurnResult takeTurn(Players player, String guess) {
        if (guess == null) {
            return new TurnResult(false, false, false, "Turn cancelled.", null);
        }

        guess = guess.trim();

        if (guess.length() != 1) {
            return new TurnResult(false, false, false, "Please enter one letter at a time.", null);
        }

        if (!Character.isLetter(guess.charAt(0))) {
            return new TurnResult(false, false, false, "Please enter a letter, not a number or symbol.", null);
        }

        if (!Phrases.hasPhrase()) {
            return new TurnResult(false, false, false, "No phrase has been set yet.", null);
        }

        try {
            boolean foundLetter = Phrases.findLetters(guess);
            if (foundLetter) {
                return handleCorrectGuess(player);
            }
            return handleWrongGuess(player);
        } catch (MultipleLettersException exception) {
            return new TurnResult(false, false, false, exception.getMessage(), null);
        }
    }

    private TurnResult handleCorrectGuess(Players player) {
        Money moneyPrize = new Money();
        int moneyWon = moneyPrize.displayWinnings(player, true);
        player.addMoney(moneyWon);

        String message = "Good guess!"
                + "\nPhrase: " + Phrases.getPlayingPhrase()
                + "\n" + player.getDisplayName() + " won "
                + Players.formatCurrency(moneyWon) + ".";

        return finishTurn(player, true, message, null);
    }

    private TurnResult handleWrongGuess(Players player) {
        boolean awardPhysicalPrize = showPhysicalPrizeOnFirstWrongGuess || !random.nextBoolean();
        showPhysicalPrizeOnFirstWrongGuess = false;

        if (awardPhysicalPrize) {
            Physical physicalPrize = new Physical();
            physicalPrize.displayWinnings(player, false);
            String prizeName = physicalPrize.getLastPrize();

            String message = "Sorry, that letter is not in the phrase."
                    + "\nPhrase: " + Phrases.getPlayingPhrase()
                    + "\n" + player.getDisplayName()
                    + " won a physical prize: " + prizeName + ".";

            return finishTurn(player, false, message, prizeName);
        }

        Money moneyPrize = new Money();
        int moneyLost = moneyPrize.displayWinnings(player, false);
        player.addMoney(moneyLost);

        String message = "Sorry, that letter is not in the phrase."
                + "\nPhrase: " + Phrases.getPlayingPhrase()
                + "\n" + player.getDisplayName() + " lost "
                + Players.formatCurrency(Math.abs(moneyLost)) + ".";

        return finishTurn(player, false, message, null);
    }

    private TurnResult finishTurn(Players player, boolean guessCorrect, String message, String physicalPrizeName) {
        String fullMessage = message
                + "\nCurrent Money: " + Players.formatCurrency(player.getMoney());

        boolean roundWon = Phrases.isSolved();
        if (roundWon) {
            fullMessage += "\nCongratulations, you solved the phrase and won "
                    + Phrases.getWinPrize() + "!";
        }

        return new TurnResult(true, roundWon, guessCorrect, fullMessage, physicalPrizeName);
    }

    public static class TurnResult {
        private final boolean valid;
        private final boolean roundWon;
        private final boolean guessCorrect;
        private final String message;
        private final String physicalPrizeName;

        public TurnResult(boolean valid, boolean roundWon, boolean guessCorrect, String message, String physicalPrizeName) {
            this.valid = valid;
            this.roundWon = roundWon;
            this.guessCorrect = guessCorrect;
            this.message = message;
            this.physicalPrizeName = physicalPrizeName;
        }

        public boolean isValid() {
            return valid;
        }

        public boolean isRoundWon() {
            return roundWon;
        }

        public boolean isGuessCorrect() {
            return guessCorrect;
        }

        public String getMessage() {
            return message;
        }

        public String getPhysicalPrizeName() {
            return physicalPrizeName;
        }
    }

}
