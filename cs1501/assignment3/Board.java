//
// Andrew Land
// Assignment 3
// aml136
// PS#3688776
//

import java.util.*;

public final class Board{

  public final int b[][];
  public final int n;
  public int hammingDist = -99;
  public int manhattanDist = -99;
  public int zeroX = -99;
  public int zeroY = -99;
  public boolean solvable = false;
  public boolean solverCalled = false;

  //Constructor sets b(the board) and n(the lenght)
  public Board(int[][] blocks){
    this.b = deepCopy(blocks);
    this.n = blocks.length;
  }

  //returns n(the length of b)
  public int dimension(){
    return this.n;
  }

  //Calculates the hamming distance
	public int hamming(){
    if(this.hammingDist == -99){
      int position = 1;
      hammingDist = 0;
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (b[i][j] != 0 && b[i][j] != ((n * i) + (j+1))){
            hammingDist++;
          }
        }
      }
    }
		return hammingDist;
	}

  //Calculates the manhattan distance
	public int manhattan(){
    if(this.manhattanDist == -99){
      manhattanDist = 0;
      for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
          if (b[i][j] != 0){
            manhattanDist += Math.abs(i - ((b[i][j]-1)/n)) + Math.abs(j - ((b[i][j]-1)%n));
          }
        }
      }
    }
		return manhattanDist;
	}

  //Checks if the board is the goal board
	public boolean isGoal(){
    if(this.b[n-1][n-1] != 0){
      return false;
    }
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
			  if (this.b[i][j] != 0 && this.b[i][j] != (i * n) + (j+1)){
          return false;
        }
			}
		}
		return true;
	}

  //returns true if the board is part of the solution
  public boolean isSolvable(){
    return solvable;
  }

  //compares two boards for equality
  public boolean equals(Object y){
    if (y == null || this == null || !(y instanceof Board)){
      return false;
    }

    Board z = (Board)y;

    for(int i = 0; i < this.n; i++){
      for(int j = 0; j < this.n; j++ ){
        if(this.b[i][j] != z.b[i][j]){
          return false;
        }
      }
    }
    return true;
  }

  //Returns an interable (arraylist) of adjacent boards
  public Iterable<Board> neighbors(){
    ArrayList<Board> neighbors = new ArrayList<Board>(4);
    int temp[][] = deepCopy(this.b);
    int i;
    int j;
    if(this.zeroX == -99 || this.zeroY == -99){
      findZero();
    }
      i = this.zeroX;
      j = this.zeroY;

    if(j+1 < this.n){
      temp[i][j] = temp[i][j+1];
      temp[i][j+1] = 0;
      neighbors.add(new Board(temp));
      temp[i][j+1] = temp[i][j];
      temp[i][j] = 0;
    }
    if(j-1 > -1){
      temp[i][j] = temp[i][j-1];
      temp[i][j-1] = 0;
      neighbors.add(new Board(temp));
      temp[i][j-1] = temp[i][j];
      temp[i][j] = 0;
    }
    if(i+1 < this.n){
      temp[i][j] = temp[i+1][j];
      temp[i+1][j] = 0;
      neighbors.add(new Board(temp));
      temp[i+1][j] = temp[i][j];
      temp[i][j] = 0;
    }
    if(i-1 > -1){
      temp[i][j] = temp[i-1][j];
      temp[i-1][j] = 0;
      neighbors.add(new Board(temp));
      temp[i-1][j] = temp[i][j];
      temp[i][j] = 0;
    }
    return neighbors;
  }

  //Builds a String of the board
  public String toString(){
    String string = "";
    for(int i = 0; i < this.n; i++){
      for(int j = 0; j < this.n; j++){
        string += String.format(" %2d ", b[i][j]);
      }
      string += "\n";
    }
    return string;
  }

  //Makes a board with a parity 1 different from the given board
  //This is used to check for solvability
  public Board flipped(){
    int flippedBoard[][] = deepCopy(this.b);
    int first;

    if(this.zeroX == -99 || this.zeroY == -99){
      findZero();
    }

    if(this.zeroX != 0){
      first = flippedBoard[0][0];
      flippedBoard[0][0] = flippedBoard[0][1];
      flippedBoard[0][1] = first;
    }
    else{
      first = flippedBoard[1][0];
      flippedBoard[1][0] = flippedBoard[1][1];
      flippedBoard[1][1] = first;
    }

    return new Board(flippedBoard);
  }

  //Makes a deep copy of the passed 2D array
  private int[][] deepCopy(int[][] original){
    int[][] copy = new int[original.length][original.length];
    for(int i = 0; i < original.length; i++){
      for(int j = 0; j < original.length; j++ ){
        copy[i][j] = original[i][j];
      }
    }
    return copy;
  }
  //Finds the zero and stores its coords as global vars to reduce
  //the number of times this N^2 loop runs
  private void findZero(){
    if(this.zeroX == -99 || this.zeroY == -99){
      for(int i = 0; i < this.n; i++){
        for(int j = 0; j < this.n; j++ ){
          if(b[i][j] == 0){
            this.zeroX = i;
            this.zeroY = j;
          }
        }
      }
    }
  }

}
