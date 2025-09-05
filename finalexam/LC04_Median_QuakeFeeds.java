
import java.io.*;
import java.util.*;

public class LC04_Median_QuakeFeeds {

    // 輕量高速字串掃描器（逐字節跳空白）
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
            // skip whitespaces
            do {
                c = read();
                if (c == -1) {
                    return null;
                }
            } while (c <= ' ');
            // read token
            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        String t1 = fs.next(); // 讀 n
        if (t1 == null) {
            // 無輸入，題目的約束 n+m>=1，這裡輸出 0.0 以防空檔
            System.out.println("0.0");
            return;
        }
        int n = Integer.parseInt(t1);
        int m = Integer.parseInt(fs.next());

        double[] A = new double[n];
        for (int i = 0; i < n; i++) {
            A[i] = fs.nextDouble();
        }

        double[] B = new double[m];
        for (int j = 0; j < m; j++) {
            B[j] = fs.nextDouble();
        }

        // 確保 A 較短
        if (A.length > B.length) {
            double[] tmp = A;
            A = B;
            B = tmp;
            int tmpn = n;
            n = m;
            m = tmpn;
        }

        double median = findMedianTwoSorted(A, B);
        System.out.printf(Locale.US, "%.1f%n", median);
    }

    // 核心演算法：在較短陣列 A 上二分切割
    static double findMedianTwoSorted(double[] A, double[] B) {
        int n = A.length, m = B.length;
        int low = 0, high = n;
        int half = (n + m + 1) / 2;

        // 使用 ±Infinity 處理邊界
        final double NEG_INF = Double.NEGATIVE_INFINITY;
        final double POS_INF = Double.POSITIVE_INFINITY;

        while (low <= high) {
            int i = (low + high) >>> 1;   // A 的切點
            int j = half - i;             // B 的切點

            double Aleft = (i == 0) ? NEG_INF : A[i - 1];
            double Aright = (i == n) ? POS_INF : A[i];
            double Bleft = (j == 0) ? NEG_INF : B[j - 1];
            double Bright = (j == m) ? POS_INF : B[j];

            if (Aleft <= Bright && Bleft <= Aright) {
                // 合法切割
                if (((n + m) & 1) == 1) { // odd
                    return Math.max(Aleft, Bleft);
                } else { // even
                    double leftMax = Math.max(Aleft, Bleft);
                    double rightMin = Math.min(Aright, Bright);
                    return (leftMax + rightMin) / 2.0;
                }
            } else if (Aleft > Bright) {
                // i 太大，往左縮
                high = i - 1;
            } else {
                // Bleft > Aright，i 太小，往右擴
                low = i + 1;
            }
        }
        // 理論上不會到這裡，若到此回傳 0.0 作保底
        return 0.0;
    }
}

/*
 * Time Complexity: O(log(min(n, m)))
 * 說明：只在較短陣列上二分搜尋切點，每一步常數時間檢查左右邊界。
 *
 * Space Complexity: O(1)
 * 說明：除輸入陣列外，僅使用固定數個變數保存切點與邊界值。
 */
