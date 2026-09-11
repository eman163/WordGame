import java.util.Random;

public class Phrases {
	private static String gamePhrase;
	private static String playingPhrase;
	private static boolean prizeAwarded;
	private static final String[] PRIZES = {
			"a cruise for two",
			"a vacation to Tahiti",
			"a ski trip to the Alps",
			"a safari adventure in Africa",
			"a spa retreat in Bali",
			"a year of gas"
	};
	private static final Random RANDOM = new Random();

	public static String getGamePhrase() {
		return gamePhrase;
	}

	public static void setGamePhrase(String gamePhrase) {
		Phrases.gamePhrase = gamePhrase == null ? "" : gamePhrase;
		playingPhrase = buildPlayingPhrase(Phrases.gamePhrase);
		prizeAwarded = false;
	}

	public static String getPlayingPhrase() {
		return playingPhrase;
	}

	public static void setPlayingPhrase(String playingPhrase) {
		Phrases.playingPhrase = playingPhrase;
	}

	private static String buildPlayingPhrase(String phrase) {
		StringBuilder hiddenPhrase = new StringBuilder(phrase.length());

		for (int i = 0; i < phrase.length(); i++) {
			char currentChar = phrase.charAt(i);
			if (Character.isLetter(currentChar)) {
				hiddenPhrase.append('_');
			} else {
				hiddenPhrase.append(currentChar);
			}
		}

		return hiddenPhrase.toString();
	}

	public static boolean findLetters(String letter) throws MultipleLettersException {
		if (letter == null) {
			letter = "";
		}

		String guess = letter.trim();
		if (guess.length() > 1) {
			throw new MultipleLettersException();
		}

		if (guess.isEmpty()) {
			System.out.println("Please enter a single letter.");
			return false;
		}

		if (gamePhrase == null || gamePhrase.isEmpty()) {
			System.out.println("No phrase has been set yet.");
			return false;
		}

		if (playingPhrase == null || playingPhrase.length() != gamePhrase.length()) {
			playingPhrase = buildPlayingPhrase(gamePhrase);
		}

		String normalizedPhrase = gamePhrase.toLowerCase();
		String normalizedGuess = guess.toLowerCase();
		char[] revealedPhrase = playingPhrase.toCharArray();
		int index = normalizedPhrase.indexOf(normalizedGuess);
		boolean foundLetter = false;

		while (index != -1) {
			revealedPhrase[index] = gamePhrase.charAt(index);
			foundLetter = true;
			index = normalizedPhrase.indexOf(normalizedGuess, index + 1);
		}

		playingPhrase = new String(revealedPhrase);

		if (playingPhrase.indexOf('_') == -1) {
			announceWin();
			return true;
		}

		if (foundLetter) {
			System.out.println("Good guess! " + playingPhrase);
		} else {
			System.out.println("Sorry, that letter is not in the phrase.");
			System.out.println(playingPhrase);
		}


		return false;
	}

	private static void announceWin() {
		if (!prizeAwarded) {
			System.out.println("Congratulations, you won " + getRandomPrize() + "!");
			prizeAwarded = true;
		}
	}

	private static String getRandomPrize() {
		return PRIZES[RANDOM.nextInt(PRIZES.length)];
	}
}
