/*
25. Reverse Nodes in k-Group
Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.

You may not alter the values in the list's nodes, only nodes themselves may be changed.

*/
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;

        // Step 1: Check if there are at least k nodes to reverse
        ListNode node = head;
        int count = 0;
        while (node != null && count < k) {
            node = node.next;
            count++;
        }

        if (count < k) {
            return head; // not enough nodes to reverse
        }

        // Step 2: Reverse first k nodes
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = null;
        count = 0;

        while (curr != null && count < k) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            count++;
        }

        // Step 3: Recursively process the rest of the list
        if (next != null) {
            head.next = reverseKGroup(next, k);
        }

        // prev is the new head of the reversed group
        return prev;
    }
}
/*
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroupEnd = dummy;

        while (true) {
            ListNode kth = getKthNode(prevGroupEnd, k);
            if (kth == null) break;

            ListNode groupStart = prevGroupEnd.next;
            ListNode nextGroupStart = kth.next;

            // Reverse the k nodes
            ListNode prev = kth.next;
            ListNode curr = groupStart;
            while (curr != nextGroupStart) {
                ListNode temp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = temp;
            }

            // Connect with the previous part
            prevGroupEnd.next = kth;
            prevGroupEnd = groupStart;
        }

        return dummy.next;
    }

    private ListNode getKthNode(ListNode curr, int k) {
        while (curr != null && k > 0) {
            curr = curr.next;
            k--;
        }
        return curr;
    }
}

*/
