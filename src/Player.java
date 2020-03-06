import java.util.Arrays;
import java.util.List;

public class Player
{
    private String name;
    private Point pos;
    private int current_image = 1;


    public Player(String name, Point pos)
    {
        this.name = name;
        this.pos = pos;
    }

    public Point getPos()
    {
        return pos;
    }

    public void setPos(Point p)
    {
        this.pos = p;
    }


    public void move(char dir, Worlds world)
    {
        Point newArea;
        List<Character> letterList = Arrays.asList('w', 's', 'a', 'd');
        if(dir == 'w')
        {
            newArea = new Point(pos.getX(), pos.getY() - 1);
        }
        else if(dir == 's')
        {
            newArea = new Point(pos.getX(), pos.getY() + 1);
        }
        else if(dir == 'a')
        {
            newArea = new Point(pos.getX() - 1, pos.getY());
        }
        else
        {
            newArea = new Point(pos.getX() + 1, pos.getY());
        }

        if (world.withinBounds(newArea))
                //grid[newArea.y][newArea.x] != VirtualMain.GridValues.OBSTACLE)
        {
            pos = newArea;
            current_image = letterList.indexOf(dir);
        }
    }

    public int getCurrent_image()
    {
        return current_image;
    }
}
