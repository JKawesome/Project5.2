
public class RoomFar extends Room {


    private Point door1, door2;

    private final int LEFT_ROOM = 1;
    private final int RIGHT_ROOM = 2;


    public RoomFar(){
        super(18,12);
        door1 = new Point(0, 6);
        door2 = new Point(17, 6);

        createBackground();
    }

    public int getClickedRoom(Point p)
    {
        if(p.equals(door1))
        {
            return LEFT_ROOM;
        }
        else if(p.equals(door2))
        {
            return RIGHT_ROOM;
        }
        else
        {
            return 0;
        }
    }

    public int nextRoom(Point p)
    {
        if(p.equals(door1))
        {
            return LEFT_ROOM;
        }
        else if(p.equals(door2))
        {
            return RIGHT_ROOM;
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
            setBackground(x,getNumRows()-1,WALL);
        }

        for(int y = 0; y< getNumRows(); y++){
            setBackground(0,y, WALL);
            setBackground(getNumCols()-1,y,WALL);
        }

        setBackground(door1.getX(),door1.getY(),DOOR);
        setBackground(door2.getX(),door2.getY(),DOOR);

    }

    @Override
    public int isDoorNum(Point p) {
        if(p == door1) return 1;
        if(p== door2) return 2;
        else return 0;
    }


}
