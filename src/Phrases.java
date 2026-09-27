import java.util.Random;

public class Phrases {
    private static final String[] PRIZES = {
            "a cruise for two",
            "a vacation to Tahiti",
            "a ski trip to the Alps",
            "a safari adventure in Africa",
            "a spa retreat in Bali",
            "a year of gas"
    };

    private static final Random random = new Random();
    private static String gamePhrase = "";
    private static String playingPhrase = "";
    private static String winPrize = "";

    public static String getGamePhrase() {
        return gamePhrase;
    }

    public static String getPlayingPhrase() {
        return playingPhrase;
    }

    public static String getWinPrize() {
        return winPrize;
    }

    public static void setGamePhrase(String phrase) {
        if (phrase == null) {
            phrase = "";
        }

        gamePhrase = phrase;
        playingPhrase = hideLetters(phrase);
        winPrize = "";
    }

    public static boolean hasPhrase() {
        return !gamePhrase.isBlank();
    }

    public static boolean isSolved() {
        return hasPhrase() && playingPhrase.equals(gamePhrase);
    }

    public static boolean findLetters(String guess) throws MultipleLettersException {
        if (guess == null) {
            guess = "";
        }

        guess = guess.trim();

        if (guess.length() > 1) {
            throw new MultipleLettersException();
        }

        if (guess.isEmpty() || !hasPhrase()) {
            return false;
        }

        char letter = Character.toLowerCase(guess.charAt(0));
        String updatedPhrase = "";
        boolean foundLetter = false;

        for (int i = 0; i < gamePhrase.length(); i++) {
            char realLetter = gamePhrase.charAt(i);
            char currentLetter = playingPhrase.charAt(i);

            if (Character.toLowerCase(realLetter) == letter) {
                updatedPhrase = updatedPhrase + realLetter;
                foundLetter = true;
            } else {
                updatedPhrase = updatedPhrase + currentLetter;
            }
        }

        playingPhrase = updatedPhrase;

        if (isSolved() && winPrize.isEmpty()) {
            winPrize = getRandomPrize();
        }

        return foundLetter;
    }

    private static String hideLetters(String phrase) {
        String hiddenPhrase = "";

        for (int i = 0; i < phrase.length(); i++) {
            char letter = phrase.charAt(i);
            if (Character.isLetter(letter)) {
                hiddenPhrase = hiddenPhrase + "_";
            } else {
                hiddenPhrase = hiddenPhrase + letter;
            }
        }

        return hiddenPhrase;
    }

    private static String getRandomPrize() {
        int prizeNumber = random.nextInt(PRIZES.length);
        return PRIZES[prizeNumber];
    }
}
