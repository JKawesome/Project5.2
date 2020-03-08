public abstract class Level {

    private Room[] rooms;
    private Entity[] entities;


    public Level(Room[] rooms, Entity[] entities){
        this.rooms = rooms;
        this.entities = entities;
    }


    public Room getRoom(int i){
        switch(i){
            case 1:
                return rooms[0];
            default:
                return rooms[0];
        }
    };
}
