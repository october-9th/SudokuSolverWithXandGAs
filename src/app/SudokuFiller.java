package app;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuFiller {

    public static List<Integer> generateChromosomeRowBacktrack(List<Integer> row) {
        int n = row.size();
        boolean[] usedNumbers = new boolean[n + 1];

        // Mark the pre-filled numbers as used
        for (int num : row) {
            if (num != 0) {
                usedNumbers[num] = true;
            }
        }

        // Start backtracking to fill the row
        if (fillRowBacktrack(row, usedNumbers, 0, n)) {
            return row;
        } else {
            throw new RuntimeException("Failed to generate a valid row for Sudoku");
        }
    }

    private static boolean fillRowBacktrack(List<Integer> row, boolean[] usedNumbers, int index, int n) {
        if (index == n) {
            return true;
        }

        if (row.get(index) != 0) {
            // Skip pre-filled cells
            return fillRowBacktrack(row, usedNumbers, index + 1, n);
        }

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (!usedNumbers[i]) {
                numbers.add(i);
            }
        }

        Collections.shuffle(numbers, new Random()); // Shuffle the available numbers

        for (int num : numbers) {
            row.set(index, num);
            usedNumbers[num] = true;

            if (fillRowBacktrack(row, usedNumbers, index + 1, n)) {
                return true;
            }

            // Backtrack
            row.set(index, 0);
            usedNumbers[num] = false;
        }

        return false;
    }

    public static void main(String[] args) {
        // Example: A row of size 9 with some pre-filled cells
        for (int i = 0; i < 10; i++) {
            List<Integer> row = new ArrayList<>(List.of(9, 0, 0, 0, 0, 0, 0, 2, 5));
            List<Integer> filledRow = generateChromosomeRowBacktrack(row);
            System.out.printf("Filled Sudoku Row # %d : %s\n", i, filledRow);
        }
    }
}
