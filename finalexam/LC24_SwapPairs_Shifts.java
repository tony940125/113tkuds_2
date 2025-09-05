
import java.io.*;
import java.util.*;

public class LC24_SwapPairs_Shifts {

    // 單向鏈結節點
    static class ListNode {

        long val;
        ListNode next;

        ListNode(long v) {
            this.val = v;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        String line = br.readLine();
        if (line == null) {
            return;                 // 無輸入

                }line = line.trim();
        if (line.isEmpty()) {
            return;               // 空串列 → 不輸出
        }
        // 讀入一行整數建表
        StringTokenizer st = new StringTokenizer(line);
        ListNode dummy = new ListNode(0), tail = dummy;
        while (st.hasMoreTokens()) {
            tail.next = new ListNode(Long.parseLong(st.nextToken()));
            tail = tail.next;
        }
        ListNode head = dummy.next;

        // 成對交換：prev 指向 pair 前一格
        head = swapPairs(head);

        // 輸出
        StringBuilder out = new StringBuilder();
        for (ListNode cur = head; cur != null; cur = cur.next) {
            if (out.length() > 0) {
                out.append(' ');
            }
            out.append(cur.val);
        }
        if (out.length() > 0) {
            System.out.println(out.toString());
        }
    }

    // 迴圈方式相鄰成對交換
    static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode a = prev.next;       // 第 1 個
            ListNode b = a.next;          // 第 2 個

            // 交換 (a, b)
            a.next = b.next;
            b.next = a;
            prev.next = b;

            prev = a; // 下一組從 a 後面開始
        }
        return dummy.next;
    }
}

/*
 * Time Complexity: O(n)
 * 說明：每個節點至多被訪問與指標重連一次。
 *
 * Space Complexity: O(1)
 * 說明：就地指標交換，僅使用常數額外節點與指標。
 */
