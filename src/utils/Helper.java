package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static ArrayList<ArrayList<Integer>> convertSudokuGrid(int[][] initialSudoku) {
        int gridSize = initialSudoku.length;
        ArrayList<ArrayList<Integer>> sudoku = new ArrayList<>();
        for (int i = 0; i < gridSize ; i++) {
            ArrayList<Integer> n_row = new ArrayList<>();
            for (int j = 0; j < gridSize; j++) {
                n_row.add(initialSudoku[i][j]);
            }
            sudoku.add(n_row);
        }
        return sudoku;
    }
    public static int[][] convertSudokuGrid(ArrayList<ArrayList<Integer>> initialSudoku) {
        int gridSize = initialSudoku.size();
        int[][] sudoku = new int[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            ArrayList<Integer> row = initialSudoku.get(i);
            for (int j = 0; j < gridSize; j++) {
                sudoku[i][j] = row.get(j);
            }
        }
        return sudoku;
    }

    public static String sudokuBoard(ArrayList<ArrayList<Integer>> sudoku) {
        StringBuilder boardString = new StringBuilder();
        int sqr = (int) Math.sqrt(sudoku.size());

        for (int i = 0; i < sudoku.size(); i++) {
            if (i % sqr == 0 && i != 0) {
                boardString.append("\n");
            }

            for (int j = 0; j < sudoku.get(i).size(); j++) {
                if (j % sqr == 0 && j != 0) {
                    boardString.append("| ");
                }
                if (sudoku.get(i).get(j) == 0) {
                    boardString.append("x").append("  ");
                } else if (sudoku.get(i).get(j) < 10) {
                    boardString.append(sudoku.get(i).get(j)).append("  ");
                } else {
                    boardString.append(sudoku.get(i).get(j)).append(" ");
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
        try{
            List<int[][]> initialSudoku = importSudoku("src/boards/9x9.txt");
            for (int[][] sudoku : initialSudoku) {
                ArrayList<ArrayList<Integer>> converted = convertSudokuGrid(sudoku);
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        System.out.println(converted.get(i).get(j));
                    }
                }
                System.out.println(sudokuBoard(converted));
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
