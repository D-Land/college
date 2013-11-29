import java.util.Arrays;

public class CircularSuffixArray {
  private Suffix[] suffix;
  private int suffixLength;

  public CircularSuffixArray(String s) {
    this.suffixLength = text.length();
    this.suffixes = new Suffix[suffixLength];

    for (int i = 0; i < N; i++){
      suffix[i] = new Suffix(s, i);
    }

    Arrays.sort(suffixes);
  }

  private static class Suffix implements Comparable<Suffix>{
    private String value;
    private int index;

    private Suffix(String s, int i){
      this.value = s;
      this.index = i;
    }

    private int length(){
      return value.length() - index;
    }
    private char charAt(int i){
      return value.charAt(index + i);
    }

    public int compareTo(Suffix that) {
      if (this == that){
        return 0;
      }

      int N = Math.min(this.length(), that.length());

      for (int i = 0; i < N; i++) {
          if (this.charAt(i) < that.charAt(i)){
            return -1;
          }

          if (this.charAt(i) > that.charAt(i)){
            return +1;
          }
      }

      return this.length() - that.length();
    }
  }

  public int length(){
    return suffixes.length;
  }

  public int index(int i){
    return suffixes[i].index;
  }
}
