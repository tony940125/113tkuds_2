/*
 * Time Complexity: O(n log K) ；Space: O(K)
 * 說明：
 * 1) 逐筆讀入 n 筆資料並維護大小為 K 的 Min-Heap，單次插入/淘汰為 O(log K)，總計 O(n log K)。
 * 2) 最後將堆中元素輸出前做一次排序（至多 K 筆），O(K log K)；整體仍為 O(n log K)。
 * 3) 若 K << n，遠優於整體排序 O(n log n)。
 */

import java.io.*;
import java.util.*;

public class M03_TopKConvenience {
    static class Item {
        String name;
        int qty;
        Item(String name, int qty) { this.name = name; this.qty = qty; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String first;
        while ((first = br.readLine()) != null && first.trim().isEmpty()) {}
        if (first == null) return;

        StringTokenizer st = new StringTokenizer(first);
        int n = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // Min-Heap：qty 升冪；同 qty 時 name 降冪（為了把「較差」者放頂端以便淘汰）
        PriorityQueue<Item> heap = new PriorityQueue<>(new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                if (a.qty != b.qty) return Integer.compare(a.qty, b.qty); // 小的更小（較差）
                return b.name.compareTo(a.name); // 名字較大的視為較差 -> 放前面以便淘汰
            }
        });

        for (int i = 0; i < n; i++) {
            String line;
            while ((line = br.readLine()) != null && line.trim().isEmpty()) {}
            if (line == null) break;
            st = new StringTokenizer(line);
            String name = st.nextToken();         // 題目保證品名不含空白
            int qty = Integer.parseInt(st.nextToken());

            Item cur = new Item(name, qty);
            if (heap.size() < K) {
                heap.offer(cur);
            } else {
                Item worst = heap.peek(); // 目前 K 名中最差
                if (isBetter(cur, worst)) {
                    heap.poll();
                    heap.offer(cur);
                }
            }
        }

        // 取出並依輸出規則排序：qty 降冪；同 qty 時 name 升冪
        List<Item> res = new ArrayList<>(heap);
        res.sort(new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                if (a.qty != b.qty) return Integer.compare(b.qty, a.qty); // 大的在前
                return a.name.compareTo(b.name); // 名字小的在前（字典序）
            }
        });

        // 輸出（不足 K 則全列）
        StringBuilder sb = new StringBuilder();
        for (Item it : res) {
            sb.append(it.name).append(' ').append(it.qty).append('\n');
        }
        System.out.print(sb.toString());
    }

    // 依最終排序規則判斷 a 是否「比 b 好」
    private static boolean isBetter(Item a, Item b) {
        if (a.qty != b.qty) return a.qty > b.qty;                 // 較大銷量較好
        return a.name.compareTo(b.name) < 0;                      // 同量時名字字典序較小較好
    }
}