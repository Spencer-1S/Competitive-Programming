import java.util.*;
import java.io.*;
public class Labyrinth {

    static int[][] dirvec=new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
    public static void main(String[] args)
    {
        FastReader fr=new FastReader();
        int n=fr.nextInt();
        int m=fr.nextInt();
        char[][] grid=new char[n][];    
        for(int i=0;i<n;i++)
            grid[i]=fr.next().toCharArray();
        TreeSet<String> path=new TreeSet<>(Comparator.comparingInt(x->x.length()));

    }

    public boolean dfs(int i, int j, char[][] grid, boolean[][] vis, TreeSet<String> path)
    {
        ArrayDeque<int[]> stack=new ArrayDeque<>();
        vis[i][j]=true;
        stack.push(new int[]{i,j});
        while(!stack.isEmpty())
        {
            int[] cur=stack.pop();
            for(int[] vector: dirvec)
            {
                
            }
        }
    }


    static class FastReader{
        BufferedReader br;
        StringTokenizer st;
        public FastReader(){br=new BufferedReader(new InputStreamReader(System.in));}
        String next(){
            while(st==null || !st.hasMoreTokens()){
                try{st=new StringTokenizer(br.readLine());}
                catch(IOException E){E.printStackTrace();}
            }
            return st.nextToken();
        }
        int nextInt() {return Integer.parseInt(next());}
        long nextLong() {return Long.parseLong(next());}
        float nextFloat() {return Float.parseFloat(next());}
        double nextDouble() {return Double.parseDouble(next());}

        String nextLine(){
            String s="";
            try{s=br.readLine().trim();}
            catch(IOException E){E.printStackTrace();}
            return s;
        }
        String[] readstringarray(int n){
            String[] res = new String[n];
            for(int i = 0; i<n; i++)res[i] = next();
            return res;
        }
        
        int [] readintarray(int n){
            int res [] = new int [n];
            for(int i=0;i<n;i++)res[i] = nextInt();
            return res;
        }
        
        long [] readlongarray(int n){
            long res [] = new long [n];
            for(int i=0;i<n;i++)res[i] = nextLong();
            return res;
        }
        
        float[] readfloatarray(int n){
            float res[] = new float[n];
            for(int i=0;i<n;i++)res[i] = (float)nextFloat();
            return res;
        }
        
        double[] readdoublearray(int n){
            double res[] = new double[n];
            for(int i=0;i<n;i++)res[i] = (double)nextDouble();
            return res;
        }
    }
}

