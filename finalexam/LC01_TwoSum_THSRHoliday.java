
import java.io.*;
import java.util.*;

/**
 * LC01_TwoSum_THSRHoliday 讀取： 第一行：n target 第二行：n 個整數（座位數） 輸出： 兩個索引 i j（空格分隔），或
 * -1 -1
 *
 * 範例： 輸入： 4 55 20 15 35 40 輸出： 0 2
 *
 * /*
 *  * Time Complexity: O(n) * 說明：單一路徑掃描陣列，每個元素對 HashMap 做一次均攤 O(1) 的查找/插入。 * *
 * Space Complexity: O(n) * 說明：最壞情況需在 HashMap 儲存最多 n 筆「需要的數」對應索引。
 *
 */
public class LC01_TwoSum_THSRHoliday {

    // 簡易且高效的輸入讀取器（支援 long）
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

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' '); // skip spaces
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int n = (int) fs.nextLong();
        long target = fs.nextLong();

        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = fs.nextLong();
        }

        // HashMap<需要的數, 索引>：若之後遇到 key（也就是目前數），就能與 value 索引成對
        HashMap<Long, Integer> needToIndex = new HashMap<>(Math.max(16, (int) (n / 0.75f) + 1));

        for (int i = 0; i < n; i++) {
            long x = a[i];
            Integer partner = needToIndex.get(x);
            if (partner != null) {
                // 找到一組解：partner + i
                System.out.println(partner + " " + i);
                return;
            }
            long need = target - x;
            // 僅在第一次出現時記錄，可輸出任一解
            if (!needToIndex.containsKey(need)) {
                needToIndex.put(need, i);
            }
        }

        // 若沒有任一對符合
        System.out.println("-1 -1");
    }
}
