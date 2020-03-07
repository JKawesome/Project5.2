import processing.core.PImage;

public class EntityFactory
{
    public static Entity createEntity(String entity, Worlds world, PImage image)
    {
        if(entity.equals("InvisibleMan"))
        {
            return new InvisibleMan("InvisibleMan", world.getStart(), world, image);
        }
        else if(entity.equals("CamBreaker"))
        {
            return new CamBreaker("InvisibleMan", world.getStart(), world, image);
        }
        else if(entity.equals("Runner"))
        {
            return new Runner("InvisibleMan", world.getStart(), world, image);
        }
        return null;
    }
}
