package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private final SolverOutcome outcome;
    private final ArrayHeapMinPQ<Vertex> PQ = new ArrayHeapMinPQ<>();
    private final Map<Vertex, Double> distTo = new HashMap<>();
    private final Map<Vertex, Vertex> edgeTo = new HashMap<>();
    private final Map<Vertex, Double> heuristicValue = new HashMap<>();
    private int numStatesExplored = -1; //The total number of priority queue dequeue operations.
    private double explorationTime;
    private Vertex end;

    /**
     * Constructor which finds the solution, computing everything necessary for all other
     * methods to return their results in constant time. Note that timeout passed in is in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex goal, double timeOut) {
        Stopwatch sw = new Stopwatch();

        distTo.put(start, 0.0);
        edgeTo.put(start, null);
        heuristicValue.put(start, input.estimatedDistanceToGoal(start, goal));
        PQ.add(start, distTo.get(start) + heuristicValue.get(start));

        while (PQ.size() != 0) {
            //check timeout
            explorationTime = sw.elapsedTime();
            if (explorationTime > timeOut) {
                outcome = SolverOutcome.TIMEOUT;
                return;
            }

            //dequeue the best vertex v from PQ
            Vertex v = PQ.removeSmallest();
            numStatesExplored++;

            // reach the final goal
            if (v.equals(goal)) {
                end = v;
                outcome = SolverOutcome.SOLVED;
                explorationTime = sw.elapsedTime();
                return;
            }

            //relax all edges pointing from v
            for (WeightedEdge<Vertex> w: input.neighbors(v)) {
                Vertex from = w.from();
                Vertex to = w.to();
                double weight = w.weight();

                //initialize
                if (!distTo.containsKey(to)) {
                    distTo.put(to, Double.POSITIVE_INFINITY);
                }
                if (!heuristicValue.containsKey(to)) {
                    heuristicValue.put(to, input.estimatedDistanceToGoal(to, goal));
                }

                //relax(w);
                if (distTo.get(from) + weight < distTo.get(to)) {
                    distTo.put(to, distTo.get(from) + weight);
                    edgeTo.put(to, from); //在这里更新edge，后面应该是from，不是v
                    //!!!!!!!!!!PROBLEM FOUND
                    if (PQ.contains(to)) {
                        PQ.changePriority(to, distTo.get(to) + heuristicValue.get(to));
                    } else {
                        PQ.add(to, distTo.get(to) + heuristicValue.get(to));
                    }
                }

            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
    }

    /** relax the edge outgoing from v */
//    private void relax(WeightedEdge<Vertex> w, Vertex v) {
//        Vertex from = w.from();
//        Vertex to = w.to();
//        double weight = w.weight();
//        if (distTo.get(from) + weight < distTo.get(to)) {
//            distTo.put(to, distTo.get(from) + weight);
//            edgeTo.put(to, from);//后面应该是from，不是v
//        }
//        if (PQ.contains(to)) {
//            PQ.changePriority(to, distTo.get(to) + heuristicValue.get(to));
//        } else {
//            PQ.add(to, distTo.get(to) + heuristicValue.get(to));
//        }
//    }

    /**
     * Returns one of SolverOutcome.SOLVED, SolverOutcome.TIMEOUT, or SolverOutcome.UNSOLVABLE.
     */
    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    /**
     * A list of vertices corresponding to a solution.
     * Should be empty if result was TIMEOUT or UNSOLVABLE.
     */
    @Override
    public List<Vertex> solution() {
        LinkedList<Vertex> solution = new LinkedList<>();
        if (outcome.equals(SolverOutcome.SOLVED)) {
            Vertex p = end;
            solution.add(p);
            while (edgeTo.get(p) != null) {
                solution.addFirst(edgeTo.get(p));
                p = edgeTo.get(p);
            }
        }
        return solution;
    }

    /**
     * The total weight of the given solution, taking into account edge weights.
     * Should be 0 if result was TIMEOUT or UNSOLVABLE.
     */
    @Override
    public double solutionWeight() {
        if (outcome.equals(SolverOutcome.SOLVED)) {
            return distTo.get(end);
        } else {
            return 0;
        }
    }

    /** The total number of priority queue dequeue operations. */
    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    /** The total time spent in seconds by the constructor. */
    @Override
    public double explorationTime() {
        return explorationTime;
    }

}
