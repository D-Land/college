/*
 *
 *     Author: Andrew Land
 *      Email: aml136#pitt.edu
 *        PS#: 3688776
 *       Date: 9/24/13
 * Assignment: 2
 *
 */

public class Node {
  public String symbol;
  public Node right;
  public Node left;

  public Node(String data, Node left, Node right){
   this.symbol = data;
   this.right = right;
   this.left = left;
  }

  public Node(char data, Node left, Node right){
    this(Character.toString(data),left, right);
  }

  public Node(String data){
    this(data, null, null);
  }

  public Node(char data){
    this(Character.toString(data), null, null);
  }

  public Node(String data, Node right){
    this(data, null, right);
  }

  public boolean hasRight(){
    if(this.right == null){
      return false;
    }
    return true;
  }

  public boolean hasLeft(){
    if(this.left == null){
      return false;
    }
    return true;
  }

  public boolean isLeaf(){
    if(this.hasRight() == false && this.hasLeft() == false){
      return true;
    }
    return false;
  }
}
