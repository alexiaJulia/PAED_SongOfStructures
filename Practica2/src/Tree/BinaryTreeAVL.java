package Tree;

public class BinaryTreeAVL {
    public Node root;

    public BinaryTreeAVL (Node[] nodes) {
        root = nodes[0];
        for (int i = 1; i < nodes.length; i++) {
            insertNodeAVL(nodes[i]);
        }
    }

    private void insertNodeAVL(Node newNode) {
        root = insertNode(root, newNode);
    }

    private Node insertNode(Node currentNode, Node newNode) {
        if (currentNode == null) {
            return newNode;
        }
        if (newNode.power < currentNode.power) {
            currentNode.left = insertNode(currentNode.left, newNode);
        }
        if (newNode.power > currentNode.power) {
            currentNode.right = insertNode(currentNode.right, newNode);
        }

        updateHeight(currentNode);

        return balanceNode(currentNode);
    }

    private Node balanceNode(Node node) {
        int factorBalance = getBalanceFactor(node);

        if (factorBalance < -1) {  // desbalancejat a la esquerra
            if (getBalanceFactor(node.left) <= 0) {
                return rightRotation(node);
            } else {
                node.left = leftRotation(node.left);
                return rightRotation(node);
            }
        }

        if (factorBalance > 1) {  // desbalancejat a la dreta
            if (getBalanceFactor(node.right) >= 0) {
                return leftRotation(node);
            } else {
                node.right = rightRotation(node.right);
                return leftRotation(node);
            }
        }
        return node;
    }

    private Node rightRotation(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Node leftRotation(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private void updateHeight(Node node) {
        if (node != null) {
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        }
    }

    private int getBalanceFactor(Node node) {
        return getHeight(node.right) - getHeight(node.left);
    }

    public void visualRepresentation(BinaryTreeAVL tree) {
        inordre(root, "", false, 1);
    }


    private void inordre(Node node, String prefix, boolean isLast, int height) {
        if (node.left != null) {
            inordre(node.left, prefix + (isLast ? "     " : "|    "), true, height + 1);
        }
        //node.printNode(isLast, height);
        if (node.right != null) {
            inordre(node.right, prefix + (isLast ? "     " : "|    "), false, height + 1);
        }
    }
}