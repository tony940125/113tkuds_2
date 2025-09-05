
import java.io.*;

public class LC11_MaxArea_FuelHoliday {

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

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        String t = fs.next();
        if (t == null) {
            System.out.println(0);
            return;
        }
        int n = Integer.parseInt(t);
        long[] h = new long[n];
        for (int i = 0; i < n; i++) {
            h[i] = fs.nextLong();
        }

        System.out.println(maxArea(h));
    }

    // 兩端夾逼，移動較短邊
    static long maxArea(long[] h) {
        int L = 0, R = h.length - 1;
        long ans = 0;
        while (L < R) {
            long height = Math.min(h[L], h[R]);
            long area = height * (R - L);
            if (area > ans) {
                ans = area;
            }
            if (h[L] <= h[R]) {
                L++;
            } else {
                R--;
            }
        }
        return ans;
    }
}

/*
 * Time Complexity: O(n)
 * 說明：左右指針各最多移動 n 次，每步常數時間。
 *
 * Space Complexity: O(1)
 * 說明：除輸入陣列外僅使用固定數個變數。
 */
