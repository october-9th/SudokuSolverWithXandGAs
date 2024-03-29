package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ga.implement.ImplementGA;

import static utils.Helper.*;

public class Test {
    public static void main(String[] args) {
//		ArrayList<Integer> a1 = new ArrayList<>(Arrays.asList(10, 6, 0, 0, 9, 0, 0, 13, 0, 16, 0, 0, 7, 0, 11, 0));
//		ArrayList<Integer> a2 = new ArrayList<>(Arrays.asList(0, 5, 13, 0, 0, 0, 2, 14, 3, 0, 0, 0, 0, 16, 12, 0));
//		ArrayList<Integer> a3 = new ArrayList<>(Arrays.asList(0, 0, 16, 0, 11, 7, 3, 0, 0, 14, 6, 0, 0, 0, 0, 0));
//		ArrayList<Integer> a4 = new ArrayList<>(Arrays.asList(0, 0, 0, 3, 12, 0, 0, 16, 1, 13, 5, 9, 6, 0, 10, 2));
//		ArrayList<Integer> a5 = new ArrayList<>(Arrays.asList(0, 0, 10, 6, 13, 0, 5, 0, 8, 0, 4, 0, 0, 0, 0, 0));
//		ArrayList<Integer> a6 = new ArrayList<>(Arrays.asList(0, 0, 12, 0, 0, 0, 0, 11, 6, 0, 0, 14, 1, 0, 13, 0));
//		ArrayList<Integer> a7 = new ArrayList<>(Arrays.asList(0, 3, 0, 7, 0, 4, 0, 12, 0, 9, 0, 13, 0, 0, 14, 0));
//		ArrayList<Integer> a8 = new ArrayList<>(Arrays.asList(13, 0, 0, 5, 0, 0, 0, 10, 0, 11, 3, 0, 4, 0, 0, 0));
//		ArrayList<Integer> a9 = new ArrayList<>(Arrays.asList(4, 16, 0, 0, 3, 15, 11, 7, 10, 0, 14, 2, 0, 0, 0, 9));
//		ArrayList<Integer> a10 = new ArrayList<>(Arrays.asList(0, 0, 7, 0, 4, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
//		ArrayList<Integer> a11 = new ArrayList<>(Arrays.asList(2, 0, 0, 0, 0, 13, 0, 0, 12, 8, 0, 0, 0, 0, 0, 0));
//		ArrayList<Integer> a12 = new ArrayList<>(Arrays.asList(1, 0, 0, 9, 2, 14, 0, 6, 11, 7, 15, 0, 0, 0, 0, 12));
//		ArrayList<Integer> a13 = new ArrayList<>(Arrays.asList(8, 0, 0, 16, 7, 11, 0, 0, 0, 2, 10, 6, 0, 0, 5, 0));
//		ArrayList<Integer> a14 = new ArrayList<>(Arrays.asList(0, 0, 3, 0, 0, 0, 0, 0, 13, 1, 0, 0, 0, 2, 6, 14));
//		ArrayList<Integer> a15 = new ArrayList<>(Arrays.asList(6, 10, 0, 0, 5, 9, 13, 0, 0, 4, 0, 8, 0, 0, 0, 0));
//		ArrayList<Integer> a16 = new ArrayList<>(Arrays.asList(0, 0, 1, 0, 0, 0, 14, 2, 0, 0, 0, 0, 0, 0, 8, 0));
//		ArrayList<ArrayList<Integer>> sudoku = new ArrayList<>(Arrays.asList(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12
//				,a13,a14,a15,a16));


        // sudoku with solution
        ArrayList<Integer> firstRow = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 4, 8, 0, 7, 2));
        ArrayList<Integer> secondRow = new ArrayList<Integer>(Arrays.asList(9, 6, 0, 0, 7, 0, 8, 0, 0));
        ArrayList<Integer> thirdRow = new ArrayList<Integer>(Arrays.asList(7, 0, 0, 6, 3, 1, 0, 0, 0));
        ArrayList<Integer> fourthRow = new ArrayList<Integer>(Arrays.asList(0, 9, 6, 3, 0, 0, 0, 1, 5));
        ArrayList<Integer> fifthRow = new ArrayList<Integer>(Arrays.asList(5, 0, 8, 4, 9, 0, 0, 0, 0));
        ArrayList<Integer> sixthRow = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 2, 8));
        ArrayList<Integer> seventhRow = new ArrayList<Integer>(Arrays.asList(6, 0, 9, 0, 2, 3, 1, 5, 0));
        ArrayList<Integer> eighthRow = new ArrayList<Integer>(Arrays.asList(0, 0, 1, 7, 0, 4, 0, 0, 6));
        ArrayList<Integer> ninthRow = new ArrayList<Integer>(Arrays.asList(3, 7, 0, 0, 0, 0, 2, 8, 0));


        // sudoku with no solution found
//		ArrayList<Integer> firstRow = new ArrayList<Integer>(Arrays.asList(0,0,0,1,6,4,0,0,0));
//		ArrayList<Integer> secondRow = new ArrayList<Integer>(Arrays.asList(0,0,0,3,0,0,0,0,7));
//		ArrayList<Integer> thirdRow = new ArrayList<Integer>(Arrays.asList(0,0,0,8,0,0,2,0,3));
//		ArrayList<Integer> fourthRow = new ArrayList<Integer>(Arrays.asList(0,0,0,8,0,0,2,0,3));
//		ArrayList<Integer> fifthRow = new ArrayList<Integer>(Arrays.asList(0,8,0,0,0,6,0,0,0));
//		ArrayList<Integer> sixthRow = new ArrayList<Integer>(Arrays.asList(0,0,3,0,9,0,0,0,0));
//		ArrayList<Integer> seventhRow = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,4,0,0));
//		ArrayList<Integer> eighthRow = new ArrayList<Integer>(Arrays.asList(6,0,0,0,8,0,1,0,0));
//		ArrayList<Integer> ninthRow = new ArrayList<Integer>(Arrays.asList(1,0,4,0,0,0,0,3,0));
//
//        ArrayList<ArrayList<Integer>> sudoku = new ArrayList<ArrayList<Integer>>(Arrays.asList(firstRow,
//                secondRow, thirdRow, fourthRow, fifthRow, sixthRow, seventhRow, eighthRow, ninthRow));
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                System.out.print(sudoku.get(i).get(j) + " ");
//            }
//            System.out.println();
//        }
//
//
//		ImplementGA gaImp = new ImplementGA(sudoku, 10000, 20, 0.25, 0.7, 100, 8);
////		Thread t1 = new Thread(gaImp);
////
////
////
////		t1.start();
//		gaImp.thuatToanDiTruyen();

        try {
            List<int[][]> initialSudoku = importSudoku("src/boards/9x9.txt");

            for (int[][] sudoku : initialSudoku) {
                ArrayList<ArrayList<Integer>> converted = convertSudokuGrid(sudoku);

                ImplementGA ga = new ImplementGA(converted, 10000, 30, 0.25, 0.7, 100, 8);
                Thread thread = new Thread(ga);
                thread.start();

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
       