package app.exception;
// [FILE TẠM CHỜ THÀNH VIÊN B - KHÔNG COMMIT ĐÈ LÊN FILE CỦA B]
public class NotFoundException extends Exception {
    public NotFoundException(String what, Object id) { super("Khong tim thay " + what + " co ma: " + id); }
}
