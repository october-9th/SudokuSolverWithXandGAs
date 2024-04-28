package backtracking;

import dancingLinks.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HeuristicBacktrackSudokuSolver {
    private static int backtrackCount;

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
        backtrackCount++;
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

        List<Integer> candidates = new ArrayList<>();
        for (int num = 1; num <= sudoku.length; num++) {
            if (isValidPlacement(sudoku, row, col, num)) {
                candidates.add(num);
            }
        }

        Collections.shuffle(candidates); // Shuffle the candidates using Fisher-Yates shuffle

        for (int num : candidates) {
            sudoku[row][col] = num;
            if (backtrack(sudoku, rows, rowIndex, col + 1)) {
                return true;
            }
            sudoku[row][col] = 0; // Backtrack
        }

//        for (int num = 1; num <= sudoku.length; num++) {
//            if (isValidPlacement(sudoku, row, col, num)) {
//                sudoku[row][col] = num;
//                if (backtrack(sudoku, rows, rowIndex, col + 1)) {
//                    return true;
//                }
//                sudoku[row][col] = 0; // Backtrack
//            }
//        }

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

    public static void solveSudokus(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null && files.length > 0) {
            List<List<String>> allResults = new ArrayList<>();
            for (File file : files) {
                List<Long> timings = new ArrayList<>();
                List<Integer> cluesCount = new ArrayList<>();
                List<String> levels = new ArrayList<>();
                List<Integer> numberOfBacktrack = new ArrayList<>();
                solveSudoku(file.getAbsolutePath(), timings, cluesCount, levels, numberOfBacktrack);
                addResultsToList(allResults, file.getName(), timings, cluesCount, levels, numberOfBacktrack);
            }
            writeToCSV(folderPath + File.separator + "sudoku_results_heuristic_moves_backtracking.csv", allResults);
        } else {
            System.out.println("No text files found in the folder.");
        }
    }

    public static void solveSudoku(String filename, List<Long> timings, List<Integer> cluesCount, List<String> levels, List<Integer> numberOfBacktrack) {
        try {
            List<int[][]> sudokuList = Utils.importSudokuByLine(filename);
            int index = 1;
            for (int[][] sudoku : sudokuList) {
                backtrackCount = 0;
                int clues = 0;
                for (int[] ints : sudoku) {
                    for (int j = 0; j < sudoku.length; j++) {
                        if (ints[j] != 0) {
                            clues++;
                        }
                    }
                }
                cluesCount.add(clues);
                String level = filename.substring(filename.lastIndexOf("_") + 1, filename.lastIndexOf("."));
                levels.add(level);
                System.out.println(">>>>> Sudoku #" + index + ": <<<<<\n" + Utils.sudokuBoard(sudoku));
                // estimate time
                long startTime = System.nanoTime();
                if (solve(sudoku)) {
                    printGrid(sudoku);
                }
                long elapse = System.nanoTime() - startTime;
                timings.add(elapse);
                numberOfBacktrack.add(backtrackCount);
                index += 1;
            }
            System.out.println(">>>>> Statistic <<<<<");
            Utils.printStats(timings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addResultsToList(List<List<String>> allResults, String filename, List<Long> timings, List<Integer> cluesCount, List<String> levels, List<Integer> numberOfBacktrack) {
        for (int i = 0; i < timings.size(); i++) {
            List<String> row = new ArrayList<>();
            row.add(filename.substring(0, filename.lastIndexOf("_")));
            row.add(String.valueOf(i + 1));
            row.add(String.valueOf(cluesCount.get(i)));
            row.add(String.valueOf(timings.get(i) * 1e-6));
            row.add(levels.get(i));
            row.add(String.valueOf(numberOfBacktrack.get(i)));
            allResults.add(row);
        }
    }

    private static void writeToCSV(String csvFilename, List<List<String>> results) {
        try (PrintWriter writer = new PrintWriter(new File(csvFilename))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Filename,Sudoku Number,Clues Given,Time to Complete (ms),Difficulty Level,Number of Backtrack\n");
            for (List<String> row : results) {
                sb.append(String.join(",", row)).append("\n");
            }
            writer.write(sb.toString());
            System.out.println("Results written to " + csvFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        solveSudokus("src/boards/backtrack_testing");
        solveSudokus("src/boards/ref");
//        solveSudoku("src/boards/sudoku16x16/16x16_easy.txt");
//        solveSudoku("src/boards/9x9.txt");
//        solveSudoku("src/boards/test.txt");
//        solveSudoku("src/boards/sudoku25x25/test.txt");
//        solveSudoku("src/boards/16x16.txt");

    }
}
