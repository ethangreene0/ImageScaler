public class QuadrantTree {
    private QTreeNode root;

    public QuadrantTree(int[][] thePixels) {
        // The root node will represent the entire image.
        // (0,0) is the upper-left corner, and the size is the length of the pixel array.
        // The color is the average color of the entire pixel array.
        this.root = buildTree(thePixels, 0, 0, thePixels.length);
    }

    private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
        if (size == 1) {
            // Base case: each leaf node represents a single pixel.
            return new QTreeNode(null, x, y, size, pixels[y][x]);
        } else {
            int newSize = size / 2;
            int avgColor = Gui.averageColor(pixels, x, y, size);
            QTreeNode node = new QTreeNode(new QTreeNode[4], x, y, size, avgColor);

            // Recursively build the tree for each quadrant.
            node.setChild(buildTree(pixels, x, y, newSize), 0);  // Upper-left quadrant
            node.setChild(buildTree(pixels, x + newSize, y, newSize), 1);  // Upper-right quadrant
            node.setChild(buildTree(pixels, x, y + newSize, newSize), 2);  // Lower-left quadrant
            node.setChild(buildTree(pixels, x + newSize, y + newSize, newSize), 3);  // Lower-right quadrant

            // Set the parent for each child
            for (int i = 0; i < 4; i++) {
                if (node.getChild(i) != null) {
                    node.getChild(i).setParent(node);
                }
            }

            return node;
        }
    }

    public QTreeNode getRoot() {
        return root;
    }

    public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
        if (r == null) {
            return null;
        }
        if (theLevel == 0) {
            return new ListNode<>(r);
        } else if (r.isLeaf()) {
            return new ListNode<>(r); // If it's a leaf, return it regardless of the level
        } else {
            ListNode<QTreeNode> list = null;
            for (int i = 0; i < 4; i++) {
                ListNode<QTreeNode> childList = getPixels(r.getChild(i), theLevel - 1);
                list = concatenateLists(list, childList);
            }
            return list;
        }
    }

    // Helper method to concatenate two lists
    private ListNode<QTreeNode> concatenateLists(ListNode<QTreeNode> list1, ListNode<QTreeNode> list2) {
        if (list1 == null) {
            return list2;
        }
        ListNode<QTreeNode> temp = list1;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(list2);
        return list1;
    }

    // Method to find all nodes matching a certain color at a certain level
    public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
        if (r == null) {
            return new Duple(null, 0);
        }
        if (theLevel == 0 || r.isLeaf()) {
            if (Gui.similarColor(r.getColor(), theColor)) {
                return new Duple(new ListNode<>(r), 1);
            } else {
                return new Duple(null, 0);
            }
        } else {
            Duple result = new Duple(null, 0);
            for (int i = 0; i < 4; i++) {
                Duple childDuple = findMatching(r.getChild(i), theColor, theLevel - 1);
                result.setFront(concatenateLists(result.getFront(), childDuple.getFront()));
                result.setCount(result.getCount() + childDuple.getCount());
            }
            return result;
        }
    }

    // Method to find a node that contains a certain point at a certain level
    public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
        if (r == null || !r.contains(x, y)) {
            return null;
        }
        if (theLevel == 0 || r.isLeaf()) {
            return r;
        }
        int midX = r.getx() + r.getSize() / 2;
        int midY = r.gety() + r.getSize() / 2;
        if (x < midX) {
            return x < midY ? findNode(r.getChild(0), theLevel - 1, x, y) : findNode(r.getChild(2), theLevel - 1, x, y);
        } else {
            return x < midY ? findNode(r.getChild(1), theLevel - 1, x, y) : findNode(r.getChild(3), theLevel - 1, x, y);
        }
    }
}