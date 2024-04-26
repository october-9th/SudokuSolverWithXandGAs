package dancingLinks;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {

public static void solveSudokus(String folderPath) {
    File folder = new File(folderPath);
    File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

    if (files != null && files.length > 0) {
        List<List<String>> allResults = new ArrayList<>();
        for (File file : files) {
            List<Long> timings = new ArrayList<>();
            List<Integer> cluesCount = new ArrayList<>();
            List<String> levels = new ArrayList<>();
            solveSudoku(file.getAbsolutePath(), timings, cluesCount, levels);
            addResultsToList(allResults, file.getName(), timings, cluesCount, levels);
        }
        writeToCSV(folderPath + File.separator + "sudoku_results_dancing_links.csv", allResults);
    } else {
        System.out.println("No text files found in the folder.");
    }
}

    private static void solveSudoku(String filename, List<Long> timings, List<Integer> cluesCount, List<String> levels) {
        try {
            List<int[][]> sudokuList = Utils.importSudoku(filename);
            int index = 1;
            for (int[][] sudoku : sudokuList) {
                System.out.println(">>>>> Sudoku #" + index + ": <<<<<\n" + Utils.sudokuBoard(sudoku));
                Sudoku findSolution = new Sudoku(sudoku.length);
                // estimate time
                long startTime = System.nanoTime();
                findSolution.solve(sudoku);
                long elapse = System.nanoTime() - startTime;
                timings.add(elapse);

                // Count the number of clues
                int clues = 0;
                for (int i = 0; i < sudoku.length; i++) {
                    for (int j = 0; j < sudoku.length; j++) {
                        if (sudoku[i][j] != 0) {
                            clues++;
                        }
                    }
                }
                cluesCount.add(clues);

                // Extract the level from the filename
                String level = filename.substring(filename.lastIndexOf("_") + 1, filename.lastIndexOf("."));
                levels.add(level);

                index += 1;
            }

            System.out.println(">>>> Statistic <<<<<");
            Utils.printStats(timings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addResultsToList(List<List<String>> allResults, String filename, List<Long> timings, List<Integer> cluesCount, List<String> levels) {
        for (int i = 0; i < timings.size(); i++) {
            List<String> row = new ArrayList<>();
            row.add(filename.substring(0, filename.lastIndexOf("_")));
            row.add(String.valueOf(i + 1));
            row.add(String.valueOf(cluesCount.get(i)));
            row.add(String.valueOf(timings.get(i) * 1e-6));
            row.add(levels.get(i));
            allResults.add(row);
        }
    }

    private static void writeToCSV(String csvFilename, List<List<String>> results) {
        try (PrintWriter writer = new PrintWriter(new File(csvFilename))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Filename,Sudoku Number,Clues Given,Time to Complete (ms),Difficulty Level\n");
            for (List<String> row : results) {
                sb.append(String.join(",", row)).append("\n");
            }
            writer.write(sb.toString());
            System.out.println("Results written to " + csvFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // extract data for research purpose
    public static void main(String[] args) {
//        solveSudoku("src/boards/sudoku25x25/25x25_easy.txt");
//        solveSudoku("src/boards/sudoku25x25/25x25_medium.txt");
//        solveSudoku("src/boards/sudoku25x25/25x25_hard.txt");
//        solveSudoku("src/boards/sudoku25x25/25x25_expert.txt");

        solveSudokus("src/boards/sudoku9x9");
        solveSudokus("src/boards/sudoku16x16");
        solveSudokus("src/boards/sudoku25x25");

    }


}