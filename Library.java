import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Library {
  // List of all available commands with associated descriptions
  public static final String HELP_STRING = 
  "EXIT ends the library process\nCOMMANDS outputs this help string\n\nLIST ALL [LONG] "
  + "outputs either the short or long string for all books\nLIST AVAILABLE [LONG] outputs "
  + "either the short of long string for all available books\nNUMBER COPIES outputs the number"
  + " of copies of each book\nLIST GENRES outputs the name of every genre in the system\nLIST "
  + "AUTHORS outputs the name of every author in the system\n\nGENRE <genre> outputs the short "
  + "string of every book with the specified genre\nAUTHOR <author> outputs the short string of"
  + " every book by the specified author\n\nBOOK <serialNumber> [LONG] outputs either the short"
  + " or long string for the specified book\nBOOK HISTORY <serialNumber> outputs the rental "
  + "history of the specified book\n\nMEMBER <memberNumber> outputs the information of the "
  + "specified member\nMEMBER BOOKS <memberNumber> outputs the books currently rented by the "
  + "specified member\nMEMBER HISTORY <memberNumber> outputs the rental history of the specified"
  + " member\n\nRENT <memberNumber> <serialNumber> loans out the specified book to the given "
  + "member\nRELINQUISH <memberNumber> <serialNumber> returns the specified book from the member"
  + "\nRELINQUISH ALL <memberNumber> returns all books rented by the specified member\n\n"
  + "ADD MEMBER <name> adds a member to the system\nADD BOOK <filename> <serialNumber> adds"
  + " a book to the system\n\nADD COLLECTION <filename> adds a collection of books to the "
  + "system\nSAVE COLLECTION <filename> saves the system to a csv file\n\nCOMMON "
  + "<memberNumber1> <memberNumber2> ... outputs the common books in members\' history";

  private ArrayList<Book> books;
  private ArrayList<Member> members;
  private int memNum;

  // constructs a new library object
  public Library() {
    this.members = new ArrayList<>();
    this.books = new ArrayList<>();
    // member number starts at 100000
    this.memNum = 100000;
  }
  // checks if library has any books
  public boolean emptyBooksChecker() {
    if (this.books.size() == 0) {
      System.out.println("No books in system.\n");
      return true;
    }
    return false;
  }
  // returns a book from this.books with the same serial number
  public Book findBook(String serialNumber) {
    // iterates through library books to find and return book
    for (Book book: this.books) {
      if (book.getSerialNumber().equals(serialNumber)) {
        return book;
      }
    }
    // if no books were found, return null
    return null;
  }
  // returns a Member from library member list with the same member number
  public Member findMember(String memberNumber) {
    // check if no members in system
    if (this.members.size() == 0) {
      System.out.println("No members in system.\n");
      return null;
    } 

    // iterates through member list to find and return member
    for (Member member: this.members) {
      if (member.getMemberNumber().equals(memberNumber)) {
        return member;
      }
    }
    // if no member was found, return null
    System.out.println("No such member in system.\n");
    return null;
  }

  // adds a book to the system by reading it from a csv file
  public void addBook​(String bookFile, String serialNumber) {
    // if book or serial number does not exist, do nothing
    if (bookFile == null || serialNumber == null) {
      return;
    }

    // creates new book object from csv information
    Book newBook = Book.readBook​(bookFile, serialNumber);
    // if book could not be created, return and do nothing
    if (newBook == null) {
      return;
    }
    // checks if book already in system
    if (findBook(serialNumber)!= null) {
      System.out.println("Book already exists in system.\n");
    } else{
      // adds book to the system
      books.add(newBook);
      System.out.printf("Successfully added: %s.\n\n", newBook.shortString());
    }
    return;
  }

  // adds the collection of books stored in a csv file to the system
  public void addCollection​(String filename) {
    if (filename == null) {
      return;
    }
    // returns a list of books read from file
    List<Book> collBooks = Book.readBookCollection​(filename);

    // checks if books could not be read from file
    if (collBooks == null) {
      return;
    }
    
    // counts number of successful books added
    int counter = 0;
    for (Book book : collBooks) {
      // if book is already in system, skip
      if (findBook(book.getSerialNumber()) != null) {
        continue;
      } else {
      books.add(book);
      counter++;
      }
    }
    // if no books were found
    if (counter == 0) {
      System.out.println("No books have been added to the system.\n");
    } else {
      // displays the amount of successful additions
      System.out.printf("%d books successfully added.\n\n", counter);
    }
    return;
  }

  // adds member to the system
  public void addMember​(String name) {
    if (name == null) {
      return;
    }
    // creates new member
    Member member = new Member(name, Integer.toString(memNum));
    members.add(member);
    // increments the number for the next member
    memNum++;
    System.out.println("Success.\n");
    return;
  }
  // Prints out all the member numbers of members who have previously rented a book.
  public void bookHistory​(String serialNumber) {
    // checks if serial number valid
    if (serialNumber == null) {
      return;
    }
    // checks if book exists in system
    if (findBook(serialNumber) == null) {
      System.out.println("No such book in system.\n");
      return;
    } else {
      // if book has not been rented before
      if (findBook(serialNumber).renterHistory().size() == 0) {
        System.out.println("No rental history.\n");
        return;
      }
      // prints out all rented members
      for (Member member: findBook(serialNumber).renterHistory()) {
        System.out.println(member.getMemberNumber());
      }
      System.out.println();
    }
    return;
  }
  // Prints out all the books that all members provided have previously rented
  public void common​(String[] memberNumbers) {
    ArrayList<Member> members = new ArrayList<>();
    for (String memNum: memberNumbers) {
      // checks if all members exist in system or no books in system
      if (findMember(memNum) == null || this.emptyBooksChecker()) {
        return;
      }
      // checks for duplicate members in list
      if (members.contains(findMember(memNum))) {
        System.out.println("Duplicate members provided.\n");
        return;
      }
      members.add(findMember(memNum));
    }
    
    // changes arraylist of members to an array 
    Member[] membersCom = new Member[members.size()];
    membersCom = members.toArray(membersCom);
    // creates list of common books between members
    List<Book> comBooks = Member.commonBooks(membersCom);
    // sorts books by serial number
    comBooks.sort((x, y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));

    // checks if there are no common books
    if (comBooks.size() == 0) {
      System.out.println("No common books.\n");
      return;
    }
    // prints out the shortstring of all the common books
    for (Book book: comBooks) {
      System.out.println(book.shortString());
    }
    System.out.println();
    return;
  
  }
  
  // prints out the formatted strings for all books in the system
  public void getAllBooks​(boolean fullString) {
    if (this.emptyBooksChecker()) {
      return;
    }
    for (Book book: books) {
      if (fullString == true) {
        System.out.printf(book.longString()+ "\n\n");
      } else {
        System.out.printf(book.shortString() + "\n");
      }
    }
    if (fullString == false) {
      System.out.println();
    }
    return;
  }
  // prints out all authors in system
  public void getAuthors() {
    if (this.emptyBooksChecker()) {
      return;
    }

    ArrayList<String> authors = new ArrayList<>();
    for (Book book: this.books) {
      // skips duplicate authors
      if (authors.contains(book.getAuthor())) {
        continue;
      }
      authors.add(book.getAuthor());
    }
    // sorts author lexicographically 
    authors.sort((x, y) -> x.compareTo(y));

    // prints out all authors
    for (String author: authors) {
      System.out.println(author);
    }
    System.out.println();
    return;
  }
  // prints out the formatted strings for all available books in the system
  public void getAvailableBooks​(boolean fullString) {
    // checks if no books in system
    if (this.emptyBooksChecker()) {
      return;
    }

    ArrayList<Book> availableBooks = new ArrayList<>();
    for (Book book: this.books) {
        // if book is not rented, added to available books
        if (book.isRented() == false) {
          availableBooks.add(book);
        }
      }
    // checks if no books available
    if (availableBooks.size() == 0) {
      System.out.println("No books available.\n");
      return;
    }
    // prints out formatted available books
    for (Book book: availableBooks) {
      String title;
      if (fullString == true) {
        title = book.longString() + "\n";
      } else {
        title = book.shortString();
      }
      System.out.printf(title + "\n");
    }
    if (fullString == false) {
      System.out.println();
    }
    return;
  }
  // Prints either the short or long string of the specified book.
  public void getBook​(String serialNumber, boolean fullString) {
    // checks if no books in system
    if (this.emptyBooksChecker()) {
      return;
    }
    if (findBook(serialNumber) == null) {
      System.out.println("No such book in system.\n");
      return;
    }

    if (fullString == true) {
      System.out.printf(findBook(serialNumber).longString()+ "\n\n");
    } else {
      System.out.printf(findBook(serialNumber).shortString() + "\n\n");
    }
    return;
  }
  // prints all books in the system by the specified author
  public void getBooksByAuthor​(String author) {
    if (this.emptyBooksChecker()) {
      return;
    }
    // returns list of books by author
    List<Book> authorBooks = Book.filterAuthor(this.books, author);
    if (authorBooks.size() == 0) {
      System.out.printf("No books by %s.\n\n", author);
    } else {
      // prints out all the authored books ordered by serial number
      for (Book book : authorBooks) {
        System.out.printf(book.shortString() + "\n");
      }
      System.out.println();
    }
    return;
  }
  // prints all books in the system with the specified genre
  public void getBooksByGenre​(String genre) {
    if (this.emptyBooksChecker()) {
      return;
    }
    // creates a list of books with genre
    List<Book> genreBooks = Book.filterGenre(this.books, genre);
    if (genreBooks.size() == 0) {
      System.out.printf("No books with genre %s.\n\n", genre);
    } else {
      // prints out all genre books sorted by serial number
      for (Book book : genreBooks) {
        System.out.println(book.shortString());
      }
      System.out.println();
    }
    return;
  }
  // prints out the number of copies of each book in the system
  public void getCopies() {
    if (this.emptyBooksChecker()) {
      return;
    }
    ArrayList<String> shortStrings = new ArrayList<>();
    ArrayList<String> copy = new ArrayList<>(); 
    // adds all short strings from library books to an arraylist
    for (Book book: this.books) {
      shortStrings.add(book.shortString());
    }

    for (Book book: this.books) {
      // finds how many times the shortstring occurs in the arraylist shortstrings 
      int copies = Collections.frequency(shortStrings, book.shortString());
      // creates short string 
      String bookStr = book.shortString() + ": " + String.valueOf(copies);
      // if list of book copies does not already have the short string, add to it
      if (copy.contains(bookStr) == false) {
        copy.add(bookStr);
      }
    }

    // sort books lexicographically
    copy.sort((x, y) -> x.compareTo(y));

    // print out all short strings
    for (String bookStr: copy) {
      System.out.println(bookStr);
    }
    System.out.println();
    return;
  }
  
  // prints out all genres in system
  public void getGenres() {
    if (this.emptyBooksChecker()) {
      return;
    }

    ArrayList<String> genres = new ArrayList<>();
    for (Book book: this.books) {
      // skips duplicate occurrences of genre
      if (genres.contains(book.getGenre())) {
        continue;
      }
      genres.add(book.getGenre());
    }
    // sort genre names lexicographically
    genres.sort((x, y) -> x.compareTo(y));

    for (String genre: genres) {
      System.out.println(genre);
    }
    System.out.println();
    return;
  }
  // prints details of specified member
  public void getMember​(String memberNumber) {
    // checks if member is in the system
    if (findMember(memberNumber) == null) {
      return;
    }
    // print member details
    System.out.printf("%s: %s\n\n", memberNumber, findMember(memberNumber).getName());
    return;
  }
  // Prints a list of all the books a member is currently renting
  public void getMemberBooks​(String memberNumber) {
    // checks if member exists in system 
    if (findMember(memberNumber) == null) {
      return;
    }
    // checks if member has rented any books before
    if (findMember(memberNumber).renting().size() == 0) {
      System.out.println("Member not currently renting.\n");
      return;
    }
    for (Book book: findMember(memberNumber).renting()) {
      System.out.println(book.shortString());
    }
    System.out.println();
    return;
  }
  // prints a list of all the books a member has previously rented 
  public void memberRentalHistory​(String memberNumber) {
    // checks if member does not exist in system
    if (findMember(memberNumber) == null) {
      return;
    }
    // checks if member has rented and relinquished a book before
    if (findMember(memberNumber).history().size() == 0) {
      System.out.println("No rental history for member.\n");
      return;
    }
    for (Book book:findMember(memberNumber).history()) {
      System.out.println(book.shortString());
    }
    System.out.println();
    return;
  }
  // makes a member return all books they are currently renting
  public void relinquishAll​(String memberNumber) {
    // checks if member exists in system
    if (findMember(memberNumber) == null) {
      return;
    }
    findMember(memberNumber).relinquishAll();
    System.out.println("Success.\n");
  }
  // returns a book to the system
  public void relinquishBook​(String memberNumber, String serialNumber) {
    // checks if member does not exist, or if membernumber or serial number are invalid
    if (memberNumber == null || findMember(memberNumber) == null || serialNumber == null) {
      return;
    }
    if (this.emptyBooksChecker()) {
      return;
    }
    if (findBook(serialNumber) == null) { // checks if book exists in system
      System.out.println("No such book in system.\n");
      return;
    } else if (findMember(memberNumber).relinquish(findBook(serialNumber))== false) {
      // checks if book can be relinquished, and if it can be, relinquishes it
      System.out.println("Unable to return book.\n");
      return;
    }
    System.out.println("Success.\n");
    return;
  }
  // loans out a book to a member within the system
  public void rentBook​(String memberNumber, String serialNumber) {
    // checks if member does not exist in system or serial number invalid
    if (findMember(memberNumber) == null || serialNumber == null ) {
      return;
    }
    if (this.emptyBooksChecker()) {
      return;
    }
    if (findBook(serialNumber) == null) {
      System.out.println("No such book in system.\n");
      return;
    }
    // checks if book already being rented
    if (findBook(serialNumber).isRented() == true) {
      System.out.println("Book is currently unavailable.\n");
      return;
    }
    // rents book
    findMember(memberNumber).rent(findBook(serialNumber));
    System.out.println("Success.\n");
    return;
  }
  // Saves the current collection of books in the system to a csv file.
  public void saveCollection​(String filename) {
    if (this.emptyBooksChecker() || filename == null) {
      return;
    }
    Book.saveBookCollection​(filename, this.books);
    System.out.println("Success.\n");
    return;
  }

  public static void main​(String[] args) {
    Library lib = new Library();
    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.printf("user: ");
      String[] line = sc.nextLine().split(" ");
      // sorts library books by serial number every loop
      lib.books.sort((x, y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));

      if (line[0].equalsIgnoreCase("EXIT")) {
        System.out.println("Ending Library process.");
        sc.close();
        return;

      } else if (line[0].equalsIgnoreCase("COMMANDS")) {
        System.out.printf(HELP_STRING + "\n\n");
        continue;

      } else if (line[0].equalsIgnoreCase("LIST") && line[1].equalsIgnoreCase("ALL")) {
        if (line.length == 2){
          lib.getAllBooks​(false);
        } else if (line[2].equalsIgnoreCase("LONG")) {
          lib.getAllBooks​(true);
        }
        continue;

      } else if (line[0].equalsIgnoreCase("LIST") && line[1].equalsIgnoreCase("AVAILABLE")) {
        if (line.length == 2) {
          lib.getAvailableBooks​(false);
          continue;
        } else if (line[2].equalsIgnoreCase("LONG")) {
          lib.getAvailableBooks(true);
        }
        continue;
        
      } else if (line[0].equalsIgnoreCase("NUMBER")&&line[1].equalsIgnoreCase("COPIES")) {
        lib.getCopies();
        continue;

      } else if (line[0].equalsIgnoreCase("LIST") && line[1].equalsIgnoreCase("GENRES")) {
        lib.getGenres();
        continue;

      } else if (line[0].equalsIgnoreCase("LIST") && line[1].equalsIgnoreCase("AUTHORS")) {
        lib.getAuthors();
        continue;

      } else if (line[0].equalsIgnoreCase("GENRE")) {
        // joins command arguments excluding "genre"
        String[] array = Arrays.copyOfRange(line, 1, line.length);
        String genre = String.join(" ", array);
        lib.getBooksByGenre(genre);
        continue;

      } else if (line[0].equalsIgnoreCase("AUTHOR")) {
        // joins command arguments excluding "author"
        String[] array = Arrays.copyOfRange(line, 1, line.length);
        String author = String.join(" ", array);
        lib.getBooksByAuthor​(author);
        continue;

      } else if (line[0].equalsIgnoreCase("BOOK") && line[1].equalsIgnoreCase("HISTORY")) {
        lib.bookHistory(line[2]);
        continue;
        
      } else if (line[0].equalsIgnoreCase("BOOK")) {
        if (line.length == 2){
          lib.getBook(line[1], false);
        } else if (line[2].equalsIgnoreCase("LONG")) {
          lib.getBook(line[1], true);
        }
        continue;
        
      } else if (line[0].equalsIgnoreCase("MEMBER") && line[1].equalsIgnoreCase("BOOKS")) {
        lib.getMemberBooks​(line[2]);
        continue;
        
      } else if (line[0].equalsIgnoreCase("MEMBER") && line[1].equalsIgnoreCase("HISTORY")) {
        lib.memberRentalHistory​(line[2]);
        continue;

      } else if (line[0].equalsIgnoreCase("MEMBER")) {
        lib.getMember(line[1]);
        continue;
        
      } else if (line[0].equalsIgnoreCase("RENT")) {
        lib.rentBook(line[1], line[2]);
        continue;
        
      } else if (line[0].equalsIgnoreCase("RELINQUISH") && line[1].equalsIgnoreCase("ALL")) {
        lib.relinquishAll​(line[2]);
        continue;

      } else if (line[0].equalsIgnoreCase("RELINQUISH")) {
        lib.relinquishBook(line[1], line[2]);
        continue;
        
      } else if (line[0].equalsIgnoreCase("ADD") && line[1].equalsIgnoreCase("MEMBER")) {
        // joins command line arguments excluding "add member"
        String[] array = Arrays.copyOfRange(line, 2, line.length);
        String memName = String.join(" ", array);
        lib.addMember​(memName);
        continue;

      } else if (line[0].equalsIgnoreCase("ADD") && line[1].equalsIgnoreCase("BOOK")) {
        lib.addBook(line[2], line[3]);
        continue;

      } else if (line[0].equalsIgnoreCase("ADD") && line[1].equalsIgnoreCase("COLLECTION")) {
        lib.addCollection​(line[2]);
        continue;

      } else if (line[0].equalsIgnoreCase("SAVE") && line[1].equalsIgnoreCase("COLLECTION")) {
        lib.saveCollection​(line[2]);
        continue;

      } else if (line[0].equalsIgnoreCase("COMMON")) {
        // creates list of members given from command line, excluding "common"
        String[] members = Arrays.copyOfRange(line, 1, line.length);
        lib.common(members);
        continue;
      } 
    }
  }   
}
