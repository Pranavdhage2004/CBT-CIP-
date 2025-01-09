import java.util.*;

class Book 
{
    private String title;
    private String author;
    private String year;

    public Book(String title, String author, String year) 
    {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() 
    {
        return title;
    }

    public String getAuthor() 
    {
        return author;
    }

    @Override
    public String toString() 
    {
        return title + " by " + author + " (" + year + ")";
    }
}

class LibraryCatalog 
{
    private Map<String, Book> books;

    public LibraryCatalog() 
    {
        books = new HashMap<>();
    }

    public void addBook(String title, String author, String year) 
    {
        try {
            if (title.isEmpty() || author.isEmpty() || year.isEmpty()) 
            {
                throw new IllegalArgumentException("Title, author, and year cannot be empty.");
            }
            if (books.containsKey(title.toLowerCase())) 
            {
                throw new IllegalArgumentException("A book with this title already exists.");
            }
            books.put(title.toLowerCase(), new Book(title, author, year));
            System.out.println("Book '" + title + "' added successfully.");

        } catch (IllegalArgumentException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchBooks(String query) 
    {
        try {
            if (query.isEmpty()) {
                throw new IllegalArgumentException("Search query cannot be empty.");
            }

            query = query.toLowerCase();
            List<Book> results = new ArrayList<>();

            for (Book book : books.values()) 
            {
                if (book.getTitle().toLowerCase().contains(query) || book.getAuthor().toLowerCase().contains(query)) 
                {
                    results.add(book);
                }
            }

            if (!results.isEmpty()) 
            {
                System.out.println("\nSearch Results:");
                for (Book book : results) 
                {
                    System.out.println(book);
                }
            } else 
            {
                System.out.println("\nNo books found matching the query.");
            }
        } catch (IllegalArgumentException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listBooks() 
    {
        if (!books.isEmpty()) 
        {
            System.out.println("\nLibrary Catalog:");
            for (Book book : books.values()) 
            {
                System.out.println(book);
            }
        } else 
        {
            System.out.println("\nThe library catalog is empty.");
        }
    }
}

public class LibraryCatalogSystem 
{
    public static void main(String[] args) 
    {
        LibraryCatalog catalog = new LibraryCatalog();
        Scanner scanner = new Scanner(System.in);

        while (true) 
        {
            try 
            {
                System.out.println("\nLibrary Catalog System");
                System.out.println("1. Add Book");
                System.out.println("2. Search Books");
                System.out.println("3. List All Books");
                System.out.println("4. Exit");

                System.out.print("Enter your choice : ");
                String choice = scanner.nextLine();

                switch (choice) 
                {
                    case "1":
                        System.out.print("Enter book title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter author name: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter publication year: ");
                        String year = scanner.nextLine();
                        catalog.addBook(title, author, year);
                        break;
                    case "2":
                        System.out.print("Enter title or author to search: ");
                        String query = scanner.nextLine();
                        catalog.searchBooks(query);
                        break;
                    case "3":
                        catalog.listBooks();
                        break;
                    case "4":
                        System.out.println("Exiting the system. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) 
            {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
