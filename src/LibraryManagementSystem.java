import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String ISBN;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    // Getters
    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getISBN() {
        return this.ISBN;
    }
}

class Borrower {
    private String name;
    private int ID;

    public Borrower(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.ID;
    }
}

class Transaction {
    private Book book;
    private Borrower borrower;
    private String dueDate;
    private boolean borrowed;
    private boolean returned;

    public Transaction(Book book, Borrower borrower, String dueDate) {
        this.book = book;
        this.borrower = borrower;
        this.dueDate = dueDate;
        this.borrowed = true;
        this.returned = false;
    }

    // Getters
    public Book getBook() {
        return this.book;
    }

    public Borrower getBorrower() {
        return this.borrower;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public boolean isBorrowed() {
        return this.borrowed;
    }

    public boolean isReturned() {
        return this.returned;
    }

    // Mark the book as returned
    public void markReturned() {
        this.returned = true;
    }
}


class Library {
    List<Book> books;
    List<Transaction> transactions;

    public Library() {
        this.books = new ArrayList<>();
        this.transactions = new ArrayList<>();
        loadBooks(); // Load books from file when initializing the library
    }

    public void addBook(Book book) {
        this.books.add(book);
        saveBooks(); // Save the books list after adding a new book
        System.out.println("Book added successfully!");
    }

    public void deleteBook(int index) {
        if (index >= 0 && index < books.size()) {
            books.remove(index);
            saveBooks(); // Save the books list after deleting a book
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Invalid book index.");
        }
    }

    public void borrowBook(Book book, Borrower borrower, String dueDate) {
        Transaction transaction = new Transaction(book, borrower, dueDate);
        this.transactions.add(transaction);
        System.out.println("Book borrowed successfully!");
    }

    public void returnBook(Book book, Borrower borrower) {
        for (Transaction transaction : this.transactions) {
            if (transaction.getBook() == book && transaction.getBorrower() == borrower) {
                this.transactions.remove(transaction);
                System.out.println("Book returned successfully!");
                return;
            }
        }
        System.out.println("Book was not borrowed by this borrower.");
    }

    public void displayBooks() {
        System.out.println("List of Books:");
        for (int i = 0; i < this.books.size(); i++) {
            Book book = this.books.get(i);
            System.out.println("Index: " + i);
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("ISBN: " + book.getISBN());
            System.out.println("---------------------------");
        }
    }

    public void displayTransactions() {
        System.out.println("List of Transactions:");
        for (Transaction transaction : this.transactions) {
            System.out.println("Book: " + transaction.getBook().getTitle());
            System.out.println("Borrower: " + transaction.getBorrower().getName());
            System.out.println("Due Date: " + transaction.getDueDate());
            System.out.println("---------------------------");
        }
    }

    private void saveBooks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("books.txt"))) {
            for (Book book : this.books) {
                writer.println(book.getTitle() + "," + book.getAuthor() + "," + book.getISBN());
            }
        } catch (IOException e) {
            System.err.println("Error occurred while saving books: " + e.getMessage());
        }
    }

    private void loadBooks() {
        try (Scanner scanner = new Scanner(new File("books.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Book book = new Book(parts[0], parts[1], parts[2]);
                    this.books.add(book);
                }
            }
        } catch (FileNotFoundException e) {
            // Ignore if the file doesn't exist initially
        } catch (IOException e) {
            System.err.println("Error occurred while loading books: " + e.getMessage());
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        boolean running = true;
        while (running) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display Books");
            System.out.println("6. Display Transactions");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nAdding a New Book:");
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    String ISBN = scanner.nextLine();
                    Book book = new Book(title, author, ISBN);
                    library.addBook(book);
                    break;
                case 2:
                    System.out.println("\nDeleting a Book:");
                    library.displayBooks();
                    System.out.print("Enter the index of the book to delete: ");
                    int deleteIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    library.deleteBook(deleteIndex);
                    break;
                case 3:
                    System.out.println("\nBorrowing a Book:");
                    library.displayBooks();
                    System.out.print("Enter the index of the book to borrow: ");
                    int bookIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter borrower's name: ");
                    String borrowerName = scanner.nextLine();
                    System.out.print("Enter borrower's ID: ");
                    int borrowerID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter due date (dd/mm/yyyy): ");
                    String dueDate = scanner.nextLine();
                    Borrower borrower = new Borrower(borrowerName, borrowerID);
                    library.borrowBook(library.books.get(bookIndex), borrower, dueDate);
                    break;
                case 4:
                    System.out.println("\nReturning a Book:");
                    library.displayTransactions();
                    System.out.print("Enter the index of the transaction to return: ");
                    int transactionIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter borrower's name: ");
                    String returnBorrowerName = scanner.nextLine();
                    System.out.print("Enter borrower's ID: ");
                    int returnBorrowerID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    library.returnBook(library.transactions.get(transactionIndex).getBook(),
                            new Borrower(returnBorrowerName, returnBorrowerID));
                    break;
                case 5:
                    System.out.println("\nDisplaying Books:");
                    library.displayBooks();
                    break;
                case 6:
                    System.out.println("\nDisplaying Transactions:");
                    library.displayTransactions();
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting the Library Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        scanner.close();
    }
}
