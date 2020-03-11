import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BoxPath implements PathingStrategy {

    private static final int LEFT = 1, UP = 2, RIGHT  = 3, DOWN = 4;
    private int nextDirection = RIGHT;

    @Override
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        return Arrays.asList(computePathHelper(start,canPassThrough));

    }

    private Point computePathHelper(Point start, Predicate<Point> canPassThrough){
        Point nextPos;
        switch (nextDirection){
            case LEFT:
                nextPos = new Point(start.getX()-1,start.getY());
                if(canPassThrough.test(nextPos)) return nextPos;
                else{
                    nextDirection = RIGHT;
                    return computePathHelper(start,canPassThrough);
                }
            case RIGHT:
                nextPos = new Point(start.getX()+1,start.getY());
                if(canPassThrough.test(nextPos)) return nextPos;
                else{
                    nextDirection = LEFT;
                    return computePathHelper(start,canPassThrough);
                }
            case UP:
                nextPos = new Point(start.getX(),start.getY()-1);
                if(canPassThrough.test(nextPos)) return nextPos;
                else{
                    nextDirection = DOWN;
                    return computePathHelper(start,canPassThrough);
                }
            case DOWN:
                nextPos = new Point(start.getX()+1,start.getY());
                if(canPassThrough.test(nextPos)) return nextPos;
                else{
                    nextDirection = UP;
                    return computePathHelper(start,canPassThrough);
                }
            default:
                System.out.println("ERROR IN:" + this.getClass());
                return null;
        }
    }
}
