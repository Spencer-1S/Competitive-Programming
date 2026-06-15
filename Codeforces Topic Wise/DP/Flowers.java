import java.io.*;
import java.util.*;

public class Flowers
{
    static int MOD=(int)1e9+7;
    public static void main(String[] args)throws IOException
    {
        FastReader fr=new FastReader();
        int T=fr.nextInt();
        int k=fr.nextInt();
        int[] dp=new int[(int)1e5+1];
        // dp[i]= number of ways to eat i flowers
        // dp[i]=dp[i-1]+d[i-k]

        dp[0]=1;
        for(int i=1;i<dp.length;i++)
            dp[i]=(dp[i-1]+((i-k>=0)?dp[i-k]:0))%MOD;


        int[] prefix=new int[dp.length];
        prefix[0]=dp[0];
        for(int i=1;i<prefix.length;i++)
            prefix[i]=(prefix[i-1]+dp[i])%MOD;
        test: while (T-->0)
        {

            int r=fr.nextInt();
            int w=fr.nextInt();
            System.out.println((prefix[w]-prefix[r-1]+MOD)%MOD);
        }
    }

    public static long mergeSortCountInv(int[] b, int l, int r)
    {
        if(l>=r)return 0;
        int mid=(l+r)/2;
        long linv=mergeSortCountInv(b, l, mid);
        long rinv=mergeSortCountInv(b, mid+1, r);
        long curinv=merge(b, l, mid, r);
        return linv+rinv+curinv;
    }
    
    public static long merge(int[] b, int l, int mid, int r)
    {
        List<Integer> temp=new ArrayList<>(r-l+1);
        int i=l, j=mid+1;
        long inv=0;
        while(i<=mid && j<=r)
        {
            if(b[i]<=b[j])
                temp.add(b[i++]);
            else
            {
                inv+=mid-i+1;
                temp.add(b[j++]);
            }
        }
        while(i<=mid)
            temp.add(b[i++]);
        while(j<=r)
            temp.add(b[j++]);
        for(int x=l;x<=r;x++)
            b[x]=temp.get(x-l);
        return inv;
    }

    static List<Integer> kmp(String s, String pattern) // returns list of starting index of all matches
    {
        int[] LPS=new int[pattern.length()];
        LPS[0]=0;
        int len=0, i=1;
        while(i<pattern.length())
        {
            if(pattern.charAt(i)==pattern.charAt(len))
            {
                len++;
                LPS[i]=len;
                i++;
            }
            else
            {
                if(len!=0)
                    len=LPS[len-1];
                else
                {
                    LPS[i]=0;
                    i++;
                }
            }
        }
        List<Integer> found=new ArrayList<>();
        int j=0; i=0;
        while(i<s.length())
        {
            if(s.charAt(i)==pattern.charAt(j))
            {
                i++; j++;
            }

            if(j==pattern.length())
            {
                found.add(i-j); // 0 based indexing
                j=LPS[j-1];
            }
            else if(i<s.length() && s.charAt(i)!=pattern.charAt(j))
            {
                if(j==0)
                    i++;
                else
                    j=LPS[j-1];
            }
        }
        return found;
    }

    static void print(long[] a)
    {
        StringBuilder res=new StringBuilder();
        for(var x:a)
            res.append(x).append(" ");
        System.out.println(res.toString());
    }

    static void printList(List<?> list)
    {
        StringBuilder res=new StringBuilder();
        for(var x:list) res.append(x).append(" ");
        System.out.println(res.toString());
    }
    
    static void print(int[] a)
    {
        StringBuilder res=new StringBuilder();
        for(var x:a)
            res.append(x).append(" ");
        System.out.println(res.toString());
    }

    static int count(int[] a, int val)
    {
        int cnt=0;
        for(var e:a)
            cnt+=(e==val)?1:0;
        return cnt;
    }
    static int count(String s, String val) // uses KMP
    {
        return kmp(s, val).size();
    }

    static boolean isSorted(long[] s)
    {
        for(int i=1;i<s.length;i++)
        {
            if(!(s[i]>=s[i-1]))
                return false;
        }
        return true;
    }

    static long maximumSubArraySumNonEmpty(long[] arr)
    {
        long best=arr[0];
        long cur=arr[0];
        for(int i=1;i<arr.length;i++)
        {
            cur=Math.max(arr[i], arr[i]+cur);
            best=Math.max(cur, best);
        }
        return best;
    }

