
import java.io.*;
import java.util.*;

public class LC39_CombinationSum_PPE {

    static int n, target;
    static int[] a;
    static BufferedWriter out;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        String t = fs.next();
        if (t == null) {
            return;
        }
        n = Integer.parseInt(t);
        target = Integer.parseInt(fs.next());

        a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = fs.nextInt();
        }
        Arrays.sort(a);

        out = new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8"));
        dfs(0, target, new ArrayDeque<>());
        out.flush();
    }

    // 回溯：可重複使用同一數字；同層跳過相同值以避免重複組合
    static void dfs(int start, int remain, Deque<Integer> path) throws IOException {
        if (remain == 0) {
            print(path);
            return;
        }
        for (int i = start; i < n; i++) {
            if (i > start && a[i] == a[i - 1]) {
                continue; // 同層去重

                        }if (a[i] > remain) {
                break;                     // 剪枝

                        }path.addLast(a[i]);
            dfs(i, remain - a[i], path);                  // 允許重複 → 傳 i
            path.removeLast();
        }
    }

    static void print(Deque<Integer> path) throws IOException {
        boolean first = true;
        for (int v : path) {
            if (!first) {
                out.write(' ');
            }
            out.write(Integer.toString(v));
            first = false;
        }
        out.newLine();
    }

    // 簡易讀取器
    static class FastScanner {

        private final InputStream in;
        private final byte[] buf = new byte[1 << 16];
        private int p = 0, m = 0;

        FastScanner(InputStream is) {
            in = is;
        }

        private int read() throws IOException {
            if (p >= m) {
                m = in.read(buf);
                p = 0;
                if (m <= 0) {
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
}

/*
 * Time Complexity: 指數級（受解空間大小主導），排序 O(n log n)。
 * Space Complexity: O(d)（遞迴深度 ≤ 目標長度），輸出不計。
 */
