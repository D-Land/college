Name: Andrew Land (aml136 - PS# 3688776)
Email: aml136@pitt.edu
Recitation: Friday 2:00-2:50pm
Project Due: 10-13-13
Handed In: 10-13-13
Source Code:

  Board.java
  Solver.java           -Uses Manhattan Distance
  SolverManhattan.java  -For Special Driver - Uses Manhattan Distance
  SolverHamming.java    -For Special Driver - Uses Hamming Distance
  SpecialDriver.java
  Checker.java

  Dependencies/Authors Files

Compiled Files:

  Board.class
  Solver.class
  SolverManhattan.class
  SolverHamming.class
  SpecialDriver.class
  Checker.class

  Dependencies/Authors Files

Other:

  puzzle*.txt's       -All 45 Provided boards

Compilation Instructions:

  javac *.java

Opperation:

  java -Xms256 -Xmx5120 Checker puzzle*.txt
  java -Xms256 -Xmx5120 Solver puzzle.txt
  java -Xms256 -Xmx5120 SpecialDriver puzzle.txt h -Uses hamming
  java -Xms256 -Xmx5120 SpecialDriver puzzle.txt m -Uses manhattan

Notes:

  Solver files that use Manhattan distance complete all given puzzles in
  aprox. 38sec.
  Solver files that use Hamming get stuck on puzzle 33 and run out of heap
  Space
