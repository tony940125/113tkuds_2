
import java.io.*;

public class LC33_SearchRotated_RentHot {

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
            return;                 // 無輸入

                }int n = Integer.parseInt(t);
        int target = fs.nextInt();

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = fs.nextInt();
        }

        System.out.println(searchRotated(a, target));
    }

    // 在被旋轉的升序陣列中找 target 的索引；不存在回 -1
    static int searchRotated(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + ((r - l) >>> 1);
            if (nums[mid] == target) {
                return mid;
            }

            // 判斷哪一半有序
            if (nums[l] <= nums[mid]) { // 左半有序
                if (nums[l] <= target && target < nums[mid]) {
                    r = mid - 1;        // 目標在左半
                } else {
                    l = mid + 1;        // 目標在右半
                }
            } else { // 右半有序
                if (nums[mid] < target && target <= nums[r]) {
                    l = mid + 1;        // 目標在右半
                } else {
                    r = mid - 1;        // 目標在左半
                }
            }
        }
        return -1;
    }
}

/*
 * Time Complexity: O(log n)
 * 說明：每步縮小一半搜尋區間。
 *
 * Space Complexity: O(1)
 * 說明：僅使用常數額外變數。
 */
