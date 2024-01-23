package dancingLinks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class DancingLinks {
    static final boolean flag = true;


    // Monitor the maximum depth threshold, this depth represent how close to a valid sudoku puzzle
    private int maxDepth;
    private int currentDepth;

    // integrate with ga
    public List<int[][]> partialSolutionGrid = new ArrayList<>();

    class Node {
        Node left, right, up, down;
        ColumnNode col;

        Node linkRight(Node node) {
            node.right = this.right;
            node.right.left = node;
            node.left = this;
            this.right = node;
            return node;
        }

        Node linkDown(Node node) {
            assert (this.col == node.col);
            node.down = this.down;
            node.down.up = node;
            node.up = this;
            this.down = node;
            return node;
        }

        void unlinkLeftRight() {
            this.left.right = this.right;
            this.right.left = this.left;
            iteration++;
        }

        void relinkLeftRight() {
            this.left.right = this.right.left = this;
            iteration++;
        }

        void unlinkUpDown() {
            this.up.down = this.down;
            this.down.up = this.up;
            iteration++;
        }

        void relinkUpdown() {
            this.up.down = this.down.up = this;
            iteration++;
        }

        public Node() {
            this.up = this.down = this.left = this.right = this;
        }

        public Node(ColumnNode col) {
            this();
            this.col = col;
        }
    }

    class ColumnNode extends Node {
        int size;
        String name;

        public ColumnNode(String name) {
            super();
            size = 0;
            this.name = name;
            this.col = this;
        }

        void cover() {
            unlinkLeftRight();
            for (Node n = this.down; n != this; n = n.down) {
                for (Node m = n.right; m != n; m = m.right) {
                    m.unlinkUpDown();
                    m.col.size--;
                }
            }
            header.size--;
        }

        void uncover() {
            for (Node n = this.up; n != this; n = n.up) {
                for (Node m = n.left; m != n; m = m.left) {
                    m.col.size++;
                    m.relinkUpdown();
                }
            }
            relinkLeftRight();
            header.size++;
        }
    }

    private ColumnNode header;
    private int solutionCount = 0;
    private int iteration = 0;
    private Solution solution;
    private List<Node> foundSolution;

    public List<Node> getFoundSolution() {
        return foundSolution;
    }

    public DancingLinks(int[][] initialSudoku, Solution solution) {
        header = generateDancingLinkMatrix(initialSudoku);
        this.solution = solution;

        // for generate chromosome ga
//        this.maxDepth = (int)(Math.floor(Math.pow(initialSudoku.length, 2) * 60 / 100));

        this.maxDepth = 60;
    }

    private void dancingLinksAlgorithmX(int i) {
        // generate potential chromosome
        if(currentDepth>= maxDepth){
            solution.getPartialSolution(foundSolution);
            partialSolutionGrid.add(solution.getPotentialChromosome());
            return;
        }
        //
        if (header.right == header) {
            if (flag) {
                System.out.println("==========================================");
                System.out.println(">>>>> solution: " + solutionCount + " <<<<< \n");
            }
            solution.handleSudokuSolution(foundSolution);
            if (flag)
                System.out.println("==========================================");
            solutionCount++;
        } else {
            ColumnNode colNode = optimizeSelection();
            colNode.cover();

            for (Node n = colNode.down; n != colNode; n = n.down) {
                foundSolution.add(n);
                for (Node m = n.right; m != n; m = m.right) {
                    m.col.cover();
                }
                currentDepth = i;
                dancingLinksAlgorithmX(i + 1);

                n = foundSolution.remove(foundSolution.size() - 1);
                colNode = n.col;

                for (Node m = n.left; m != n; m = m.left) {
                    m.col.uncover();
                }
            }
            colNode.uncover();
        }
    }

    private ColumnNode naiveSelection() {
        return (ColumnNode) header.right;
    }

    private ColumnNode optimizeSelection() {
        int min = Integer.MAX_VALUE;
        ColumnNode node = null;
        for (ColumnNode n = (ColumnNode) header.right; n != header; n = (ColumnNode) n.right) {
            if (n.size < min) {
                min = n.size;
                node = n;
            }
        }
        return node;
    }

    private void sudokuBoard() {
        StringBuilder strBuilder = new StringBuilder();
        System.out.println(">>>>> Board: <<<<< \n");
        for (ColumnNode n = (ColumnNode) header.right; n != header; n = (ColumnNode) n.right) {
            for (Node m = n.down; m != n; m = m.down) {
                strBuilder.append(m.col.name).append("-->");
                for (Node k = m.right; k != m; k = k.right) {
                    strBuilder.append(k.col.name).append("-->");
                }
                System.out.println(strBuilder);
            }
        }
    }

    private ColumnNode generateDancingLinkMatrix(int[][] sudoku) {
        int cols = sudoku[0].length;
        int rows = sudoku.length;

        ColumnNode header = new ColumnNode("header");
        ArrayList<ColumnNode> columnNodes = new ArrayList<>();

        for (int i = 0; i < cols; i++) {
            ColumnNode node = new ColumnNode(Integer.toString(i));
            columnNodes.add(node);
            header = (ColumnNode) header.linkRight(node);
        }
        header = header.right.col;

        for (int i = 0; i < rows; i++) {
            Node prev = null;
            for (int j = 0; j < cols; j++) {
                int tempVal = sudoku[i][j];
                if (tempVal == 1) {
                    ColumnNode col = columnNodes.get(j);
                    Node generatedNode = new Node(col);
                    if (prev == null)
                        prev = generatedNode;
                    col.up.linkDown(generatedNode);
                    prev = prev.linkRight(generatedNode);
                    col.size++;
                }
            }
        }

        header.size = cols;
        return header;
    }

    public int getiteration() {
        return iteration;
    }

    // monitor the maximum depth

    public int getMaxDepth() {
        return maxDepth;
    }

    public void runSolver() {
        this.solutionCount = 0;
        this.iteration = 0;
        this.currentDepth = 0;
        this.foundSolution = new LinkedList<>();
        dancingLinksAlgorithmX(0);
        if (flag) {
            System.out.println(">>>>> Number of iteration: " + iteration + " <<<<<");
            System.out.printf(">>>> maximum depth reach: %d <<<<< \n", getMaxDepth());
        }
    }

    // function to run with ga
}
