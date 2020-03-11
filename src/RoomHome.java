import java.util.Arrays;
import java.util.Random;

public class RoomHome extends Room {


    private Point door1, door2, doorButton, cameraButton;

    private int[][] wallColors = new int[20][15];


    
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
            setBackground(p.getX()-1, p.getY()-1, WALL);
            setBackground(p.getX()-1, p.getY()+1, WALL);
            setBackground(p.getX()+1, p.getY()+1, WALL);
            setBackground(p.getX()+1, p.getY()-1, WALL);
        }

        setBackground(door1.getX(),door1.getY(),DOOR);
        setBackground(door2.getX(),door2.getY(),DOOR);

        setBackground(cameraButton.getX(),cameraButton.getY(), CAMSCREEN);

        setBackground(doorButton.getX(),doorButton.getY(),BOMBING);

    }

    public Point[] explosionOfColors(Point location){
        Point[] points = new Point[8];
        for(Point p: entitySpots) {
            if(p.equals(location)) {
                points[0] = new Point(p.getX(), p.getY() - 1);
                points[1] = new Point(p.getX() - 1, p.getY());
                points[2] = new Point(p.getX(), p.getY() + 1);
                points[3] = new Point(p.getX() + 1, p.getY());
                points[4] = new Point(p.getX() - 1, p.getY() - 1);
                points[5] = new Point(p.getX() - 1, p.getY() + 1);
                points[6] = new Point(p.getX() + 1, p.getY() + 1);
                points[7] = new Point(p.getX() + 1, p.getY() - 1);
                for(Point pSub: points) if(wallColors[p.getX()][p.getY()] == 0){
                    wallColors[p.getX()][p.getY()] = 0;
                }
                break;
            }


        }

        return points;
//        for(Point p: entitySpots) {
//            setBackground(p.getX(), p.getY(), FLOOR);
//            setBackground(p.getX(), p.getY()-1, WALL);
//            setBackground(p.getX()-1, p.getY(), WALL);
//            setBackground(p.getX(), p.getY()+1, WALL);
//            setBackground(p.getX()+1, p.getY(), WALL);
//            setBackground(p.getX()-1, p.getY()-1, WALL);
//            setBackground(p.getX()-1, p.getY()+1, WALL);
//            setBackground(p.getX()+1, p.getY()+1, WALL);
//            setBackground(p.getX()+1, p.getY()-1, WALL);
//        }
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

    public int getColor(int row, int col) {
        if(wallColors[col][row] == 0){
            wallColors[col][row] = rand.nextInt(8)+1;
        }
        return wallColors[col][row];
    }


//    public void bombedSpot(Point p) {
//        if(p.equals(entitySpots[0])){
//
//        }
//    }
}
