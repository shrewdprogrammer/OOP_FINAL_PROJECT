import java.util.Locale;

/**
 * Entry
 * Represents a single record containing city, gender, and age.
 */
public class Entry {
    private final String city;
    private final String gender;
    private final int age;

    public Entry(String city, String gender, int age) {
        // Store data in a normalized format (lowercase) to ensure consistency
        this.city = city.toLowerCase(Locale.ROOT);
        this.gender = gender.toLowerCase(Locale.ROOT);
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}