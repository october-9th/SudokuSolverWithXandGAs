package dancingLinks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {

    public static void solveSudoku(String filename){
        try {
            List<Long> timings = new ArrayList<>();
            List<int[][]> sudokuList = Utils.importSudoku(filename);
            int index = 1;
            for (int[][] sudoku: sudokuList) {
                System.out.println(">>>>> Sudoku #" + index + ": <<<<<\n" + Utils.sudokuBoard(sudoku));
                Sudoku findSolution = new Sudoku(sudoku.length);
                // estimate time
                long startTime =  System.nanoTime();
                findSolution.solve(sudoku);
                long elapse = System.nanoTime() - startTime;
                timings.add(elapse);
                index+=1;

            }
            System.out.println(">>>> Statistic <<<<<");
            Utils.printStats(timings);

        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
//        solveSudoku("src/boards/sudoku25x25/25x25_easy.txt");
//        solveSudoku("src/boards/sudoku25x25/25x25_medium.txt");
//        solveSudoku("src/boards/sudoku25x25/25x25_hard.txt");
//        solveSudoku("src/boards/sudoku25x25/25x25_expert.txt");

        solveSudoku("src/boards/9x9.txt");
    }


}