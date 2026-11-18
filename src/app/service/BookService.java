package app.service;

import app.exception.BusinessException;
import app.exception.DuplicateIdException;
import app.exception.NotFoundException;
import app.model.Book;
import app.model.Criteria.BookCriteria;
import app.model.Criteria.BookView;
import app.model.Page;
import app.repository.BookRepository;
import app.repository.LoanRepository;

import java.util.List;

public class BookService {
    private final BookRepository bookRepo;
    private final LoanRepository loanRepo; // Chờ C/D cài đặt, dùng để check BR-09

    public BookService(BookRepository bookRepo, LoanRepository loanRepo) {
        this.bookRepo = bookRepo;
        this.loanRepo = loanRepo;
    }

    public Book getBook(String id) throws NotFoundException {
        return bookRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Đầu sách", id));
    }

    public void addBook(Book book, int initialCopies) throws DuplicateIdException, BusinessException {
        if (bookRepo.existsById(book.getId())) {
            throw new DuplicateIdException("Dau sach", book.getId());
        }
        if (initialCopies < 0 || initialCopies > 50) {
            throw new BusinessException("So luong ban sao ban dau tu 0 den 50");
        }
        bookRepo.insert(book);
        if (initialCopies > 0) {
            try {
                bookRepo.addCopies(book.getId(), initialCopies);
            } catch (NotFoundException ignored) {}
        }
    }

    public void updateBook(Book book) throws NotFoundException {
        if (!bookRepo.update(book)) {
            throw new NotFoundException("Dau sach", book.getId());
        }
    }

    public void deleteBook(String id) throws NotFoundException, BusinessException {
        Book book = getBook(id);
        boolean hasBorrowed = book.getCopies().stream()
                .anyMatch(c -> c.getStatus().name().equals("BORROWED"));
        if (hasBorrowed || (loanRepo != null && loanRepo.existsByBook(id))) {
            throw new BusinessException("Khong the xao dau sach " + id + "vi dang co ban sao dang muon hoac co phieu muon lien quan");
        }
        bookRepo.deleteById(id);
    }

    public List<String> addCopies(String bookId, int n) throws NotFoundException, BusinessException {
        if (n < 1 || n > 50) {
            throw new BusinessException("So luong ban sao them moi moi ngay tu 1 den 50");
        }
        return bookRepo.addCopies(bookId, n);
    }

    public Page<BookView> search(BookCriteria criteria, int page, int size) {
        return bookRepo.search(criteria, page, size);
    }

    public List<Book> allBooks() {
        return bookRepo.findAll();
    }
}
