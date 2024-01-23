package dancingLinks;

import dancingLinks.DancingLinks.Node;

import java.util.List;

public interface Solution {

    void handleSudokuSolution(List<Node> foundSolution);

    void getPartialSolution(List<Node> foundSolution);

    int[][] getPotentialChromosome();
}

class SudokuSolution implements Solution {
    private int[][] potentialChromosome;
    private int size;
    private Validation validator;

    public void handleSudokuSolution(List<Node> foundSolution) {
        int[][] result = parseBoard(foundSolution);
        validator = new Validation(result);
        if (result.length != 0) {
//            if (validator.isValidSolution(result))
            System.out.println(">>>>> Sudoku validated <<<<< ");
            System.out.println(Sudoku.sudokuBoard(result));
        } else {
            System.out.println("Empty solution");
        }
    }


    // for ga
    public void getPartialSolution(List<Node> foundSolution) {
        int[][] result = parseBoard(foundSolution);
        validator = new Validation(result);
        this.potentialChromosome = result;
    }
    //


    @Override
    public int[][] getPotentialChromosome() {
        return potentialChromosome;
    }

    private int[][] parseBoard(List<Node> foundSolution) {
        int[][] result = new int[size][size];
        for (Node n : foundSolution) {
            Node rcNode = n;
            int min = Integer.parseInt(rcNode.col.name);
            for (Node tmp = n.right; tmp != n; tmp = tmp.right) {
                int val = Integer.parseInt(tmp.col.name);
                if (val < min) {
                    min = val;
                    rcNode = tmp;
                }
            }
            int ans1 = Integer.parseInt(rcNode.col.name);
            int ans2 = Integer.parseInt(rcNode.right.col.name);
            int r = ans1 / size;
            int c = ans1 % size;
            int num = (ans2 % size) + 1;
            result[r][c] = num;
        }
        return result;
    }


    public SudokuSolution(int boardSize) {
        size = boardSize;
    }

    public int getSize() {
        return size;
    }
}