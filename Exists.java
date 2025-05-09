/*

79. Word Search

Given an m x n grid of characters board and a string word, return true if word exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

 

*/

class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;

        // Frequency-based pruning
        if (!canConstruct(board, word)) return false;

        // Normal DFS search
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, i, j, 0)) return true;
            }
        }

        return false;
    }

    private boolean dfs(char[][] board, String word, int i, int j, int index) {
        if (index == word.length()) return true;
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length ||
            board[i][j] != word.charAt(index)) return false;

        char temp = board[i][j];
        board[i][j] = '#'; // mark visited

        boolean found = dfs(board, word, i + 1, j, index + 1) ||
                        dfs(board, word, i - 1, j, index + 1) ||
                        dfs(board, word, i, j + 1, index + 1) ||
                        dfs(board, word, i, j - 1, index + 1);

        board[i][j] = temp; // unmark
        return found;
    }

    private boolean canConstruct(char[][] board, String word) {
        int[] boardFreq = new int[128];
        int[] wordFreq = new int[128];

        for (char[] row : board)
            for (char c : row)
                boardFreq[c]++;

        for (char c : word.toCharArray())
            wordFreq[c]++;

        for (int i = 0; i < 128; i++)
            if (wordFreq[i] > boardFreq[i])
                return false;

        return true;
    }
}
