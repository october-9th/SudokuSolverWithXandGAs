package dancingLinks;

public class Validation {

    private final int gridSize;

    public Validation(int[][] sudoku) {
        this.gridSize = sudoku.length;
    }

    public boolean isValidSolution(int[][] grid) {
        for (int i = 0; i < gridSize; i++) {
            if (!isRowValid(grid, i) || !isColumnValid(grid, i) || !isBoxValid(grid, i)) {
                return false;
            }
        }
        return true;
    }

    private boolean isRowValid(int[][] grid, int row) {
        boolean[] seen = new boolean[gridSize + 1];
        for (int i = 0; i < gridSize; i++) {
            if (!checkCell(grid[row][i], seen)) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnValid(int[][] grid, int col) {
        boolean[] seen = new boolean[gridSize + 1];
        for (int i = 0; i < gridSize; i++) {
            if (!checkCell(grid[i][col], seen)) {
                return false;
            }
        }
        return true;
    }

    private boolean isBoxValid(int[][] grid, int box) {
        boolean[] seen = new boolean[gridSize + 1];
        int squareSize = (int ) Math.sqrt(gridSize);
        int rowStart = squareSize * (box / squareSize);
        int colStart = squareSize * (box % squareSize);
        for (int r = rowStart; r < rowStart + squareSize; r++) {
            for (int c = colStart; c < colStart + squareSize; c++) {
                if (!checkCell(grid[r][c], seen)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkCell(int value, boolean[] seen) {
        if (value != 0) {
            if (seen[value]) {
                return false;
            }
            seen[value] = true;
        }
        return true;
    }

}
