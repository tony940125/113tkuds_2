import java.io.*;
import java.util.*;

public class M08_BSTRangedSum {
    static class Node {
        int val;
        Node left, right;
        Node(int v) { this.val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 讀 n
        String line;
        while ((line = br.readLine()) != null && line.trim().isEmpty()) {}
        if (line == null) return;
        int n = Integer.parseInt(line.trim());

        // 讀 n 個整數（層序，-1 表 null）
        int[] arr = new int[n];
        int filled = 0;
        while (filled < n && (line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && filled < n) {
                arr[filled++] = Integer.parseInt(st.nextToken());
            }
        }

        // 讀 L R
        String lastLine;
        while ((lastLine = br.readLine()) != null && lastLine.trim().isEmpty()) {}
        if (lastLine == null) return;
        StringTokenizer st = new StringTokenizer(lastLine);
        int L = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());

        Node root = buildTree(arr);
        int sum = rangeSum(root, L, R);
        System.out.println("Sum: " + sum);
    }

    // 層序建 BST（與第7題相同，但輸入保證是 BST 的層序表示）
    private static Node buildTree(int[] arr) {
        if (arr.length == 0 || arr[0] == -1) return null;
        Node root = new Node(arr[0]);
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (i < arr.length && !q.isEmpty()) {
            Node cur = q.poll();
            if (i < arr.length && arr[i] != -1) {
                cur.left = new Node(arr[i]);
                q.offer(cur.left);
            }
            i++;
            if (i < arr.length && arr[i] != -1) {
                cur.right = new Node(arr[i]);
                q.offer(cur.right);
            }
            i++;
        }
        return root;
    }

    // 遞迴計算區間和
    private static int rangeSum(Node root, int L, int R) {
        if (root == null) return 0;
        if (root.val < L) return rangeSum(root.right, L, R);
        if (root.val > R) return rangeSum(root.left, L, R);
        return root.val + rangeSum(root.left, L, R) + rangeSum(root.right, L, R);
    }
}