//
// Andrew Land
// Assignment 3
// aml136
// PS#3688776
//

import java.util.*;

public class SolverManhattan {

  public MinPQ<Node> pq;
  public Stack<Board> solution = null;
  public int moves = 0;
  public boolean solvable = false;
  public Node goal = null;

  public SolverManhattan(Board initial){
    solution = new Stack<Board>();
    pq = new MinPQ<Node>();
    MinPQ<Node> flippedPq = new MinPQ<Node>();
    ArrayList<Board> neighbors;
    Node node;
    Node flippedNode;
    boolean done = false;
    int totalMoves = 0;
    Board prevBoard = initial;

    pq.insert(new Node(initial));
    flippedPq.insert(new Node(initial.flipped()));

    while(this.pq.size() != 0 && flippedPq.size() != 0 && !done){
      node = pq.delMin();
      flippedNode = flippedPq.delMin();

      if(node.board.isGoal()){
        solvable = true;
        done = true;
        goal = node;
        solutionPath();
      }

     if(flippedNode.board.isGoal()){
        solvable = false;
        done = true;
        moves = -1;
      }

      if(!done){
        neighbors = (ArrayList<Board>) node.board.neighbors();
        if(node.prev != null){
          prevBoard = node.prev.board;
        }
        for(Board neighbor: neighbors){
          if(!prevBoard.equals(neighbor)){
            totalMoves = node.moves + 1;
            pq.insert(new Node(totalMoves, neighbor, node));
          }
        }

        neighbors = (ArrayList<Board>) flippedNode.board.neighbors();
        if(node.prev != null){
          prevBoard = flippedNode.prev.board;
        }
        for(Board neighbor: neighbors){
          if(!prevBoard.equals(neighbor)){
            flippedPq.insert(new Node(totalMoves, neighbor, flippedNode));
          }
        }
      }

    }
  }

  public boolean isSolvable(){
    return this.solvable;
  }

  public int moves(){
    return this.moves;
  }

  public Iterable<Board> solution(){
    return this.solution;
  }

  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];

    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
      blocks[i][j] = in.readInt();

    Board initial = new Board(blocks);      // solve the puzzle
    Solver solver = new Solver(initial);    // print solution to standard output

    if (!solver.isSolvable())
      System.out.println("No solution possible");

    else {
      System.out.println("Minimum number of moves = " + solver.moves());

      for (Board board : solver.solution())
        System.out.println(board);
    }
  }

  private void solutionPath(){
    solution = new Stack<Board>();
    Node curr = goal;
    do{
      solution.push(curr.board);
      curr.board.solvable = true;
      if(curr.prev != null){
        curr = curr.prev;
      }
      moves++;
    }while(curr.prev != null);
  }

  private class Node implements Comparable <Node>{
    public int moves = 0;
    public Board board = null;
    public Node prev = null;

    public Node(int moves, Board board, Node prev){
      this.moves = moves;
      this.board = board;
      this.prev = prev;
    }

    public Node(Board curr){
      this.moves = 0;
      this.board = curr;
      this.prev = null;
    }

    public int compareTo(Node node){
      return  (this.board.manhattan() + this.moves) - (node.board.manhattan() + node.moves);
    }
  }
}
