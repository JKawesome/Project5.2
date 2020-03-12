public class RoomFactory {


    public static Room[] createRooms (){
        Room[] rooms = new Room[4];
        rooms[0] = createHome();
        rooms[1] = createMid();
        rooms[2] = createMid();
        rooms[3] = createFar();
        return rooms;
    }
    
    public static Room createHome(){
        return new RoomHome();
    }

    public static Room createMid(){
        return new RoomMid();
    }

    public static Room createFar(){
        return new RoomFar();
    }
}
