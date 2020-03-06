public class Node
{
    private int g;
    private int h;
    private int f;
    private Point pos;
    private Node prevNode;

    public Node(int g, int h, Point pos, Node prevNode)
    {
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.pos = pos;
        this.prevNode = prevNode;
    }
    
    public int getG()
    {
        return g;
    }
    
    public int getH()
    {
        return h;
    }
    
    public int getF()
    {
        return f;
    }
    
    public Point getPos()
    {
        return pos;
    }

    public Node getPrevNode()
    {
        return prevNode;
    }
}
