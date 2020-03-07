import processing.core.PImage;

import java.util.List;

public class Runner extends Entity
{
    private boolean running = false;

    public Runner(String name, Point pos, Worlds world, PImage image)
    {
        super(name, pos, world, image);
    }

    public void setRunning()
    {
        running = false;
    }

    public boolean isRunning()
    {
        return running;
    }

    public void running()
    {
        System.out.println("running");
        super.movingToExit();
    }

    public void executeAbility()
    {
        running = true;
        super.createPath();
        super.resetTimers();
    }
}
