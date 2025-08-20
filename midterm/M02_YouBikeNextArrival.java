import java.io.*;

public class M02_YouBikeNextArrival {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 讀 n
        String line;
        while ((line = br.readLine()) != null && line.trim().isEmpty()) {}
        if (line == null) return;
        int n = Integer.parseInt(line.trim());

        // 讀 n 行已排序時間（HH:mm）
        int[] t = new int[n];
        int filled = 0;
        while (filled < n && (line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            t[filled++] = parseHHmm(line);
        }
        if (filled != n) return;

        // 讀 query
        String qLine;
        while ((qLine = br.readLine()) != null && qLine.trim().isEmpty()) {}
        if (qLine == null) return;
        int q = parseHHmm(qLine.trim());

        // 二分搜尋：找第一個 > q 的索引
        int idx = upperBound(t, q); // 嚴格大於
        if (idx == n) {
            System.out.println("No bike");
        } else {
            System.out.println(formatHHmm(t[idx]));
        }
    }

    // upper_bound：回傳第一個 > key 的索引（若都不大於，回傳 n）
    private static int upperBound(int[] a, int key) {
        int l = 0, r = a.length; // [l, r)
        while (l < r) {
            int m = l + (r - l) / 2;
            if (a[m] <= key) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    // "HH:mm" → 分鐘（自 00:00 起）
    private static int parseHHmm(String s) {
        int h = Integer.parseInt(s.substring(0, 2));
        int m = Integer.parseInt(s.substring(3, 5));
        return h * 60 + m;
    }

    // 分鐘 → "HH:mm"
    private static String formatHHmm(int minutes) {
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format("%02d:%02d", h, m);
    }
}