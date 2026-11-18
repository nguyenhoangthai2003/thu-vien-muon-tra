package app.repository;
// [FILE TẠM CHỜ THÀNH VIÊN B - KHÔNG COMMIT ĐÈ LÊN FILE CỦA B]
import java.util.List;
import java.util.Optional;
public interface GenericRepository<T, ID> {
    List<T> findAll();
    Optional<T> findById(ID id);
    boolean existsById(ID id);
    void insert(T entity);
    boolean update(T entity);
    boolean deleteById(ID id);
}
