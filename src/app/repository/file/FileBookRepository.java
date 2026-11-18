package app.repository.file;

import app.exception.NotFoundException;
import app.model.Book;
import app.model.BookCopy;
import app.model.CopyStatus;
import app.model.Criteria.BookCriteria;
import app.model.Criteria.BookSort;
import app.model.Criteria.BookView;
import app.model.Page;
import app.repository.BookRepository;

import java.util.*;
import java.util.stream.Collectors;

public class FileBookRepository implements BookRepository {
    private final Map<String, Book> data = new LinkedHashMap<>();

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return data.containsKey(id);
    }

    @Override
    public void insert(Book entity) {
        data.put(entity.getId(), entity);
    }

    @Override
    public boolean update(Book entity) {
        if (!data.containsKey(entity.getId())) return false;
        data.put(entity.getId(), entity);
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        return data.remove(id) != null;
    }

    @Override
    public List<String> addCopies(String bookId, int n) throws NotFoundException {
        Book book = findById(bookId)
                .orElseThrow(() -> new NotFoundException("Đầu sách", bookId));
        List<String> newIds = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String nextId = book.nextCopyId();
            BookCopy copy = new BookCopy(nextId, bookId, CopyStatus.AVAILABLE);
            book.getCopies().add(copy);
            newIds.add(nextId);
        }
        update(book);
        return newIds;
    }

    @Override
    public Optional<BookCopy> findCopy(String copyId) {
        return data.values().stream()
                .flatMap(b -> b.getCopies().stream())
                .filter(c -> c.getId().equals(copyId))
                .findFirst();
    }

    @Override
    public List<BookCopy> findAllCopies() {
        return data.values().stream()
                .flatMap(b -> b.getCopies().stream())
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookView> search(BookCriteria c, int page, int size) {
        BookSort sort = (c != null && c.sort() != null) ? c.sort() : BookSort.TITLE;

        List<BookView> filtered = data.values().stream()
                .filter(b -> c == null || c.title() == null || b.getTitle().toLowerCase().contains(c.title().toLowerCase()))
                .filter(b -> c == null || c.author() == null || b.getAuthor().toLowerCase().contains(c.author().toLowerCase()))
                .filter(b -> c == null || c.category() == null || b.getCategory().equalsIgnoreCase(c.category()))
                .filter(b -> c == null || c.yearFrom() == null || b.getPublishYear() >= c.yearFrom())
                .filter(b -> c == null || c.yearTo() == null || b.getPublishYear() <= c.yearTo())
                .filter(b -> c == null || !c.availableOnly() || b.availableCount() > 0)
                .map(b -> new BookView(
                        b.getId(),
                        b.getTitle(),
                        b.getAuthor(),
                        b.getCategory(),
                        b.getPublishYear(),
                        b.getCopies().size(),
                        (int) b.availableCount(),
                        0
                ))
                .sorted(sort.getComparator())
                .collect(Collectors.toList());

        return Page.of(filtered, page, size);
    }
}
