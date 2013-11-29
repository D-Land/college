
public class EWG extends EdgeWeightedGraph{

  public EWG(In file){
    super(file);
  }

  public void up(int i, int j, double w){
    addEdge(new Edge(i, j, w));
  }

  public boolean down(int i, int j){
    return removeEdge(new Edge(i, j, 1));
  }

  public boolean updateWeight(int i, int j, double w){
    Edge newEdge = new Edge(i, j, w);
    boolean changed = false;
    changed = removeEdge(newEdge);
    if(w >= 0){
      addEdge(newEdge);
    }

    return changed;
  }

  private boolean removeEdge(Edge removeThis){
    int v1 = removeThis.either();
    int v2 = removeThis.other(v1);
    boolean found = false;

    found = this.adj[v1].remove(removeThis);

    if(found){
      adj[v2].remove(new Edge(v2, v1, removeThis.weight()));
    }

    return found;
  }

  public int[] adjV(int i){
    int[][] adjv = adjV();
    return adjv[i];
  }

  private int[][] adjV(){
    int[][] adjv = new int[this.V][];
    int count = 0;

    for(int i = 0; i < this.V; i++){
      adjv[i] = new int[adj[i].size()];
      for(Edge e: adj[i]){
        if(e.either() != i){
         adjv[i][count] = e.either();
        }
        else{
          adjv[i][count] = e.other(e.either());
        }
        count++;
      }
      count = 0;
    }
    return adjv;
  }
}
