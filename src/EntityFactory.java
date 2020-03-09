import processing.core.PImage;

import java.util.Random;

public class EntityFactory
{


    public static Entity[] createEntity(Room[] rooms, PImage cambreaker, PImage invisibleMan, PImage runner)
    {
        Random rand = new Random();
        Entity[] entities = new Entity[3];
        Room currentRoom = rooms[rand.nextInt(3) + 1];
        entities[0] = new InvisibleMan(currentRoom.getStart(), currentRoom, invisibleMan);
        currentRoom = rooms[rand.nextInt(3) + 1];
        entities[1] = new CamBreaker( currentRoom.getStart(), currentRoom, cambreaker);
        currentRoom = rooms[rand.nextInt(3) + 1];
        entities[2] = new Runner(currentRoom.getStart(), currentRoom, runner);
        return entities;
    }
}