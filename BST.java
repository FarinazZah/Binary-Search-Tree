import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Farinaz Zahiri
 * @version 1.0
 * @userid fzahiri6
 * @GTID 903687032
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        for (T element : data) {
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        root = helperAdd(root, data);
    }

    /**
     * helps add data to the tree.
     * @param currentNode starts off as being root node
     * @param data we want to add to the tree
     * @return node we are going through
     */
    private BSTNode<T> helperAdd(BSTNode<T> currentNode, T data) {
        // Root is empty
        if (currentNode == null) {
            size++;
            return new BSTNode<>(data);
        }
        // Traverse Left
        if (currentNode.getData().compareTo(data) > 0) {
            currentNode.setLeft(helperAdd(currentNode.getLeft(), data));

        }
        // Traverse Right
        if (currentNode.getData().compareTo(data) < 0) {
            currentNode.setRight(helperAdd(currentNode.getRight(), data));
        }
        return currentNode;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = helperRemove(data, root, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException("The data is not in the tree.");
        }
        return dummy.getData();
    }

    /**
     * helps remove a node.
     * @param data we want to remove.
     * @param currentNode we are checking
     * @param dummy temporary variable
     * @return node
     */
    private BSTNode<T> helperRemove(T data, BSTNode<T> currentNode,
                                    BSTNode<T> dummy) {
        // If the data is not in the tree
        if (currentNode == null) {
            return null;
        }
        if (data.compareTo(currentNode.getData()) < 0) {
            currentNode.setLeft(helperRemove(data, currentNode.getLeft(),
                    dummy));
        } else if (data.compareTo(currentNode.getData()) > 0) {
            currentNode.setRight(helperRemove(data, currentNode.getRight(),
                    dummy));
        } else {
            dummy.setData(currentNode.getData());
            size--;
            if (currentNode.getLeft() == null
                    && currentNode.getRight() == null) {
                return null;
            } else if (currentNode.getRight() != null
                    && currentNode.getLeft() == null) {
                return currentNode.getRight();
            } else if (currentNode.getLeft() != null
                    && currentNode.getRight() == null) {
                return currentNode.getLeft();
            } else {
                BSTNode<T> dummy2 = new BSTNode<T>(null);
                currentNode.setRight(removeSuccessor(currentNode.getRight(),
                        dummy2));
                currentNode.setData(dummy2.getData());
            }

        }
        return currentNode;
    }

    /**
     * allows us to get the successor of a root.
     * @param currentNode we are in.
     * @param dummy temporary value.
     * @return a node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> currentNode,
                                       BSTNode<T> dummy) {
        if (currentNode.getLeft() == null) {
            dummy.setData(currentNode.getData());
            return currentNode.getRight();
        } else {
            currentNode.setLeft(removeSuccessor(currentNode.getLeft(),
                    dummy));
        }
        return currentNode;
    }

    /**
     * finds a specific node.
     * @param data we are looking for
     * @param currentNode we are checking
     * @return data that we found
     */
    private T hunt(T data, BSTNode<T> currentNode) {
        if (currentNode == null) {
            return null;
        }
        T found = null;
        if (data.compareTo(currentNode.getData()) < 0) {
            found = hunt(data, currentNode.getLeft());
        } else if (data.compareTo(currentNode.getData()) > 0) {
            found = hunt(data, currentNode.getRight());
        } else {
            found = currentNode.getData();
        }
        return found;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        T answer = hunt(data, root);
        if (answer == null) {
            throw new NoSuchElementException("Data is not in the tree.");
        }
        return answer;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        return hunt(data, root) != null;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> dataList = new ArrayList<>();
        preorderHelper(dataList, root);
        return dataList;
    }

    /**
     * recursive method to permit preorder traversal
     * @param dataList of data
     * @param currentNode we are traversing
     */
    private void preorderHelper(List<T> dataList, BSTNode<T> currentNode) {
        if (currentNode == null) {
            return;
        }
        dataList.add(currentNode.getData());
        preorderHelper(dataList, currentNode.getLeft());
        preorderHelper(dataList, currentNode.getRight());
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> dataList = new ArrayList<>();
        inorderHelper(dataList, root);
        return dataList;

    }

    /**
     * recursive helper method for inorder traversal
     * @param dataList we are collecting
     * @param currentNode we are traversing through
     */
    private void inorderHelper(List<T> dataList, BSTNode<T> currentNode) {
        if (currentNode == null) {
            return;
        }
        inorderHelper(dataList, currentNode.getLeft());
        dataList.add(currentNode.getData());
        inorderHelper(dataList, currentNode.getRight());
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> dataList = new ArrayList<>();
        postorderHelper(dataList, root);
        return dataList;

    }

    /**
     * recursive method for post order traversal.
     * @param dataList we are collecting
     * @param currentNode we are traversing through
     */
    private void postorderHelper(List<T> dataList, BSTNode<T> currentNode) {
        if (currentNode == null) {
            return;
        }
        postorderHelper(dataList, currentNode.getLeft());
        postorderHelper(dataList, currentNode.getRight());
        dataList.add(currentNode.getData());
    }


    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        if (root == null) {
            return new ArrayList<>();
        }
        ArrayList<T> dataList = new ArrayList<>();
        // Create a queue
        Queue<BSTNode<T>> nodeQueue = new LinkedList<BSTNode<T>>();
        // Add root to queue
        nodeQueue.add(root);
        // while q is not empty
        while (!nodeQueue.isEmpty()) {
            // node curr = q.dequeue
            BSTNode<T> currentNode = nodeQueue.poll();
            // populate list
            dataList.add(currentNode.getData());
            // if curr is not null
            if (currentNode.getLeft() != null) {
                // q.enqueue(curr.left)
                nodeQueue.add(currentNode.getLeft());
            }
            if (currentNode.getRight() != null) {
                // q.enqueue(curr.right)
                nodeQueue.add(currentNode.getRight());
            }

        }
        return dataList;

    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return nodeHeight(root);

    }

    /**
     * calculate node height.
     * @param currentNode we want to get height
     * @return node height
     */
    private int nodeHeight(BSTNode<T> currentNode) {
        if (currentNode == null) {
            return -1;
        }
        int height = Math.max(nodeHeight(currentNode.getLeft()),
                nodeHeight(currentNode.getRight())) + 1;
        return height;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * This must be done recursively.
     * 
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. 
     *
     * Please note that there is no relationship between the data parameters 
     * in that they may not belong to the same branch. 
     * 
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * 
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data input(s) cannot be null.");
        }

        List<T> nodeList = levelorder();
        if (!nodeList.contains(data1) || !nodeList.contains(data2)) {
            throw new NoSuchElementException("The data does not exist in the tree.");
        }
        if (data1.equals(data2)) {
            if (root.getData().equals(data1)) {
                nodeList.clear();
                nodeList.add(root.getData());
                return nodeList;
            }
        }
        T dcaData = null;
        for (T element : nodeList) {
            if ((element.compareTo(data2) < 0 && element.compareTo(data1) > 0)
                || (element.compareTo(data1) < 0 && element.compareTo(data2) > 0)
                    || element.compareTo(data1) == 0
                        || element.compareTo(data2) == 0) {
                dcaData = element;
                break;
            }
        }
        nodeList.clear();
        nodeList.add(dcaData);

        pathHelper(data1, root, nodeList, true, dcaData, false);

        pathHelper(data2, root, nodeList, false, dcaData, false);

        return nodeList;

    }

    /**
     * help find path between nodes.
     * @param data we are given
     * @param currentNode we are traversing through
     * @param nodeList output
     * @param leftTrue leeft or right traversal
     * @param dcaData deepest common ancestor
     * @param found if dca is found
     */
    private void pathHelper(T data, BSTNode<T> currentNode,
                            List<T> nodeList, boolean leftTrue,
                            T dcaData, boolean found) {
        if (currentNode == null) {
            return;
        }
        if (!found) {
            if (dcaData.compareTo(currentNode.getData()) < 0) {
                pathHelper(data, currentNode.getLeft(), nodeList, leftTrue, dcaData, found);
            } else if (dcaData.compareTo(currentNode.getData()) > 0) {
                pathHelper(data, currentNode.getRight(), nodeList, leftTrue, dcaData, found);
            } else {
                found = true;
                if (data.compareTo(currentNode.getData()) < 0) {
                    pathHelper(data, currentNode.getLeft(), nodeList, leftTrue, dcaData, found);
                } else if (data.compareTo(currentNode.getData()) > 0) {
                    pathHelper(data, currentNode.getRight(), nodeList, leftTrue, dcaData, found);
                } else {
                    return;
                }
                return;
            }
        }

        if (found) {
            if (leftTrue) {
                nodeList.add(0, currentNode.getData());
            } else {
                nodeList.add(currentNode.getData());
            }
            if (data.compareTo(currentNode.getData()) < 0) {
                pathHelper(data, currentNode.getLeft(), nodeList, leftTrue, dcaData, found);
            } else if (data.compareTo(currentNode.getData()) > 0) {
                pathHelper(data, currentNode.getRight(), nodeList, leftTrue, dcaData, found);
            } else {
                return;
            }
        }

    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
