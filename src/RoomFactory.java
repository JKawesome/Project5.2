public class RoomFactory {


    public static Room[] createRooms (){
        Room[] rooms = new Room[4];
        rooms[0] = new RoomHome();
        rooms[1] = new RoomMid();
        rooms[2] = new RoomMid();
        rooms[3] = new RoomFar();
        return rooms;
    }
}
