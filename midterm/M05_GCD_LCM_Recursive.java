/*
 * Time Complexity: O(log(min(a,b)))
 * 說明：使用遞迴歐幾里得演算法計算 GCD，每次遞迴令參數縮小為餘數，
 *       最多 O(log(min(a,b))) 層遞迴。LCM 在得到 GCD 後，以 a/GCD * b 求得，
 *       避免溢位：先除後乘。整體時間複雜度為 O(log(min(a,b)))。
 */

import java.io.*;
import java.util.*;

public class M05_GCD_LCM_Recursive {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null && line.trim().isEmpty()) {}
        if (line == null) return;
        StringTokenizer st = new StringTokenizer(line);
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        long g = gcd(a, b);
        long l = (a / g) * b;   // 避免乘法溢位

        System.out.println("GCD: " + g);
        System.out.println("LCM: " + l);
    }

    // 遞迴版歐幾里得算法
    private static long gcd(long x, long y) {
        if (y == 0) return x;
        return gcd(y, x % y);
    }
}
