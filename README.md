# Ex2_OOP_Graphs
> Made by Or cohen and Shlomi lantser

### Introduction
  This project is an assignment in an object-oriented course.
  
  This project consists of two parts:
   -  The first part is an implenentation of directed weighted graph.
   -  The second part is paint the graph with GUI.
   
  This project uses the capabilities of data structures, object-oriented and GUI.
  
  **This project implements hashmap data structures for data storage and data management most efficiently.**

# First part (Implementaion)

* We were required in the current project to implements interfaces.
 
The interfaces are:

| *Interfaces* | *Details* |
|---------------|---------------|
|DirectedWeightedGraph | represents a Directional Weighted Graph|
|DirectedWeightedGraphAlgorithm | represents a Directed (positive) Weighted Graph Theory Algorithms|
|EdgeData |represents the set of operations applicable on a directional edge(src,dest) in a (directional) weighted graph|
|NodeData |represents the set of operations applicable on a node (vertex) in a (directional) weighted graph|
|GeoLocation |represents a geo location <x,y,z>, (aka Point3D data)|






## Point3D class - implement GeoLocation
- This class is a simple class that represent location.

| *Methods* | *Details* |
| ---------------|--------------- |
|x(),y(),z() | return double variable|
|distance() | calculate distance from me to other point3D|

## Node class - implement NodeData
- This class is a simple class that represent a vertex on a directed weighted graph and implement a Set of simple operations.

- Each node contains few fields:
  - Location: An object that represent the location of the node by (x,y,z).
  - Weight: A variable that is help implement other methods for calculations.
  - Info : A variable that used to implement other methods.
  - Tag : A variable that used to implement other methods.
  - Key : A unique key that is used as each node's ID.
 
 | *Methods* | *Details* |
| ---------------|--------------- |
|getKey() \ setKey(int key) |Get or set key of the Node|
|getLocation() \ setLocation(GeoLocation p) |Get or set location of Node|
|getWeight() \ setWeight(double w) |Get or set weight of Node|
|getTag() \ setTag(int tag) |Get or set tag of Node|
|getInfo() \ setInfo(String s) |Get or set info of Node|

## Edges class - implement EdgeData
- This class implement a set of operations applicable on a directional edge(src --> dest) in a (directional) weighted graph.

- Each edge contains few fields:
  - src: A variable that represent the id of the source node of this edge.
  - dest: A variable that represent the id of the destination node of this edge.
  - w: A variable represent this edge weight (positive value).
  - info: A variable represent this edge remark (meta data).
  - tag: A variable represent temporal data.
 
 | *Methods* | *Details* |
| ---------------|--------------- |
|getSrc() |Get the id of the source node of this edge|
|getDest() |Get the id of the destination node of this edge|
|getWeight() |Get the weight of this edge (positive value)|
|getTag() \ setTag(int tag) |This method allows setting the "tag" value for temporal marking an edge - common practice for marking by algorithms|
|getInfo() \ setInfo(String s) |Get or set info of Node|

## DirectWeightGraph class - implement DirectedWeightedGraph
- This class implement an directional weighted graph (Support a large number of nodes).
- This implementation based on HashMap data structure.

- Each DirectWeightGraph contains few fields:
  - nodes: HashMap data structure that represent the groupd of nodes by their ID's
  - edges: HashMap of Hashmaps data structure that represent each node group of directed outgoing edges in this graph.
  - ingoing: HashMap data structure that represent each node group of directed ingoing edges in this graph.
  - node_size: A variable that stored the amount of nodes in this graph.
  - edge_size: A variable that stored the amount of edges in this graph.
  - MC: Mode count a variable that stored the amount of changes that happend on graph (e.g remove node,add node ,remove edge .. )

| *Methods* | *Details* | *Time Complexity*|
| ---------------|--------------- |-------------|
|getNode(int node_id|Returns the node_data by the node_id|O(1)|
|getEdge(int src,int dest)|Returns the data of the edge (src,dest), null if none|O(1)|
|addNode(NodeData n)|Adds a new node to the graph with the given node_data|O(1)|
|connect(int src,int dest,double w)|Connects an edge with weight w between node src to node dest|O(1)|
|nodeIter()|This method returns an Iterator for the collection representing all the nodes in the graph|O(V) ,|V| = vertexes size|
|edgeIter()|This method returns an Iterator for all the edges in this graph|O(E) ,|E| = edges size|
|edgeIter(int node_id)This method returns an Iterator for edges getting out of the given node (all the edges starting (source) at the given node)||O(k) , k = size of outgoing edges of given node|
|removeNode(int key)|Deletes the node (with the given ID) from the graph and removes all edges which starts or ends at this node.|O(k) , V.degree = k|
|removeEdge(int src,int dest)|Deletes the edge from the graph|O(1)|
|nodeSize()|Returns the number of vertices (nodes) in the graph|O(1)|
|edgeSize()|Returns the number of edges (assume directional graph)|O(1)|
|getMC()|Returns the Mode Count - for testing changes in the graph|O(1)|

#### Private methods
 | *Methods* | *Details* | *Time Complexity*|
| ---------------|--------------- |-------------|
|nodeOutEdges(int key)|Return true if this node have outgoing edges|O(1)
|nodeInEdges(int key|Return true if this node have ingoing edges|O(1)

## DirectWeightGraphAlgo class - implement DirectedWeightedGraphAlgorithm
- This class represents a directed (positive) weighted Graph and implement Theory Algorithms including:
 init,copy, isConnected, shortedPath , center , tsp and save&load with JSON file.

- This implementation based on HashMap data structure.

- Each DirectWeightGraph contains few fields:
  - dwg : DirectedWeightedGraph that represent a graph.
  - parents: HashMap data structure that represent each node and his parent

 | *Methods* | *Details* | *Time Complexity*|
| ---------------|--------------- |-------------|
|init(DirectedWeightedGraph g)|Inits the graph on which this set of algorithms operates on|O(1)|
|getGraph()|Returns the underlying graph of which this class works|O(1)|
|copy()|Computes a deep copy of this weighted graph|O(V+E) V - Size of vertices , E - Size of edges|
|isConnected()|Returns true if and only if (iff) there is a valid path from each node to each|O(V+E) V - Size of vertices , E - Size of edges|
|shortestPathDist(int src,int dest)|Computes the length of the shortest path between src to dest|O(V+E* Log(V)) V - Size of vertices , E - Size of edges|
|shortestPath(int src, int dest)|Computes the the shortest path between src to dest - as an ordered List of nodes|O(V+E* Log(V)) V - Size of vertices , E - Size of edges
|center()|Finds the NodeData which minimizes the max distance to all the other nodes|O(V^3) V - Size of vertices|
|tsp(List<NodeData> cities)|Computes a list of consecutive nodes which go over all the nodes in cities|
|save(String file)|Saves this weighted (directed) graph to the given file name - in JSON format|
|load(String file)|This method loads a graph to this graph algorithm|


