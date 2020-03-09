public class RoomMid extends Room {


    private Point door1, door2;


    public RoomMid(){
        super(18,14);
        door1 = new Point(getNumCols()/2, 0);
        door2 = new Point(getNumRows()/2, 13);

        createBackground();
    }

    @Override
    public Point getDoor(int i) {
        switch (i){
            case 1:
                return door1;
            case 2:
                return door2;
            default:
                return null;
        }
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

        setBackground(door1.x,door1.y,DOOR);
        setBackground(door2.x,door2.y,DOOR);

    }

    @Override
    public int isDoorNum(Point p) {
        if(p == door1) return 1;
        if(p== door2) return 2;
        else return 0;
    }


}
