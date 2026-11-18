package app;

import app.model.Book;
import app.model.Criteria.BookCriteria;
import app.model.Criteria.BookSort;
import app.model.Criteria.BookView;
import app.model.Page;
import app.repository.file.FileBookRepository;
import app.service.BookService;

import java.util.List;

public class TestMemberA {
    public static void main(String[] args) {
        BookService bookService = new BookService(new FileBookRepository(), null);

        try {
            System.out.println("1. THEM DAU SACH & BAN SAO ");
            Book b1 = new Book("S001", "LAP TRINH JAVA CO BAN", "Nguyen Van A", "CNTT", 2023, 120000);
            Book b2 = new Book("S002", "Cau truc du lieu", "Tran Thi B", "CNTT", 2022, 150000);
            Book b3 = new Book("S003", "Dac nhan tam", "Dale Carnegie", "Ky nang", 2020, 95000);

            bookService.addBook(b1, 3);
            bookService.addBook(b2, 2);
            bookService.addBook(b3, 0);

            System.out.println("2. TEST TC-10: THEM 5 BAN SAO CHO S001");
            List<String> newCopies = bookService.addCopies("S001", 5);
            System.out.println("Cac ma vua sinh: " + newCopies);

            System.out.println("\n3. TEST TC-09: TIM SACH 'Java', CHI CON BAN RANH");
            BookCriteria criteria = new BookCriteria("Java", null, null, null, null, true, BookSort.TITLE);
            Page<BookView> result = bookService.search(criteria, 1, 5);
            System.out.printf("Trang %d/%d (Tong: %d ket qua)%n", result.page(), result.totalPages(), result.totalItems());
            result.items().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
}
