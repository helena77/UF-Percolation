import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int size;
    private final WeightedQuickUnionUF uf;
    private final int virtualTop;
    private final int virtualBottom;
    private int count;
    
    // create n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) 
            throw new IllegalArgumentException("n and trials should be > 0");
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);     
        count = 0;
        virtualTop = n * n;
        virtualBottom = n * n + 1;              
        size = n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int row2 = row - 1;
        int col2 = col - 1;
        // rowColValidate(row2, col2);
        grid[row2][col2] = true;
        count++;
        if (row2 == 0) 
            uf.union(col2 + size * row2, virtualTop);
        if (row2 == (size - 1))
            uf.union(col2 + size * row2, virtualBottom);
        if (row2 - 1 >= 0 && grid[row2 - 1][col2]) 
            uf.union(col2 + size * (row2 - 1), col2 + size * row2);           
        if (row2 + 1 < size && grid[row2 + 1][col2]) 
            uf.union(col2 + size * row2, col2 + size * (row2 + 1));           
        if (col2 - 1 >= 0 && grid[row2][col2 - 1]) 
            uf.union(col2 - 1 + size * row2, col2 + size * row2);          
        if (col2 + 1 < size && grid[row2][col2 + 1]) 
            uf.union(col2 + size * row2, col2 + 1 + size * row2);         
        
    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // rowColValidate(row - 1, col - 1);
        return grid[row - 1][col - 1];        
    }
    
    // is the site (row, col) fully?
    public boolean isFull(int row, int col) {
        // rowColValidate(row - 1, col - 1);
        return uf.find(col - 1 + size * (row - 1)) == uf.find(virtualTop);
    }
    
    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }
    
    // does the system percolate?
    public boolean percolates() {
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }
    
//    private void rowColValidate(int row, int col) {
//        if (row < 1 || row >= size || col < 1 || col >= size) 
//            throw new IllegalArgumentException();
//    }
}