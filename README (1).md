# Library Fine Calculator

A Java console application that calculates library fines. It records the books kept by members, displays all records, searches by member name and sorts records by fine amount. Data is saved to a file.

**Project:** PS No. 46 | **Subject:** Data Structures and Algorithms (DSA)
**University:** Rungta International Skills University, School of Computer Science and Engineering

## Features

| Menu | Feature | Description |
|------|---------|-------------|
| 1 | Add Record | Takes member, book and days kept; validates days; generates the ID (first is F101); calculates the fine; stores the record |
| 2 | Display All | Lists every record with ID, member, book, days and fine |
| 3 | Search by Member | Linear search (case-insensitive) that shows every record of that member |
| 4 | Sort by Fine | Bubble sort, highest fine first |
| 5 | Exit | Saves all records to `fines.txt` and closes the program |

## Fine Rule

- No fine for 14 days or less
- Rs 2 per day for every day beyond 14 days
- Days kept must be 1 or more

```
fine = 0                  if days <= 14
fine = (days - 14) x 2    if days > 14
```

Examples: 20 days gives Rs 12, 30 days gives Rs 32.

## Concepts Used

Array, Searching, Sorting, Methods, File Handling

- Records are stored as objects of a `Record` class in an array, with a count of the records stored
- Searching (linear search) and sorting (bubble sort) are written manually, with no built-in sort or search methods
- The array grows automatically when full

## Requirements

- JDK 8 or higher (JDK 17 or 21 recommended)
- Any terminal, VS Code or IntelliJ IDEA

## How to Run

```bash
javac LibraryFineCalculator.java
java LibraryFineCalculator
```

## Data File

Records are loaded from `fines.txt` when the program starts and saved back to it when you choose Exit (option 5). If the file does not exist, the program starts with an empty list. Each line has the form:

```
ID|Member|Book|Days
```

Example:

```
F101|Riya|Java Basics|20
F102|Sameer|Data Science|30
```

## Sample Run

```
Enter choice: 1
Member: Sameer
Book: Data Science
Days kept: 30
Record added. F102 | Sameer | Data Science | Days: 30 | Fine: Rs 32

Enter choice: 4
---- Sorted by Fine (high to low) ----
1. F102 | Sameer | Fine: Rs 32
2. F101 | Riya | Fine: Rs 12

Enter choice: 3
Member name to search: Riya
Found: F101 | Riya | Java Basics | Days: 20 | Fine: Rs 12
```

## Test Cases

| Case | Input | Expected output |
|------|-------|-----------------|
| 1. Normal | Riya, Java Basics, 20 days | `Record added. F101 \| Riya \| Java Basics \| Days: 20 \| Fine: Rs 12` |
| 2. Boundary | Riya, Java Basics, 14 days | `Record added. F101 \| Riya \| Java Basics \| Days: 14 \| Fine: Rs 0` |
| 3. Invalid | Riya, Java Basics, -3 days | `Invalid days. Days kept must be 1 or more.` |
| 4. Different scenario | Existing F101; add Sameer, Data Science, 30; sort by fine; search Riya | F102 added with Rs 32; sorted list F102, F101; Found: F101 |

All four test cases pass.

## Time Complexity (N = number of records)

| Operation | Complexity |
|-----------|------------|
| Add record | O(1) amortized |
| Display all | O(N) |
| Search by member | O(N) |
| Sort by fine (bubble sort) | O(N^2) worst case, O(N) best case |
| Load / save file | O(N) |

## Project Files

- `LibraryFineCalculator.java` - complete source code (single file)
- `Library_Fine_Calculator_Synopsis.docx` - project synopsis
- `Library_Fine_Calculator.pptx` - project presentation

## Future Scope

- Store due and return dates so days kept are calculated automatically
- Edit and delete records by ID
- Track fine payment status and member details
- Database storage and a graphical user interface

## Author

[Your Name] | ERP ID: [Your ERP ID]
