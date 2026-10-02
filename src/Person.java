public class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName) {
        this(firstName, "");
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public String getDisplayName() {
        if (lastName == null || lastName.trim().isEmpty()) {
            return firstName;
        }

        return firstName + " " + lastName;
    }
}
