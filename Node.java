import api.GeoLocation;
import api.NodeData;

public class Node implements NodeData ,Comparable<Node>{
    private int key=0;
    private static int id=0;
    private GeoLocation location;   private int d_time =0;
    private double weight;      private int f_time = 0;
    private String info;
    private int tag;
//    private final String white = "white" , gray = "gray", black = "black";


    public Node() {
        this.key = id++;// id
        this.tag = 0 ;
        this.d_time = 0;
        this.f_time = 0;
    }
    public Node(int key){
        this.key = key; // id
        this.tag = 0 ;
        this.d_time = 0;
        this.f_time = 0;
    }
    public Node(GeoLocation location ,int tag) {
        this.key = id++; // id
        this.location = location; // pos
        this.info = "key :"+ key+"\nlocation :" + location; //key + location
        this.tag = tag; // mark by value
        this.d_time = 0;
        this.f_time = 0;
    }


    public int getD_time() {
        return d_time;
    }

    public void setD_time(int d_time) {
        this.d_time = d_time;
    }

    public int getF_time() {
        return f_time;
    }

    public void setF_time(int f_time) {
        this.f_time = f_time;
    }
    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location = p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight=w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }

    public String toString(){
        return String.valueOf(this.getKey());
    }


    // ---------For Junit testing---------

    public boolean equals(Object d){
        if ( d==null ) return false;
        if ( d.getClass() != this.getClass() ) return false;
        NodeData temp = (NodeData) d;
        return ( this.info.equals(temp.getInfo()) && temp.getKey() == this.getKey() && this.location.equals(temp.getLocation()) && this.tag == temp.getTag() );
    }


    @Override
    public int compareTo(Node o) {
        if(o==null) return 1;
        if(this.weight > o.getWeight()) return 1;
        else if(this.getWeight() == o.getWeight()) return 0;
        return -1;
    }
}
