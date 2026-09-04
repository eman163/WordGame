public class Players extends Person{
public int money;
public Players(String name){
    super(name);
    this.money=1000;
}

    public Players(String firstName, String lastname) {
        super(firstName, lastname);
    }

    public int getMoney(){
    return money;
}
public void setMoney(int money){
    this.money=money;
}

    @Override
    public String toString() {
        return "Player Name: " + getFirstname()  +  getLastname() + ", Money: $" + money;
    }
}
