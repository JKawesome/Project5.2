import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoxPath implements PathingStrategy {

    private static final int LEFT = 1, UP = 2, RIGHT  = 3, DOWN = 4;
    private int nextDirection = RIGHT;
    private boolean recursive;
    private Predicate<Point> canPassThrough;

    @Override
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        List<Point> points = new ArrayList<>();
        int runs = 0;
        while(points.size()<4 && runs<4) {
            runs++;
            points.addAll(potentialNeighbors.apply(start).filter(canPassThrough).filter(p -> start.getX() - p.getX() > 0).limit(1).collect(Collectors.toList())); //left
            points.addAll(potentialNeighbors.apply(points.get(points.size()-1)).filter(canPassThrough).filter(p -> start.getY() - p.getY() > 0).limit(1).collect(Collectors.toList())); //up
            points.addAll(potentialNeighbors.apply(points.get(points.size()-1)).filter(canPassThrough).filter(p -> start.getX() - p.getX() < 0).limit(1).collect(Collectors.toList())); //right
            points.addAll(potentialNeighbors.apply(points.get(points.size()-1)).filter(canPassThrough).filter(p -> start.getY() - p.getY() < 0).limit(1).collect(Collectors.toList())); //down
        }
        if(points.size()<4) System.out.println("box path: " + points );

        for(int i=0; i<3;i++)
            points.addAll(points);
        return points;



//        points.add(start);
//        this.canPassThrough = canPassThrough;
//        for(int i = 0; i<20; i++){
//            if(i==0) points.add(computePathHelper(start));
//            else points.add(computePathHelper(points.get(i-1)));
//        }
//
//        return points;

    }



    private Point computePathHelper(Point start){
        Point nextPos;
        switch (nextDirection){
            case LEFT:
                nextPos = new Point(start.getX()-1,start.getY());
                if(canPassThrough.test(nextPos)) {
//                    nextDirection = DOWN;
                    return nextPos;

                }
//                else{
//                    nextDirection = RIGHT;
//                    if(recursive){
//                        recursive = false;
//
//                        break;
//                    }
//                    recursive = true;
//                    return computePathHelper(start);
//                }
            case RIGHT:
                nextPos = new Point(start.getX()+1,start.getY());
                if(canPassThrough.test(nextPos)){
                    nextDirection = UP;
                    return nextPos;
                }
//                else{
//                    nextDirection = LEFT;
//                    if(recursive){
//                        recursive = false;
//
//                        break;
//                    }
//                    recursive = true;
//                    return computePathHelper(start);
//                }
            case UP:
                nextPos = new Point(start.getX(),start.getY()-1);
                if(canPassThrough.test(nextPos)){
                    nextDirection = LEFT;
                    return nextPos;
                }
//                else{
//                    nextDirection = DOWN;
//                    if(recursive){
//                        recursive = false;
//
//                        break;
//                    }
//                    recursive = true;
//                    return computePathHelper(start);
//                }
            case DOWN:
                nextPos = new Point(start.getX()+1,start.getY());
                if(canPassThrough.test(nextPos)){
                    nextDirection = RIGHT;
                    return nextPos;
                }
//                else{
//                    nextDirection = UP;
//                    if(recursive){
//                        recursive = false;
//
//                        break;
//                    }
//                    recursive = true;
//                    return computePathHelper(start);
//                }
            default:
                System.out.println("ERROR IN - boxing isn't finding a path");
                return start;
        }
    }
}
