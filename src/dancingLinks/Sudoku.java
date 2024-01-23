package dancingLinks;

import java.util.Arrays;
import java.util.List;

public class Sudoku {

    // constructor define grid size
    public Sudoku(int size) {
        this.gridSize = size;
    }

    private int gridSize;
    private int squareSize;

    public void solve(int[][] sudoku) {
        if (!checkSudoku(sudoku)) {
            System.out.println("Invalid sudoku");
            return;
        }
        this.gridSize = sudoku.length;
        this.squareSize = (int) Math.sqrt(gridSize);
        runSolve(sudoku);
    }
    public int[][] generatePotentialChromosome(int[][] sudoku){
        this.gridSize = sudoku.length;
        this.squareSize = (int) Math.sqrt(gridSize);
        runSolve(sudoku);
        return null;
    }

    public static void printSolution(int[][] result) {
        int N = result.length;
        for (int i = 0; i < N; i++) {
            String ret = "";
            for (int j = 0; j < N; j++) {
                ret += result[i][j] + " ";
            }
            System.out.println(ret);
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

    public static boolean checkSudoku(int[][] sudoku) {
        int size = sudoku.length;
        for (int i = 0; i < size; i++) {
            int colSize = sudoku[i].length;
            if (colSize != size)
                return false;
        }
        boolean[] sudokuMask = new boolean[size + 1];
        for (int[] ints : sudoku) {
            for (int j = 0; j < size; j++) {
                if (ints[j] == 0)
                    continue;
                int currentVal = ints[j];
                if (sudokuMask[currentVal])
                    return false;
                sudokuMask[currentVal] = true;
            }
            Arrays.fill(sudokuMask, false);
        }
        int squareSize = (int) Math.sqrt(size);
        for (int i = 0; i < size; i += squareSize) {
            for (int j = 0; j < size; j += squareSize) {
                for (int k = 0; k < squareSize; k++) {
                    for (int l = 0; l < squareSize; l++) {
                        if (sudoku[i + k][j + l] == 0)
                            continue;
                        int currentValue = sudoku[i + k][j + l];
                        if (sudokuMask[currentValue])
                            return false;
                        sudokuMask[currentValue] = true;
                    }
                }
                Arrays.fill(sudokuMask, false);
            }
        }
        return true;
    }

    private int[][] generateExactCoverGrid(int[][] initialSudoku) {
        int[][] grid = convertExactCoverProblem();
        for (int i = 1; i <= gridSize; i++) {
            for (int j = 1; j <= gridSize; j++) {
                int num = initialSudoku[i - 1][j - 1];
                if (num != 0) {
                    for (int val = 1; val <= gridSize; val++) {
                        if (val != num)
                            Arrays.fill(grid[calculateIdx(i, j, val)], 0);
                    }
                }
            }
        }
        return grid;
    }

    private int[][] convertExactCoverProblem() {
        int[][] grid = new int[gridSize * gridSize * gridSize][gridSize * gridSize * 4];
        int trackPtr = 0;

        // define 4 type of constraints
        // row - col
        for (int row = 1; row <= gridSize; row++) {
            for (int col = 1; col <= gridSize; col++, trackPtr++) {
                for (int val = 1; val <= gridSize; val++) {
                    int num = calculateIdx(row, col, val);
                    grid[num][trackPtr] = 1;
                }
            }
        }

        // row - val
        for (int row = 1; row <= gridSize; row++) {
            for (int val = 1; val <= gridSize; val++, trackPtr++) {
                for (int col = 1; col <= gridSize; col++) {
                    int num = calculateIdx(row, col, val);
                    grid[num][trackPtr] = 1;
                }
            }
        }

        // col - val
        for (int col = 1; col <= gridSize; col++) {
            for (int val = 1; val <= gridSize; val++, trackPtr++) {
                for (int row = 1; row <= gridSize; row++) {
                    int num = calculateIdx(row, col, val);
                    grid[num][trackPtr] = 1;
                }
            }
        }

        // square - val
        for (int sqrRow = 1; sqrRow <= gridSize; sqrRow += squareSize) {
            for (int sqrCol = 1; sqrCol <= gridSize; sqrCol += squareSize) {
                for (int val = 1; val <= gridSize; val++, trackPtr++) {
                    for (int rowWeight = 0; rowWeight < squareSize; rowWeight++) {
                        for (int colWeight = 0; colWeight < squareSize; colWeight++) {
                            int num = calculateIdx(sqrRow + rowWeight, sqrCol + colWeight, val);
                            grid[num][trackPtr] = 1;
                        }
                    }
                }
            }
        }
        return grid;
    }

    private int calculateIdx(int row, int col, int val) {
        return (row - 1) * gridSize * gridSize + (col - 1) * gridSize + (val - 1);
    }

    protected void runSolve(int[][] sudoku) {
        int[][] initialSudoku = generateExactCoverGrid(sudoku);
        DancingLinks dcl = new DancingLinks(initialSudoku, new SudokuSolution(gridSize));
        dcl.runSolver();
    }

}
