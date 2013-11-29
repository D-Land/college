import java.util.Scanner;

public class hw4_aml136{

  static EWG graph;

  public static void main(String args[]){
    Scanner input;
    int option;

    if(args.length == 1){
      graph = new EWG(new In(args[0]));
    }
    else{
      System.out.println("Error, please retry: java hw4_aml136 {filename}.");
    }

    while(true){

      System.out.print("\n1. R (eport)"+
                       "\n2. M (inimum Spanning Tree)"+
                       "\n3. S (hortest Path from) i j"+
                       "\n4. D (own edge) i j"+
                       "\n5. U (p edge) i j"+
                       "\n6. C (hange weight of edge) i j x"+
                       "\n7. E (ulerian)"+
                       "\n8. Q (uit)"+
                       "\nEnter your choice: ");

      input = new Scanner(System.in);
      option = input.nextInt();
      System.out.println();

      if(option == 1){
        System.out.println(graph.toString());
        CC cc = new CC(graph);
        System.out.println(cc.toString());
      }

      else if(option == 2){
        KruskalMST mst = new KruskalMST(graph);
        for(Edge e : mst.edges()){
          StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
      }

      else if(option == 3){
        int v1;
        int v2;
        System.out.println("Enter from vertex: ");
        input = new Scanner(System.in);
        v1 = input.nextInt();

        System.out.println("Enter to vertex: ");
        input = new Scanner(System.in);
        v2 = input.nextInt();

        DijkstraSP dsp = new DijkstraSP(graph, v1);

        System.out.println("The shortest path from "+ v1 +" to "+ v2 +" is:");
        System.out.println(dsp.pathTo(v2));
      }

      else if(option == 4){
        int v1;
        int v2;
        boolean takenDown = false;

        System.out.println("Enter from vertex: ");
        input = new Scanner(System.in);
        v1 = input.nextInt();

        System.out.println("Enter to vertex: ");
        input = new Scanner(System.in);
        v2 = input.nextInt();

        takenDown = graph.down(v1, v2);

        if(!takenDown){
          System.out.println("Edge " + v1 + "~>" + v2 + " not found.");
        }
      }

      else if(option == 5){
        int v1;
        int v2;
        double w;

        System.out.println("Enter from vertex: ");
        input = new Scanner(System.in);
        v1 = input.nextInt();

        System.out.println("Enter to vertex: ");
        input = new Scanner(System.in);
        v2 = input.nextInt();

        System.out.println("Enter weight: ");
        input = new Scanner(System.in);
        w = input.nextDouble();

        graph.up(v1, v2, w);

        System.out.println("Insert edge " + v1 + "~>" + v2 + " (" + w + ") ");
      }

      else if(option == 6){
        int v1;
        int v2;
        double w;
        boolean change;

        System.out.println("Enter from vertex: ");
        input = new Scanner(System.in);
        v1 = input.nextInt();

        System.out.println("Enter to vertex: ");
        input = new Scanner(System.in);
        v2 = input.nextInt();

        System.out.println("Enter weight: ");
        input = new Scanner(System.in);
        w = input.nextDouble();

        change = graph.updateWeight(v1, v2, w);

        if(w < 0){
          System.out.println("Remove edge " + v1 + "~>" + v2);
        }
        else if(change){
          System.out.println("Change edge " + v1 + "~>" + v2 + " to " + w);
        }
        else{
          System.out.println("Add edge " + v1 + "~>" + v2 + " (" + w + ") ");
        }
      }

      else if(option == 7){
        int odd = 0;
        for(int i = 0; i < graph.V(); i++){
          if(graph.adjV(i).length % 2 != 0){
            odd++;
          }
        }
        if(odd != 2){
          System.out.println("No Eulerian path exists");
        }
        if(odd != 0){
          System.out.println("No Eulerian tour exists");
        }
      }

      else{
        System.exit(1);
      }
    }
  }
}
