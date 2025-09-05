
import java.io.*;
import java.util.Arrays;

/**
 * LC03_NoRepeat_TaipeiMetroTap 輸入：一行字串 s（可為空） 輸出：最長不含重複字元之子字串長度
 *
 * 範例： 輸入: abcabcbb -> 輸出: 3 輸入: bbbbb -> 輸出: 1
 *
 * /*
 *  * Time Complexity: O(n) * 說明：右指針每個位置最多被處理一次；左指針只前進不回退； * 每個字元的查詢/更新為
 * O(1)，總計線性。 * * Space Complexity: O(k) * 說明：使用大小為 65536 的 lastIndex 陣列（k
 * 為字元集大小，對 Unicode char 近似常數）。
 *
 */
public class LC03_NoRepeat_TaipeiMetroTap {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        String line = br.readLine();
        String s = (line == null) ? "" : line;

        // 以 65536 支援所有 Java char（避免非 ASCII 輸入造成索引越界）
        int[] lastIndex = new int[65536];
        Arrays.fill(lastIndex, -1);

        int left = 0;
        int ans = 0;

        for (int r = 0; r < s.length(); r++) {
            char ch = s.charAt(r);
            int prev = lastIndex[ch];

            if (prev >= left) {
                // ch 在視窗中出現過，收縮左界到其上一位置 + 1
                left = prev + 1;
            }

            lastIndex[ch] = r;
            ans = Math.max(ans, r - left + 1);
        }

        System.out.println(ans);
    }
}
