public abstract class Worlds {
    private int numRows;
    private int numCols;
    private Point exit;
    private Point start;

    public Worlds(int numRows, int numCols, Point start, Point exit) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.start = start;
        this.exit = exit;
    }

    public Point getStart()
    {
        return start;
    }

    public Point getExit()
    {
        return exit;
    }

    public boolean withinBounds(Point p)
    {
        return p.getY() >= 0 && p.getY() < numRows &&
                p.getX() >= 0 && p.getX() < numCols;
    }

    public int getNumRows()
    {
        return numRows;
    }

    public int getNumCols()
    {
        return numCols;
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
        Worlds o = (Worlds)other;

        if(this.numRows == o.numRows && this.numCols == o.numCols)
        {
            return true;
        }
        return false;
    }

    public static boolean neighbors(Point p1, Point p2)
    {
        return p1.getX()+1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()+1 == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()-1 == p2.getY();
    }

}
