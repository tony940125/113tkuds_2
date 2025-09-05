
import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class LC20_ValidParentheses_AlertFormat {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        String s = br.readLine();
        if (s == null) {
            s = "";
        }

        System.out.println(isValid(s) ? "true" : "false");
    }

    // 驗證括號是否完全正確巢狀
    static boolean isValid(String s) {
        int n = s.length();
        if (n == 0) {
            return true;       // 邊界：空字串

                }if ((n & 1) == 1) {
            return false; // 長度為奇數不可能配對成功
        }
        Deque<Character> st = new ArrayDeque<>(Math.min(n, 1 << 16));

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                case '[':
                case '{':
                    st.push(c);
                    break;
                case ')': {
                    if (st.isEmpty() || st.pop() != '(') {
                        return false;
                    }
                    break;
                }
                case ']': {
                    if (st.isEmpty() || st.pop() != '[') {
                        return false;
                    }
                    break;
                }
                case '}': {
                    if (st.isEmpty() || st.pop() != '{') {
                        return false;
                    }
                    break;
                }
                default:
                    // 題目保證只有 ()[]{}，此處可視為非法輸入
                    return false;
            }
        }
        return st.isEmpty();
    }
}

/*
 * Time Complexity: O(n)
 * 說明：每個字元僅進出棧一次；配對檢查為 O(1)。
 *
 * Space Complexity: O(n)
 * 說明：最壞情況（全為開括號）棧大小為 n。
 */
