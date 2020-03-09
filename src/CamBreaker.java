import processing.core.PImage;


public class CamBreaker extends Entity
{
    public CamBreaker(Point pos, Room room, PImage image)
    {
        super(pos, room, image);
    }


    public void executeAbility()
    {
        super.getRoom().setBlackScreen(true);
    }
}

