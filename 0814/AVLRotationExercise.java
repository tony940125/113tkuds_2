public class AVLRotationExercise {
    static class Node {
        int key, height = 1;
        Node left, right;
        Node(int k) { key = k; }
    }

    int height(Node n) { return n == null ? 0 : n.height; }

    Node leftRotate(Node x) {
        Node y = x.right, T2 = y.left;
        y.left = x; x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    Node rightRotate(Node y) {
        Node x = y.left, T2 = x.right;
        x.right = y; y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    Node leftRightRotate(Node z) {
        z.left = leftRotate(z.left);
        return rightRotate(z);
    }

    Node rightLeftRotate(Node z) {
        z.right = rightRotate(z.right);
        return leftRotate(z);
    }

    public static void main(String[] args) {
        AVLRotationExercise test = new AVLRotationExercise();

        Node root = new Node(30);
        root.left = new Node(20);
        root.left.left = new Node(10);
        root.height = 3;
        System.out.println("右旋測試");
        root = test.rightRotate(root);
        System.out.println("新根: " + root.key);

        root = new Node(10);
        root.right = new Node(20);
        root.right.right = new Node(30);
        root.height = 3;
        System.out.println("左旋測試");
        root = test.leftRotate(root);
        System.out.println("新根: " + root.key);
    }
}