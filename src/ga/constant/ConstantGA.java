package ga.constant;

public enum ConstantGA {
    KICH_THUOC_SUDOKU(9),
    SO_LUONG_QUAN_THE(1000),
    VONG_DOI_TIEN_HOA(50000),
    VONG_DOI_QUAN_THE_DAO(50),
    SO_DAO(6),
    KICH_THUOC_BO_ME(2),
    TI_LE_LAI_GHEP(0.9),
    TI_LE_DOT_BIEN(0.1);
    private final double giaTri;
    private ConstantGA(double giaTri) {
        // TODO Auto-generated constructor stub
        this.giaTri = giaTri;
    }
    public double getGiaTri() {
        return giaTri;
    }


}
