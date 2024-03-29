package ga.chromosome;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Chromosome {
    private ArrayList<ArrayList<Integer>> giaiPhapSudoku; // 1 ca the la 1 giai phap sudoku 9x9
    private int doThichNghi; // do thich nghi

    /**
     * Constructor bat dau tao gia tri ngau nhien khong trung o hang.
     *
     * @param sudoku: Truyen du lieu cau hoi Sudoku vao va luu tru vi tri co dinh.
     *                Nhung o trong duoc thay the bang so 0.
     */



    public Chromosome(ArrayList<ArrayList<Integer>> sudoku) {
        ArrayList<ArrayList<Integer>> sudokuCopy = deepCopySudoku(sudoku);
        this.giaiPhapSudoku = new ArrayList<ArrayList<Integer>>(sudokuCopy.size()); // Add 9 different
        // ArrayList in the
        // Solution.

        // Lap lai tat ca cac hang cua Ma tran sudoku de tao ra 1 giai phap ngau nhien
        int i = 0;
        for (ArrayList<Integer> hangCauDo : sudokuCopy) {
//            ArrayList<Integer> hangGiaiPhap = new ArrayList<Integer>(sudoku.size()); // Create a new hang

            // for the solution.

            // Lap lai tat ca phan tu tren hang
//            for (Integer phanTu : hangCauDo) {
//                if (phanTu != 0) {
//                    hangGiaiPhap.add(phanTu);
//                } else {
//                    while (true) {
//                        int phatSinhSo = new Random().nextInt(sudoku.size()) + 1; // Ngau nhien phat sinh
//                        // so tu 1 - 9.
//                        if (!hangCauDo.contains(phatSinhSo) && !hangGiaiPhap.contains(phatSinhSo)) {
//                            hangGiaiPhap.add(phatSinhSo);
//                            break;// Ngat vong lap, den phan tu tiep theo.
//                        }
//                    }
//
//                }
//            }
            // used backtrack to generate valid row for sudoku
            ArrayList<Integer> hangGiaiPhap = generateChromosomeRowBacktrack(hangCauDo);
            giaiPhapSudoku.add(hangGiaiPhap);// Them tat ca hang vao giai phap.
            i++;
        }
        setDoThichNghi(tinhDoThichNghi(sudokuCopy.size()));
    }
    // make a deep copy of sudoku to ensure random ness is acquired

    private ArrayList<ArrayList<Integer>> deepCopySudoku(ArrayList<ArrayList<Integer>> original) {
        ArrayList<ArrayList<Integer>> copy = new ArrayList<>();
        for (ArrayList<Integer> row : original) {
            ArrayList<Integer> rowCopy = new ArrayList<>(row);
            copy.add(rowCopy);
        }
        return copy;
    }
    public ArrayList<Integer> generateChromosomeRowBacktrack(ArrayList<Integer> sudokuRow) {
        int size = sudokuRow.size();
        boolean[] isUsed = new boolean[size + 1];

        // Mark the pre-filled numbers as used
        for (int num : sudokuRow) {
            if (num != 0)
                isUsed[num] = true;
        }
        if (fillRowWithBacktrack(sudokuRow, isUsed, 0, size))
            return sudokuRow;
        else
            throw new RuntimeException("Failed to generate valid row for sudoku");
    }

    private boolean fillRowWithBacktrack(List<Integer> sudokuRow, boolean[] isUsed, int index, int size) {
        if (index == size)
            return true;

        if (sudokuRow.get(index) != 0) {
            // ignore pre-filled cell
            return fillRowWithBacktrack(sudokuRow, isUsed, index + 1, size);
        }
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            if (!isUsed[i])
                numbers.add(i);
        }
        Collections.shuffle(numbers, new Random());

        for (int num :
                numbers) {
            sudokuRow.set(index, num);
            isUsed[num] = true;

            if (fillRowWithBacktrack(sudokuRow, isUsed, index + 1, size))
                return true;

            // trigger backtrack
            sudokuRow.set(index, 0);
            isUsed[num] = false;
        }
        return false;
    }

    public Chromosome() {
        this.giaiPhapSudoku = new ArrayList<ArrayList<Integer>>(); // Them 9 Arraylist khac																					// nhau vao.
        setDoThichNghi(0);
    }

    public int tinhDoThichNghi(int kichThuocSudoku) {
        int doThichNghi = 0; // khoi tao do thich nghi ban dau = 0;

        // Lap lai qua tat ca cac cot cua giai phap sudoku
        for (int cot = 0; cot < kichThuocSudoku; cot++) {
            // ArrayList nay se dem so lan xuat hien phan tu tren 1 cot.
            // VD: panaltyCounter(0) = 3. co nghia la 3 lan xuat hien so 1 trong cot
            ArrayList<Integer> danhGiaViPham = new ArrayList<Integer>();
            for (int i = 0; i < kichThuocSudoku; i++) {
                danhGiaViPham.add(0);
            }
            for (int hang = 0; hang < kichThuocSudoku; hang++) {
                int giaTri = this.giaiPhapSudoku.get(hang).get(cot); // Gia tri tren 1 o cu the
                danhGiaViPham.set(giaTri - 1, danhGiaViPham.get(giaTri - 1) + 1);// Tang bo dem.
            }
            // Cong tat ca diem phat
            for (Integer viPhamCot : danhGiaViPham) {
                // Neu 1 so xuat hien nhieu lan trong 1 cot
                // Them vao diem phat neu gia tri viPhamCot khong bang 1
                if (viPhamCot > 1) {
                    doThichNghi += viPhamCot;
                } else if (viPhamCot == 0) {
                    doThichNghi += 1;
                }
            }
        }

        // Dem so diem phat tren mo hop 3x3
        for (int hang = 0; hang < kichThuocSudoku; hang += Math.sqrt(kichThuocSudoku)) {
            for (int cot = 0; cot < kichThuocSudoku; cot += Math.sqrt(kichThuocSudoku)) {
                // Arraylist dem so lan xuat hien cua 1 phan tu trong 1 hop 3x3 trong cau do 9x9

                ArrayList<Integer> danhGiaViPham = new ArrayList<Integer>();
                for (int i = 0; i < kichThuocSudoku; i++) {
                    danhGiaViPham.add(0);
                }
                // Duyet qua tat ca box 3x3
                for (int boxHang = hang; boxHang < hang + Math.sqrt(kichThuocSudoku); boxHang++) {
                    for (int boxCot = cot; boxCot < cot + Math.sqrt(kichThuocSudoku); boxCot++) {
                        int giaTri = this.giaiPhapSudoku.get(boxHang).get(boxCot); // Gia tri cu the
                        danhGiaViPham.set(giaTri - 1, danhGiaViPham.get(giaTri - 1) + 1);// Tang bo dem phat trong mang.
                    }
                }

                // Tong tat ca diem phat trong box
                for (Integer viPhamBox : danhGiaViPham) {
                    // Neu 1 so xuat hien nhieu hon 1 lan, tang diem phat.
                    if (viPhamBox > 1) {
                        doThichNghi += viPhamBox;
                    } else if (viPhamBox == 0) {
                        doThichNghi += 1;
                    }
                }
            }
        }
        return doThichNghi;
    }

    public ArrayList<ArrayList<Integer>> getGiaiPhapSudoku() {
        return giaiPhapSudoku;
    }

    public void setGiaiPhapSudoku(ArrayList<ArrayList<Integer>> giaiPhapSudoku) {
        this.giaiPhapSudoku = new ArrayList<ArrayList<Integer>>(giaiPhapSudoku);
    }

    public int getDoThichNghi() {
        return doThichNghi;
    }

    // set do thich nghi
    public void setDoThichNghi(int doThichNghi) {
        this.doThichNghi = doThichNghi;
    }

