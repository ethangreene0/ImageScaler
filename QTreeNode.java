public class QTreeNode {
    private int x, y; // Coordinates of the upper left corner of the quadrant
    private int size; // Size of the quadrant
    private int color; // Average color of the pixels in the quadrant
    private QTreeNode parent; // Parent node
    private QTreeNode[] children; // Children nodes

    // Default constructor
    public QTreeNode() {
        this.x = 0;
        this.y = 0;
        this.size = 0;
        this.color = 0;
        this.parent = null;
        this.children = new QTreeNode[4];  // Initialize with 4 null children
    }

    // Parameterized constructor
    public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
        this.x = xcoord;
        this.y = ycoord;
        this.size = theSize;
        this.color = theColor;
        this.parent = null;  // Parent is initially null; it can be set later using setParent method
        this.children = theChildren;  // Set the children directly from the parameter
    }

    // Check if the point is within the quadrant
    public boolean contains(int xcoord, int ycoord) {
        return xcoord >= x && xcoord < x + size && ycoord >= y && ycoord < y + size;
    }

    // Getters
    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getColor() {
        return color;
    }

    public QTreeNode getParent() {
        return parent;
    }

    public QTreeNode getChild(int index) throws QTreeException {
        if (index < 0 || index > 3 || children == null) {
            throw new QTreeException("Child index is out of bounds or children are null");
        }
        return children[index];
    }

    // Setters
    public void setX(int newx) {
        this.x = newx;
    }

    public void setY(int newy) {
        this.y = newy;
    }

    public void setSize(int newSize) {
        this.size = newSize;
    }

    public void setColor(int newColor) {
        this.color = newColor;
    }

    public void setParent(QTreeNode newParent) {
        this.parent = newParent;
    }

    public void setChild(QTreeNode newChild,int index) throws QTreeException {
        if (index < 0 || index > 3 || children == null) {
            throw new QTreeException("Child index is out of bounds or children are null");
        }
        this.children[index] = newChild;
    }

    // Check if this node is a leaf
    public boolean isLeaf() {
        if (children == null) {
            return true;
        }
        for (QTreeNode child : children) {
            if (child != null) {
                return false;
            }
        }
        return true;
    }
}
