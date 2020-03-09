public class RoomHome extends Room {


    private Point door1, door2, doorButton, cameraButton;


    public RoomHome(){
        super(20,15);
        door1 = new Point(0, 7);
        door2 = new Point(19, 7);
        doorButton = new Point(1,6);
        cameraButton =new Point( 9,1);

        createBackground();
    }

    @Override
    Point getDoor(int i) {
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
    void createBackground() {
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

        setBackground(door1.x,door1.y,DOOR);
        setBackground(door2.x,door2.y,DOOR);

        setBackground(cameraButton.x,cameraButton.y, CAMSCREEN);

    }

    @Override
    public int isDoorNum(Point p) {
        if(p == door1) return 1;
        if(p== door2) return 2;
        else return 0;
    }


}
