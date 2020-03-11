import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class DijkstraPathing
        implements PathingStrategy
{
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        HashMap<Point, DNode> open = new HashMap<>();
        HashMap<Point, DNode> closed = new HashMap<>();

        Comparator<DNode> nodeFCompare = Comparator.comparing(DNode::getDist);
        PriorityQueue<DNode> openSorted = new PriorityQueue<DNode>(11, nodeFCompare);

        open.put(start, new DNode(0, start, null));
        openSorted.add(new DNode(0, start, null));

        List<Point> nextPossible;
        DNode currDNode = open.get(start);

        while(open.size() > 0)
        {
            nextPossible =  potentialNeighbors.apply(currDNode.getPos())
                    .filter(canPassThrough)
                    .filter(pt -> !pt.equals(start))
                    .filter(pt -> !closed.containsKey(pt))
                    .collect(Collectors.toList());


            for(Point pt : nextPossible)
            {
                DNode newDNode = new DNode(currDNode.getDist() + 1, pt, currDNode);
                if(open.containsKey(pt))
                {
                    if(newDNode.getDist() < open.get(pt).getDist())
                    {
                        openSorted.remove(open.get(pt));
                        open.remove(pt);
                        open.put(pt, newDNode);
                        openSorted.add(newDNode);
                    }
                }
                else
                {
                    open.put(pt, newDNode);
                    openSorted.add(newDNode);
                }
            }

            open.remove(currDNode.getPos());
            openSorted.remove(currDNode);
            closed.put(currDNode.getPos(), currDNode);

            currDNode = openSorted.peek();
        }

        List<Point> path = new LinkedList();
        DNode finishedPath = closed.get(end);
        if(finishedPath != null)
        {
            if(withinReach.test(finishedPath.getPrevNode().getPos(), end))
            {
                while(finishedPath != null && !finishedPath.equals(closed.get(start)))
                {
                    path.add(0, finishedPath.getPos());
                    finishedPath = finishedPath.getPrevNode();
                }
            }
        }
        return path;
    }
}
