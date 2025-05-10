/**
 * Leetcode: Word Search II (Hard)
 * 
 * Given a 2D board and a list of words, find all words in the board that exist in the word list.
 * Each word must be constructed from letters of sequentially adjacent cells, where 
 * adjacent cells are horizontally or vertically neighboring. The same letter cell may not 
 * be used more than once in a word.
 * 
 * This solution uses:
 * - A Trie (Prefix Tree) to store the word list for fast prefix lookup.
 * - DFS backtracking to explore valid paths on the board.
 * 
 * Time Complexity: O(M * N * 4^L) where M,N = board size, L = max word length.
 * Space Complexity: O(W * L) for the Trie, where W = number of words.
 */

import java.util.*;

public class Solution {

    /**
     * TrieNode represents each node in the prefix tree.
     * Each node has up to 26 children (one for each lowercase English letter).
     * The `word` field is non-null only at the end of a valid word.
     */
      /**
     * ======================================
     * Trie (Prefix Tree) - Data Structure
     * ======================================
     *
     * A Trie is a tree-like data structure used to efficiently store and retrieve strings, 
     * especially useful for prefix-based operations. It's widely used in applications like:
     * 
     *  - Autocomplete systems
     *  - Spell checkers
     *  - Word games (like Boggle, Word Search)
     *  - IP routing (with binary tries)
     *
     * ---------------------------
     * Structure of a Trie Node:
     * ---------------------------
     * Each TrieNode typically contains:
     *  - An array of child TrieNodes, one for each letter (usually 26 for lowercase a-z)
     *  - An optional flag or field to indicate the end of a valid word (e.g., `isEnd`, or storing the word itself)
     *
     * --------------------------
     * Basic Trie Operations:
     * --------------------------
     * 1. insert(String word): Add a word into the Trie.
     * 2. search(String word): Check if a full word exists in the Trie.
     * 3. startsWith(String prefix): Check if any word in the Trie starts with the given prefix.
     *
     * ------------------------------------
     * Advantages of Trie over HashMap Set:
     * ------------------------------------
     * - Faster prefix searching
     * - No need to iterate through all keys for prefix match
     * - Better suited for problems involving sequential character access
     *
     * ------------------------------------------
     * Use in Word Search II (LeetCode Problem):
     * ------------------------------------------
     * In this problem, we use a Trie to:
     *  - Store all dictionary words efficiently.
     *  - Prune DFS branches early if current path doesn't match any prefix.
     * 
     * This leads to major performance gains versus brute force approaches,
     * especially when the board is large or the word list is long.
     *
     * Time Complexity:
     * - Trie construction: O(W * L), where W = number of words, L = average word length
     * - DFS search: O(M * N * 4^L), where M, N = board dimensions, L = max word length
     */

    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word = null; // stores full word at end node
    }

    /**
     * Finds all words from the list that can be constructed from letters of adjacent
     * cells on the board.
     *
     * @param board 2D grid of characters
     * @param words Array of words to search for
     * @return List of found words
     */
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();

        TrieNode root = buildTrie(words); // Step 1: Build the Trie from words

        int m = board.length;
        int n = board[0].length;

        // Step 2: DFS from every cell on the board
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, i, j, root, result);
            }
        }

        return result;
    }

    /**
     * Builds a Trie from the given list of words.
     *
     * @param words List of words to insert into the Trie
     * @return Root of the Trie
     */
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char ch : word.toCharArray()) {
                int idx = ch - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.word = word; // Mark the end of a word
        }
        return root;
    }

    /**
     * Depth-first search to explore all valid word paths from a given board cell.
     *
     * @param board  The board being explored
     * @param i      Row index
     * @param j      Column index
     * @param node   Current Trie node
     * @param result List to collect found words
     */
    private void dfs(char[][] board, int i, int j, TrieNode node, List<String> result) {
        char ch = board[i][j];

        // Base case: visited cell or invalid character path
        if (ch == '#' || node.children[ch - 'a'] == null) return;

        node = node.children[ch - 'a'];

        // Found a word at this node
        if (node.word != null) {
            result.add(node.word);
            node.word = null; // Avoid duplicates
        }

        board[i][j] = '#'; // Mark the cell as visited

        // Explore in 4 directions: up, down, left, right
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : dirs) {
            int ni = i + d[0], nj = j + d[1];
            if (ni >= 0 && ni < board.length && nj >= 0 && nj < board[0].length) {
                dfs(board, ni, nj, node, result);
            }
        }

        board[i][j] = ch; // Unmark the cell
    }
}
