import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CamBreaker
{
    private String name;
    private Point pos;
    private Worlds world;

    private final int STARE_TIME = 5;
    private final int RANDOM_MOVE_TIME = 2;
    private final int NEXT_ROOM_TIME = 5;

    private int currentStareTimer = STARE_TIME;
    private int randMoveTimer = RANDOM_MOVE_TIME;
    private int next_room_timer = NEXT_ROOM_TIME;

    private boolean moving = false;

    private Random rand = new Random();

    //pathing
    private PathingStrategy strategy = new AStarPathingStrategy();
    private List<Point> points;
    private int pointIndex = 0;

    public CamBreaker(String name, Point pos, Worlds world)
    {
        this.name = name;
        this.pos = pos;
        this.world = world;
    }


    public Worlds getWorld()
    {
        return world;
    }

    public void setWorld(Worlds world)
    {
        this.world = world;
    }

    public Point getPos()
    {
        return pos;
    }

    public void setPos(Point p)
    {
        this.pos = p;
    }

    public boolean isMoving()
    {
        return moving;
    }

    public void stareDecrease()
    {
        if(moving == false)
        {
            currentStareTimer -= 1;
            if(currentStareTimer <= 0)
            {
                currentStareTimer = STARE_TIME;
            }
        }
    }

    public void randomMovementTimer()
    {
        if(moving == false)
        {
            randMoveTimer -= 1;
            if(randMoveTimer <= 0)
            {
                randMoveTimer = RANDOM_MOVE_TIME;
                move(randPoint());
            }

            next_room_timer -= 1;
            if(next_room_timer <= 0)
            {
                moving = true;
                next_room_timer = NEXT_ROOM_TIME;
                points = strategy.computePath(pos, world.getExit(),
                        p ->  world.withinBounds(p),
                        (p1, p2) -> world.neighbors(p1,p2),
                        PathingStrategy.CARDINAL_NEIGHBORS);
            }
        }
        else
        {
            //LEAVING ROOM
            if(pointIndex < points.size())
            {
                if(move(points.get(pointIndex)))
                {
                    pointIndex += 1;
                }
            }
            else
            {
                pointIndex = 0;
                moving = false;
            }
        }
    }

    public Point randPoint()
    {
        int dir = rand.nextInt(4);
        Point newArea;
        if(dir == 0)
        {
            newArea = new Point(pos.getX(), pos.getY() - 1);
        }
        else if(dir == 1)
        {
            newArea = new Point(pos.getX(), pos.getY() + 1);
        }
        else if(dir == 2)
        {
            newArea = new Point(pos.getX() - 1, pos.getY());
        }
        else
        {
            newArea = new Point(pos.getX() + 1, pos.getY());
        }
        return newArea;
    }


    private boolean move(Point p)
    {
        if (world.withinBounds(p))
        {
            pos = p;
            return true;
        }
        return false;
    }
}
