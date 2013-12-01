/*
 * Andrew Land
 * Assignment 5
 * aml135
 * 3688776
 */

import java.util.Arrays;

public class CircularSuffixArray{
  private Suffix[] suffixes;

  public CircularSuffixArray(String s){
    this.suffixes = new Suffix[s.length()];
    for (int i = 0; i < s.length(); i++){
        suffixes[i] = new Suffix(s, i);
    }
    Arrays.sort(suffixes);
  }

  public int length(){
    return suffixes.length;
  }

  public int index(int i){
    return suffixes[i].index;
  }

  private static class Suffix implements Comparable<Suffix>{
    private String s;
    private int index;

    private Suffix(String s, int index){
      this.s = s;
      this.index = index;
    }

    private int length(){
      return s.length() - index;
    }

    private char charAt(int i){
      return s.charAt(index + i);
    }

    public String toString(){
      return s.substring(index);
    }

    public int compareTo(Suffix other){
      int length = Math.min(this.length(), other.length());

      if (this == other){
        return 0;
      }

      for (int i = 0; i < length; i++){
        if (this.charAt(i) < other.charAt(i)){
          return -1;
        }
        if (this.charAt(i) > other.charAt(i)){
          return 1;
        }
      }
      return this.length() - other.length();
    }
  }
}