    // O(n!)
    static void generatePermutations(String n, StringBuilder p, List<Integer> per, boolean vis[])
    {
        if(p.length()==n.length())
        {
            per.add(Integer.valueOf(p.toString()));
            return;
        }

        for(int i=0;i<n.length();i++)
        {
            if(!vis[i])
            {
                p.append(n.charAt(i));
                vis[i]=true;
                generatePermutations(n, p, per, vis);
                p.deleteCharAt(p.length()-1);
                vis[i]=false;
            }
        }
    }

    static class MultiSet<E> implements Iterable<E>
    {
        private final TreeMap<E, Integer> map;
        private int size=0;
        public MultiSet(){ map=new TreeMap<>(); size=0; }
        public MultiSet(Comparator<E> c){ map=new TreeMap<>(c);}

        public void insert(E ele){ map.put(ele, map.getOrDefault(ele, 0)+1); size++; }
        public boolean erase(E ele)
        {
            Integer count=map.get(ele);
            if(count==null)
                return false;
            if(count==1)
                map.remove(ele);
            else
                map.put(ele, map.get(ele)-1);
            size--;
            return true;
        }

        public boolean eraseAll(E ele)
        {
            Integer count=map.remove(ele);
            if(count==null)
                return false;
            size-=count;
            return true;
        }

        public int count(E ele){ return map.getOrDefault(ele, 0); }

        public boolean contains(E ele){ return map.containsKey(ele); }

        public E first(){ return map.firstKey(); }
        public E last(){ return map.lastKey(); }
        public E lowerBound(E key){ return map.ceilingKey(key); }
        public E upperBound(E key){ return map.higherKey(key); }

        public int size(){ return size; }
        public int uniqueSize(){ return map.size(); }
        public boolean isEmpty(){ return map.isEmpty(); }
        public void clear(){ map.clear(); size=0; }

        @Override
        public Iterator<E> iterator()
        {
            return new Iterator<E>(){
                private final Iterator<Map.Entry<E,Integer>> itr = map.entrySet().iterator();
                private E currEle=null;
                private int remCount=0;

                @Override
                public boolean hasNext(){ return remCount>0 || itr.hasNext(); }

                @Override
                public E next()
                {
                    if(remCount==0)
                    {
                        var ent=itr.next();
                        currEle=ent.getKey();
                        remCount=ent.getValue();
                    }
                    remCount--;
                    return currEle;
                }
            };
        }

        @Override
        public String toString()
        {
            StringBuilder sb=new StringBuilder();
            for(E ele:this)
                sb.append(ele).append(" ");
            if(sb.length()>1)
                sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        }
    }

    // O(V+E)
    static int dfs(int vertex, List<List<Integer>> adjList, boolean[] visited, int cnt)
    {
        // take action on vertex after entering the vertex
        visited[vertex]=true;
        for(var child : adjList.get(vertex))
        {
            if(visited[child])
                continue;
            //take action on child before entering the child
            dfs(child, adjList, visited, cnt+1);
            //take action on child after exiting the child
        }
        return cnt;
        // take action on vertex after returning from the vertex
    }

    static ArrayList<Long> distinctPrimeFactors(long x){
        ArrayList<Long> res=new ArrayList<>();
        if(x<=1) return res;
        if((x&1)==0){
            res.add(2L);
            while ((x&1)==0) x>>=1;
        }
        for(int f=3;(long)f*f<=x;f+=2L){
            if(x%f==0){
                res.add((long)f);
                while(x%f==0) x/=f;
            }
        }
        if(x>1) res.add(x);
        return res;
    }

    static boolean isPalindrome(String s) {
        int i=0, j=s.length()-1;
        while(i<j){
            if(s.charAt(i++) != s.charAt(j--)) return false;
        }
        return true;
    }

    static long maximumSubArraySum(long[] arr,int l,int r)
    {
        long maxSum=0;
        long currSum=0;
        for(int i=l;i<=r;i++)
        {
            currSum+=arr[i];
            if(currSum<0)
                currSum=0;
            maxSum=Math.max(maxSum, currSum);
        }
        return maxSum;
    }

    static boolean isPrime(int n){
        if(n<=1)
            return false;
        if(n<=3)
            return true;
        if(n%2==0 || n%3==0)
            return false;
        for(int i=5;i*i<=n;i+=6){
            if(n%i==0 || n%(i+2)==0)
                return false;
        }
        return true;
    }

    //returns index of first element that is >= target
    public static int lowerBound(long[] arr, long target)
    {
        int low=0, high=arr.length;
        while(low<high)
        {
            int mid=(low+high)/2;
            if(arr[mid]<target)
                low=mid+1;
            else
                high=mid;
        }
        return low;
    }

