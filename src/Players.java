import java.text.NumberFormat;
import java.util.Locale;

public class Players extends Person {
    private int money;

    public Players(String name) {
        super(name);
        this.money = 1000;
    }

    public Players(String firstName, String lastname) {
        super(firstName, lastname);
        this.money = 1000;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
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
        return getPlayerDisplayName() + ": " + formatCurrency(money);
    }

    private String getPlayerDisplayName() {
        String lastName = getLastname();
        if (lastName == null || lastName.isBlank()) {
            return getFirstname();
        }
        return getFirstname() + " " + lastName;
    }
}
