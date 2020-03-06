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

    public boolean equals(Object other)
    {
        if(other == null)
        {
            return false;
        }
        if(!other.getClass().equals(this.getClass()))
        {
            return false;
        }

        Node o = (Node)other;
        if(this.g == o.g && this.h == o.h &&
                this.f == o.f && this.pos.equals(o.pos))
        {
            if(this.prevNode != null && o.prevNode != null)
            {
                return this.prevNode.equals(o.prevNode);
            }
            else if(this.prevNode == null && o.prevNode == null)
            {
                return true;
            }
        }
        return false;

    }
}
