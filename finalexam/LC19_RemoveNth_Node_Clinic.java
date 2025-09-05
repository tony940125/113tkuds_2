
import java.io.*;

public class LC19_RemoveNth_Node_Clinic {

    // 節點定義
    static class ListNode {

        int val;
        ListNode next;

        ListNode(int v) {
            this.val = v;
        }
    }

    // 輕量高速輸入
    static class FastScanner {

        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            this.in = is;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) {
                    return -1;
                }
            }
            return buffer[ptr++];
        }

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = read();
                if (c == -1) {
                    return null;
                }
            } while (c <= ' ');
            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        String tok = fs.next();
        if (tok == null) {
            return; // 無輸入則不輸出

                }int n = Integer.parseInt(tok);

        // 讀入 n 個節點，建立單向鏈結
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int i = 0; i < n; i++) {
            tail.next = new ListNode(fs.nextInt());
            tail = tail.next;
        }
        int k = fs.nextInt();

        ListNode newHead = removeNthFromEnd(dummy.next, k);

        // 輸出刪除後序列（以空白分隔；若為空串則不輸出任何數字）
        StringBuilder out = new StringBuilder();
        for (ListNode cur = newHead; cur != null; cur = cur.next) {
            if (out.length() > 0) {
                out.append(' ');
            }
            out.append(cur.val);
        }
        if (out.length() > 0) {
            System.out.println(out.toString());
        } // 空串時不輸出任何行
    }

    // 一次遍歷刪除倒數第 k 個節點
    static ListNode removeNthFromEnd(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next; // 先走 k 步
        }

        ListNode slow = dummy;
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // slow.next 為待刪節點
        slow.next = slow.next.next;
        return dummy.next;
    }
}

/*
 * Time Complexity: O(n)
 * 說明：fast 先前進 k 步，之後 fast/slow 同步一次掃描到串尾。
 *
 * Space Complexity: O(1)
 * 說明：僅使用固定數個指標變數，原地調整連結。
 */
