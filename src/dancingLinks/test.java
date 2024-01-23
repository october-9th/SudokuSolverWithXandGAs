package dancingLinks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void solveSudoku(String filename){
        List<int[][]> potentialChromosome = new ArrayList<>();
        try {
            List<int[][]> sudokuList = Utils.importSudoku(filename);
            for (int[][] sudoku: sudokuList) {
                Sudoku findSolution = new Sudoku(sudoku.length);
                findSolution.solve(sudoku);
                // estimate time
            }
            for(int[][] chromo : potentialChromosome){
                System.out.println(sudokuBoard(chromo));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public static String sudokuBoard(int[][] sudoku) {
        StringBuilder boardString = new StringBuilder();
        int sqr = (int) Math.sqrt(sudoku.length);

        for (int i = 0; i < sudoku.length; i++) {
            if (i % sqr == 0 && i != 0) {
                boardString.append("\n");
            }

            for (int j = 0; j < sudoku[i].length; j++) {
                if (j % sqr == 0 && j != 0) {
                    boardString.append("| ");
                }
                if (sudoku[i][j] == 0) {
                    boardString.append("x").append("  ");
                } else if (sudoku[i][j] < 10) {
                    boardString.append(sudoku[i][j]).append("  ");
                } else {
                    boardString.append(sudoku[i][j]).append(" ");
                }
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    public static void main(String[] args) {
//        solveSudoku("src/boards/sudoku25x25/25x25_easy.txt");
//        solveSudoku("src/boards/sudoku25x25/25x25_medium.txt");
//        solveSudoku("src/boards/sudoku25x25/25x25_hard.txt");
//        solveSudoku("src/boards/sudoku25x25/25x25_expert.txt");

        solveSudoku("src/boards/sudoku9x9/9x9_easy.txt");
    }
}
