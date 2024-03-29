package app;

import ga.chromosome.Chromosome;
import ga.implement.ImplementGA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static utils.Helper.convertSudokuGrid;
import static utils.Helper.importSudoku;

public class TestPopulation {
    private static ArrayList<Integer> doThichNghi = new ArrayList<>();
    public static void generateSameplePopulation(int kichThuocQT, ArrayList<ArrayList<Integer>> cauDo) {
        int min = Integer.MAX_VALUE;
        ArrayList<Chromosome> quanThe;

        int tongDiemThichNghi = 0;

        System.out.println("Khoi tao quan the...");
        quanThe = new ArrayList<>();
        while (quanThe.size() < kichThuocQT) {
            Chromosome caThe = new Chromosome(cauDo);
            if (true) {
                quanThe.add(caThe);
                TestPopulation.doThichNghi.add(caThe.getDoThichNghi());
                tongDiemThichNghi += caThe.getDoThichNghi();
            }
        }

//        for (int i = 1; i <= kichThuocQT; i++) {
//            System.out.printf("Chromosome number %d in population:\n%s \n Has fitness score: %d\n", i, quanThe.get(i - 1).toString(), doThichNghi.get(i - 1));
//            System.out.println();
//        }

    }

    public static void main(String[] args) throws IOException {
        List<int[][]> initialSudoku = importSudoku("src/boards/9x9.txt");
        int min = Integer.MAX_VALUE;
        for (int[][] sudoku : initialSudoku) {
            ArrayList<ArrayList<Integer>> converted = convertSudokuGrid(sudoku);
            generateSameplePopulation(100000, converted);
            for (int num:
                 doThichNghi) {
                if(num <= min){
                    min = num;
                }
            }
            System.out.println(min);
        }
    }

}
