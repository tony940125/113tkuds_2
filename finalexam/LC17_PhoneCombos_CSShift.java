
import java.io.*;

public class LC17_PhoneCombos_CSShift {

    private static final String[] MAP = {
        "abc", // 2
        "def", // 3
        "ghi", // 4
        "jkl", // 5
        "mno", // 6
        "pqrs", // 7
        "tuv", // 8
        "wxyz" // 9
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        String digits = br.readLine();
        if (digits == null) {
            return;
        }
        digits = digits.trim();
        if (digits.isEmpty()) {
            return; // 明確規格：空字串→無輸出
        }
        // 確保僅含 2-9；若有違規字元，這裡選擇直接不輸出（也可改為丟出例外或印錯誤）
        for (int i = 0; i < digits.length(); i++) {
            char c = digits.charAt(i);
            if (c < '2' || c > '9') {
                return;
            }
        }

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8"));
        StringBuilder sb = new StringBuilder(digits.length());
        dfs(digits, 0, sb, out);
        out.flush();
    }

    private static void dfs(String digits, int idx, StringBuilder sb, BufferedWriter out) throws IOException {
        if (idx == digits.length()) {
            out.write(sb.toString());
            out.newLine();
            return;
        }
        String letters = MAP[digits.charAt(idx) - '2'];
        for (int i = 0; i < letters.length(); i++) {
            sb.append(letters.charAt(i));
            dfs(digits, idx + 1, sb, out);
            sb.setLength(sb.length() - 1);
        }
    }
}

/*
 * Time Complexity: O(Π_k choices_k)
 * 說明：每位數展開其對應字母數（3 或 4），總組合數為乘積。
 *
 * Space Complexity: O(d)
 * 說明：遞迴深度為 digits 長度 d；輸出流邊產生邊寫入，無需保存全部結果。
 */
