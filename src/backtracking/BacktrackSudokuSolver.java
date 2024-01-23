package backtracking;

import dancingLinks.Sudoku;
import dancingLinks.Utils;

import java.io.IOException;
import java.util.*;

public class BacktrackSudokuSolver {

    private static final int UNASSIGNED = 0;
    private static int TOTAL_MEMORY_USAGE = 0;
    private static class Cell {
        int row, col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
        public int calculateMemoryUsage() {
            int objectOverhead = 16; // Approximate overhead in bytes
            int intFieldSize = 4; // Size of an int in bytes
            int totalSize = objectOverhead + (2 * intFieldSize); // Total size for 2 int fields
            return totalSize;
        }

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
                solve(sudoku);
                long elapse = System.nanoTime() - startTime;
                timings.add(elapse);
                index += 1;
                printGrid(sudoku);
                System.out.printf(">>>>> Total Memory Usage: %d\n", TOTAL_MEMORY_USAGE);
            }
            System.out.println(">>>>> Statistic <<<<<");
            Utils.printStats(timings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean solve(int[][] grid) {
        Cell unassignedCell = findUnassignedLocation(grid);
        if (unassignedCell == null) return true; // Puzzle solved
        TOTAL_MEMORY_USAGE += unassignedCell.calculateMemoryUsage();
        System.out.printf(">>>>> Total Memory Usage: %d\n", TOTAL_MEMORY_USAGE);
        List<Integer> possibleNumbers = getPossibleNumbers(grid, unassignedCell);
        for (int num : possibleNumbers) {
            if (isValidPlacement(grid, unassignedCell.row, unassignedCell.col, num)) {
                grid[unassignedCell.row][unassignedCell.col] = num;
                if (solve(grid)) return true;
                grid[unassignedCell.row][unassignedCell.col] = UNASSIGNED; // Backtrack
            }
        }

        return false;
    }

    private static Cell findUnassignedLocation(int[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {
                if (grid[row][col] == UNASSIGNED) {
                    return new Cell(row, col);
                }
            }
        }
        return null;
    }

    private static List<Integer> getPossibleNumbers(int[][] grid, Cell cell) {
        boolean[] used = new boolean[grid.length + 1];
        Arrays.fill(used, false);

        // Check row and column
        for (int i = 0; i < grid.length; i++) {
            used[grid[cell.row][i]] = true;
            used[grid[i][cell.col]] = true;
        }

//         Check box
        int sqrt = (int) Math.sqrt(grid.length);
        int boxStartRow = cell.row - cell.row % sqrt;
        int boxStartCol = cell.col - cell.col % sqrt;

        for (int r = boxStartRow; r < boxStartRow + sqrt; r++) {
            for (int d = boxStartCol; d < boxStartCol + sqrt; d++) {
                used[grid[r][d]] = true;
            }
        }

        List<Integer> possibleNumbers = new ArrayList<>();
        for (int num = 1; num <= grid.length; num++) {
            if (!used[num]) {
                possibleNumbers.add(num);
            }
        }

        return possibleNumbers;
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

    // Main method to test the solver
    public static void main(String[] args) {
//        solveSudoku("src/boards/sudoku9x9/9x9_expert.txt");
        solveSudoku("src/boards/sudoku16x16/16x16_hard.txt");
//        solveSudoku("src/boards/9x9.txt");
//        solveSudoku("src/boards/test.txt");
//        solveSudoku("src/boards/sudoku25x25/test.txt");
//        solveSudoku("src/boards/16x16.txt");
    }
}

