package Algorithms.Graphs;

import java.util.*;
public class MinimumCostToConnectPoints {
    public int minCostConnectPoints(int[][] points) {
        List<Point> plist=new ArrayList<>();

        for(int[] p:points)
            plist.add(new Point(p[0], p[1]));

        List<Edge> edges=new ArrayList<>();
        for(int i=0;i<plist.size();i++)
        {
            for(int j=i+1;j<plist.size();j++)
            {
                edges.add(new Edge(plist.get(i), plist.get(j)));
            }
        }

        edges.sort(
            Comparator.comparingInt((Edge e)->cost(e))
        );

        DSU dsu=new DSU(plist);

        int mstcost=0;
        
        for(int i=0;i<edges.size();i++)
        {
            Edge e=edges.get(i);
            Point u=e.p;
            Point v=e.q;
            Point pu=dsu.find(u);
            Point pv=dsu.find(v);
            if(pu.equals(pv))
                continue;
            
            dsu.union(u, v);
            mstcost+=cost(e);
        }
        return mstcost;
    }

    static class DSU
    {
        Map<Point, Point> par;
        Map<Point, Integer> rank;
        public DSU(List<Point> plist)
        {
            par=new HashMap<>();
            rank=new HashMap<>();
            for(int i=0;i<plist.size();i++)
            {
                par.put(plist.get(i), plist.get(i));
                rank.put(plist.get(i), 0);
            }

        }

        public void union(Point a, Point b)
        {
            Point parA=find(a);
            Point parB=find(b);

            if(parA.equals(parB))
                return;

            if(rank.get(parA).equals(rank.get(parB)))
            {
                par.put(parB, parA);
                rank.put(parA, rank.getOrDefault(parA, 0)+1);
            }
            else if(rank.get(parA) > rank.get(parB))
            {
                par.put(parB, parA);
            }
            else
            {
                par.put(parA, parB);
            }
        }

        public Point find(Point p)
        {
            if(par.get(p).equals(p))
                return p;

            Point parent=find(par.get(p));
            par.put(p, parent);
            return parent;
        }
    }
    
    static int cost(Edge e){return Math.abs(e.p.x - e.q.x) + Math.abs(e.p.y - e.q.y);}
    static record Point(int x, int y){}
    static record Edge(Point p, Point q){}
}