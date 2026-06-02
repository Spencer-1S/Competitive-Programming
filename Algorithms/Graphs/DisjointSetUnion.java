package Algorithms.Graphs;

import java.util.Arrays;

public class DisjointSetUnion {
    int[] par;
    int[] rank;

    DisjointSetUnion(int n) // n=number of elements we plan to store in DSU
    {
        par=new int[n];
        rank=new int[n];
        for(int i=0;i<n;i++)
        {
            par[i]=i;
            rank[i]=0; // redundant in java
        }
    }

    public void union(int a, int b)
    {
        int parentA=find(a);
        int parentB=find(b);

        if(parentA==parentB)
            return;

        if(rank[parentA]==rank[parentB])
        {
            par[parentB]=parentA;
            rank[parentA]++;
        }
        else if(rank[parentA]>rank[parentB])
        {
            par[parentB]=parentA;
        }
        else // rank[parentB]>rank[parentA]
        { 
            par[parentA]=parentB;
        }

    }

    public int find(int x)
    {
        if(x==par[x])
            return x;
        return par[x]=find(par[x]); // path compression
    }

    // helper (not required)
    public void display()
    {
        System.out.println(Arrays.toString(par));
        System.out.println(Arrays.toString(rank));
    }
}
