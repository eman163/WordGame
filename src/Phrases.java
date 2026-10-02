import java.util.Random;

public class Phrases {
    public static final String[] PRIZES = {
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
        StringBuilder newPhrase = new StringBuilder();
        boolean foundLetter = false;

        for (int i = 0; i < gamePhrase.length(); i++) {
            char originalLetter = gamePhrase.charAt(i);
            char shownLetter = playingPhrase.charAt(i);

            if (Character.toLowerCase(originalLetter) == letter) {
                newPhrase.append(originalLetter);
                foundLetter = true;
            } else {
                newPhrase.append(shownLetter);
            }
        }

        playingPhrase = newPhrase.toString();

        if (isSolved() && winPrize.isEmpty()) {
            winPrize = getRandomPrize();
        }

        return foundLetter;
    }

    private static String hideLetters(String phrase) {
        StringBuilder hiddenPhrase = new StringBuilder();

        for (int i = 0; i < phrase.length(); i++) {
            char letter = phrase.charAt(i);
            if (Character.isLetter(letter)) {
                hiddenPhrase.append("_");
            } else {
                hiddenPhrase.append(letter);
            }
        }

        return hiddenPhrase.toString();
    }

    public static boolean isPrizeWithPicture(String prizeName) {
        if (prizeName == null || prizeName.isBlank()) {
            return false;
        }

        for (String prize : PRIZES) {
            if (prize.equals(prizeName)) {
                return true;
            }
        }

        return false;
    }

    private static String getRandomPrize() {
        int prizeNumber = random.nextInt(PRIZES.length);
        return PRIZES[prizeNumber];
    }
}
