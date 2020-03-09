public class Level {



    private Room[] rooms;
    private Room currentRoom;
    private Entity[] entities;


    public Level( Entity[] entities){
        this.rooms = RoomFactory.createRooms();
        this.entities = entities;
    }

    public Room getCurrentRoom(){
        return currentRoom;
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

    public Room getRoom(int i){
        try{
            return rooms[i];
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

    }
}
