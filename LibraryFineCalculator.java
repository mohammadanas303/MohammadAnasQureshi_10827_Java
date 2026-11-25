import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Library Fine Calculator (PS No. 46)
 * Rungta International Skills University - School of Computer Science and Engineering
 *
 * Console application to record books kept by library members, calculate
 * fines, display all records, search by member name and sort by fine.
 * Data is persisted in fines.txt (loaded at start, saved on exit).
 *
 * Concepts: Array, Searching, Sorting, Methods, File Handling.
 * Sorting and searching logic is written manually (no built-in sort/search).
 */
public class LibraryFineCalculator {

    // ---------- Constants ----------
    static final String FILE_NAME = "fines.txt";
    static final int FREE_DAYS = 14;      // no fine up to 14 days
    static final int FINE_PER_DAY = 2;    // Rs 2 per day beyond 14 days
    static final int FIRST_ID = 101;      // first generated ID is F101

    // ---------- Record class ----------
    static class Record {
        String id;
        String member;
        String book;
        int days;
        int fine;

        Record(String id, String member, String book, int days) {
            this.id = id;
            this.member = member;
            this.book = book;
            this.days = days;
            this.fine = calculateFine(days);
        }

        String details() {
            return id + " | " + member + " | " + book + " | Days: " + days + " | Fine: Rs " + fine;
        }
    }

    // ---------- Data store (array + count) ----------
    static Record[] records = new Record[10];
    static int count = 0;
    static int nextIdNumber = FIRST_ID;

    // ---------- Main ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadFromFile();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt(sc, "Enter choice: ");
            switch (choice) {
                case 1:
                    addRecord(sc);
                    break;
                case 2:
                    displayAll();
                    break;
                case 3:
                    searchByMember(sc);
                    break;
                case 4:
                    sortByFine();
                    break;
                case 5:
                    saveToFile();
                    System.out.println("Data saved to " + FILE_NAME + ". Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-5.");
            }
        }
        sc.close();
    }

    // ---------- Menu ----------
    static void printMenu() {
        System.out.println();
        System.out.println("===== LIBRARY FINE CALCULATOR =====");
        System.out.println("1. Add Record");
        System.out.println("2. Display All");
        System.out.println("3. Search by Member");
        System.out.println("4. Sort by Fine (high to low)");
        System.out.println("5. Exit");
    }

    // ---------- Fine logic ----------
    static int calculateFine(int days) {
        if (days <= FREE_DAYS) {
            return 0;
        }
        return (days - FREE_DAYS) * FINE_PER_DAY;
    }

    // ---------- 1. Add record ----------
    static void addRecord(Scanner sc) {
        System.out.print("Member: ");
        String member = clean(sc.nextLine());
        System.out.print("Book: ");
        String book = clean(sc.nextLine());
        int days = readInt(sc, "Days kept: ");

        if (member.isEmpty() || book.isEmpty()) {
            System.out.println("Member and book name cannot be empty.");
            return;
        }
        if (days < 1) {
            System.out.println("Invalid days. Days kept must be 1 or more.");
            return;
        }

        String id = "F" + nextIdNumber;
        nextIdNumber++;
        Record r = new Record(id, member, book, days);
        append(r);
        System.out.println("Record added. " + r.details());
    }

    // Adds a record to the array, growing it manually when full.
    static void append(Record r) {
        if (count == records.length) {
            Record[] bigger = new Record[records.length * 2];
            for (int i = 0; i < count; i++) {
                bigger[i] = records[i];
            }
            records = bigger;
        }
        records[count] = r;
        count++;
    }

    // ---------- 2. Display all ----------
    static void displayAll() {
        if (count == 0) {
            System.out.println("No records found.");
            return;
        }
        System.out.println("---- All Records (" + count + ") ----");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + records[i].details());
        }
    }

    // ---------- 3. Search by member (linear search, case-insensitive) ----------
    static void searchByMember(Scanner sc) {
        System.out.print("Member name to search: ");
        String name = clean(sc.nextLine());
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (records[i].member.equalsIgnoreCase(name)) {
                System.out.println("Found: " + records[i].details());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No record found for member: " + name);
        }
    }

    // ---------- 4. Sort by fine, high to low (bubble sort, stable) ----------
    static void sortByFine() {
        if (count == 0) {
            System.out.println("No records to sort.");
            return;
        }
        for (int pass = 0; pass < count - 1; pass++) {
            boolean swapped = false;
            for (int j = 0; j < count - 1 - pass; j++) {
                if (records[j].fine < records[j + 1].fine) {
                    Record temp = records[j];
                    records[j] = records[j + 1];
                    records[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        System.out.println("---- Sorted by Fine (high to low) ----");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + records[i].id + " | " + records[i].member
                    + " | Fine: Rs " + records[i].fine);
        }
    }

    // ---------- File handling ----------
    // File format, one record per line: ID|Member|Book|Days
    static void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] p = line.split("\\|");
                if (p.length < 4) {
                    continue; // skip malformed line
                }
                try {
                    int days = Integer.parseInt(p[3].trim());
                    Record r = new Record(p[0].trim(), p[1].trim(), p[2].trim(), days);
                    append(r);
                    int num = Integer.parseInt(r.id.substring(1));
                    if (num >= nextIdNumber) {
                        nextIdNumber = num + 1;
                    }
                } catch (NumberFormatException e) {
                    // skip malformed line
                }
            }
        } catch (IOException e) {
            // File not found on first run: start with an empty list.
        }
    }

    static void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < count; i++) {
                pw.println(records[i].id + "|" + records[i].member + "|"
                        + records[i].book + "|" + records[i].days);
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // ---------- Helpers ----------
    // Trims input and removes the '|' separator so the file format stays intact.
    static String clean(String s) {
        return s.replace("|", " ").trim();
    }

    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }
}
