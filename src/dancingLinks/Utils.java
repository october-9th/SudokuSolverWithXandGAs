package dancingLinks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void printStats(List<Long> timings) {
        if (timings.size() == 1) {
            System.out.println("avg: " + timings.get(0) * 1e-6 + "ms");
            return;
        }
        long min = timings.get(0);
        long max = timings.get(0);

        long sum = 0;

        for (long ll : timings) {
            min = Math.min(min, ll);
            max = Math.max(max, ll);
            sum += ll;
        }

        double avg = sum / timings.size();


        System.out.println("min: " + min * 1e-6 + "ms");
        System.out.println("max: " + max * 1e-6 + "ms");
        System.out.println("avg: " + avg * 1e-6 + "ms");
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

    public static List<int[][]> importSudoku(String filename) throws IOException {
        List<int[][]> sudokus = new ArrayList<>();
        List<String[]> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                if (!lines.isEmpty()) {
                    sudokus.add(processSudokuGrid(lines));
                    lines.clear();
                }
            } else {
                String[] values = line.trim().split("\\s+");
                lines.add(values);
            }
        }

        if (!lines.isEmpty()) {
            sudokus.add(processSudokuGrid(lines));
        }

        reader.close();
        return sudokus;
    }
    public static List<int[][]> importSudokuByLine(String filename) throws IOException {
        List<int[][]> sudokus = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                int[][] sudoku = processSudokuGridLineByLine(line);
                if (sudoku != null) {
                    sudokus.add(sudoku);
                }
            }
        }

        reader.close();
        return sudokus;
    }

    private static int[][] processSudokuGridLineByLine(String line) {
        int size = (int) Math.sqrt(line.length());
        int[][] grid = new int[size][size];
        int row = 0, col = 0;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (Character.isDigit(c)) {
                int digit = Character.digit(c, 10);
                if (digit >= 0 && digit <= size) {
                    grid[row][col] = digit;
                    col++;
                    if (col == size) {
                        col = 0;
                        row++;
                    }
                }
            } else if (c == '.') {
                col++;
                if (col == size) {
                    col = 0;
                    row++;
                }
            }
        }

        if (row == size && col == 0) {
            return grid;
        } else {
            System.err.println("Invalid Sudoku grid: " + line);
            return null;
        }
    }
    private static int[][] processSudokuGrid(List<String[]> lines) {
        int gridSize = lines.size();
        int[][] sudoku = new int[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            if (lines.get(i).length != gridSize)
                throw new IllegalArgumentException("Invalid sudoku file: inconsistent row length");
            for (int j = 0; j < gridSize; j++) {
                sudoku[i][j] = lines.get(i)[j].equals("0") ? 0 :
                        lines.get(i)[j].equals("x") ? 0 :
                                lines.get(i)[j].equals(".") ? 0 :
                                        Integer.parseInt(lines.get(i)[j]);
            }
        }
        return sudoku;
    }


    public static void main(String[] args) {
        try {
            List<int[][]> sudokus = importSudoku("src/boards/sudoku36x36/36x36.txt");
            for (int[][] sudoku : sudokus) {
                System.out.println(sudokuBoard(sudoku));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
