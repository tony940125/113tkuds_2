
import java.io.*;
import java.util.*;

public class LC25_ReverseKGroup_Shifts {

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

        // 讀取 k
        String lineK = br.readLine();
        if (lineK == null) {
            return;
        }
        int k = Integer.parseInt(lineK.trim());

        // 讀取序列（可能為空行）
        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) {
            return; // 空序列→不輸出
        }
        // 建表
        StringTokenizer st = new StringTokenizer(line);
        ListNode dummy = new ListNode(0), tail = dummy;
        while (st.hasMoreTokens()) {
            tail.next = new ListNode(Long.parseLong(st.nextToken()));
            tail = tail.next;
        }
        ListNode head = dummy.next;

        // k=1 或空表直接輸出原樣
        if (k <= 1 || head == null) {
            printList(head);
            return;
        }

        // 分組反轉
        head = reverseKGroup(head, k);

        // 輸出
        printList(head);
    }

    // 反轉每組 k 節點；不足 k 的尾端保持原樣
    static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode groupPrev = dummy;

        while (true) {
            // 找到本組的第 k 個節點
            ListNode kth = getKth(groupPrev, k);
            if (kth == null) {
                break;              // 不足 k：結束

                        }ListNode groupNext = kth.next;

            // 反轉 [groupPrev.next, kth] 區間
            ListNode prev = groupNext;
            ListNode cur = groupPrev.next;
            while (cur != groupNext) {
                ListNode tmp = cur.next;
                cur.next = prev;
                prev = cur;
                cur = tmp;
            }

            // 接回已反轉的這一組
            ListNode newGroupHead = kth;         // 反轉後的新表頭
            ListNode newGroupTail = groupPrev.next; // 反轉前的起點變尾巴
            groupPrev.next = newGroupHead;
            groupPrev = newGroupTail;            // 下一組從尾巴後開始
        }
        return dummy.next;
    }

    // 從 start 出發往後走 k 步（回傳第 k 個節點；若不足 k 回傳 null）
    static ListNode getKth(ListNode start, int k) {
        while (start != null && k > 0) {
            start = start.next;
            k--;
        }
        return start;
    }

    static void printList(ListNode head) throws IOException {
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
}

/*
 * Time Complexity: O(n)
 * 說明：每個節點最多被走訪與指標重連一次；每組反轉為 O(k)，總長度 n ⇒ O(n)。
 *
 * Space Complexity: O(1)
 * 說明：原地反轉，僅使用固定數量的指標變數。
 */
