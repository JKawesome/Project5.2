public class Room {

    private int numRows;
    private int numCols;
   // private Entity occupancy[][];
    private int background[][]; //value for which background image to draw
    private final int WALL = 1, FLOOR = 2;
    private Point door1;
    private Point door2;


    public Room(int numCols,int numRows/*, Entity[] entities*/){

        this.numCols = numCols;
        this.numRows = numRows;
        //this.occupancy = new Entity[numCols][numRows];
//        for(Entity entity:entities){
//            Point p = entity.getPosition();
//            occupancy[p.x][p.y] = entity;
//        }
        this.background = new int[numCols][numRows];

        createBackground();
    }

    private void createBackground() {
        for(int i=0; i<numCols; i++){
            background[i][0] = WALL;
            background[i][numRows-1] = WALL;
            for(int j=1; j<numRows-1; j++){
                background[0][j] = WALL;
                background[numCols-1][j] = WALL;
                background[i][j] = FLOOR;
            }
        }

    }

    public int isDoorNum(Point p){
        if(p == door1) return 1;
        if(p== door2) return 2;
        else return 0;
    }


    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getType(int col, int row) {
        return background[col][row];
    }
}
