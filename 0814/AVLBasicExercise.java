public class AVLBasicExercise {
    static class Node {
        int key, height;
        Node left, right;
        Node(int key) { this.key = key; height = 1; }
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

    boolean search(Node node, int key) {
        if (node == null) return false;
        if (key < node.key) return search(node.left, key);
        if (key > node.key) return search(node.right, key);
        return true;
    }

    boolean isValidAVL(Node node) {
        if (node == null) return true;
        int bf = height(node.left) - height(node.right);
        if (bf < -1 || bf > 1) return false;
        return isValidAVL(node.left) && isValidAVL(node.right);
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
        AVLBasicExercise avl = new AVLBasicExercise();
        int[] keys = {10, 20, 30, 40, 50, 25};
        for (int k : keys) avl.root = avl.insert(avl.root, k);

        System.out.println("搜尋 25: " + avl.search(avl.root, 25));
        System.out.println("高度: " + avl.height(avl.root));
        System.out.println("是否有效 AVL: " + avl.isValidAVL(avl.root));
    }
}