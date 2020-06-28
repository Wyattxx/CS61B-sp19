package bearmaps.hw4.lectureexample;

import bearmaps.hw4.AStarSolver;
import bearmaps.hw4.ShortestPathsSolver;
import bearmaps.hw4.SolutionPrinter;


/**
 * Showcases how the AStarSolver can solve the example from the spec.
 * NOTE: YOU MUST REPLACE LazySolver WITH AStarSolver OR THIS DEMO WON'T WORK!
 * Created by hug.
 */
public class DemoAlternateExampleSolution {
    public static void main(String[] args) {
        WeightedDirectedGraph wdg = new WeightedDirectedGraph(3);
        /* Edges from vertex 0. */
        wdg.addEdge(0, 1, 5);
        wdg.addEdge(0, 2, 1);

        wdg.addEdge(1, 2, -10);

//        wdg.addEdge(2, 4, -20);
//        wdg.addEdge(2, 5, 2);
//
//        wdg.addEdge(3, 1, 4);
//        wdg.addEdge(3, 4, 3);
//
//        wdg.addEdge(4, 5, 1);


        int start = 0;
        int goal = 2;

        ShortestPathsSolver<Integer> solver = new AStarSolver<>(wdg, start, goal, 10);
        SolutionPrinter.summarizeSolution(solver, " => ");
    }
}
