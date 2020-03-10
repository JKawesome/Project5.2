import processing.core.PImage;


public class InvisibleMan extends Entity
{
    private boolean currentInvis = false;
    private final int INVIS_TIME = 6;
    private int invisTimer = INVIS_TIME;


    public InvisibleMan (Point pos, Room room, PImage image)
    {
        super(pos, room, image);
    }

    public boolean isInvis()
    {
        return currentInvis;
    }


    public void executeAbility()
    {
        currentInvis = true;
    }

    public void invisibleTime()
    {
        invisTimer -= 1;
        if(invisTimer <= 0)
        {
            currentInvis = false;
            invisTimer = INVIS_TIME;
        }
    }

    public String toString()
    {
        return "Invisible Man";
    }
}
