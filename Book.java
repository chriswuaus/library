import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Book {
  private String title; 
  private String author; 
  private String genre;
  private String serialNumber;
  private Member currMem;
  private ArrayList<Member> rentHistory; //all members who have rented and returned this book
  private boolean rented;

  //Constructs new book object
  public Book(String title, String author, String genre, String serialNumber) {
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.serialNumber = serialNumber;
    this.currMem = null;
    this.rentHistory = new ArrayList<Member>();
    this.rented = false;
  }
  // creates a new list containing books by the specified author
  public static List<Book> filterAuthor​(List<Book> books, String author) {
    if (books == null || author == null) {
      return null;
    }
    ArrayList<Book> collection = new ArrayList<>();
    // iterates through library books and adds books with given genre to arraylist
    for (Book book: books) {
      if (book.getAuthor().equals(author)) {
        collection.add(book);
      }
    }
    // sorts array by serial number
    collection.sort((x, y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
    return collection;
  }
  // creates a new list containing books by the specified genre
  public static List<Book> filterGenre​(List<Book> books, String genre) {
    if (books == null || genre == null) {
      return null;
    }

    ArrayList<Book> collection = new ArrayList<>();
    // iterates through library books and adds books with given genre to arraylist
    for (Book book: books) {
      if (book.getGenre().equals(genre)) {
        collection.add(book);
      }
    } 
    // sorts genre list by serial number
    collection.sort((x, y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
    return collection;
  }
  // returns author of book
  public String getAuthor() {
    return this.author;
  }
  // returns book genre
  public String getGenre() {
    return this.genre;
  }
  // returns book serial number
  public String getSerialNumber() {
    return this.serialNumber;
  }
  // returns book title
  public String getTitle() {
    return this.title;
  }

  // returns whether the book is currently being rented by a member of the library
  public boolean isRented() {
    return this.rented;
  }
  //Formats the Book object to create the long form of its toString().
  public String longString() {
    String endString;
    if (this.rented == false) {
      endString = "\nCurrently available.";
    } else {
      endString = "\nRented by: " + this.currMem.getMemberNumber() + ".";
    }
    return String.format("%s: %s (%s, %s)", serialNumber, title, author, genre) + endString;
  }

  // retrieves the book from the given file based on its serial number 
  public static Book readBook​(String filename, String serialNumber) {
    // return null if filename or serial number does not exist
    if (filename == null || serialNumber == null) {
      return null;
    }
    try{
      File bookFile = new File(filename);
      Scanner sc = new Scanner(bookFile); 
      while (sc.hasNextLine()) {
        String[] bookInfo = sc.nextLine().split(",");
        // if serial number equals the csv serial number, returns a new book
        if (bookInfo[0].equals(serialNumber)) {
          return new Book(bookInfo[1], bookInfo[2], bookInfo[3], bookInfo[0]);
        }
      }
      sc.close();
      // return null if book does not exist
      System.out.println("No such book in file.\n");
      return null;

     } catch(FileNotFoundException e) {
      System.out.println("No such file.\n");
      return null;      
    }
  }
  // reads in the collection of books from the given file
  public static List<Book> readBookCollection​(String filename) {
    if (filename == null) {
      return null;
    }
        
    try{
      File bookFile = new File(filename);
      Scanner sc = new Scanner(bookFile); 
      ArrayList<Book> bookCollection = new ArrayList<Book>();
      // skip csv header
      sc.nextLine();
      while (sc.hasNextLine()){
        String[] data = sc.nextLine().split(",");
        // adds new book to collection
        bookCollection.add(new Book(data[1], data[2], data[3], data[0]));
      }
      return bookCollection;
      
     } catch(FileNotFoundException e){
      System.out.println("No such collection.\n");
      return null;      
    }
  }
  // returns book to library
  public boolean relinquish​(Member member) {
    // if member does not exist, or if there is no current renter
    // or if member has not rented the book, returns false
    if (member == null || currMem == null || currMem.equals(member) == false) {
      return false;
    } 

    this.rented = false;
    // removes current member 
    this.currMem = null;
    rentHistory.add(member);
    return true;
  }
  // sets the current renter to be the given member
  public boolean rent​(Member member) {
    // if member does not exist or if book is already rented, return false
    if (member == null || this.rented == true) {
      return false;
    }
    // sets current member to given member
    this.currMem = member;
    this.rented = true;
    return true;
  }
  // returns the renter history, in chronological order
  public ArrayList<Member> renterHistory() {
    return rentHistory;
  }
  // save the collection of books to the given file
  public static void saveBookCollection​(String filename, Collection<Book> books) {
    // if filename or books do not exist, or there are no books, do nothing
    if (filename == null || books == null || books.size() == 0) {
      return;
    }

    try{
      FileWriter collectionFile = new FileWriter(filename);
      BufferedWriter writer = new BufferedWriter(collectionFile);
      // write header
      writer.write("serialNumber,title,author,genre");
        for (Book book: books) {
          if (book == null) {
            continue;
          }
          String serial = book.getSerialNumber();
          String title = book.getTitle();
          String author = book.getAuthor();
          String genre = book.getGenre();

          String[] data = {serial, title, author, genre};
          writer.newLine();
          // write book information with a comma delimiter to the csv
          writer.write(String.join(",", data));
      }
      writer.close();
      collectionFile.close();
      return;
      
    } catch (IOException E) {
      System.out.println("IO exception!");
    }
    return;
  }
  // formats the Book object to create the short form of its toString()
  public String shortString() {
    return String.format("%s (%s)", this.title, this.author);
  }
}
