//
// Andrew Land
// Assignment 3
// aml136
// PS#3688776
//

import java.util.*;

public class SpecialDriver{

  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];

    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
      blocks[i][j] = in.readInt();

    Board initial = new Board(blocks);      // solve the puzzle

    if(args[1].toLowerCase().charAt(0) == 'h'){
      SolverHamming solver = new SolverHamming(initial);
      if (!solver.isSolvable())
        System.out.println("No solution possible");

      else {
        System.out.println("Minimum number of moves = " + solver.moves());

        for (Board board : solver.solution())
          System.out.println(board);
      }
    }
    else{
      SolverManhattan solver = new SolverManhattan(initial);    // print solution to standard output
      if (!solver.isSolvable())
        System.out.println("No solution possible");

      else {
        System.out.println("Minimum number of moves = " + solver.moves());

        for (Board board : solver.solution())
          System.out.println(board);
      }
    }

  }
}
