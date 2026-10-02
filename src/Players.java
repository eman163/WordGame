import java.text.NumberFormat;
import java.util.Locale;

public class Players extends Person {
    private static final int STARTING_MONEY = 1000;

    private int money;

    public Players(String name) {
        super(name);
        money = STARTING_MONEY;
    }

    public Players(String firstName, String lastName) {
        super(firstName, lastName);
        money = STARTING_MONEY;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int amount) {
        money = money + amount;
    }

    public static String formatCurrency(int amount) {
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        if (amount < 0) {
            return "-" + currency.format(Math.abs(amount));
        }
        return currency.format(amount);
    }

    @Override
    public String toString() {
        return getDisplayName() + ": " + formatCurrency(money);
    }
}
