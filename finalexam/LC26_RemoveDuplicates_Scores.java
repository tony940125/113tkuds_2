
import java.io.*;

public class LC26_RemoveDuplicates_Scores {

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
            return; // 無輸入

                }int n = Integer.parseInt(tok);

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = fs.nextInt();
        }

        int newLen = removeDuplicatesInPlace(a, n);

        // 輸出新長度
        System.out.println(newLen);

        // 輸出前段結果（若 newLen=0，依題意可不輸出第二行）
        if (newLen > 0) {
            StringBuilder out = new StringBuilder();
            for (int i = 0; i < newLen; i++) {
                if (i > 0) {
                    out.append(' ');
                }
                out.append(a[i]);
            }
            System.out.println(out.toString());
        }
    }

    // 就地壓縮：保留唯一值，回傳新長度
    static int removeDuplicatesInPlace(int[] a, int n) {
        if (n == 0) {
            return 0;
        }
        int write = 1; // 下一個可寫位置
        for (int i = 1; i < n; i++) {
            if (a[i] != a[write - 1]) {
                a[write++] = a[i];
            }
        }
        return write;
    }
}

/*
 * Time Complexity: O(n)
 * 說明：單次線性掃描，每個元素比較一次並在必要時寫入一次。
 *
 * Space Complexity: O(1)
 * 說明：就地覆寫原陣列前段，僅使用常數額外變數。
 */
