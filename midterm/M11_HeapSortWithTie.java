/*
 * Time Complexity: O(n log n)
 * 說明：先以 Bottom-up 建立 Max-Heap，建堆為 O(n)；接著做 n-1 次 extract-max，
 *       每次下濾為 O(log n)，總計 O(n log n)。額外空間 O(1)（就地排序）。
 * 平手規則（重要）：
 *   以 (score, index) 比較；建立「Max-Heap」時定義：
 *     A 比 B 大 ⇔ (A.score > B.score) 或 (A.score == B.score 且 A.index > B.index)
 *   如此在從尾端回填時（由大到小放右邊），同分者「索引較大者」先被放到更右邊，
 *   因而最終遞增輸出時，左到右會是「索引較小者」在前，符合題意。
 */

import java.io.*;
import java.util.*;

public class M11_HeapSortWithTie {
    static class P {
        int score;
        int idx;
        P(int s, int i) { score = s; idx = i; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 讀 n
        String line;
        while ((line = br.readLine()) != null && line.trim().isEmpty()) {}
        if (line == null) return;
        int n = Integer.parseInt(line.trim());

        // 讀 n 個分數（可能跨行）
        P[] a = new P[n];
        int filled = 0;
        while (filled < n && (line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && filled < n) {
                int s = Integer.parseInt(st.nextToken());
                a[filled] = new P(s, filled); // 記錄原始索引
                filled++;
            }
        }

        // Heap Sort（Max-Heap，依 (score↑, tie: index↑) 視為較大）
        heapSort(a);

        // 輸出：遞增分數（中間以空白分隔）
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(' ');
            sb.append(a[i].score);
        }
        System.out.println(sb.toString());
    }

    // ---------------- Heap Sort 核心 ----------------

    private static void heapSort(P[] a) {
        int n = a.length;
        buildMaxHeap(a, n);
        for (int end = n - 1; end > 0; end--) {
            swap(a, 0, end);          // 將最大值放到當前尾端
            siftDown(a, 0, end);      // 縮小堆範圍，恢復堆性質
        }
        // 由於我們用 Max-Heap 從尾端回填，最終 a 為遞增序
    }

    private static void buildMaxHeap(P[] a, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) siftDown(a, i, n);
    }

    // 下濾：在 [0, size) 範圍維持 Max-Heap
    private static void siftDown(P[] a, int i, int size) {
        while (true) {
            int left = i * 2 + 1;
            if (left >= size) break;
            int right = left + 1;
            int best = left;
            if (right < size && greater(a[right], a[left])) best = right;
            if (greater(a[best], a[i])) {
                swap(a, i, best);
                i = best;
            } else break;
        }
    }

    // 定義「較大」：score 較大者大；同分則 index 較大者大（見上方註解）
    private static boolean greater(P x, P y) {
        if (x.score != y.score) return x.score > y.score;
        return x.idx > y.idx;
    }

    private static void swap(P[] a, int i, int j) {
        P t = a[i]; a[i] = a[j]; a[j] = t;
    }
}