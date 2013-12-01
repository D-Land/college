/*
 * Andrew Land
 * Assignment 5
 * aml135
 * 3688776
 */

public class BurrowsWheeler{
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
    String s = BinaryStdIn.readString();
    CircularSuffixArray suffixes = new CircularSuffixArray(s);
    int index;
    int i;
    char out;

    for (i = 0; i < suffixes.length(); i++){
      if (suffixes.index(i) == 0){
        break;
      }
    }

    BinaryStdOut.write(i);

    for (i = 0; i < suffixes.length(); i++){
      index = (suffixes.index(i) + s.length() - 1) % s.length();
      out = s.charAt(index);
      BinaryStdOut.write(out);
    }

    BinaryStdOut.close();
  }

  public static void decode(){

  int number = BinaryStdIn.readInt();
  int c = 0;
  String s = BinaryStdIn.readString();
  int count[] = new int[256 + 1];
  int next[] = new int[s.length()];

  for (int i = 0; i < s.length(); i++){
    count[s.charAt(i) + 1]++;
  }

  for (int i = 1; i < 256 + 1; i++){
    count[i] += count[i - 1];
  }

  for (int i = 0; i < s.length(); i++){
    next[count[s.charAt(i)]++] = i;
  }

  for (int i = next[number]; c < s.length(); i = next[i], c++){
    BinaryStdOut.write(s.charAt(i));
  }

  BinaryStdOut.close();
  }
}
