import java.util.*;

public class AVLRangeQueryExercise {
    static class Node {
        int key, height = 1;
        Node left, right;
        Node(int k) { key = k; }
    }

    Node root;
    int height(Node n) { return n == null ? 0 : n.height; }

    Node insert(Node node, int key) {
        if (node == null) return new Node(key);
        if (key < node.key) node.left = insert(node.left, key);
        else if (key > node.key) node.right = insert(node.right, key);
        else return node;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    List<Integer> rangeQuery(Node node, int min, int max) {
        List<Integer> res = new ArrayList<>();
        rangeQueryHelper(node, min, max, res);
        return res;
    }

    void rangeQueryHelper(Node node, int min, int max, List<Integer> res) {
        if (node == null) return;
        if (node.key > min) rangeQueryHelper(node.left, min, max, res);
        if (node.key >= min && node.key <= max) res.add(node.key);
        if (node.key < max) rangeQueryHelper(node.right, min, max, res);
    }

    Node balance(Node node) {
        int bf = height(node.left) - height(node.right);
        if (bf > 1) {
            if (height(node.left.left) >= height(node.left.right))
                return rightRotate(node);
            else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        if (bf < -1) {
            if (height(node.right.right) >= height(node.right.left))
                return leftRotate(node);
            else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }

    Node rightRotate(Node y) {
        Node x = y.left, T2 = x.right;
        x.right = y; y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right, T2 = y.left;
        y.left = x; x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public static void main(String[] args) {
        AVLRangeQueryExercise avl = new AVLRangeQueryExercise();
        int[] keys = {20,8,22,4,12,10,14};
        for (int k : keys) avl.root = avl.insert(avl.root, k);
        System.out.println("範圍查詢 [10, 20]: " + avl.rangeQuery(avl.root, 10, 20));
    }
}