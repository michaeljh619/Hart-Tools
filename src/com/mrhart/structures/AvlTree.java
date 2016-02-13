package com.mrhart.structures;

public class AvlTree {
	 
    private Node<Comparable> root;
 
    private class Node <Comparable>{
        private Comparable key;
        private int balance;
        private Node<Comparable> left, right, parent;
 
        Node(Comparable k, Node<Comparable> p) {
            key = k;
            parent = p;
        }
    }
 
    public boolean insert(Comparable key) {
        if (root == null)
            root = new Node<Comparable>(key, null);
        else {
            Node<Comparable> n = root;
            Node<Comparable> parent;
            while (true) {
                if (n.key == key)
                    return false;
 
                parent = n;
 
                boolean goLeft = n.key.compareTo(key) == 1;
                n = goLeft ? n.left : n.right;
 
                if (n == null) {
                    if (goLeft) {
                        parent.left = new Node<Comparable>(key, parent);
                    } else {
                        parent.right = new Node<Comparable>(key, parent);
                    }
                    rebalance(parent);
                    break;
                }
            }
        }
        return true;
    }
 
    public void delete(Comparable delKey) {
        if (root == null)
            return;
        Node<Comparable> n = root;
        Node<Comparable> parent = root;
        Node<Comparable> delNode = null;
        Node<Comparable> child = root;
 
        while (child != null) {
            parent = n;
            n = child;
            child = (delKey.compareTo(n.key) == 1) ? n.right : n.left;
            if (delKey == n.key)
                delNode = n;
        }
 
        if (delNode != null) {
            delNode.key = n.key;
 
            child = n.left != null ? n.left : n.right;
 
            if (root.key == delKey) {
                root = child;
            } else {
                if (parent.left == n) {
                    parent.left = child;
                } else {
                    parent.right = child;
                }
                rebalance(parent);
            }
        }
    }
 
    private void rebalance(Node<Comparable> n) {
        setBalance(n);
 
        if (n.balance == -2) {
            if (height(n.left.left) >= height(n.left.right))
                n = rotateRight(n);
            else
                n = rotateLeftThenRight(n);
 
        } else if (n.balance == 2) {
            if (height(n.right.right) >= height(n.right.left))
                n = rotateLeft(n);
            else
                n = rotateRightThenLeft(n);
        }
 
        if (n.parent != null) {
            rebalance(n.parent);
        } else {
            root = n;
        }
    }
 
    private Node<Comparable> rotateLeft(Node<Comparable> a) {
 
        Node<Comparable> b = a.right;
        b.parent = a.parent;
 
        a.right = b.left;
 
        if (a.right != null)
            a.right.parent = a;
 
        b.left = a;
        a.parent = b;
 
        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }
 
        setBalance(a, b);
 
        return b;
    }
 
    private Node<Comparable> rotateRight(Node<Comparable> a) {
 
        Node<Comparable> b = a.left;
        b.parent = a.parent;
 
        a.left = b.right;
 
        if (a.left != null)
            a.left.parent = a;
 
        b.right = a;
        a.parent = b;
 
        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }
 
        setBalance(a, b);
 
        return b;
    }
 
    private Node<Comparable> rotateLeftThenRight(Node<Comparable> n) {
        n.left = rotateLeft(n.left);
        return rotateRight(n);
    }
 
    private Node<Comparable> rotateRightThenLeft(Node<Comparable> n) {
        n.right = rotateRight(n.right);
        return rotateLeft(n);
    }
 
    private int height(Node<Comparable> n) {
        if (n == null)
            return -1;
        return 1 + Math.max(height(n.left), height(n.right));
    }
 
    private void setBalance(Node<Comparable>... nodes) {
        for (Node<Comparable> n : nodes)
            n.balance = height(n.right) - height(n.left);
    }
 
    public void printBalance() {
        printBalance(root);
    }
 
    private void printBalance(Node<Comparable> n) {
        if (n != null) {
            printBalance(n.left);
            System.out.printf("%s ", n.balance);
            printBalance(n.right);
        }
    }
 
    public static void main(String[] args) {
        AvlTree tree = new AvlTree();
 
        System.out.println("Inserting values 1 to 10");
        for (int i = 1; i < 10; i++)
            tree.insert(i);
 
        System.out.print("Printing balance: ");
        tree.printBalance();
    }
}