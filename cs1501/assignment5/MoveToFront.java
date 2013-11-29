import java.util.ArrayList;

public class MoveToFront{
  public static void main(String[] args){
    if (args.length != 1){
      System.out.println("Error: Expected + or - as argument.");
      System.exit(1);
    }
    if (args[0].equals("+")){
      decode();
    }
    else if (args[0].equals("-")){
      encode();
    }
    else {
      System.out.println("Error: Expected + or - as argument.");
      System.exit(1);
    }
  }

  public static void encode(){
    ArrayList<Character> alphabet = alphabet();
    Character input;
    int index;

    while(!BinaryStdIn.isEmpty()){
      input = (Character)BinaryStdIn.readChar();
      index = (int)alphabet.indexOf(input);
      BinaryStdOut.write(index);

      alphabet.remove(index);
      alphabet.add(0, input);
    }
  }

  public static void decode(){
    ArrayList<Character> alphabet = alphabet();
    int index;
    char output;

    while(!BinaryStdIn.isEmpty()){
      index = BinaryStdIn.readChar();
      output = (char)alphabet.get(index);
      BinaryStdOut.write(output);

      alphabet.remove(index);
      alphabet.add(0, output);
    }
  }

  private static ArrayList<Character> alphabet(){
    ArrayList<Character> alphabet = new ArrayList<Character>(256);

    for(char i = 0; i < 256; i++){
      alphabet.add((Character)i);
    }
    return alphabet;
  }
}
