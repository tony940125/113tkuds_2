/*
 * Time Complexity: O(n)
 * 說明：採用自底向上的 heapifyDown 建堆；內層下濾在各層總位移次數不超過 n，
 *       依位階幾何級數收斂，整體建堆時間為線性 O(n)，較逐一 insert 的 O(n log n) 更優。
 */

import java.io.*;
import java.util.*;

public class M01_BuildHeap {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String typeLine;
        // 讀取 type（max/min），允許前後空白
        while ((typeLine = br.readLine()) != null && typeLine.trim().isEmpty()) {}
        if (typeLine == null) return;
        String type = typeLine.trim().toLowerCase();

        // 讀取 n
        String nLine;
        while ((nLine = br.readLine()) != null && nLine.trim().isEmpty()) {}
        if (nLine == null) return;
        int n = Integer.parseInt(nLine.trim());
        int[] a = new int[n];

        // 讀取 n 個整數（可能跨行）
        int filled = 0;
        String line;
        StringTokenizer st = null;
        while (filled < n && (line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            st = new StringTokenizer(line);
            while (st.hasMoreTokens() && filled < n) {
                a[filled++] = Integer.parseInt(st.nextToken());
            }
        }

        buildHeap(a, type);

        // 輸出：h1 h2 … hn (0-based 陣列表示)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(' ');
            sb.append(a[i]);
        }
        System.out.println(sb.toString());
    }

    // 自底向上建堆
    private static void buildHeap(int[] a, String type) {
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(a, i, n, type);
        }
    }

    // 下濾（iterative），依 type 決定比較方向
    private static void siftDown(int[] a, int i, int n, String type) {
        // sign=+1 表示 max-heap（較大者往上），sign=-1 表示 min-heap（較小者往上）
        int sign = "max".equals(type) ? 1 : -1;

        while (true) {
            int left = i * 2 + 1;
            if (left >= n) break;
            int right = left + 1;

            // 選擇更「優先」的子節點
            int best = left;
            if (right < n && compare(a[right], a[left], sign) > 0) {
                best = right;
            }

            // 若子節點比父節點更「優先」，則交換並繼續下濾
            if (compare(a[best], a[i], sign) > 0) {
                int tmp = a[i];
                a[i] = a[best];
                a[best] = tmp;
                i = best;
            } else {
                break;
            }
        }
    }

    // 根據 sign 決定比較方向：sign=+1 -> 大頂堆；sign=-1 -> 小頂堆
    private static int compare(int x, int y, int sign) {
        // 回傳 >0 代表 x 比 y 更「優先」
        return sign * Integer.compare(x, y);
    }
}