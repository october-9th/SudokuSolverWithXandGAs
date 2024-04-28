package backtracking;

import dancingLinks.Record;
import dancingLinks.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class BacktrackSudokuSolver {

    // random mechannism
    private static Random random = new Random();
    // add backtrack count
    private static int backtrackCount;
    // add flag to check exceed terminate time
    private static final long TIME_LIMIT_NANOS = 300_000_000_000L;
    static long startTime = 0;
    private static final int UNASSIGNED = 0;

    private static class Cell {
        int row, col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
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

    private static void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[j];
            array[j] = array[i];
            array[i] = temp;
        }
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
//     // Shuffle the possible numbers using Fisher-Yates shuffle
//        int[] shuffledArray = possibleNumbers.stream().mapToInt(Integer::intValue).toArray();
//        shuffleArray(shuffledArray);
//        possibleNumbers.clear();
//        for (int num : shuffledArray) {
//            possibleNumbers.add(num);
//        }
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

    private static boolean solve(int[][] grid) throws TimeLimitExceededException {
        backtrackCount++;
        Cell unassignedCell = findUnassignedLocation(grid);
        if (unassignedCell == null) return true; // Puzzle solved
//        System.out.printf(">>>>> Total Memory Usage: %d\n", TOTAL_MEMORY_USAGE);
        List<Integer> possibleNumbers = getPossibleNumbers(grid, unassignedCell);
        for (int num : possibleNumbers) {
            if (isValidPlacement(grid, unassignedCell.row, unassignedCell.col, num)) {
                grid[unassignedCell.row][unassignedCell.col] = num;
                long elapsedTime = System.nanoTime() - startTime;
                if (elapsedTime > TIME_LIMIT_NANOS) {
                    throw new TimeLimitExceededException();
                }
                if (solve(grid)) return true;
                grid[unassignedCell.row][unassignedCell.col] = UNASSIGNED; // Backtrack
            }
        }

        return false;
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
            writeToCSV(folderPath + File.separator + "sudoku_results_traditional_backtracking.csv", allResults);
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
                startTime = System.nanoTime();
                boolean solved = false;
                try {
                    solved = solve(sudoku);
                } catch (TimeLimitExceededException e) {
                    System.out.println("Skipping Sudoku #" + index + " (Time limit exceeded)");

                }
                long elapse = System.nanoTime() - startTime;
                numberOfBacktrack.add(backtrackCount);
                if (solved) {
                    timings.add(elapse);
                    printGrid(sudoku);

                }
                index += 1;
            }
            System.out.println(">>>>> Statistic Timings <<<<<");
            Utils.printStats(timings);
            System.out.println(">>>>> Statistic Backtracks <<<<<");
            Utils.printStatsBacktrack(numberOfBacktrack);


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

    private static class TimeLimitExceededException extends Exception {
        public TimeLimitExceededException() {
            super("Time limit exceeded");
        }
    }

    // Main method to test the solver
    public static void main(String[] args) {
//        solveSudoku("src/boards/sudoku9x9/9x9_expert.txt");
//        solveSudoku("src/boards/9x9_top2365.txt");
//        solveSudoku("src/boards/9x9.txt");
//        solveSudoku("src/boards/test.txt");
//        solveSudoku("src/boards/sudoku25x25/test.txt");
//        solveSudoku("src/boards/16x16.txt");

        // extract algorithm Result
//        solveSudokus("src/boards/backtrack_testing");
//        solveSudokus("src/boards/ref");
//        solveSudokus("src/boards/sudoku16x16");
//        solveSudokus("src/boards/sudoku25x25");
        for(int i = 1; i <=20; i++)
            solveSudokus("src/boards/ref");
        System.out.println("Record timings statistic");
        int i = 0;
        for(Record rc : Utils.timingsRecord){
            i++;
            System.out.println("Record " + i + ":\n" + rc.toString() + "\n");
        }
        System.out.println("Record backtracks statistic");
        i = 0;
        for(Record rc : Utils.backtrackRecord){
            i++;
            System.out.println("Record " + i + ":\n" + rc.toString() + "\n");
        }
    }
}

