import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Member{
  private String name;
  private String memberNumber;
  private ArrayList<Book> history; // books member has rented and relinquished chronologically
  private ArrayList<Book> renting; // books member is currently renting

  // 	constructs a new Member object given a name and member number
  public Member(String name, String memberNumber) {
    this.name = name;
    this.memberNumber = memberNumber;
    this.history = new ArrayList<Book>();
    this.renting = new ArrayList<Book>();
  }
  // returns the intersection of the members' histories, ordered by serial number
  public static List<Book> commonBooks​(Member[] members) {
    // if members do not exist, do nothing
    if (members == null) {
      return null;
    }
    // union of all books found in member array
    Map<Book, Integer> union = new HashMap<>();
    for (Member member : members) {
      // if members do not exist, do nothing
      if (member == null) {
        return null;
      }
      // add the set of members books to a hashset to eliminate duplicates of books
      Set<Book> memberSet = new HashSet<>();
      memberSet.addAll(member.history());
      for (Book book: memberSet) {
        if (union.containsKey(book)) {
          // if a book is found which has already been recorded
          // the number of copies is incremented by 1
          union.replace(book, union.get(book) + 1);
        } else {
          // adds book to union map with copy number 1
          union.put(book, 1);
        }
      }
    }
    // arraylist of books which are common to all members
    ArrayList<Book> intersection = new ArrayList<Book>();

    for (Map.Entry<Book, Integer> entry: union.entrySet()) {
      // if the book value is equal to the number of members
      // i.e, the book is common to every member, added to 
      // intersection arraylist
      if (entry.getValue() == members.length) {
        intersection.add(entry.getKey());
      }
    }
    // intersection sorted by serial number
    intersection.sort((x, y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
    return intersection;
    
  }

  // returns member number
  public String getMemberNumber() {
    return this.memberNumber;
  }
  
  // returns member name
  public String getName() {
    return this.name;
  }
  // returns the history of books rented, in the order they were returned (oldest first)
  public List<Book> history() {
    return history;
  }

  // returns book to library
  public boolean relinquish​(Book book) {
    // if book does not exist, or if member is not renting this book, return false
    if (book == null || this.renting.contains(book) == false) {
      return false;
    } else {
      // removes book from renting list
      renting.remove(book);
      // adds book to history- books that have been rented and relinquished
      history.add(book);
      book.relinquish(this);
      return true;
    }
  }

  // returns all books rented by the member
  public void relinquishAll() {
    // iterates through all the books and relinquishes them
    for(Book book: renting) {
      book.relinquish(this);
    }
    // adds all books that were rented to history- books that have been rented and relinquished
    history.addAll(renting);
    // clears all the books that have been rented
    renting.clear();
    return;
  }
  // rents the given book
  public boolean rent​(Book book) {
    // if book does not exist or is already rented, return false
    if (book == null || book.isRented() == true){
      return false;
    }
    book.rent(this);
    // adds book to list of currently renting books
    renting.add(book);
    return true;
  }

  // returns list of books the member is renting
  public List<Book> renting() {
    return this.renting;
  }
}
