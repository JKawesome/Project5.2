import processing.core.PImage;


public class CamBreaker extends Entity
{
    private final int BLACK_SCREEN_TIME = 5;
    private int blackScreenTimer = BLACK_SCREEN_TIME;

    public CamBreaker(Point pos, Room room, PImage image)
    {
        super(pos, room, image);
    }



    public void executeAbility()
    {
        super.getRoom().setBlackScreen(true);
    }

    public boolean blackScreenTimer()
    {
        blackScreenTimer -= 1;
        if(blackScreenTimer <= 0)
        {
            super.getRoom().setBlackScreen(false);
            blackScreenTimer = BLACK_SCREEN_TIME;
            return true;
        }
        return false;
    }
}

