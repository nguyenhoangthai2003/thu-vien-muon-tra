package app.model;

import java.util.Collections;
import java.util.List;

public record Page<T>(List<T> items, int page, int size, long totalItems) {

    public int totalPages() {
        if (size <= 0) return 0;
        return (int) Math.ceil((double) totalItems / size);
    }

    public static <T> Page<T> of(List<T> all, int page, int size) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;
        long total = all.size();
        int skip = (page - 1) * size;
        if (skip >= total) {
            return new Page<>(Collections.emptyList(), page, size, total);
        }
        List<T> sub = all.stream()
                .skip(skip)
                .limit(size)
                .toList();
        return new Page<>(sub, page, size, total);
    }
}
