import processing.core.PImage;

public class Level {

    private Room[] rooms;
    private Room currentRoom;
    private Room prevRoom;
    private Entity[] entities;



    public Level(PImage cambreaker, PImage invisibleMan, PImage runner){
        this.rooms = RoomFactory.createRooms();
        this.currentRoom = rooms[0];
        this.prevRoom = rooms[0];
        this.entities = EntityFactory.createEntity(rooms, cambreaker, invisibleMan, runner);
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public Room getPrevRoom()
    {
        return prevRoom;
    }

    public void setPrevRoom(Room room)
    {
        prevRoom = room;
    }

    public Room getRoom(int i){
        return rooms[i];
    }

    public void setRoom(int i)
    {
        currentRoom = rooms[i];
    }

    public Entity[] getEntities()
    {
        return entities;
    }

    public void nextRoom(Entity entity)
    {
        entity.setRoom(rooms[entity.getRoom().nextRoom(entity.getPos())]);
        entity.setPos(entity.getRoom().getStart());
    }
}
