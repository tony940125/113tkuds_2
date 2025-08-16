public class AVLRotationTest {
    public static void main(String[] args) {
        
        AVLNode root = new AVLNode(30);
        root.left = new AVLNode(20);
        root.left.left = new AVLNode(10);

        root.updateHeight();
        root.left.updateHeight();

        System.out.println("Before Right Rotation: root = " + root.data);
        root = AVLRotations.rightRotate(root);
        System.out.println("After Right Rotation: root = " + root.data);

        AVLNode root2 = new AVLNode(10);
        root2.right = new AVLNode(20);
        root2.right.right = new AVLNode(30);

        root2.updateHeight();
        root2.right.updateHeight();

        System.out.println("Before Left Rotation: root = " + root2.data);
        root2 = AVLRotations.leftRotate(root2);
        System.out.println("After Left Rotation: root = " + root2.data);
    }
}