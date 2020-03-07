public class World1 extends Worlds
{


    private static enum GridValues { BACKGROUND, OBSTACLE, GOAL, BLACKSCREEN};


    public World1(int numRows, int numCols)
    {
        super(numRows, numCols, new Point(5, 10));
    }




}
