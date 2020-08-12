/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop_project;

/**
 *
 * @author LENOVO
 */
class ShortestPath
            {
                int i1;
                static int[][] mat;
                static final int V = 9;
                static int[][] graph = new int[][]{{0,7,0,0,0,0,0,0,0},{7,0,3,0,0,0,19,15,0},{0,3,0,16,0,0,0,0,9},{0,0,16,0,0,5,4,0,0},{0,0,0,0,0,3,0,0,0},{0,0,0,5,3,0,0,0,0},{0,19,0,4,0,0,0,0,0},{0,15,0,0,0,0,0,0,0},{0,0,9,0,0,0,0,0,0}};
                ShortestPath(int i1)
                {
                    this.i1 = i1;
                    mat = new int[9][9];
                }
                int minDistance(int dist[], Boolean sptSet[])
                {
                    int min = Integer.MAX_VALUE, min_index = -1;
                    for (int v = 0; v < V; v++)
                        if (sptSet[v] == false && dist[v] <= min) 
                        {
                            min = dist[v];
                            min_index = v;
                        }
                    return min_index;
                }
                    // A utility function to print the constructed distance array
                void copySolution(int dist[], int n,int src,int mat[][])
                {
                    System.arraycopy(dist, 0, mat[src], 0, V);
                }
                    // Funtion that implements Dijkstra's single source shortest path
                // algorithm for a graph represented using adjacency matrix
                // representation
                void dijkstra(int src)
                {
                    int dist[] = new int[V]; // The output array. dist[i] will hold
                // the shortest distance from src to i

                // sptSet[i] will true if vertex i is included in shortest
                // path tree or shortest distance from src to i is finalized
                    Boolean sptSet[] = new Boolean[V];

                // Initialize all distances as INFINITE and stpSet[] as false
                    for (int i = 0; i < V; i++) 
                        {
                            dist[i] = Integer.MAX_VALUE;
                            sptSet[i] = false;
                        }

                // Distance of source vertex from itself is always 0
                        dist[src] = 0;

                // Find shortest path for all vertices
                        for (int count = 0; count < V - 1; count++)
                        {
                // Pick the minimum distance vertex from the set of vertices
                // not yet processed. u is always equal to src in first
                // iteration.
                            int u = minDistance(dist, sptSet);

                // Mark the picked vertex as processed
                            sptSet[u] = true;

                // Update dist value of the adjacent vertices of the
                // picked vertex.
                            for (int v = 0; v < V; v++)

                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                                if (!sptSet[v] && graph[u][v] != 0 &&
                                        dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                                dist[v] = dist[u] + graph[u][v];
                        }
                        copySolution(dist, V,src,mat);
                }
                static int give_dist(int i1,int i2)
                    {
                    return mat[i1][i2];
                    }
                static int give_fare(int i1,int i2)
                    {
                        return mat[i1][i2]*10;
                    }
                static int give_time(int i1,int i2)
                    {
                        return mat[i1][i2]*1000;
                    }
            }
