package app.model;
// [FILE TẠM CHỜ THÀNH VIÊN B - KHÔNG COMMIT ĐÈ LÊN FILE CỦA B]
public enum CopyStatus {
    AVAILABLE("Con sach"), BORROWED("Dang muon"), DAMAGED("Hu hong"), LOST("Mat");
    private final String label;
    CopyStatus(String label) { 
        this.label = label; 
    }
    public String getLabel() { 
        return label; 
    }
    @Override public String toString() { 
        return label; 
    }
}
