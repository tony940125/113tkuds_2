/*
 * Time Complexity: O(n)
 * 說明：輸入建立二元樹需要走訪 n 個節點值；層序 BFS 遍歷所有節點一次，
 *       每層只輸出第一個節點。整體為 O(n)，空間為 O(n) 以儲存佇列。
 */

import java.io.*;
import java.util.*;

public class M07_BinaryTreeLeftView {
    // 樹節點定義
    static class Node {
        int val;
        Node left, right;
        Node(int v) { this.val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while ((line = br.readLine()) != null && line.trim().isEmpty()) {}
        if (line == null) return;
        int n = Integer.parseInt(line.trim());

        // 讀 n 個整數（可能跨行）
        int[] arr = new int[n];
        int filled = 0;
        while (filled < n && (line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && filled < n) {
                arr[filled++] = Integer.parseInt(st.nextToken());
            }
        }

        Node root = buildTree(arr);
        List<Integer> leftView = getLeftView(root);

        // 輸出
        StringBuilder sb = new StringBuilder("LeftView:");
        for (int v : leftView) {
            sb.append(' ').append(v);
        }
        System.out.println(sb.toString());
    }

    // 層序陣列建樹
    private static Node buildTree(int[] arr) {
        if (arr.length == 0 || arr[0] == -1) return null;
        Node root = new Node(arr[0]);
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (i < arr.length && !q.isEmpty()) {
            Node cur = q.poll();
            // 左子
            if (i < arr.length && arr[i] != -1) {
                cur.left = new Node(arr[i]);
                q.offer(cur.left);
            }
            i++;
            // 右子
            if (i < arr.length && arr[i] != -1) {
                cur.right = new Node(arr[i]);
                q.offer(cur.right);
            }
            i++;
        }
        return root;
    }

    // BFS 左視圖：每層第一個節點
    private static List<Integer> getLeftView(Node root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                Node cur = q.poll();
                if (i == 0) res.add(cur.val); // 每層第一個就是左視圖
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
        }
        return res;
    }
}