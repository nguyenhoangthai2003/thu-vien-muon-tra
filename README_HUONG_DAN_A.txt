HƯỚNG DẪN SỬ DỤNG & PUSH GIT CHO THÀNH VIÊN A
=============================================
1. Cấu trúc thư mục:
   - Các file chính của A:
     + src/app/model/Book.java
     + src/app/model/BookCopy.java
     + src/app/model/Page.java
     + src/app/model/Criteria.java
     + src/app/service/BookService.java
     + src/app/repository/file/FileBookRepository.java
   - Các file có ghi chú [FILE TẠM CHỜ THÀNH VIÊN B/C]: Dùng để biên dịch và chạy thử file TestMemberA.java.

2. Lệnh Git để đẩy phần của A lên nhánh riêng (không đẩy đè file tạm của B/C):
   git checkout -b feature/a-book-module
   git add src/app/model/Book.java src/app/model/BookCopy.java src/app/model/Page.java src/app/model/Criteria.java src/app/service/BookService.java src/app/repository/file/FileBookRepository.java
   git commit -m "A: Hoan thanh model Book, BookCopy, Page, BookService va FileBookRepository"
   git push origin feature/a-book-module
