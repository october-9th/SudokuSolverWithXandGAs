    package ga.chromosome;

    import java.util.ArrayList;
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
            this.giaiPhapSudoku = new ArrayList<ArrayList<Integer>>(sudoku.size()); // Add 9 different
            // ArrayList in the
            // Solution.

            // Lap lai tat ca cac hang cua Ma tran sudoku de tao ra 1 giai phap ngau nhien
            for (ArrayList<Integer> hangCauDo : sudoku) {
                ArrayList<Integer> hangGiaiPhap = new ArrayList<Integer>(sudoku.size()); // Create a new hang
                // for the solution.

                // Lap lai tat ca phan tu tren hang
                for (Integer phanTu : hangCauDo) {
                    if (phanTu != 0) {
                        hangGiaiPhap.add(phanTu);
                    } else {
                        while (true) {
                            int phatSinhSo = new Random().nextInt(sudoku.size()) + 1; // Ngau nhien phat sinh
                            // so tu 1 - 9.
                            if (!hangCauDo.contains(phatSinhSo) && !hangGiaiPhap.contains(phatSinhSo)) {
                                hangGiaiPhap.add(phatSinhSo);
                                break;// Ngat vong lap, den phan tu tiep theo.
                            }
                        }

                    }
                }
                giaiPhapSudoku.add(hangGiaiPhap);// Them tat ca hang vao giai phap.
            }

            setDoThichNghi(tinhDoThichNghi(sudoku.size()));
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
                for(int i=0;i<kichThuocSudoku;i++) {
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
                    for(int i=0;i<kichThuocSudoku;i++) {
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
                        }
                        else if(viPhamBox == 0) {
                            doThichNghi +=1;
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

        @Override
        public String toString() {
            String result = "";
            ArrayList<Integer> all = new ArrayList<>();

            for (ArrayList<Integer> sudokuhang : this.giaiPhapSudoku) {
                all.addAll(sudokuhang);
            }
            result += all.toString();
            return result;
        }

        public ArrayList<Integer> ketQua() {
            ArrayList<Integer> all = new ArrayList<>();

            for (ArrayList<Integer> sudokuhang : this.giaiPhapSudoku) {
                all.addAll(sudokuhang);
            }

            return all;
        }
    }
