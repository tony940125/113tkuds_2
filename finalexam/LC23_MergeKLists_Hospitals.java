
import java.io.*;
import java.util.*;

/**
 * LC23_MergeKLists_Hospitals 輸入： k 接著 k 行，每行若干升序整數，並以 -1 作為該行串列結尾 輸出：
 * 合併後升序序列（空白分隔；若為空則不輸出）
 */
public class LC23_MergeKLists_Hospitals {

    // 單向鏈結節點
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

        Integer nextIntOrNull() throws IOException {
            String s = next();
            return (s == null) ? null : Integer.parseInt(s);
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        Integer kTok = fs.nextIntOrNull();
        if (kTok == null) {
            return; // 無輸入

                }int k = kTok;

        ListNode[] lists = new ListNode[k];
        for (int i = 0; i < k; i++) {
            lists[i] = readOneListUntilMinusOne(fs);
        }

        ListNode merged = mergeKLists(lists);

        // 輸出合併後序列（空白分隔；若為空不輸出）
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

    // 讀取一行串列：以 -1 結尾；可能是空串列（第一個即為 -1）
    private static ListNode readOneListUntilMinusOne(FastScanner fs) throws IOException {
        ListNode dummy = new ListNode(0), tail = dummy;
        while (true) {
            int v = fs.nextInt(); // 題目保證格式正確
            if (v == -1) {
                break;
            }
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    // 以最小堆合併 k 條已排序鏈結串列（升序）
    static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        for (ListNode head : lists) {
            if (head != null) {
                pq.offer(head);
            }
        }

        ListNode dummy = new ListNode(0), tail = dummy;
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            tail.next = node;
            tail = tail.next;
            if (node.next != null) {
                pq.offer(node.next);
            }
        }
        return dummy.next;
    }
}

/*
 * Time Complexity: O(N log k)
 * 說明：N 為所有節點總數；每次從大小 ≤k 的最小堆提取/插入為 O(log k)，總共 N 次。
 *
 * Space Complexity: O(k)
 * 說明：最小堆同時最多存放 k 個當前頭節點；合併過程原地重連指標。
 */
