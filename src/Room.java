public abstract class Room {

    private int numRows;
    private int numCols;
   // private Entity occupancy[][];
    private int[][] background; //value for which background image to draw
    public static final int WALL = 1, FLOOR = 2, DOOR = 3, CAMSCREEN = 4;


    public Room(int numCols,int numRows/*, Entity[] entities*/){

        this.numCols = numCols;
        this.numRows = numRows;
        //this.occupancy = new Entity[numCols][numRows];
//        for(Entity entity:entities){
//            Point p = entity.getPosition();
//            occupancy[p.x][p.y] = entity;
//        }
        this.background = new int[numCols][numRows];
    }

    abstract Point getDoor(int i);

    abstract void createBackground();

    abstract int isDoorNum(Point p);


    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getType(int col, int row) {
        return background[col][row];
    }

    public int[][] getBackground() {
        return background;
    }

    public void setBackground(int x, int y, int imageType) {
        this.background[x][y] = imageType;
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
