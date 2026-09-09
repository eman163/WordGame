public class Numbers {
    private static int randomNum;

    public static int getRandomNum() {
        return randomNum;
    }

    public static void setRandomNum(int randomNum) {
        Numbers.randomNum = randomNum;
    }

    public static void generateNumber() {
        randomNum = (int) (Math.random() * 101);
    }

    public static boolean compareNumbers(int guess) {
        if (guess == randomNum) {
            System.out.println("Congratulations, you guessed the number!");
            return true;
        } else if (guess > randomNum) {
            System.out.println("I'm sorry. That guess was too high.");
            return false;
        } else {
            System.out.println("I'm sorry. That guess was too low.");
            return false;
        }
    }
}
