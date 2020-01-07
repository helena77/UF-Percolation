import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] times;
    private final int trial;

    
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) 
            throw new IllegalArgumentException("n and trials should be > 0");
        trial = trials;
        int count = 0;
        
        times = new double[trials];      
        for (int i = 0; i < trials; i++) {
            times[i] = -1.0;
        }
        
        for (int i = 0; i < trial; i++) {
            Percolation grid = new Percolation(n);          
            while (!grid.percolates()) {
                int openRow = StdRandom.uniform(1, n + 1);
                int openCol = StdRandom.uniform(1, n + 1);
                if (!grid.isOpen(openRow, openCol)) {
                    grid.open(openRow, openCol);
                    count++;
                }              
            }
            times[i] = (double) count / (n * n);
        }
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(times);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(times);
    }
    
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trial);
        
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trial);
    }
    
     public static void main(String[] args) {
         if (args.length != 2) {
            StdOut.println("Usage: PercolationStats N T");
            return;
        }

        PercolationStats compute = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        
        StdOut.println("mean: " + compute.mean());
        StdOut.println("stddev: " + compute.stddev());
        StdOut.println("95% confidence interval: [" + compute.confidenceLo() + 
                       ", " + compute.confidenceHi() + "]");
    }
    
}