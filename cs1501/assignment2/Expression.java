/*
 *
 *     Author: Andrew Land
 *      Email: aml136#pitt.edu
 *        PS#: 3688776
 *       Date: 9/24/13
 * Assignment: 2
 *
 */

public class Expression {

  public static boolean atoms[] = new boolean[26];

  public  Node root;
  public String input;

  //Constructor
  public Expression(String line) throws ParseError{
    this.input = line;
    line = line.replace(" ", "");
    this.root = createTree(line);

    TreeDisplay display = new TreeDisplay(this.input);
    display.setRoot(this.root);
  }

  //Overloaded constructor for copy that copy's the expression and not the tree
  private Expression(String line, boolean copy) throws ParseError{
    this.input = line;
    line = line.replace(" ", "");
    this.root = createTree(line);
  }

  //Sets Atoms array
  public static void setAtom(String atom, String value){

    if(Character.isLetter(atom.charAt(0))){
      if(value.toLowerCase().contains("true")){
        atoms[atom.charAt(0)-65] = true;
      }

      if(value.toLowerCase().contains("false")){
        atoms[atom.charAt(0)-65] = false;
      }
    }
  }

  //Primer method for evaluate
  public boolean evaluate(){
    return evaluate(this.root);
  }

  //Evaluates the tree based on values in atoms
  private boolean evaluate(Node tree){
    boolean left, right;

    if(tree.isLeaf()){
      return atoms[tree.symbol.charAt(0)-65];
    }

    else if(tree.symbol.equals("v")){
      left = evaluate(tree.left);
      right = evaluate(tree.right);
      return left || right;
    }

    else if(tree.symbol.equals("^")){
      left = evaluate(tree.left);
      right = evaluate(tree.right);
      return left && right;
    }
    else{
      return !evaluate(tree.right);
    }
  }

  //Copies the Expression using overloaded constructor
  public Expression copy() throws ParseError{
    return new Expression(this.input, true);
  }

  //Copys any tree from the node given as a param
  private Node copyTree(Node tree){
    if(tree == null){
      return null;
    }
    if(tree.isLeaf()){
      return new Node(tree.symbol, null, null);
    }
    return new Node(tree.symbol, copyTree(tree.left), copyTree(tree.right));
  }

  //Primer method of normalize
  public void normalize(){
    this.root = normalize(this.root);
  }

  //Normalizes the tree into disjunctive normal form
  private Node normalize(Node tree){
    if(tree.isLeaf()){
      return tree;
    }

    if(isBang(tree.symbol) && tree.right.isLeaf()){
      return tree;
    }

    if(isBang(tree.symbol) && isBang(tree.right.symbol)){
      tree = normalize(tree.right.right);
    }

    else if(isBang(tree.right.symbol) && isBang(tree.right.right.symbol)){
      tree.right = normalize(tree.right.right.right);
    }

    else if(isBang(tree.right.symbol) && tree.right.symbol.equals("v")){
      tree = tree.right;
      tree.symbol = "^";
      tree = bangInsert(tree);

      tree.right = normalize(tree.right);
      tree.left = normalize(tree.left);
    }

    else if(isBang(tree.right.symbol) && tree.right.symbol.equals("^")){
      tree = tree.right;
      tree.symbol = "v";
      tree = bangInsert(tree);

      tree.right = normalize(tree.right);
      tree.left = normalize(tree.left);
    }
    else{
      tree.right = normalize(tree.right);
      tree.left = normalize(tree.left);
    }

    //if there is an and about an or it creates new subtrees and flips symbols
    if(tree.symbol.equals("^") && tree.left.symbol.equals("v") && tree.right.symbol.equals("v")){
      tree.symbol = "v";
      tree.left = new Node("^", new Node("^", copyTree(tree.left.left), copyTree(tree.right.left)), new Node("^", copyTree(tree.left.left), copyTree(tree.right.right)));
      tree.right = new Node("^", new Node("^", copyTree(tree.left.right), copyTree(tree.right.left)), new Node("^", copyTree(tree.left.right), copyTree(tree.right.right)));
    }

    else if(tree.symbol.equals("^") && tree.left.symbol.equals("v")){
      tree.symbol = "v";
      tree.right = new Node("^", copyTree(tree.left.right), copyTree(tree.left));
      tree.left = new Node("^", copyTree(tree.left.left), copyTree(tree.left));
      tree.right = normalize(tree.right);
      tree.left = normalize(tree.left);
    }

    else if(tree.symbol.equals("^") && tree.right.symbol.equals("v")){
      tree.symbol = "v";
      tree.right = new Node("^", copyTree(tree.left), copyTree(tree.right.left));
      tree.left = new Node("^", copyTree(tree.left), copyTree(tree.right.right));
      tree.right = normalize(tree.right);
      tree.left = normalize(tree.left);
    }
    return tree;
  }

