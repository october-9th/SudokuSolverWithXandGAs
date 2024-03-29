package backtracking;
import java.util.*;
public class SudokuSolver {
    public static void solve(int[][] sudoku) {
        List<Integer> rowsOrderedByEmptyCells = getRowsOrderedByEmptyCells(sudoku);
        if (backtrack(sudoku, rowsOrderedByEmptyCells, 0, 0)) {
            printSudoku(sudoku);
        } else {
            System.out.println("No solution exists");
        }
    }

    private static List<Integer> getRowsOrderedByEmptyCells(int[][] sudoku) {
        List<Integer> rows = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            rows.add(i);
        }

        rows.sort(Comparator.comparingInt(row -> countEmptyCells(sudoku, row)));
        return rows;
    }

    private static boolean backtrack(int[][] sudoku, List<Integer> rows, int rowIndex, int col) {
        if (rowIndex == 9) {
            return true; // Puzzle solved
        }

        int row = rows.get(rowIndex);
        if (col == 9) {
            return backtrack(sudoku, rows, rowIndex + 1, 0); // Move to the next row
        }

        if (sudoku[row][col] != 0) {
            return backtrack(sudoku, rows, rowIndex, col + 1); // Move to the next column
        }

        for (int num = 1; num <= 9; num++) {
            if (isValid(sudoku, row, col, num)) {
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
        for (int i = 0; i < 9; i++) {
            if (sudoku[row][i] == 0) {
                count++;
            }
        }
        return count;
    }

    private static void printSudoku(int[][] sudoku) {
        for (int[] row : sudoku) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

    // Assume isValid is defined elsewhere
    private static boolean isValid(int[][] grid, int row, int col, int num) {
        // Check row
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == num) {
                return false;
            }
        }

        // Check 3x3 box
        int boxRowStart = row - row % 3;
        int boxColStart = col - col % 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int d = boxColStart; d < boxColStart + 3; d++) {
                if (grid[r][d] == num) {
                    return false;
                }
            }
        }

        return true; // Number can be placed
    }

    public static void main(String[] args) {
        int[][] sudoku = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        // Initialize with your Sudoku puzzle
//        solveSudoku(sudoku);
    }
}
