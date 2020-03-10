public abstract class Room {

    private int numRows;
    private int numCols;
   // private Entity occupancy[][];
    private int[][] background; //value for which background image to draw
    public static final int WALL = 1, FLOOR = 2, DOOR = 3, CAMSCREEN = 4, WHITESCREEN = 5;

    private Point start;

    private boolean blackScreen = false;



    public Room(int numCols,int numRows/*, Entity[] entities*/){

        this.numCols = numCols;
        this.numRows = numRows;
        this.background = new int[numCols][numRows];
        start = new Point(numCols/2,numRows/2);
    }

    public abstract int getClickedRoom(Point p);

    public boolean isBlackScreen()
    {
        return blackScreen;
    }

    public void setBlackScreen(boolean mode)
    {
        blackScreen = mode;
    }

    abstract Point getDoor(int i);

    abstract boolean isDoor(Point p);

    abstract void createBackground();

    abstract int isDoorNum(Point p);

    abstract int nextRoom(Point p);


    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getType(int x, int y) {
        try{
            return background[x][y];
        }
        catch (IndexOutOfBoundsException e){
            return -1;
        }
    }

    public int[][] getBackground() {
        return background;
    }


    public Point getStart()
    {
        return start;
    }

    public void setBackground(int x, int y, int imageType) {
        this.background[x][y] = imageType;
    }

    public boolean withinBounds(Point p)
    {
        return p.getY() >= 0 && p.getY() < numRows &&
                p.getX() >= 0 && p.getX() < numCols &&
                this.getType(p.getX(), p.getY())!= WALL;
    }

//    public static String getFilenameOfType(int type){
//        switch(type){
//            case WALL:
//                return "images/wall_tile.png";
//            case DOOR:
//                return "images/wall_tile.png";
//            case CAMSCREEN:
//                return "images/camera_screen_icon.gif";
//            case FLOOR:
//                return "images/camera_screen_icon.gif";
//            default:
//                return "images/grass.bmp";
//
//
//        }
//    } todo - add breaks if we use this
}
