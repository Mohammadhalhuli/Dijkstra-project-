package src.main.java.com.emad.dijkstra;



//بشتغل 7 خطوات 
/*****
    1-بعمل 2 اري 
    1 مزيورة وغير مزيورة 
    اول ما تبلش البرنامج 
    --------------------------------
    2-بشوف الديتا من خلال الجيسنت 
    4*4  =save spase توفير مساحة  بدل من استخدام لكراف 
    بكون فيها راندوم داتا 
    -------------------------------------
    3-size =100 (0--99)--100 مدنده 3اجيزت 3 طرق لكل نود
    -----------------------------------------------
    3-بعد الحفظ بخزن في المكان الي ما زرناه 
    ------------------------------------
    4- الرسم 
    كل طريق لها رقم 
    بنعبي في الخالي كل اجيسنت 
--------------------------
5-استخراج نتيجة 


 */

// A Java program for Dijkstra's
// single source shortest path
// algorithm. The program is for
// adjacency matrix representation
// of the graph.

import java.util.ArrayList;

class DijkstrasAlgorithm {
    // enable to see Dijkstras shortest path tree
    public static Boolean print_results = false;
    private static final int NO_PARENT = -1;

    // Function that implements Dijkstra's
    // single source shortest path
    // algorithm for a graph represented
    // using adjacency matrix
    // representation
    public static int[] shortestDistances;
    public static ArrayList<ArrayList<Integer>> paths;

    public static void dijkstra(int[][] adjacencyMatrix,
                                 int startVertex)
    {
        int nVertices = adjacencyMatrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        shortestDistances = new int[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
             vertexIndex++)
        {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++)
        {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++)
            {
                if (!added[vertexIndex] &&
                        shortestDistances[vertexIndex] <
                                shortestDistance)
                {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++)
            {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex]))
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;
                }
            }
        }

        printSolution(startVertex, shortestDistances, parents);
    }

    // A utility function to print
    // the constructed distances
    // array and shortest paths

    private static void printSolution(int startVertex, int[] distances, int[] parents)
    {
        paths = new ArrayList<>();
        int nVertices = distances.length;
        if(print_results)
        System.out.print("Vertex\t Distance\tPath");

        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
        {
            if (vertexIndex != startVertex)
            {
                if(print_results) {
                    System.out.print("\n" + startVertex + " -> " + vertexIndex);
                    System.out.print(" \t\t " + distances[vertexIndex] + "\t\t");
                }
                ArrayList<Integer> path = new ArrayList<>();
                int cv = vertexIndex;
                while(cv != NO_PARENT){
                    path.add(0, cv);
                    cv = parents[cv];
                }
                if(print_results)
                for (int i : path) {
                    System.out.print(i + " ");
                }
                //System.out.println();
                paths.add(path);
                //printPath(vertexIndex, parents);
            }
        }
    }

    // Function to print shortest path
    // from source to currentVertex
    // using parents array
    public static void getPath(){

    }
    private static void printPath(int currentVertex,
                                  int[] parents)
    {

        // Base case : Source node has
        // been processed
        //if (currentVertex == NO_PARENT)
        //{
        //    return;
        //}
        int cv = currentVertex;
        while(cv != NO_PARENT){
            System.out.print(cv + " ");
            cv = parents[cv];
        }

        //printPath(parents[currentVertex], parents);
        //System.out.print(currentVertex + " ");
    }

    // Driver Code
    public static void main()
    {
        // y = index of first source, x = index of distance
        int[][] adjacencyMatrix = {{0, 7, 1 ,0 },
                {7, 0, 0 , 1},
                {1, 0, 0 , 10},
                {0, 1, 10 , 0}};
        dijkstra(adjacencyMatrix, 0);
    }
}

// This code is contributed by Harikrishnan Rajan
