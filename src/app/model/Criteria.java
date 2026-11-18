package app.model;

import java.util.Comparator;

public final class Criteria {
    private Criteria() {}

    public record BookCriteria(
            String title,
            String author,
            String category,
            Integer yearFrom,
            Integer yearTo,
            boolean availableOnly,
            BookSort sort
    ) {}

    public record BookView(
            String id,
            String title,
            String author,
            String category,
            int year,
            int totalCopies,
            int available,
            int loanCount
    ) {
        @Override
        public String toString() {
            return String.format("%-6s | %-25s | %-18s | %-12s | %d | Tổng: %2d | Ranh: %2d | Lượt mượn: %d",
                    id, title, author, category, year, totalCopies, available, loanCount);
        }
    }

    public record BookLoanCount(String bookId, String title, long loans) {}

    public enum BookSort {
        TITLE("Ten sach (A-Z)", "b.title ASC", Comparator.comparing(BookView::title, String.CASE_INSENSITIVE_ORDER)),
        AUTHOR("Tac gia (A-Z)", "b.author ASC", Comparator.comparing(BookView::author, String.CASE_INSENSITIVE_ORDER)),
        YEAR_DESC("Nam XB moi nhat", "b.publish_year DESC", Comparator.comparingInt(BookView::year).reversed()),
        LOANS_DESC("Luot muon nhieu nhat", "loan_count DESC", Comparator.comparingInt(BookView::loanCount).reversed());

        private final String label;
        private final String sql;
        private final Comparator<BookView> comparator;

        BookSort(String label, String sql, Comparator<BookView> comparator) {
            this.label = label;
            this.sql = sql;
            this.comparator = comparator;
        }

        public String getLabel() { 
            return label; 
        }
        public String getSql() { 
            return sql; 
        }
        public Comparator<BookView> getComparator() { 
            return comparator; 
        }

        @Override
        public String toString() { 
            return label; 
        }
    }
}
