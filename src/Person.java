public class Person {
    private String firstname;
    private String lastname;

    public String getFirstname() {
    return firstname;
}
public void setFirstname(String firstname) {
        this.firstname = firstname;
}
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Person(String firstname) {
        this.firstname = firstname;
        this.lastname = "";
    }

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getDisplayName() {
        if (lastname == null || lastname.isBlank()) {
            return firstname;
        }
        return firstname + " " + lastname;
    }
}
