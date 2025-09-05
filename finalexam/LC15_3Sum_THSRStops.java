
import java.io.*;
import java.util.*;

public class LC15_3Sum_THSRStops {

    // 輕量讀取器
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
        String t = fs.next();
        if (t == null) {
            return; // 無輸入則不輸出

                }int n = Integer.parseInt(t);

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = fs.nextInt();
        }

        StringBuilder out = new StringBuilder();
        Arrays.sort(nums);

        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 跳過重複基準

                        }if (nums[i] > 0) {
                break; // 提前結束
            }
            int L = i + 1, R = n - 1;
            while (L < R) {
                int sum = nums[i] + nums[L] + nums[R];
                if (sum == 0) {
                    out.append(nums[i]).append(' ')
                            .append(nums[L]).append(' ')
                            .append(nums[R]).append('\n');
                    // 跳過重複 L、R
                    int leftVal = nums[L], rightVal = nums[R];
                    while (L < R && nums[L] == leftVal) {
                        L++;
                    }
                    while (L < R && nums[R] == rightVal) {
                        R--;
                    }
                } else if (sum < 0) {
                    L++;
                } else {
                    R--;
                }
            }
        }

        // 題目：若無任一組合 → 無輸出（不印任何行）
        if (out.length() > 0) {
            System.out.print(out.toString());
        }
    }
}

/*
 * Time Complexity: O(n^2)
 * 說明：排序 O(n log n)；外層 i 與內層雙指針合計最壞 O(n^2) 佔主導。
 *
 * Space Complexity: O(1)
 * 說明：就地排序（視 Arrays.sort 對 primitive 使用的快排/雙軸快排為原地），
 *       另僅常數額外變數；不計輸入陣列與輸出緩衝。
 */
