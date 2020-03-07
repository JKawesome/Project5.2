import processing.core.PImage;

import java.util.List;
import java.util.Random;

public abstract class Entity
{

    private String name;
    private Point pos;
    private Worlds world;
    private PImage image;

    private final int STARE_TIME = 4;
    private final int RANDOM_MOVE_TIME = 2;
    private final int NEXT_ROOM_TIME = 8;

    private int currentStareTimer = STARE_TIME;
    private int randMoveTimer = RANDOM_MOVE_TIME;
    private int next_room_timer = NEXT_ROOM_TIME;

    private boolean moving = false;

    private Random rand = new Random();

    //pathing
    private PathingStrategy strategy = new AStarPathingStrategy();
    private List<Point> points;
    private int pointIndex = 0;


    public Entity(String name, Point pos, Worlds world, PImage image)
    {
        this.name = name;
        this.pos = pos;
        this.world = world;
        this.image = image;
    }

    public PImage getImage()
    {
        return image;
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

    public void stareDecrease()
    {
        if(moving == false)
        {
            currentStareTimer -= 1;
            if(currentStareTimer <= 0)
            {
                currentStareTimer = STARE_TIME;
                executeAbility();
            }
        }
    }

    public abstract void executeAbility();


    public void randomMovementTimer()
    {
        if(this.getClass().equals(InvisibleMan.class))
        {
            if(((InvisibleMan)this).isInvis())
            {
                ((InvisibleMan)this).invisibleTime();
            }
        }
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
                resetTimers();
                moving = true;
                createPath();
            }
        }
        else
        {
            movingToExit();
        }
    }

    public void resetTimers()
    {
        randMoveTimer = RANDOM_MOVE_TIME;
        next_room_timer = NEXT_ROOM_TIME;
    }


    public void movingToExit()
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
            moving = false;
        }
    }

    public void createPath()
    {
        pointIndex = 0;
        points = strategy.computePath(pos, world.getExit(),
                p ->  world.withinBounds(p),
                (p1, p2) -> world.neighbors(p1,p2),
                PathingStrategy.CARDINAL_NEIGHBORS);
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
