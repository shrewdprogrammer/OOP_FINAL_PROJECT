import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * History
 * Manages the collection of entries and performs operations like 
 * adding, sorting, deleting, and computing statistics.
 */
public class History {
    private final int capacity;
    private final List<Entry> entries;

    public History(int capacity) {
        this.capacity = capacity;
        this.entries = new ArrayList<>();
    }

    /**
     * Adds an entry if capacity not reached.
     * @return true if added, false if full
     */
    public boolean addEntry(Entry entry) {
        if (entries.size() >= capacity) return false;
        entries.add(entry);
        return true;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public void clear() {
        entries.clear();
    }

    public int size() {
        return entries.size();
    }

    /**
     * Returns a sorted copy of entries by age (ascending).
     * We return a copy so the original insertion order is preserved internally.
     */
    public List<Entry> getSortedByAge() {
        List<Entry> copy = new ArrayList<>(entries);
        Collections.sort(copy, Comparator.comparingInt(Entry::getAge));
        return copy;
    }

    /**
     * Deletes all entries associated with the given city.
     */
    public void deleteByCity(String city) {
        String lowerCity = city.toLowerCase(Locale.ROOT);
        entries.removeIf(e -> e.getCity().equals(lowerCity));
    }

    /**
     * Iterates through entries and calculates population stats per city.
     */
    public Stats computeStats() {
        Stats s = new Stats();
        for (Entry e : entries) {
            String city = e.getCity();
            String gender = e.getGender();
            int age = e.getAge();

            if (city.equals("batangas")) {
                if (gender.equals("female")) s.batangasFemale++;
                else if (gender.equals("male")) s.batangasMale++;
                if (age > 17) s.legalBatangas++;
            } 
            else if (city.equals("cabuyao")) {
                if (gender.equals("female")) s.cabuyaoFemale++;
                else if (gender.equals("male")) s.cabuyaoMale++;
                if (age > 17) s.legalCabuyao++;
            } 
            else if (city.equals("calamba")) {
                if (gender.equals("female")) s.calambaFemale++;
                else if (gender.equals("male")) s.calambaMale++;
                if (age > 17) s.legalCalamba++;
            }
        }
        return s;
    }
}