public class LevelFactory {




    public static Level createLevel (int num){
        switch(num) {
            case 1:
                return new Level1();
            case 2:
                return null;
            default:
                return null;
        }
    }
}