  //helper method for normalize that insers ! between current node and its children
  private Node bangInsert(Node tree){
    tree.right = new Node("!", null, tree.right);
    tree.left = new Node("!", null, tree.left);
    return tree;
  }

  //Copies expression and displays the normalized tree
  public void displayNormalized() throws ParseError{
    Expression normalized = this.copy();
    normalized.normalize();
    TreeDisplay display = new TreeDisplay(this.input + " Normalized");
    display.setRoot(normalized.root);
  }

  //Returns a string of the original expression
  public String toString(){
    return this.input + " is " + evaluate();
  }

  //builds the tree from a string expression
  private Node createTree(String line) throws ParseError{
    line = trimParens(line);
    if(isAtom(line) && line.length() == 1){
      return new Node(line);
    }

    if(isBang(line)){
      return new Node('!', null, createTree(line.substring(1, line.length())));
    }

    if(line.length() < 3){
      throw new ParseError("Bad Expression: " + line);
    }

    if(isAtom(line.charAt(0)) && isOpp(line.charAt(1)) && isAtom(line.charAt(2))){
      return new Node(line.charAt(1), new Node(line.charAt(0)), new Node(line.charAt(2)));
    }
    return new Node(opp(line), createTree(leftTree(line)), createTree(rightTree(line)));
  }

  //create tree helper method for grabbing left half of the expression string
  private String leftTree(String line) throws ParseError{
    if(isAtom(line)){
      return Character.toString(line.charAt(0));
    }

    int parens = 0;
    for(int i = 0; i < line.length(); i++){
      if(line.charAt(i) == '('){
        parens++;
      }
      if(line.charAt(i) == ')'){
        parens--;
      }
      if(parens == 0){
        return line.substring(0, i+1);
      }
    }
    throw new ParseError("Bad Expression: " + line);
  }

  //create tree helper method for grabbing right half of the expression string
  private String rightTree(String line) throws ParseError{
    if(isAtom(line.charAt(line.length()-1))){
      return Character.toString(line.charAt(line.length()-1));
    }

    int parens = 0;
    for(int i = line.length()-1; i > -1; i--){
      if(line.charAt(i) == '('){
        parens++;
      }
      if(line.charAt(i) == ')'){
        parens--;
      }
      if(parens == 0){
        return line.substring(i, line.length());
      }
    }
    throw new ParseError("Bad Expression: " + line);
  }

  //create tree helper that gets the opp seperating the two halves of the expression
  private String opp(String line) throws ParseError{
    if(isAtom(line.charAt(0)) && isOpp(line.charAt(1))){
      return Character.toString(line.charAt(1));
    }

    int parens = 0;
    for(int i = 0; i < line.length(); i++){
      if(line.charAt(i) == '('){
        parens++;
      }
      if(line.charAt(i) == ')'){
        parens--;
      }
      if(parens == 0){
        if(isOpp(line.charAt(i+1))){
          return Character.toString(line.charAt(i+1));
        }
      }
    }
    throw new ParseError("Bad Expression: " + line);
  }

  //removes outside parens and checks if parens are valid
  private String trimParens(String line) throws ParseError{

    validParens(line);

    if(line.charAt(0) == '(' && line.charAt(line.length()-1) == ')'){
      return line.substring(1, line.length()-1);
    }

    return line;
  }

  //checks if parens are valid
  private void validParens(String line) throws ParseError{
    int parens = 0;

    for(int i = 0; i < line.length(); i++){
      if(line.charAt(i) == '('){
        parens++;
      }
      if(line.charAt(i) == ')'){
        parens--;
      }
      if(parens < 0){
        throw new ParseError("Missmatched Parenthesis: " + line);
      }
    }
  }

  private boolean isAtom(String line){
    if(line.length() < 1){
      return false;
    }
    return Character.isLetter(line.charAt(0));
  }

  private boolean isAtom(char atom){
    return isAtom(Character.toString(atom));
  }

  private boolean isOpp(String line){
    if(line.length() < 1){
      return false;
    }
    if(line.charAt(0) == '^' || line.charAt(0) == 'v'){
      return true;
    }
    return false;
  }

  private boolean isOpp(char opp){
    return isOpp(Character.toString(opp));
  }

  private boolean isBang(String line){
    if(line.charAt(0) == '!'){
      return true;
    }
    return false;
  }

  private boolean isBang(char bang){
    return isBang(Character.toString(bang));
  }
}
