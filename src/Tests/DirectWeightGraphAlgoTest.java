package Tests;

import api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.NodeSetData;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectWeightGraphAlgoTest {
    DirectWeightGraph dwg = new DirectWeightGraph();
    DirectWeightGraphAlgo dwga = new DirectWeightGraphAlgo();
    DirectWeightGraph dwg1 = new DirectWeightGraph();
    DirectWeightGraphAlgo dwga1 = new DirectWeightGraphAlgo();
    DirectWeightGraph dwg2 = new DirectWeightGraph();
    DirectWeightGraphAlgo dwga2 = new DirectWeightGraphAlgo();
    DirectWeightGraph dwg3 = new DirectWeightGraph();
    DirectWeightGraphAlgo dwga3 = new DirectWeightGraphAlgo();

    @BeforeEach
    void setUp() {
        dwga.init(dwg);
        dwga.load("G1.json");
        dwg = (DirectWeightGraph) dwga.getGraph();

        dwga1.init(dwg1);
        dwga1.load("1000Nodes (1).json");
        dwg1 = (DirectWeightGraph) dwga1.getGraph();

        dwga2.init(dwg2);
        dwga2.load("G2.json");
        dwg2 = (DirectWeightGraph) dwga2.getGraph();

        dwga3.init(dwg2);
        dwga3.load("G3.json");
        dwg3 = (DirectWeightGraph) dwga3.getGraph();
    }

    @Test
    void init() {
        DirectedWeightedGraphAlgorithms g1Check = new DirectWeightGraphAlgo();
        DirectedWeightedGraph g1 = dwga.copy();
        g1Check.init(g1);
        Iterator<NodeData> g1Iter = g1.nodeIter();
        while (g1Iter.hasNext()){
            NodeData tmp = g1Iter.next();
            assertEquals(tmp , dwga.getGraph().getNode(tmp.getKey()));
        }

        DirectedWeightedGraphAlgorithms g1000Check = new DirectWeightGraphAlgo();
        DirectedWeightedGraph g1000 = dwga1.copy();
        g1Check.init(g1000);
        Iterator<NodeData> g1000Iter = g1000.nodeIter();
        while (g1000Iter.hasNext()){
            NodeData tmp1000 = g1000Iter.next();
            assertEquals(tmp1000 , dwga1.getGraph().getNode(tmp1000.getKey()));
        }

        DirectedWeightedGraphAlgorithms g2Check = new DirectWeightGraphAlgo();
        DirectedWeightedGraph g2 = dwga2.copy();
        g1Check.init(g2);
        Iterator<NodeData> g2Iter = g2.nodeIter();
        while (g2Iter.hasNext()){
            NodeData tmp2 = g2Iter.next();
            assertEquals(tmp2 , dwga2.getGraph().getNode(tmp2.getKey()));
        }

        DirectedWeightedGraphAlgorithms g3Check = new DirectWeightGraphAlgo();
        DirectedWeightedGraph g3 = dwga3.copy();
        g1Check.init(g3);
        Iterator<NodeData> g3Iter = g3.nodeIter();
        while (g3Iter.hasNext()){
            NodeData tmp3 = g3Iter.next();
            assertEquals(tmp3 , dwga3.getGraph().getNode(tmp3.getKey()));
        }
    }

    @Test
    void getGraph() {
        DirectedWeightedGraph g1Check = dwga.getGraph();
        assertEquals(g1Check , dwga.getGraph());
        DirectedWeightedGraph g2Check = dwga2.getGraph();
        assertEquals(g2Check , dwga2.getGraph());
        DirectedWeightedGraph g3Check = dwga3.getGraph();
        assertEquals(g3Check , dwga3.getGraph());
        DirectedWeightedGraph g1000Nodes = dwga1.getGraph();
        assertEquals(g1000Nodes , dwga1.getGraph());
    }

    @Test
    void copy() {
        DirectedWeightedGraph g1Copy = dwga.copy();
        Iterator<NodeData> g1Iter = dwga.getGraph().nodeIter();
        while (g1Iter.hasNext()){
            NodeData tmp = g1Iter.next();
            assertEquals(tmp , g1Copy.getNode(tmp.getKey()));
        }
        DirectedWeightedGraph g1000Copy = dwga1.copy();
        Iterator<NodeData> g1000Iter = dwga1.getGraph().nodeIter();
        while (g1000Iter.hasNext()){
            NodeData tmp1000 = g1000Iter.next();
            assertEquals(tmp1000 , g1000Copy.getNode(tmp1000.getKey()));
        }
        DirectedWeightedGraph g2Copy = dwga2.copy();
        Iterator<NodeData> g2Iter = dwga2.getGraph().nodeIter();
        while (g2Iter.hasNext()){
            NodeData tmp2 = g2Iter.next();
            assertEquals(tmp2 , g2Copy.getNode(tmp2.getKey()));
        }
        DirectedWeightedGraph g3Copy = dwga3.copy();
        Iterator<NodeData> g3Iter = dwga3.getGraph().nodeIter();
        while (g3Iter.hasNext()){
            NodeData tmp3 = g3Iter.next();
            assertEquals(tmp3 , g3Copy.getNode(tmp3.getKey()));
        }
    }

    @Test
    void isConnected() {
        DirectWeightGraph notConnected = new DirectWeightGraph();
        DirectWeightGraphAlgo notConnectedAlgo = new DirectWeightGraphAlgo();
        notConnectedAlgo.init(notConnected);
        notConnectedAlgo.getGraph().addNode(new Node(0));
        notConnectedAlgo.getGraph().addNode(new Node(1));
        notConnectedAlgo.getGraph().addNode(new Node(2));
        assertFalse(notConnectedAlgo.isConnected());
        assertTrue(dwga.isConnected());
        assertTrue(dwga2.isConnected());
        assertTrue(dwga3.isConnected());
        assertTrue(dwga1.isConnected());
    }

    @Test
    void shortestPathDist() {
        double g1Dist = dwga.shortestPathDist(0,8);
        assertEquals(7.436808665895908 , g1Dist);
        double g1000Dist = dwga1.shortestPathDist(4067 , 9963);
        assertEquals(-1 , g1000Dist);
        double g2Dist = dwga2.shortestPathDist(7,20);
        assertEquals(6.920014881348438 , g2Dist);
        double g3Dist = dwga3.shortestPathDist(0,40);
        assertEquals(8.502968926650318 , g3Dist);
    }

    @Test
    void shortestPath() {
        List<NodeData> g1Path = new LinkedList<>();
        g1Path.add(dwga.getGraph().getNode(0));
        g1Path.add(dwga.getGraph().getNode(1));
        g1Path.add(dwga.getGraph().getNode(2));
        g1Path.add(dwga.getGraph().getNode(6));
        g1Path.add(dwga.getGraph().getNode(7));
        g1Path.add(dwga.getGraph().getNode(8));
        assertEquals(g1Path , dwga.shortestPath(0,8));
        List<NodeData> g1000Path = null;
        assertEquals(g1000Path , dwga1.shortestPath(4067,9963));
        List<NodeData> g2Path = dwga2.shortestPath(7,20);
        assertEquals(g2Path , dwga2.shortestPath(7,20));
        List<NodeData> g3Path = new LinkedList<>();
        g3Path.add(dwga3.getGraph().getNode(0));
        g3Path.add(dwga3.getGraph().getNode(2));
        g3Path.add(dwga3.getGraph().getNode(3));
        g3Path.add(dwga3.getGraph().getNode(13));
        g3Path.add(dwga3.getGraph().getNode(14));
        g3Path.add(dwga3.getGraph().getNode(15));
        g3Path.add(dwga3.getGraph().getNode(39));
        g3Path.add(dwga3.getGraph().getNode(40));
        assertEquals(g3Path , dwga3.shortestPath(0,40));
    }

    @Test
    void center() {
        assertEquals(8 , dwga.center().getKey());
        assertEquals(0 , dwga2.center().getKey());
        assertEquals(40 , dwga3.center().getKey());
    }

    @Test
    void tsp() {
        List<NodeData> cities = new LinkedList<>();
        cities.add(this.dwga.getGraph().getNode(5));
        cities.add(this.dwga.getGraph().getNode(9));
        cities.add(this.dwga.getGraph().getNode(10));
        List<NodeData> path = dwga.tsp(cities);
        assertEquals(path , dwga.tsp(cities));

        List<NodeData> citiesBig = new LinkedList<>();
        citiesBig.add(this.dwga1.getGraph().getNode(7));
        citiesBig.add(this.dwga1.getGraph().getNode(300));
        citiesBig.add(this.dwga1.getGraph().getNode(50));
        List<NodeData> path1 = dwga1.tsp(citiesBig);
        assertEquals(path1 , dwga1.tsp(citiesBig));
    }

    @Test
    void save() {
        dwga.save("JunitTest.json");
        DirectWeightGraphAlgo junitTest = new DirectWeightGraphAlgo();
        DirectedWeightedGraph junitTestGraph = new DirectWeightGraph();
        junitTest.init(junitTestGraph);
        junitTest.load("JunitTest.json");
        Iterator<NodeData> g1Iter = dwga.getGraph().nodeIter();
        int i=0;
        while (g1Iter.hasNext()){
            Node temp = (Node)g1Iter.next();
            assertTrue(temp.equals( dwga.getGraph().getNode(temp.getKey())));
        }

    }

    @Test
    void load() {
        dwga.save("JunitTestLoad.json");
        DirectWeightGraphAlgo junitTest = new DirectWeightGraphAlgo();
        DirectedWeightedGraph junitTestGraph = new DirectWeightGraph();
        junitTest.init(junitTestGraph);
        junitTest.load("JunitTestLoad.json");
        Iterator<NodeData> g1Iter = dwga.getGraph().nodeIter();
        int i=0;
        while (g1Iter.hasNext()){
            Node temp = (Node)g1Iter.next();
        }
    }
}