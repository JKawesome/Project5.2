import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RoomHome extends Room {


    private Point door1, door2, doorButton, cameraButton;
    
    private Point[] entitySpots = new Point[]{new Point(15, 3), new Point(17,10), new Point( 5, 12)};

    private final int END_TIME = 10;
    private int endTimer = END_TIME;

    private Random rand = new Random();

    public RoomHome(){
        super(10,10);
        door1 = new Point(0, 5);
        door2 = new Point(getNumCols()-1, 5);
        doorButton = new Point(1,4);
        cameraButton =new Point( 4,1);

        createBackground();
    }

    //DOES NOTHING
    public int getClickedRoom(Point p)
    {
        return 0;
    }

    public Point getCameraButton()
    {
        return cameraButton;
    }

    public Point getDoorButton()
    {
        return doorButton;
    }


    public boolean ending()
    {
        endTimer -= 1;
        if(endTimer <= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void leftEnd()
    {
        endTimer = END_TIME;
    }

    public int nextRoom(Point p)
    {
        return rand.nextInt(3) + 1;
    }


    @Override
    public Point getDoor(int i) {
        switch (i){
            case 0:
                return door1;
            case 1:
                return door2;
            default:
                return null;
        }
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

        for(Point p: entitySpots) {
           setBackground(p.getX(), p.getY(), FLOOR);
            setBackground(p.getX(), p.getY()-1, WALL);
            setBackground(p.getX()-1, p.getY(), WALL);
            setBackground(p.getX(), p.getY()+1, WALL);
            setBackground(p.getX()+1, p.getY(), WALL);
        }

        setBackground(door1.getX(),door1.getY(),DOOR);
        setBackground(door2.getX(),door2.getY(),DOOR);

        setBackground(cameraButton.getX(),cameraButton.getY(), CAMSCREEN);

    }

    public void showMonsterLocations(){

    }

    public void hideMonsterLocations(){
        createBackground();
    }

    @Override
    public int isDoorNum(Point p) {
        if(p == door1) return 1;
        if(p== door2) return 2;
        else return 0;
    }


    public Point getStart()
    {
        //MOVING ENTITY TO SOME RANDOM SPOT
        Point newStart = entitySpots[rand.nextInt(entitySpots.length)];
        return newStart;
    }



}
