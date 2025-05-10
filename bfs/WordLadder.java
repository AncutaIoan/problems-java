/* 127. Word Ladder */

import java.util.*;

/**
 * This class solves the Word Ladder problem using Breadth-First Search (BFS).
 *
 * Problem:
 * Given a beginWord, endWord, and a wordList, determine the length of the shortest
 * transformation sequence from beginWord to endWord such that:
 * 
 * 1. Only one letter can be changed at a time.
 * 2. Each transformed word must exist in the wordList.
 * 3. Return the number of words in the shortest transformation (including begin and end words).
 * 4. If no such transformation is possible, return 0.
 *
 * Example:
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * Output: 5
 * Explanation: "hit" -> "hot" -> "dot" -> "dog" -> "cog"
 *
 * Approach:
 * - Treat each word as a node in a graph.
 * - Two words are connected if they differ by exactly one letter.
 * - Use BFS to find the shortest path from beginWord to endWord.
 *
 * Time Complexity: O(N * L^2), where N = size of wordList, L = length of each word.
 * Space Complexity: O(N) for the queue and visited word set.
 */
public class Solution {

    /**
     * Returns the length of the shortest transformation sequence from beginWord to endWord.
     *
     * @param beginWord the starting word
     * @param endWord the target word to reach
     * @param wordList the list of allowed words (dictionary)
     * @return the number of words in the shortest transformation, or 0 if impossible
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Convert wordList to a set for O(1) lookups
        Set<String> wordSet = new HashSet<>(wordList);
        
        // If the endWord is not in the dictionary, return 0
        if (!wordSet.contains(endWord)) return 0;

        // Queue for BFS
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        int steps = 1; // Start with step 1 (including beginWord)

        // Begin BFS
        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            // Process all nodes at the current level
            for (int i = 0; i < levelSize; i++) {
                String currentWord = queue.poll();
                char[] chars = currentWord.toCharArray();

                // Try replacing every character with 'a' to 'z'
                for (int j = 0; j < chars.length; j++) {
                    char originalChar = chars[j];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) continue;

                        chars[j] = c;
                        String newWord = new String(chars);

                        // Check if we found the end word
                        if (newWord.equals(endWord)) {
                            return steps + 1;
                        }

                        // If the new word is in the dictionary, add to queue
                        if (wordSet.contains(newWord)) {
                            queue.offer(newWord);
                            wordSet.remove(newWord); // Avoid revisiting
                        }
                    }

                    // Restore original character before trying next position
                    chars[j] = originalChar;
                }
            }

            // Increment step count (going to next level in BFS)
            steps++;
        }

        // If endWord was never found
        return 0;
    }
}
