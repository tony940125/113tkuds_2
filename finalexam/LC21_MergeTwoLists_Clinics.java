
import java.io.*;

public class LC21_MergeTwoLists_Clinics {

    // 單向鏈結節點
    static class ListNode {

        int val;
        ListNode next;

        ListNode(int v) {
            this.val = v;
        }
    }

    // 輕量輸入
    static class FastScanner {

        private final InputStream in;
        private final byte[] buf = new byte[1 << 16];
        private int p = 0, n = 0;

        FastScanner(InputStream is) {
            this.in = is;
        }

        private int read() throws IOException {
            if (p >= n) {
                n = in.read(buf);
                p = 0;
                if (n <= 0) {
                    return -1;
                }
            }
            return buf[p++];
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
            return;
        }
        int n = Integer.parseInt(tok);
        int m = Integer.parseInt(fs.next());

        ListNode a = build(fs, n);
        ListNode b = build(fs, m);

        ListNode merged = mergeTwoLists(a, b);

        // 輸出
        StringBuilder out = new StringBuilder();
        for (ListNode cur = merged; cur != null; cur = cur.next) {
            if (out.length() > 0) {
                out.append(' ');
            }
            out.append(cur.val);
        }
        if (out.length() > 0) {
            System.out.println(out.toString());
        }
    }

    // 建立長度 n 的鏈結串列（依輸入順序）
    static ListNode build(FastScanner fs, int n) throws IOException {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int i = 0; i < n; i++) {
            tail.next = new ListNode(fs.nextInt());
            tail = tail.next;
        }
        return dummy.next;
    }

    // 合併兩條已排序鏈結串列（升序）
    static ListNode mergeTwoLists(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0), tail = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }
}

/*
 * Time Complexity: O(n + m)
 * 說明：每個節點恰被讀取與連結一次。
 *
 * Space Complexity: O(1)
 * 說明：就地指標操作，僅使用固定輔助節點與指標。
 */
