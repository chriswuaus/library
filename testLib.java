public class testLib{
    public static void main(String[] args){
        addBook​(String bookFile, String serialNumber)
        - file does not exist
        - book does not exist in file
        - book already exists 
        - successful adding

        addCollection​(String filename)
        - file does not exist
        - no books can be added (empty)
        - all books already exist
        - all books new
        - 10 books already exist 5 books new
        
        addMember​(String name)
        - adds member
        
        bookHistory​(String serialNumber)
        - book does not exist
        - book has not been rented
        - 1 Member
        - 10 Members
        
        common​(String[] memberNumbers)
        - no members in system
        - no books in system
        - one member does not exist
        - 2 members do not exist
        - all members do not exist
        - 1 duplicate member
        - 5 duplicate members
        - no common books
        - 1 common book
        - 10 common books
        
        getAllBooks​(boolean fullString)
        - no books  
        - 1 book short
        - 10 books short
        - 1 book long
        - 10 books long

        getAuthors()
        - no books
        - 1 author
        - 10 authors
        
        getAvailableBooks​(boolean fullString)
        - 1 book long
        - 10 books long
        - 1 book short
        - 10 books short
        - no books
        - no books (all rented)
        
        getBook​(String serialNumber, boolean fullString)
        - long book
        - short book
        - no books in system
        - book does not exist
        
        getBooksByAuthor​(String author)
        - no books
        - no books by author
        - 1 books
        - 10 books
        
        getBooksByGenre​(String genre)
        - no books in system
        - no books with genre
        - 1 books
        - 10 books
        
        getCopies()
        - 0 copies
        - 1 copy
        - 10 copies
        
        getGenres()
        - no books
        - 1 genre
        - 10 genres
        
        getMember​(String memberNumber)
        - no members in system
        - member does not exist
        - successful
        
        getMemberBooks​(String memberNumber)
        - no members in system
        - member does not exist
        - member not renting any books
        - successful
        
        main​(String[] args)
        
        memberRentalHistory​(String memberNumber)
        - no members in system
        - member does not exist
        - no rental history for member
        - successful
        
        relinquishAll​(String memberNumber)
        - no members in system
        - member does not exist
        - successful
        
        relinquishBook​(String memberNumber, String serialNumber)
        - no members in system
        - no books in system
        - member does not exist
        - book does not exist
        - book not loaned out by the member
        - successful return
        
        rentBook​(String memberNumber, String serialNumber)
        - no members in system
        - no books
        - member does not exist
        - book does not exist
        - book unavaliable
        - successful loaning
        
        run()
        
        saveCollection​(String filename)
        - no books in system
        - success
        - bad formatting
    }
}
