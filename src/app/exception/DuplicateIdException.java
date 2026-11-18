package app.exception;
// [FILE TẠM CHỜ THÀNH VIÊN B - KHÔNG COMMIT ĐÈ LÊN FILE CỦA B]
public class DuplicateIdException extends Exception {
    public DuplicateIdException(String what, Object id) { super(what + " da ton tai ma: " + id); }
}
