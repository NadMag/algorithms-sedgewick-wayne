import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double T95 = 1.96;
    private final double[] percThresholds;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        percThresholds = new double[trials];

        for (int trialNumer = 0; trialNumer < trials; trialNumer++) {
            int openSites = runPercolationTrial(n);
            double percThreshold = (double) openSites / (n*n);
            percThresholds[trialNumer] = percThreshold;
        }
    }

    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(gridSize, trials);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println(String.format("95%% confidence interval = [%1$,.16f, %2$,.16f]", stats.confidenceLo(),
                                     stats.confidenceHi()));
    }

    public double mean() {
        return StdStats.mean(percThresholds);
    }

    public double stddev() {
        return StdStats.stddev(percThresholds);
    }

    public double confidenceLo() {
        return mean() - (T95 * stddev()) / Math.sqrt(percThresholds.length);
    }                  // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + (T95 * stddev()) / Math.sqrt(percThresholds.length);
    }

    private int runPercolationTrial(int gridSize) {
        Percolation grid = new Percolation(gridSize);

        while (!grid.percolates()) {
            int[] randPoint = getRandomPoint(gridSize);
            grid.open(randPoint[0], randPoint[1]);
        }

        return grid.numberOfOpenSites();
    }

    private static int[] getRandomPoint(int gridSize) {
        return new int[]{StdRandom.uniform(gridSize) + 1, StdRandom.uniform(gridSize) + 1};
    }
}

