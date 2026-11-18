package app.repository;
// [FILE TẠM CHỜ THÀNH VIÊN B - KHÔNG COMMIT ĐÈ LÊN FILE CỦA B]
import app.exception.NotFoundException;
import app.model.*;
import app.model.Criteria.*;
import java.util.List;
import java.util.Optional;
public interface BookRepository extends GenericRepository<Book, String> {
    Page<BookView> search(BookCriteria criteria, int page, int size);
    List<String> addCopies(String bookId, int n) throws NotFoundException;
    Optional<BookCopy> findCopy(String copyId);
    List<BookCopy> findAllCopies();
}
