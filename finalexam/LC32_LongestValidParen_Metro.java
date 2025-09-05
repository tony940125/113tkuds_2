
import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class LC32_LongestValidParen_Metro {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        String s = br.readLine();
        if (s == null) {
            s = "";
        }
        System.out.println(longestValidParentheses(s));
    }

    // 核心：索引棧，棧底放 -1 為基準
    static int longestValidParentheses(String s) {
        Deque<Integer> st = new ArrayDeque<>();
        st.push(-1); // 初始基準
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                st.push(i);
            } else { // c == ')'
                st.pop(); // 嘗試配對最近的 '('
                if (st.isEmpty()) {
                    // 沒有可配對的 '('，以當前位置作為新的基準
                    st.push(i);
                } else {
                    // 以最近未配對位置（棧頂）到當前位置的距離更新答案
                    ans = Math.max(ans, i - st.peek());
                }
            }
        }
        return ans;
    }
}

/*
 * Time Complexity: O(n)
 * 說明：每個索引至多進棧/出棧一次，常數時間更新答案。
 *
 * Space Complexity: O(n)
 * 說明：最壞情況（全為 '('）棧中保存所有索引。
 *
 * 備註：亦可用雙向計數法（左右各掃一遍累計 '('、')'）達成 O(1) 空間，但索引棧法較直觀。
 */
