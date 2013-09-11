//CS1501 ASSIGNMENT 1
//Andrew Land

import java.util.*;
import java.io.*;

public class assignment1 {

  public static void main(String [] args) throws java.io.IOException {
    Stopwatch timer = new Stopwatch();
    if(args.length != 2) {
      System.out.println("Usage: java assignment1 file1.txt file2.txt");
    }
    else{
      Hashtable<String, Integer> text1 = new Hashtable<String, Integer>();
      Hashtable<String, Integer> text2 = new Hashtable<String, Integer>();
      if(new File(args[0]).exists()){
        text1 = readText(args[0]);
      }
      if(new File(args[1]).exists()){
        text2 = readText(args[1]);
      }
      if(text1.size() > 0 && text2.size() > 0){
        doMath(text1, text2);
      }
      else {
        System.out.println("Distance: " + 0.0/0.0);
      }
    }
    System.out.println("Time Elapsed: " + timer.elapsedTime());
  }

  public static void doMath(Hashtable text1, Hashtable text2) {
    double text1Norm = 0;
    double text2Norm = 0;
    double innerProduct = 0;
    double distance = 0;
    Collection<Integer> text1Collection = text1.values();
    Collection<Integer> text2Collection = text2.values();
    Set<String> keys;
    Integer temp;

    for(Integer i: text1Collection) {
      text1Norm += i*i;
    }

    for(Integer i: text2Collection) {
      text2Norm += i*i;
    }

    text1Norm = Math.sqrt(text1Norm);
    text2Norm = Math.sqrt(text2Norm);

    keys = text1.keySet();
    for(String key: keys) {
      temp = (Integer)text2.get(key);

      if(temp != null){innerProduct += temp * (Integer)text1.get(key);}
    }
    
    distance = Math.acos(innerProduct / (text1Norm * text2Norm));
    System.out.println("Distance: " + distance);
  }

  public static Hashtable readText(String filename) throws java.io.IOException {
    Hashtable<String, Integer> dict = new Hashtable<String, Integer>(50000);
    BufferedReader text = new BufferedReader(new FileReader(filename));
    String line = "";
    String[] words;
    Integer count = 0;
    int lineCount = 0;
    int wordCount = 0;

    line = text.readLine();

    while(line != null) {
      words = line.toLowerCase().split("[\\W_]");
      for(String word : words) {
        if(word.equals("")){continue;}
        else{count = dict.get(word);}
        if(count == null){dict.put(word, 1); wordCount++;}
        else{dict.put(word, count+1); wordCount++;}
      }

      line = text.readLine();
      lineCount++;
    }
    System.out.println(filename + " has " + lineCount + " lines, "  +  wordCount + " words, and " + dict.size() + " unique words.");
    return dict;
  }
}
