//CS1501 ASSIGNMENT 1
//Andrew Land

import java.util.*;
import java.io.*;

public class assignment1 {

  public static Stopwatch timer = new Stopwatch();

  public static void main(String [] args) throws java.io.IOException {
    Hashtable<String, Integer> text1 = readText(args[0]);
    Hashtable<String, Integer> text2 = readText(args[1]);
    doMath(text1, text2);
    System.out.println(timer.elapsedTime());
  }

  public static void doMath(Hashtable text1, Hashtable text2) {
    double text1Norm = 0;
    double text2Norm = 0;
    double innerProduct = 0;
    Collection<Integer> text1Collection = text1.values();
    Collection<Integer> text2Collection = text2.values();
    Set<String> keys;
    Integer temp;
    double distance = 0;

    for(Integer i: text1Collection) {
      text1Norm += i*i;
    }

    for(Integer i: text2Collection) {
      text2Norm += i*i;
    }

    text1Norm = Math.sqrt(text1Norm);
    text2Norm = Math.sqrt(text2Norm);

    if(text1.size() <= text2.size()) {
       keys = text1.keySet();
       for(String i: keys) {
         temp = (Integer)text2.get(i);
         if(i != null) {
           innerProduct += temp.intValue() * ((Integer)text1.get(i)).intValue();
         }
       }
    }
    else {
      keys = text2.keySet();
      for(String i: keys) {
        temp = (Integer)text1.get(i);
        if(temp != null) {
          innerProduct += temp.intValue() * ((Integer)text2.get(i)).intValue();
        }
      }
    }
    distance = Math.acos(innerProduct /(text1Norm*text2Norm));
    System.out.println("norm1: " + text1Norm);
    System.out.println("norm2: " + text2Norm);
    System.out.println("innerProduct: "+ innerProduct);
    System.out.println("distance: " + distance);
  }

  public static Hashtable readText(String filename) throws java.io.IOException {
    Hashtable<String, Integer> dict = new Hashtable<String, Integer>(50000);
    BufferedReader text = new BufferedReader(new FileReader(filename));
    StringBuilder currentLine;
    String line = "";
    String word = "";
    char temp;
    Integer count = 0;
    int start = -1;
    int lineCount = 0;
    int wordCount = 0;

    line = text.readLine().toLowerCase();
    currentLine = new StringBuilder(line);
    while(line != null) {
      start = -1;
      lineCount++;

      for(int i = 0; i < currentLine.length(); i++) {
        temp = currentLine.charAt(i);

        if(!(temp >= 65 && temp <= 90 || temp >= 97 && temp <= 122 || temp >= 48 && temp <= 57  || temp == 32)) {
          currentLine.replace(i, i+1, " ");
          temp = ' ';
        }

        if(temp == 32 && i == start + 1 || temp == 32 && i == start) {temp = 0; start = i;};
        if(temp == 32) {
          word = currentLine.substring(start + 1, i);
          if(word != " ") {
            count = dict.get(word);
            if(count == null) {
              dict.put(word, 1);
            }
            else {
              dict.put(word, count+1);
            }
            wordCount++;
          }
          start = i;
        }
      }

      if(currentLine.length() > 0) {
        if(start == -1){ start = 0;};
        word = currentLine.substring(start, currentLine.length());
        if(word != " ") {
          wordCount++;
          count = dict.get(word);
          if(count == null) {
            dict.put(word, 1);
          }
          else {
            dict.put(word, count+1);
          }
        }
      }

      line = text.readLine();
      if(line != null) {
        line = line.toLowerCase();
        currentLine = new StringBuilder(line);
      }
    }

    //Even though lines 48 and 65 prevent " " from being added it still gets added so im removing it manually if it gets added
    if (dict.remove(" ") != null){wordCount--;};

    System.out.println(filename + " has " + lineCount + " lines, "  +  wordCount + " words, and " + dict.size() + " unique words.");
    return dict;
  }
}
