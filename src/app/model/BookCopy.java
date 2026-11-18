package app.model;

import java.io.Serializable;

public class BookCopy implements Identifiable<String>, Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String bookId;
    private CopyStatus status;

    public BookCopy(String id, String bookId, CopyStatus status) {
        if (id == null || !id.matches("^S\\d{3}-\\d{2}$")) {
            throw new IllegalArgumentException("Ma ban sao phai co dang S001-01");
        }
        if (bookId == null || !bookId.matches("^S\\d{3}$")) {
            throw new IllegalArgumentException("Ma dau sach phai co dang S001");
        }
        this.id = id;
        this.bookId = bookId;
        setStatus(status);
    }

    @Override
    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public CopyStatus getStatus() {
        return status;
    }

    public void setStatus(CopyStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Thieu ban sao");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%-10s | Đầu sách: %-6s | Trang thai: %s", id, bookId, status);
    }
}
