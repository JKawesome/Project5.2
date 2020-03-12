import processing.core.PImage;

import java.util.Random;

public class EntityFactory
{


    public static Entity[] createEntities(Room[] rooms, PImage cambreaker, PImage invisibleMan, PImage runner)
    {
        Random rand = new Random();
        Entity[] entities = new Entity[3];
        Room currentRoom = rooms[rand.nextInt(3) + 1];
        entities[0] = createInvis(invisibleMan,currentRoom);
        currentRoom = rooms[rand.nextInt(3) + 1];
        entities[1] = createCamBreaker(cambreaker,currentRoom);
        currentRoom = rooms[rand.nextInt(3) + 1];
        entities[2] = createRunner(runner,currentRoom);
        return entities;
    }

    public static Entity createInvis(PImage invisibleMan, Room room){
        return new InvisibleMan(room.getStart(), room, invisibleMan);
    }

    public static Entity createCamBreaker(PImage cambreaker, Room room){
        return new CamBreaker(room.getStart(), room, cambreaker);
    }

    public static Entity createRunner(PImage runner, Room room){
        return new Runner(room.getStart(), room, runner);
    }
}