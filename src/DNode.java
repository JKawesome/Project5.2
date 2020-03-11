public class DNode
{
    private int dist;
    private Point pos;
    private DNode prevNode;

    public DNode(int dist, Point pos, DNode prevNode)
    {
        this.dist = dist;
        this.pos = pos;
        this.prevNode = prevNode;
    }

    public int getDist()
    {
        return dist;
    }

    public Point getPos()
    {
        return pos;
    }

    public DNode getPrevNode()
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

        DNode o = (DNode)other;
        if(this.dist == o.dist && this.pos.equals(o.pos))
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
