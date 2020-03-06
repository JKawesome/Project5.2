import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class AStarPathingStrategy
        implements PathingStrategy
{
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        HashMap<Point, Node> open = new HashMap<>();
        HashMap<Point, Node> closed = new HashMap<>();

        Comparator<Node> nodeFCompare = Comparator.comparing(Node::getF);
        PriorityQueue<Node> openSorted = new PriorityQueue<Node>(11, nodeFCompare);

        open.put(start, new Node(0, manhattanDist(start, end), start, null));
        openSorted.add(new Node(0, manhattanDist(start, end), start, null));

        List<Point> nextPossible;
        Node currNode = open.get(start);
        Node finishedPath = null;


        while(open.size() > 0)
        {
            nextPossible =  potentialNeighbors.apply(currNode.getPos())
                    .filter(canPassThrough)
                    .filter(pt -> !pt.equals(start))
                    .filter(pt -> !closed.containsKey(pt))
                    .collect(Collectors.toList());

            if(nextPossible != null && nextPossible.contains(end))
            {
                finishedPath = new Node(currNode.getG() + 1,
                        0, end, currNode);
                break;
            }

            for(Point pt : nextPossible)
            {
                Node newNode = new Node(currNode.getG() + 1,
                        manhattanDist(pt, end), pt, currNode);
                if(open.containsKey(pt))
                {
                    if(newNode.getF() < open.get(pt).getF())
                    {
                        openSorted.remove(open.get(pt));
                        open.remove(pt);
                        open.put(pt, newNode);
                        openSorted.add(newNode);
                    }
                }
                else
                {
                    open.put(pt, newNode);
                    openSorted.add(newNode);
                }
            }

            open.remove(currNode.getPos());
            openSorted.remove(currNode);
            closed.put(currNode.getPos(), currNode);

            currNode = openSorted.peek();
        }
        List<Point> path = new LinkedList();

        if(finishedPath != null)
        {
            while(finishedPath != null)
            {
                path.add(0, finishedPath.getPos());
                finishedPath = finishedPath.getPrevNode();
            }
        }
        return path;
    }

    public int manhattanDist(Point start, Point end)
    {
        return Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
    }
}
