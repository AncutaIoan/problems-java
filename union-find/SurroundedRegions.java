/*
130. Surrounded Regions

You are given an m x n matrix board containing letters 'X' and 'O', capture regions that are surrounded:

Connect: A cell is connected to adjacent cells horizontally or vertically.
Region: To form a region connect every 'O' cell.
Surround: The region is surrounded with 'X' cells if you can connect the region with 'X' cells and none of the region cells are on the edge of the board.
To capture a surrounded region, replace all 'O's with 'X's in-place within the original board. You do not need to return anything.

 

Example 1:

Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]

Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
*/

class Solution {
    private int rows, cols;
    private int[] parent;
    private int dummyNode;

    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;
        rows = board.length;
        cols = board[0].length;
        int size = rows * cols;
        dummyNode = size;  // Virtual node for border-connected 'O's
        parent = new int[size + 1]; // +1 for dummy node

        // Initialize Union-Find
        for (int i = 0; i <= size; i++) {
            parent[i] = i;
        }

        // Union all border 'O's with dummy node
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == 'O') {
                    int index = r * cols + c;

                    if (isOnBorder(r, c)) {
                        union(index, dummyNode);
                    }

                    // Union with neighbors if they are also 'O'
                    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
                    for (int[] dir : directions) {
                        int nr = r + dir[0];
                        int nc = c + dir[1];
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && board[nr][nc] == 'O') {
                            int neighborIndex = nr * cols + nc;
                            union(index, neighborIndex);
                        }
                    }
                }
            }
        }

        // Flip all 'O's not connected to dummy node
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == 'O') {
                    int index = r * cols + c;
                    if (find(index) != find(dummyNode)) {
                        board[r][c] = 'X';
                    }
                }
            }
        }
    }

    private boolean isOnBorder(int r, int c) {
        return r == 0 || c == 0 || r == rows - 1 || c == cols - 1;
    }

    private int find(int x) {
        if (x != parent[x]) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            parent[rootX] = rootY;
        }
    }
}
