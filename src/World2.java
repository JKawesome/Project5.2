public class World2 extends Worlds
{


    private static enum GridValues { BACKGROUND, OBSTACLE, GOAL, BLACKSCREEN};


    public World2(int numRows, int numCols)
    {
        super(numRows, numCols, new Point(7,5));
    }



}
