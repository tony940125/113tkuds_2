
public class AVLDeleteExercise {
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

    Node delete(Node root, int key) {
        if (root == null) return root;
        if (key < root.key) root.left = delete(root.left, key);
        else if (key > root.key) root.right = delete(root.right, key);
        else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = (root.left != null) ? root.left : root.right;
                if (temp == null) root = null;
                else root = temp;
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }
        if (root == null) return root;
        root.height = 1 + Math.max(height(root.left), height(root.right));
        return balance(root);
    }

    Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) current = current.left;
        return current;
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
        AVLDeleteExercise avl = new AVLDeleteExercise();
        int[] keys = {9,5,10,0,6,11,-1,1,2};
        for (int k : keys) avl.root = avl.insert(avl.root, k);

        avl.root = avl.delete(avl.root, 10);
        System.out.println("刪除 10 完成");
    }
}