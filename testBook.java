public class testBook{
    public static void main(String[] args){
        // filterAuthor​(List<Book> books, String author)
        // - author 1 book
        // - author 10 books
        // - author 0 books
        // - author null
        // - list null 

        // one book filter by author

        // ArrayList<Book> booklist1 = new ArrayList<Book>();
        // booklist1.add(Hp);
        // ArrayList<Book> booklist2 = new ArrayList<Book>();
        // booklist2.add(Hp);
        // ArrayList<Book> booklist3 = new ArrayList<Book>();

        // assert (filterAuthor(booklist1, "jkrow").equals(booklist2);

        // // 0 books filter by author
        // assert (filterAuthor(booklist1, "jkro").equals(booklist3);


        // filterGenre​(List<Book> books, String genre)
        // - list null
        // - genre null
        // - genre 1 book
        // - genre 10 books
        // - genre 0 books

        // getAuthor()
        // - 1 author

        // getGenre()
        // - 1 genre

        // getSerialNumber()
        // - returns serial number

        // getTitle()
        // - 1 book

        // isRented()
        // - 1 book

        // longString()
        // - if rented
        // - if avaliable
        // - if null
        Book Hp = new Book("harry potter", "jkrow", "fantasy", "100000");
        System.out.println(Hp.longString());

        // readBook​(String filename, String serialNumber)
        // - retrieves book
        // - book does not exist
        // - file does not exist

        // readBookCollection​(String filename)
        // - if Works
        // - file does not exist
        // - book does not exist
        // - 1 book
        // - 10 books


        // relinquish​(Member member)
        // - member does not exist
        // - member not current renter
        // - if correct

        // rent​(Member member)
        // - 1 book successful
        // - member does not exist
        // - book rented Already
        
        // renterHistory()
        // - 1 Renter
        // - 15 renters
        // = 0 renters

        // saveBookCollection​(String filename, Collection<Book> books)
        // - file does not exist
        // - collection does not exist
        // - 1 book
        // - 10 books

        // shortString()
        // - 1 version
        // - if null
    }
}

