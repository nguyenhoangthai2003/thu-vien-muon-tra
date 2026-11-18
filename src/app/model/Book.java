package app.model;

import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Book implements Identifiable<String>, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String author;
    private String category;
    private int publishYear;
    private double price;
    private final List<BookCopy> copies = new ArrayList<>();

    public Book(String id, String title, String author, String category, int publishYear, double price) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setCategory(category);
        setPublishYear(publishYear);
        setPrice(price);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || !id.matches("^S\\d{3}$")) {
            throw new IllegalArgumentException("Ma sach phai co dang S001");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Ten sach khong duoc trong");
        }
        this.title = title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Tac gia khong duoc trong");
        }
        this.author = author.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("The loai khong duoc trong");
        }
        this.category = category.trim();
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        int currentYear = Year.now().getValue();
        if (publishYear < 1900 || publishYear > currentYear) {
            throw new IllegalArgumentException("Nam xuat ban phai tu 1900 đen " + currentYear);
        }
        this.publishYear = publishYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Gia sach phai > 0");
        }
        this.price = price;
    }

    public List<BookCopy> getCopies() {
        return copies;
    }

    public String nextCopyId() {
        int maxSeq = copies.stream()
                .map(c -> c.getId().substring(c.getId().indexOf('-') + 1))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        return String.format("%s-%02d", id, maxSeq + 1);
    }

    public long availableCount() {
        return copies.stream()
                .filter(c -> c.getStatus() == CopyStatus.AVAILABLE)
                .count();
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-25s | %-18s | %-12s | %d | %,.0fđ | Ranh: %d/%d",
                id, title, author, category, publishYear, price, availableCount(), copies.size());
    }
}
