import java.util.*;

class PlayerNode {
    int score;
    String name;
    PlayerNode left, right;
    int height;
    int size; 

    public PlayerNode(String name, int score) {
        this.name = name;
        this.score = score;
        this.height = 1;
        this.size = 1;
    }

    void updateHeightAndSize() {
        int lh = (left != null) ? left.height : 0;
        int rh = (right != null) ? right.height : 0;
        this.height = Math.max(lh, rh) + 1;

        int ls = (left != null) ? left.size : 0;
        int rs = (right != null) ? right.size : 0;
        this.size = ls + rs + 1;
    }

    int balance() {
        int lh = (left != null) ? left.height : 0;
        int rh = (right != null) ? right.height : 0;
        return lh - rh;
    }
}

class AVLLeaderboard {
    private PlayerNode root;

    // 插入或更新玩家分數
    public void addOrUpdate(String name, int score) {
        root = insert(root, name, score);
    }

    private PlayerNode insert(PlayerNode node, String name, int score) {
        if (node == null) return new PlayerNode(name, score);

        if (score > node.score || (score == node.score && name.compareTo(node.name) < 0)) {
            node.left = insert(node.left, name, score);
        } else if (score < node.score || (score == node.score && name.compareTo(node.name) > 0)) {
            node.right = insert(node.right, name, score);
        } else {
            node.score = score; 
            return node;
        }

        node.updateHeightAndSize();
        return balance(node);
    }

    // 平衡 AVL 樹
    private PlayerNode balance(PlayerNode node) {
        int b = node.balance();
        if (b > 1) {
            if (node.left.balance() < 0) node.left = leftRotate(node.left);
            node = rightRotate(node);
        } else if (b < -1) {
            if (node.right.balance() > 0) node.right = rightRotate(node.right);
            node = leftRotate(node);
        }
        return node;
    }

    private PlayerNode rightRotate(PlayerNode y) {
        PlayerNode x = y.left;
        PlayerNode T2 = x.right;
        x.right = y;
        y.left = T2;
        y.updateHeightAndSize();
        x.updateHeightAndSize();
        return x;
    }

    private PlayerNode leftRotate(PlayerNode x) {
        PlayerNode y = x.right;
        PlayerNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.updateHeightAndSize();
        y.updateHeightAndSize();
        return y;
    }

    // 查詢玩家排名 (1 為最高分)
    public int getRank(String name) {
        return getRank(root, name, 0);
    }

    private int getRank(PlayerNode node, String name, int rankSoFar) {
        if (node == null) return -1;

        if (name.equals(node.name)) {
            int leftSize = (node.left != null) ? node.left.size : 0;
            return rankSoFar + leftSize + 1;
        }

        int cmpLeft = getRank(node.left, name, rankSoFar);
        if (cmpLeft != -1) return cmpLeft;

        int leftSize = (node.left != null) ? node.left.size : 0;
        return getRank(node.right, name, rankSoFar + leftSize + 1);
    }

    // 取得前 K 名玩家
    public List<String> topK(int K) {
        List<String> res = new ArrayList<>();
        traverseTopK(root, res, K);
        return res;
    }

    private void traverseTopK(PlayerNode node, List<String> res, int K) {
        if (node == null || res.size() >= K) return;
        traverseTopK(node.left, res, K);
        if (res.size() < K) res.add(node.name + "(" + node.score + ")");
        traverseTopK(node.right, res, K);
    }
}

public class AVLLeaderboardSystem {
    public static void main(String[] args) {
        AVLLeaderboard leaderboard = new AVLLeaderboard();
        leaderboard.addOrUpdate("Alice", 50);
        leaderboard.addOrUpdate("Bob", 70);
        leaderboard.addOrUpdate("Charlie", 60);
        leaderboard.addOrUpdate("Alice", 80); // 更新分數

        System.out.println("Alice 排名: " + leaderboard.getRank("Alice"));
        System.out.println("前 3 名玩家: " + leaderboard.topK(3));
    }
}