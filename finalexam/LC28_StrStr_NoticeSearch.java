import java.io.*;

public class LC28_StrStr_NoticeSearch {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        String haystack = br.readLine();
        String needle   = br.readLine();

        if (haystack == null) haystack = "";
        if (needle == null) needle = "";

        System.out.println(strStrKMP(haystack, needle));
    }

    // KMP 搜尋：回傳 needle 首次出現於 haystack 的起始索引，否則 -1
    static int strStrKMP(String s, String p) {
        int n = s.length(), m = p.length();
        if (m == 0) return 0;
        if (m > n) return -1;

        int[] lps = buildLPS(p);
        int i = 0, j = 0; // i for s, j for p
        while (i < n) {
            if (s.charAt(i) == p.charAt(j)) {
                i++; j++;
                if (j == m) return i - m; // 命中
            } else {
                if (j > 0) j = lps[j - 1];
                else i++;
            }
        }
        return -1;
    }

    // 建立 KMP 失敗函數（最長相等前後綴長度）
    static int[] buildLPS(String p) {
        int m = p.length();
        int[] lps = new int[m];
        int len = 0; // 當前 LPS 長度
        for (int i = 1; i < m; ) {
            if (p.charAt(i) == p.charAt(len)) {
                lps[i++] = ++len;
            } else {
                if (len > 0) len = lps[len - 1];
                else lps[i++] = 0;
            }
        }
        return lps;
    }
}

/*
 * Time Complexity: O(n + m)
 * 說明：建 LPS O(m)，比對 O(n)，總計線性。
 *
 * Space Complexity: O(m)
 * 說明：僅使用長度為 m 的 LPS 陣列與常數額外變數。
 */
