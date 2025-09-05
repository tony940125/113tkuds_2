
import java.io.*;

public class LC34_SearchRange_DelaySpan {

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
            
                }}
            return buf[p++];
        }

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = read();
                if (c == -1) {
                    return null;
            
                }} while (c <= ' ');
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
            System.out.println("-1 -1");
            return;
        } // 無輸入
        int n = Integer.parseInt(t);
        int target = fs.nextInt();

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = fs.nextInt();
        }

        if (n == 0) {
            System.out.println("-1 -1");
            return;
        }

        int left = lowerBound(a, target);
        int right = upperBound(a, target) - 1;

        if (left <= right && left >= 0 && right < n && a[left] == target && a[right] == target) {
            System.out.println(left + " " + right);
        } else {
            System.out.println("-1 -1");
        }
    }

    // 第一個 >= target 的索引；若全都小於 target，回 n
    static int lowerBound(int[] a, int target) {
        int l = 0, r = a.length; // [l, r)
        while (l < r) {
            int m = l + ((r - l) >>> 1);
            if (a[m] >= target) {
                r = m; 
            }else {
                l = m + 1;
            }
        }
        return l;
    }

    // 第一個 > target 的索引；若全都 <= target，回 n
    static int upperBound(int[] a, int target) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = l + ((r - l) >>> 1);
            if (a[m] > target) {
                r = m; 
            }else {
                l = m + 1;
            }
        }
        return l;
    }
}

/*
 * Time Complexity: O(log n)
 * 說明：兩次二分搜尋，各為 O(log n)。
 *
 * Space Complexity: O(1)
 * 說明：僅使用常數額外變數。
 */
