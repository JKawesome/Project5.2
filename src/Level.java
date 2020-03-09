public class Level {



    private Room[] rooms;
    private Entity[] entities;


    public Level( /*Entity[] entities*/){
        this.rooms = RoomFactory.createRooms();
        //this.entities = entities;
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
