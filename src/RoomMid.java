
public class RoomMid extends Room {


    private Point door1, door2, doorButton, cameraButton;

    private final int UPPER_ROOM = 3;
    //MUST CHANGE LOWER ROOM
    private final int LOWER_ROOM = 0;


    public RoomMid(){
        super(20,15);
        door1 = new Point(0, 7);
        door2 = new Point(19, 7);
        doorButton = new Point(1,6);
        cameraButton =new Point( 9,1);

        createBackground();
    }


    public int nextRoom(Point p)
    {
        if(p.equals(door1))
        {
            return UPPER_ROOM;
        }
        else if(p.equals(door2))
        {
            return LOWER_ROOM;
        }
        return 0;
    }


    @Override
    public Point getDoor(int i) {
            if(i == 0)
            {
                return door1;
            }
            else if(i==1)
            {
                return door2;
            }
            return null;
    }

    public boolean isDoor(Point p)
    {
        if(door1.equals(p) || door2.equals(p))
        {
            return true;
        }
        return false;
    }

    @Override
    public void createBackground() {
        for(int x = 0; x< getNumCols(); x++){
            for(int y=0; y< getNumRows()-1; y++){
                setBackground(x,y,FLOOR);
            }
        }

        for(int x = 0; x< getNumCols(); x++){
            setBackground(x,0, WALL);
            setBackground(x,14,WALL);
        }

        for(int y = 0; y< getNumRows(); y++){
            setBackground(0,y, WALL);
            setBackground(19,y,WALL);
        }

        setBackground(door1.getX(),door1.getY(),DOOR);
        setBackground(door2.getX(),door2.getY(),DOOR);

        setBackground(cameraButton.getX(),cameraButton.getY(), CAMSCREEN);

    }

    @Override
    public int isDoorNum(Point p) {
        if(p == door1) return 1;
        if(p== door2) return 2;
        else return 0;
    }


}
