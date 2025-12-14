import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * LegalAgeTracker
 * Main application class handling the console UI and user input.
 */
public class LegalAgeTracker {

    private static final int MAX_ENTRIES = 100;
    private final History history = new History(MAX_ENTRIES);
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LegalAgeTracker app = new LegalAgeTracker();
        app.run();
    }

    /**
     * Runs the main menu loop.
     */
    public void run() {
        printHeader();

        while (true) {
            try {
                System.out.println("=======================================");
                System.out.println("||         LEGAL AGE TRACKER         ||");
                System.out.println("=======================================");
                System.out.println("| 1. Input                            |");
                System.out.println("| 2. View Data                        |");
                System.out.println("| 3. View History                     |");
                System.out.println("| 4. Delete                           |");
                System.out.println("| 0. Exit                             |");
                System.out.println("+_____________________________________|");
                System.out.print("Choice : ");

                int mainInput = readInt();

                System.out.println();

                switch (mainInput) {
                    case 1:
                        inputLoop();
                        break;
                    case 2:
                        viewDataLoop();
                        break;
                    case 3:
                        viewHistory();
                        break;
                    case 4:
                        deleteMenu();
                        break;
                    case 0:
                        System.out.println("Exiting program. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please choose again.\n");
                }
            } catch (Exception e) {
                // Catch any unexpected exception and continue safely
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // clear buffer
            }
        }
    }

    private void printHeader() {
        System.out.println(" *********************************************************");
        System.out.println("* *");
        System.out.println("* __      __   _                               | |     *");
        System.out.println("* \\ \\    / /__| | ___ ___  _ __ ___   ___  | |     *");
        System.out.println("* \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_  _ \\ / _ \\  |_|     *");
        System.out.println("* \\ V  V /  __/ | (_| (_) | | | | | |  __/           *");
        System.out.println("* \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___| (_)       *");
        System.out.println("* *");
        System.out.println(" *********************************************************");
        System.out.println();
    }

    /**
     * Input loop: repeatedly accept entries until user returns to main menu.
     */
    private void inputLoop() {
        while (true) {
            System.out.println("============================================");
            System.out.println("||              CITY POPULATION           ||");
            System.out.println("||                   AND                  ||");
            System.out.println("||                AGE TRACKER             ||");
            System.out.println("============================================\n");

            String city = promptCity();
            String gender = promptGender();
            int age = promptAge();

            boolean added = history.addEntry(new Entry(city, gender, age));
            if (!added) {
                System.out.println("History full: maximum entries reached (" + MAX_ENTRIES + ").");
            } else {
                System.out.println("Entry added.\n");
            }

            System.out.print("Press 1 to add another entry, or 0 to return to main menu: ");
            int menu = readInt();
            if (menu == 0) break;
            System.out.println();
        }
    }

    /**
     * View aggregated data (counts) loop.
     */
    private void viewDataLoop() {
        while (true) {
            System.out.println("+---------------------------------------------+");
            System.out.println("               POPULATION DATA");
            System.out.println("+---------------------------------------------+\n");

            Stats stats = history.computeStats();

            System.out.println("+============================================+");
            System.out.println("||           BATANGAS POPULATION            ||");
            System.out.println("+============================================+");
            System.out.printf("|| No. Female: %-28d||%n", stats.batangasFemale);
            System.out.printf("|| No. Male: %-30d||%n", stats.batangasMale);
            System.out.printf("|| No. of legal age: %-23d||%n", stats.legalBatangas);
            System.out.println("+============================================+\n");

            System.out.println("+============================================+");
            System.out.println("||           CABUYAO POPULATION             ||");
            System.out.println("+============================================+");
            System.out.printf("|| No. Female: %-28d||%n", stats.cabuyaoFemale);
            System.out.printf("|| No. Male: %-30d||%n", stats.cabuyaoMale);
            System.out.printf("|| No. of legal age: %-23d||%n", stats.legalCabuyao);
            System.out.println("+============================================+\n");

            System.out.println("+============================================+");
            System.out.println("||           CALAMBA POPULATION             ||");
            System.out.println("+============================================+");
            System.out.printf("|| No. Female: %-28d||%n", stats.calambaFemale);
            System.out.printf("|| No. Male: %-30d||%n", stats.calambaMale);
            System.out.printf("|| No. of legal age: %-23d||%n", stats.legalCalamba);
            System.out.println("+============================================+\n");

            System.out.print("Press 0 to return to the main menu: ");
            int menu = readInt();
            if (menu == 0) {
                System.out.println("Thank you for filling up the data!\n");
                break;
            }
        }
    }

    /**
     * Displays sorted history by age (ascending).
     */
    private void viewHistory() {
        if (history.isEmpty()) {
            System.out.println("No history available.\n");
            return;
        }

        List<Entry> sorted = history.getSortedByAge();

        System.out.println();
        System.out.println("+=============================+");
        System.out.println("||          HISTORY          ||");
        System.out.println("+=============================+ ");
        System.out.println();
        System.out.println("+--------------------------------------------+\n");
        for (Entry e : sorted) {
            System.out.printf(" | City: %-10s | Gender: %-6s | Age: %-3d |%n",
                    capitalize(e.getCity()), capitalize(e.getGender()), e.getAge());
        }
        System.out.println("+____________________________________________+\n");

        System.out.println("Press Enter to return to the main menu...");
        scanner.nextLine();
    }

    /**
     * Delete menu allows deleting all data or by city.
     */
    private void deleteMenu() {
        System.out.println("+=============================================+");
        System.out.println("||               DELETE DATA MENU            ||");
        System.out.println("+=============================================+");
        System.out.println("|| 0. Delete ALL data                        ||");
        System.out.println("|| 1. Delete by City                         ||");
        System.out.println("+---------------------------------------------+");
        System.out.print("Choice: ");
        int deleteOption = readInt();

        if (deleteOption == 0) {
            history.clear();
            System.out.println("\n+---------------------------------------------+");
            System.out.println("|      All data has been successfully deleted. |");
            System.out.println("+---------------------------------------------+");
        } else if (deleteOption == 1) {
            System.out.println("+=============================================+");
            System.out.println("||           SELECT CITY TO DELETE           ||");
            System.out.println("+=============================================+");
            System.out.println("|| 1. Batangas                               ||");
            System.out.println("|| 2. Cabuyao                                ||");
            System.out.println("|| 3. Calamba                                ||");
            System.out.println("+---------------------------------------------+");
            System.out.print("Choice: ");
            int cityChoice = readInt();
            String targetCity;
            if (cityChoice == 1) targetCity = "batangas";
            else if (cityChoice == 2) targetCity = "cabuyao";
            else if (cityChoice == 3) targetCity = "calamba";
            else {
                System.out.println("Invalid city selection.\n");
                return;
            }

            history.deleteByCity(targetCity);
            System.out.println("\n+---------------------------------------------+");
            System.out.println("|      Data for " + capitalize(targetCity) + " has been deleted.    |");
            System.out.println("+---------------------------------------------+");
        } else {
            System.out.println("Invalid option.\n");
        }

        System.out.println("Press Enter to return to the main menu...");
        scanner.nextLine();
    }

    // ----------------------------
    // Input helpers & validation
    // ----------------------------
    private String promptCity() {
        while (true) {
            System.out.print("Select your city (Batangas/Cabuyao/Calamba): ");
            String city = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (city.equals("batangas") || city.equals("cabuyao") || city.equals("calamba")) {
                return city;
            }
            System.out.println("Invalid city. Please enter Batangas, Cabuyao, or Calamba.");
        }
    }

    private String promptGender() {
        while (true) {
            System.out.print("Select your gender (Male/Female): ");
            String gender = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (gender.equals("male") || gender.equals("female")) {
                return gender;
            }
            System.out.println("Invalid gender. Please enter Male or Female.");
        }
    }

    private int promptAge() {
        while (true) {
            System.out.print("Enter age: ");
            try {
                int age = readInt();
                if (age < 0) {
                    System.out.println("Age cannot be negative. Try again.");
                    continue;
                }
                return age;
            } catch (InputMismatchException ex) {
                System.out.println("Please enter a valid integer age.");
            }
        }
    }

    /**
     * Reads an integer safely from scanner.
     */
    private int readInt() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                // Check if empty input (just Enter key) was pressed when we expect a number
                if (line.isEmpty()) {
                    continue;
                }
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter an integer: ");
            }
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}