import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class Percolation {

    private boolean[][] grid;
    private int openSites = 0;
    private final WeightedQuickUnionUF disjointData;
    private final int virtualTopIndex;
    private final int virtualBottomIndex;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        grid = new boolean[n][n];
        for (boolean[] row : grid) {
            for (int col = 0; col < row.length; col++) {
                row[col] = true;
            }
        }
        disjointData = new WeightedQuickUnionUF(n*n + 2); // Two virtual sites for top and bottom

        virtualTopIndex = n*n;
        virtualBottomIndex = n*n +1;

        for (int col = 0; col < n; col++) {
            disjointData.union(toMatrixIndex(0, col), virtualTopIndex);
            disjointData.union(toMatrixIndex(n -1, col), virtualBottomIndex);
        }
    }

    public void open(int row, int col) {
        row = row - 1;
        col = col -1;
        validate(row);
        validate(col);

        if (grid[row][col]) {
            grid[row][col] = false;
            openSites++;
        }

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1 ; dx++) {
                if (!(row + dy < grid.length && 0 <= row + dy) || !(col + dx < grid.length
                        && 0 <= col + dx)) continue;
                if (Math.abs(dx + dy) != 1) continue;
                if (isOpen(row + 1 + dy, col + dx + 1)) {
                    disjointData.union(toMatrixIndex(row, col), toMatrixIndex(row + dy, col + dx));
                }
            }
        }
    }

    public boolean isOpen(int row, int col)  {
        row = row - 1;
        col = col -1;
        validate(row);
        validate(col);

        return !grid[row][col];
    }

    public boolean isFull(int row, int col)  {
        row = row - 1;
        col = col -1;
        validate(row);
        validate(col);

        return !grid[row][col] && disjointData.connected(virtualTopIndex, toMatrixIndex(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return disjointData.connected(virtualTopIndex, virtualBottomIndex);
    }

    private int toMatrixIndex(int row, int col) {
        return row * grid.length + col;
    }

    private void validate(int p) {
        int n = grid.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }
}