    //return index of first element > target
    public static int upperBound(long[] arr, long target)
    {
        int low=0, high=arr.length;
        while (low<high)
        {
            int mid=(low+high)/2;
            if (arr[mid]<=target)
                low=mid+1;
            else
                high=mid;
        }
        return low;
    }

    static void primeFactors(Map<Integer,Integer> map, int n)
    {
        for(int i=2;i*i<n;i++){
            if(n%i==0){
                while(n%i==0){
                    map.put(i, map.getOrDefault(i, 0)+1);
                    n=n/i;
                }
            }
        }
        if(n!=1)map.put(n, map.getOrDefault(n, 0)+1);
    }

    static long sumN(long n){return ((long)n*(n+1))/2L;}

    static Pair<Integer,Integer> fsMax(int[] x)
    {
        int fm=Integer.MIN_VALUE;
        int sm=Integer.MIN_VALUE;
        for (int num : x) {
            if (num >= fm) {
                sm = fm;
                fm = num;
            } else if (num > sm) {
                sm = num;
            }
        }
        return Pair.create(fm, sm);
    }

    static Pair<Integer,Integer> fsMin(int[] x)
    {
        int fm=Integer.MAX_VALUE;
        int sm=Integer.MAX_VALUE;
        for (int num : x) {
            if (num <= fm) {
                sm = fm;
                fm = num;
            } else if (num < sm) {
                sm = num;
            }
        }
        return Pair.create(fm, sm);
    }

    static int max(int[] a)
    {
        int max=Integer.MIN_VALUE;
        for(int x:a)
            max=Math.max(x, max);
        return max;
    }

    static int min(int[] a)
    {
        int min=Integer.MAX_VALUE;
        for(int x:a)
            min=Math.min(x, min);
        return min;
    }

    static long ceilDivision(long a,long b){return (a+b-1)/b;}

    static long gcd(long a,long b){
        if(b==0)return a;
        return gcd(b,a%b);
    }
    
    static long lcm(long a, long b){return (a / gcd(a, b)) * b;}

    static int[] reverseArray(int[] arrayclone){
        for(int i=0,j=arrayclone.length-1;i<=j;i++,j--){
            int temp=arrayclone[i];
            arrayclone[i]=arrayclone[j];
            arrayclone[j]=temp;
        }
        return arrayclone;
    }

    static int lenghtOfLongestRepetition(String s){
        int cnt=1;
        int max=1;
        for(int i=1;i<s.length();i++){
            if(s.charAt(i)==s.charAt(i-1)) cnt++;
            else cnt=1;
            max=Math.max(max, cnt);
        }
        return max;
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

    //lexicographical list comparision (ascending)
    static class ListComparator<T extends Comparable<T>> implements Comparator<List<T>>
    {
        @Override
        public int compare(List<T> o1, List<T> o2) {
            int min=Math.min(o1.size(), o2.size());
            for(int i=0;i<min;i++)
            {
                int res=o1.get(i).compareTo(o2.get(i));
                if(res!=0)
                    return res;
            }
            return Integer.compare(o1.size(), o2.size()); 
        }
    }

    static class Pair<F,S>{
        F first; // can be accessed directly from the object reference since it is not private
        S second; // can be accessed directly from the object reference since it is not private
        // example p1.first or p2.second
        // we can also do p1.getFirst() or p1.getSecond()

        Pair(F f, S s){first = f;second = s;}
        // call directly by class name since it is static: Pair.create(1,2);
        static <F, S> Pair<F, S> create(F f, S s){return new Pair<>(f, s);}
        F getFirst() {return first;}
        S getSecond() {return second;}
        void setFirst(F f) {first = f;}
        void setSecond(S s) {second = s;}
        void setPair(F f, S s){first = f;second = s;}

        @Override
        public boolean equals(Object o){
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
        }
        @Override
        public int hashCode(){return Objects.hash(first, second);}
        @Override
        public String toString(){return "Pair{" + "first=" + first + ", second=" + second + '}';}
    }
    static class PairComparator<F extends Comparable<F>,S extends Comparable<S>> implements Comparator<Pair<F,S>>
    {
        @Override
        public int compare(Pair<F, S> o1, Pair<F, S> o2) {
            int cmp = o1.first.compareTo(o2.first);
            if(cmp != 0) return cmp;
            return o1.second.compareTo(o2.second);
        }
    }
}