//    @Override
//    public String toString() {
//        String result = "";
//        ArrayList<Integer> all = new ArrayList<>();
//
//        for (ArrayList<Integer> sudokuhang : this.giaiPhapSudoku) {
//            all.addAll(sudokuhang);
//        }
//        result += all.toString();
//        return result;
//    }


    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        int sqr = (int) Math.sqrt(giaiPhapSudoku.size());

        for (int i = 0; i < giaiPhapSudoku.size(); i++) {
            if (i % sqr == 0 && i != 0) {
                boardString.append("\n");
            }

            for (int j = 0; j < giaiPhapSudoku.get(i).size(); j++) {
                if (j % sqr == 0 && j != 0) {
                    boardString.append("| ");
                }
                if (giaiPhapSudoku.get(i).get(j) == 0) {
                    boardString.append("x").append("  ");
                } else if (giaiPhapSudoku.get(i).get(j) < 10) {
                    boardString.append(giaiPhapSudoku.get(i).get(j)).append("  ");
                } else {
                    boardString.append(giaiPhapSudoku.get(i).get(j)).append(" ");
                }
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    public ArrayList<Integer> ketQua() {
        ArrayList<Integer> all = new ArrayList<>();

        for (ArrayList<Integer> sudokuhang : this.giaiPhapSudoku) {
            all.addAll(sudokuhang);
        }

        return all;
    }
}
