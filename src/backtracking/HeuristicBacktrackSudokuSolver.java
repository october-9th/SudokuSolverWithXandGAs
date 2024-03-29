package backtracking;

import dancingLinks.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HeuristicBacktrackSudokuSolver {
    public static boolean solve(int[][] sudoku) {
        List<Integer> rowsOrderedByEmptyCells = getRowsOrderedByEmptyCells(sudoku);
        return backtrack(sudoku, rowsOrderedByEmptyCells, 0, 0);
    }

    private static List<Integer> getRowsOrderedByEmptyCells(int[][] sudoku) {
        List<Integer> rows = new ArrayList<>();
        for (int i = 0; i < sudoku.length; i++) {
            rows.add(i);
        }

        rows.sort(Comparator.comparingInt(row -> countEmptyCells(sudoku, row)));
        return rows;
    }

    private static boolean backtrack(int[][] sudoku, List<Integer> rows, int rowIndex, int col) {
        if (rowIndex == sudoku.length) {
            return true; // Puzzle solved
        }

        int row = rows.get(rowIndex);
        if (col == sudoku.length) {
            return backtrack(sudoku, rows, rowIndex + 1, 0); // Move to the next row
        }

        if (sudoku[row][col] != 0) {
            return backtrack(sudoku, rows, rowIndex, col + 1); // Move to the next column
        }

        for (int num = 1; num <= sudoku.length; num++) {
            if (isValidPlacement(sudoku, row, col, num)) {
                sudoku[row][col] = num;
                if (backtrack(sudoku, rows, rowIndex, col + 1)) {
                    return true;
                }
                sudoku[row][col] = 0; // Backtrack
            }
        }

        return false; // Trigger backtracking
    }


    private static int countEmptyCells(int[][] sudoku, int row) {
        int count = 0;
        for (int i = 0; i < sudoku.length; i++) {
            if (sudoku[row][i] == 0) {
                count++;
            }
        }
        return count;
    }

    private static boolean isValidPlacement(int[][] grid, int row, int col, int number) {
        return !isInRow(grid, row, number) && !isInCol(grid, col, number) && !isInBox(grid, row - row % (int) Math.sqrt(grid.length), col - col % (int) Math.sqrt(grid.length), number);
    }

    private static boolean isInRow(int[][] grid, int row, int number) {
        for (int i = 0; i < grid.length; i++) {
            if (grid[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInCol(int[][] grid, int col, int number) {
        for (int[] ints : grid) {
            if (ints[col] == number) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInBox(int[][] grid, int boxStartRow, int boxStartCol, int number) {
        int boxSize = (int) Math.sqrt(grid.length);
        for (int r = 0; r < boxSize; r++) {
            for (int d = 0; d < boxSize; d++) {
                if (grid[r + boxStartRow][d + boxStartCol] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void printGrid(int[][] grid) {
        System.out.println(">>>>> Solution: <<<<<");
        System.out.println(sudokuBoard(grid));
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

    public static void solveSudoku(String filename) {
        try {
            List<Long> timings = new ArrayList<>();
            List<int[][]> sudokuList = Utils.importSudoku(filename);
            int index = 1;
            for (int[][] sudoku : sudokuList) {
                System.out.println(">>>>> Sudoku #" + index + ": <<<<<\n" + Utils.sudokuBoard(sudoku));
                // estimate time
                long startTime = System.nanoTime();
                if (solve(sudoku)){
                    printGrid(sudoku);
                }
                long elapse = System.nanoTime() - startTime;
                timings.add(elapse);
                index += 1;
            }
            System.out.println(">>>>> Statistic <<<<<");
            Utils.printStats(timings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        solveSudoku("src/boards/sudoku9x9/9x9_expert.txt");
//        solveSudoku("src/boards/sudoku16x16/16x16_easy.txt");
//        solveSudoku("src/boards/9x9.txt");
//        solveSudoku("src/boards/test.txt");
//        solveSudoku("src/boards/sudoku25x25/test.txt");
//        solveSudoku("src/boards/16x16.txt");

    }
}
