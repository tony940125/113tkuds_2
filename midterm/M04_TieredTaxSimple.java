/*
 * Time Complexity: O(n)
 * 說明：每筆收入以固定 4 個稅率區間逐段累加，單筆計算為 O(1)；
 *       共處理 n 筆輸入，總時間為 O(n)。額外空間為 O(1)。
 */

import java.io.*;
import java.util.*;

public class M04_TieredTaxSimple {
    // 區間上界（含），最後一段用 Long.MAX_VALUE 表示「以上」
    static final long[] UPPER = {120_000L, 500_000L, 1_000_000L, Long.MAX_VALUE};
    // 各段稅率（對應 UPPER）
    static final double[] RATE = {0.05, 0.12, 0.20, 0.30};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 讀 n
        String line;
        while ((line = br.readLine()) != null && line.trim().isEmpty()) {}
        if (line == null) return;
        int n = Integer.parseInt(line.trim());

        long[] taxes = new long[n];
        long sum = 0;

        for (int i = 0; i < n; i++) {
            String s;
            while ((s = br.readLine()) != null && s.trim().isEmpty()) {}
            if (s == null) break;
            long income = Long.parseLong(s.trim());

            long tax = calcTax(income);
            taxes[i] = tax;
            sum += tax;
        }

        // 輸出每筆稅額
        StringBuilder out = new StringBuilder();
        for (long t : taxes) {
            out.append("Tax: ").append(t).append('\n');
        }

        // 平均稅額（四捨五入到整數）
        long avg = Math.round((double) sum / n);
        out.append("Average: ").append(avg);
        System.out.print(out.toString());
    }

    // 依區間逐段累加之邊際稅：0–120000(含)5%，120001–500000 12%，500001–1000000 20%，1000001 以上 30%
    private static long calcTax(long x) {
        long prev = 0;
        double total = 0.0;

        for (int i = 0; i < UPPER.length; i++) {
            long hi = UPPER[i];
            if (x > prev) {
                long taxable = Math.min(x, hi) - prev;
                total += taxable * RATE[i];
                if (x <= hi) break;
                prev = hi;
            } else break;
        }
        // 四捨五入到整數金額
        return Math.round(total);
    }
}