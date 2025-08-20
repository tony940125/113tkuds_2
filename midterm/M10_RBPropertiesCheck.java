import java.io.*;
import java.util.*;

public class M10_RBPropertiesCheck {

    static final int NIL_BLACK_HEIGHT = 1;   // NIL 葉為黑，高度貢獻 1
    static final int BH_MISMATCH = Integer.MIN_VALUE; // 黑高度不一致之哨兵

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 讀 n
        String line;
        while ((line = br.readLine()) != null && line.trim().isEmpty()) {}
        if (line == null) return;
        int n = Integer.parseInt(line.trim());

        int[] val = new int[n];
        char[] col = new char[n];

        // 讀取 n 組 (value, color)；可能跨行，color 為 'B' 或 'R'
        int filled = 0;
        StringTokenizer st = null;
        while (filled < n) {
            line = br.readLine();
            if (line == null) break;
            if (line.trim().isEmpty()) continue;
            st = new StringTokenizer(line);
            while (st.hasMoreTokens() && filled < n) {
                // value
                int v = Integer.parseInt(st.nextToken());
                // color 可能與 value 在同一行或下一個 token
                String cTok;
                // 若沒有下一個 token，需要再讀一行
                if (!st.hasMoreTokens()) {
                    String extra;
                    do {
                        extra = br.readLine();
                        if (extra == null) break;
                    } while (extra.trim().isEmpty());
                    if (extra == null) { cTok = "B"; } // 防呆：缺色時當作黑
                    else {
                        StringTokenizer st2 = new StringTokenizer(extra);
                        cTok = st2.hasMoreTokens() ? st2.nextToken() : "B";
                        // 把剩餘 token 放回 st（簡化：忽略；輸入按題意不會殘缺）
                    }
                } else {
                    cTok = st.nextToken();
                }
                char c = Character.toUpperCase(cTok.charAt(0));

                // -1 代表 null，顏色以黑處理
                if (v == -1) c = 'B';

                val[filled] = v;
                col[filled] = c;
                filled++;
            }
        }

        // 情況：空樹或根為 null（-1） → 視為合法紅黑樹
        if (n == 0 || val[0] == -1) {
            System.out.println("RB Valid");
            return;
        }

        // 1) 根節點必須為黑
        if (col[0] != 'B') {
            System.out.println("RootNotBlack");
            return;
        }

        // 2) 不得有相鄰紅節點（父紅且子紅）
        // 以層序索引檢查，回報第一個違規的子節點索引 i
        for (int i = 1; i < n; i++) {
            if (val[i] == -1) continue; // NIL 視為黑
            int p = (i - 1) / 2;
            // 父節點若超出或為 NIL，視為黑，不構成違規
            if (p >= 0 && p < n && val[p] != -1) {
                if (col[i] == 'R' && col[p] == 'R') {
                    System.out.println("RedRedViolation at index " + i);
                    return;
                }
            }
        }

        // 3) 黑高度一致：自任一節點到其所有 NIL 葉之黑節點數相同
        // 使用後序遞迴回傳黑高度，不一致則回傳 BH_MISMATCH
        int bh = blackHeight(0, val, col, n);
        if (bh == BH_MISMATCH) {
            System.out.println("BlackHeightMismatch");
            return;
        }

        System.out.println("RB Valid");
    }

    // 回傳以 idx 為根之黑高度；若任一路徑黑高度不一致，回傳 BH_MISMATCH
    private static int blackHeight(int idx, int[] val, char[] col, int n) {
        // 超出範圍 → 視為 NIL 葉（黑）
        if (idx >= n) return NIL_BLACK_HEIGHT;

        // 目前節點為 NIL（-1）→ 視為 NIL 葉（黑）
        if (val[idx] == -1) return NIL_BLACK_HEIGHT;

        int left = 2 * idx + 1;
        int right = 2 * idx + 2;

        int lb = blackHeight(left, val, col, n);
        if (lb == BH_MISMATCH) return BH_MISMATCH;

        int rb = blackHeight(right, val, col, n);
        if (rb == BH_MISMATCH) return BH_MISMATCH;

        if (lb != rb) return BH_MISMATCH;

        // 當前節點為黑則黑高度 +1；紅則不加
        return lb + (col[idx] == 'B' ? 1 : 0);
    }
}