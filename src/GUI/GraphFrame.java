package GUI;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import api.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

public class GraphFrame extends JFrame implements ActionListener,MouseListener {
    final int _WIDTH = 1280;
    final int _HEIGHT = 900;
    final double EPS = 0.06;
    DirectedWeightedGraphAlgorithms dwga;
    DirectedWeightedGraph dwg;
    NodeData nodeData;
    GraphPanel gp ;

    boolean flag = false;
    double[] minmaxX = new double[2];
    double[] minmaxY = new double[2];
    private int kRADIUS = 2;



    public GraphFrame(DirectedWeightedGraphAlgorithms dwga) {
        super();
        this.dwga = dwga;
        this.dwg = dwga.getGraph();
        initGUI();


    }



    public void fillminmax() {
        HashMap<Integer, Double> xs = new HashMap<>();
        HashMap<Integer, Double> ys = new HashMap<>();
        Iterator<NodeData> it = dwg.nodeIter();
        while (it.hasNext()) {
            NodeData temp = it.next();
            GeoLocation point = temp.getLocation();
            xs.put(temp.getKey(), point.x());
            ys.put(temp.getKey(), point.y());
        }
        double minx = Collections.min(xs.values());
        double maxx = Collections.max(xs.values());
        double miny = Collections.min(ys.values());
        double maxy = Collections.max(ys.values());
        minmaxX[0] = minx;
        minmaxY[0] = miny;
        minmaxX[1] = maxx;
        minmaxY[1] = maxy;

    }

    private void initGUI() {
        this.setName("My Graph");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLocationRelativeTo(null);
        this.setResizable(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int height = dim.height;
        int width = dim.width;
        gp = new GraphPanel(dwga,dim);
        add(gp);
        gp.setVisible(true);

        MenuBar menuBar = new MenuBar();
        initMenu(menuBar);

        this.setSize(_WIDTH, _HEIGHT);
//        this.pack();
        this.setBackground(Color.WHITE);
        this.setVisible(true);



    }

    private void initMenu(MenuBar mb) {
        Menu menu = new Menu("Menu");
        Menu menuedit = new Menu("Edit");
        Menu menualgo = new Menu("Algorithms");

        mb.add(menu);
        mb.add(menuedit);
        mb.add(menualgo);
        this.setMenuBar(mb);


        MenuItem item1 = new MenuItem("Load");
        item1.addActionListener(gp);
        MenuItem item2 = new MenuItem("Save");
        item2.addActionListener(gp);
        MenuItem item3 = new MenuItem("clean-up");
        item3.addActionListener(gp);


        MenuItem item4 = new MenuItem("Add vertex");
        item4.addActionListener(gp);
        MenuItem item5 = new MenuItem("Add edge");
        item5.addActionListener(gp);
        MenuItem item6 = new MenuItem("Remove vertex");
        item6.addActionListener(gp);
        MenuItem item7 = new MenuItem("Remove edge");
        item7.addActionListener(gp);

        MenuItem item8 = new MenuItem("Is Connected");
        item8.addActionListener(gp);
        MenuItem item9 = new MenuItem("Shortest Path");
        item9.addActionListener(gp);
        MenuItem item10 = new MenuItem("Center");
        item10.addActionListener(gp);
        MenuItem item11 = new MenuItem("TSP");
        item11.addActionListener(gp);

        menu.add(item1);
        menu.add(item2);
        menu.add(item3);

        menuedit.add(item4);
        menuedit.add(item5);
        menuedit.add(item6);
        menuedit.add(item7);

        menualgo.add(item8);
        menualgo.add(item9);
        menualgo.add(item10);
        menualgo.add(item11);

    }
    @Override
    public void actionPerformed(ActionEvent e) {


    }




//    private JPanel centerpanel(){
//        fillminmax();
//        return new JPanel() {
//            public void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                HashMap<Integer, Double> xs = new HashMap<>();
//                HashMap<Integer, Double> ys = new HashMap<>();
//                Iterator<NodeData> it = dwg.nodeIter();
//                int counter = 0;
//                while (it.hasNext()) {
//                    NodeData temp = it.next();
//                    GeoLocation point = temp.getLocation();
//                    xs.put(temp.getKey(), point.x());
//                    ys.put(temp.getKey(), point.y());
//                }
//                double minX = Collections.min(xs.values());
//                double maxX = Collections.max(xs.values());
//                double minY = Collections.min(ys.values());
//                double maxY = Collections.max(ys.values());
//                it = dwga.getGraph().nodeIter();
//                g.setFont(g.getFont().deriveFont(20.0F));
//
//                while (it.hasNext()) {
//                    NodeData temp = it.next();
//
//                    double ansx = scalex(xs.get(temp.getKey()), minX, maxX);
//                    double ansy = scaley(ys.get(temp.getKey()), minY, maxY);
//                    g.setColor(Color.BLACK);
////                    g.drawArc((int) (ansx), (int) (ansy), 20, 20, 0, 360);
//                    g.fillOval((int) (ansx) + 5, (int) (ansy) + 5, 13, 13);
//                    //test
//                    System.out.println("id :" + counter++ + " " + (int) (ansx) + 5 + " , " + (int) (ansy) + 5);
//
//                    ((Graphics2D) g).setStroke(new BasicStroke(3));
//                    //node key
//                    g.drawString(String.valueOf(temp.getKey()), (int) (ansx) - kRADIUS, (int) (ansy));
//
//                }
//                Iterator<EdgeData> edgeIter = dwg.edgeIter();
//
//                //Increase font size
//                g.setFont(g.getFont().deriveFont(15.0F));
//
//                //Edges
//                edgeIter.forEachRemaining(edgeData -> {
//
//                    int x1 = (int) scalex(xs.get(edgeData.getSrc()), minX, maxX);
//                    int y1 = (int) scaley(ys.get(edgeData.getSrc()), minY, maxY);
//                    int x2 = (int) scalex(xs.get(edgeData.getDest()), minX, maxX);
//                    int y2 = (int) scaley(ys.get(edgeData.getDest()), minY, maxY);
//                    g.setColor(Color.BLACK);
////                    ((Graphics2D) g).setStroke(new BasicStroke(3));
//                    drawArrow(g, (int) (x1) + 10, (int) (y1) + 10, x2 + 10, y2 + 10);
//
//                });
//                System.out.println("rePaintGraph");
//
//                int x1 = (int) scalex(cent.getLocation().x(), minX, maxX);
//                int y1 = (int) scaley(cent.getLocation().y(), minY, maxY);
//                g.setColor(Color.RED);
//                g.fillOval((int) (x1) + 5, (int) (y1) + 5, 13, 13);
//            }
//        };
//
//    }
//    private JPanel addVertex(){
////        fillminmax();
//        try{
//        String inputString1 = JOptionPane.showInputDialog(null, "Enter x");
//        String inputString2 = JOptionPane.showInputDialog(null, "Enter y");
//        String key = JOptionPane.showInputDialog(null, "Enter key");
//        xin = Double.parseDouble(inputString1);
//        yin = Double.parseDouble(inputString2);
//
//        int node_id = Integer.parseInt(key);
//        if(this.dwga.getGraph().getNode(node_id)==null) {
//            this.dwga.getGraph().addNode(new Node(new Point3D(xin, yin, 0),0,node_id));
//        }} catch (HeadlessException e) {
//            e.printStackTrace();
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//
//
//        return new JPanel() {
//            public void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                HashMap<Integer, Double> xs = new HashMap<>();
//                HashMap<Integer, Double> ys = new HashMap<>();
//                Iterator<NodeData> it = dwga.getGraph().nodeIter();
//                int counter = 0;
//                while (it.hasNext()) {
//                    NodeData temp = it.next();
//                    GeoLocation point = temp.getLocation();
//                    xs.put(temp.getKey(), point.x());
//                    ys.put(temp.getKey(), point.y());
//                }
//                double minX = Collections.min(xs.values());
//                double maxX = Collections.max(xs.values());
//                double minY = Collections.min(ys.values());
//                double maxY = Collections.max(ys.values());
//                it = dwga.getGraph().nodeIter();
//                g.setFont(g.getFont().deriveFont(20.0F));
//
//                while (it.hasNext()) {
//                    NodeData temp = it.next();
//
//                    double ansx = scalex(xs.get(temp.getKey()), minX, maxX);
//                    double ansy = scaley(ys.get(temp.getKey()), minY, maxY);
//                    g.setColor(Color.BLACK);
////                    g.drawArc((int) (ansx), (int) (ansy), 20, 20, 0, 360);
//                    g.fillOval((int) (ansx) + 5, (int) (ansy) + 5, 13, 13);
//                    //test
//                    System.out.println("id :" + counter++ + " " + (int) (ansx) + 5 + " , " + (int) (ansy) + 5);
//
//                    ((Graphics2D) g).setStroke(new BasicStroke(3));
//                    //node key
//                    g.drawString(String.valueOf(temp.getKey()), (int) (ansx) - kRADIUS, (int) (ansy));
//
//                }
//                Iterator<EdgeData> edgeIter = dwg.edgeIter();
//
//                //Increase font size
//                g.setFont(g.getFont().deriveFont(15.0F));
//
//                //Edges
//                edgeIter.forEachRemaining(edgeData -> {
//
//                    int x1 = (int) scalex(xs.get(edgeData.getSrc()), minX, maxX);
//                    int y1 = (int) scaley(ys.get(edgeData.getSrc()), minY, maxY);
//                    int x2 = (int) scalex(xs.get(edgeData.getDest()), minX, maxX);
//                    int y2 = (int) scaley(ys.get(edgeData.getDest()), minY, maxY);
//                    g.setColor(Color.BLACK);
////                    ((Graphics2D) g).setStroke(new BasicStroke(3));
//                    drawArrow(g, (int) (x1) + 10, (int) (y1) + 10, x2 + 10, y2 + 10);
//
//                });
//                System.out.println("rePaintGraph");
//            }
//
//        };
//    }
//    private void options(){
//        String inputString1 = JOptionPane.showInputDialog(null, "Enter node source ID");
//        String inputString2 = JOptionPane.showInputDialog(null, "Enter node destination ID");
//        srcc = Integer.parseInt(inputString1);
//        destt = Integer.parseInt(inputString2);
//        double path_dist= this.dwga.shortestPathDist(srcc,destt);
//        JOptionPane.showMessageDialog(null, "The shortest distance to "+ destt +" from "+srcc+" is: " + String.format("%.2f",path_dist),"Shortest path",JOptionPane.INFORMATION_MESSAGE);
//
//
//    }
//    private JPanel shortestPath(List<NodeData> ls) {
//        fillminmax();
//        return new JPanel() {
//            public void paintComponent(Graphics g) {
//                super.paintComponent(g);
//
//                HashMap<Integer, Double> xs = new HashMap<>();
//                HashMap<Integer, Double> ys = new HashMap<>();
//                Iterator<NodeData> it = dwg.nodeIter();
//                int counter = 0;
//                while (it.hasNext()) {
//                    NodeData temp = it.next();
//                    GeoLocation point = temp.getLocation();
//                    xs.put(temp.getKey(), point.x());
//                    ys.put(temp.getKey(), point.y());
//                }
//                double minX = Collections.min(xs.values());
//                double maxX = Collections.max(xs.values());
//                double minY = Collections.min(ys.values());
//                double maxY = Collections.max(ys.values());
//                it = dwg.nodeIter();
//                g.setFont(g.getFont().deriveFont(20.0F));
//
//                while (it.hasNext()) {
//                    NodeData temp = it.next();
//
//                    double ansx = scalex(xs.get(temp.getKey()), minX, maxX);
//                    double ansy = scaley(ys.get(temp.getKey()), minY, maxY);
//                    g.setColor(Color.BLACK);
////                    g.drawArc((int) (ansx), (int) (ansy), 20, 20, 0, 360);
//                    g.fillOval((int) (ansx) + 5, (int) (ansy) + 5, 13, 13);
//                    //test
//                    System.out.println("id :" + counter++ + " " + (int) (ansx) + 5 + " , " + (int) (ansy) + 5);
//
//                    ((Graphics2D) g).setStroke(new BasicStroke(3));
//                    //node key
//                    g.drawString(String.valueOf(temp.getKey()), (int) (ansx) - kRADIUS, (int) (ansy));
//
//                }
//                Iterator<EdgeData> edgeIter = dwg.edgeIter();
//
//                //Increase font size
//                g.setFont(g.getFont().deriveFont(15.0F));
//
//                //Edges
//                edgeIter.forEachRemaining(edgeData -> {
//
//                    int x1 = (int) scalex(xs.get(edgeData.getSrc()), minX, maxX);
//                    int y1 = (int) scaley(ys.get(edgeData.getSrc()), minY, maxY);
//                    int x2 = (int) scalex(xs.get(edgeData.getDest()), minX, maxX);
//                    int y2 = (int) scaley(ys.get(edgeData.getDest()), minY, maxY);
//                    g.setColor(Color.BLACK);
////                    ((Graphics2D) g).setStroke(new BasicStroke(3));
//                    drawArrow(g, (int) (x1) + 10, (int) (y1) + 10, x2 + 10, y2 + 10);
//
//                });
//
//                xs.clear();
//                ys.clear();
//                ls.forEach(nodeData1 -> {
//                    GeoLocation point = nodeData1.getLocation();
//                    xs.put(nodeData1.getKey(), point.x());
//                    ys.put(nodeData1.getKey(), point.y());
//                });
//
//                int i = 0;
//                while (i + 1 < ls.size()) {
//                    int x1 = (int) scalex(xs.get(ls.get(i).getKey()), minX, maxX);
//                    int y1 = (int) scaley(ys.get(ls.get(i).getKey()), minY, maxY);
//                    int x2 = (int) scalex(xs.get(ls.get(i + 1).getKey()), minX, maxX);
//                    int y2 = (int) scaley(ys.get(ls.get(i + 1).getKey()), minY, maxY);
//
//
//
//                    g.setColor(Color.RED);
////                    g.setFont(g.getFont().deriveFont(20.0F));
//                    ((Graphics2D) g).setStroke(new BasicStroke(4));
//
//
//                    drawArrow(g, (int) (x1) + 10, (int) (y1) + 10, x2 + 10, y2 + 10);
//                    g.fillOval((int) (x1) + 5, (int) (y1) + 5, 13, 13);
////                    g.drawString(String.valueOf(ls.get(i).getKey()), (int) (x1) - kRADIUS, (int) (y1));
//                    if(i==ls.size()-2){
//                        g.fillOval((int) (x2) + 5, (int) (y2) + 5, 13, 13);
////                        g.drawString(String.valueOf(ls.get(i+1).getKey()), (int) (x2) - kRADIUS, (int) (y2));
//                    }
//                    i+=1;
//                }
//            }
//        };
//    }
//    private JPanel rePaintGraph() {
//
//        return new JPanel() {
//            public void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                HashMap<Integer, Double> xs = new HashMap<>();
//                HashMap<Integer, Double> ys = new HashMap<>();
//                Iterator<NodeData> it = dwg.nodeIter();
//                int counter = 0;
//                while (it.hasNext()) {
//                    NodeData temp = it.next();
//                    GeoLocation point = temp.getLocation();
//                    xs.put(temp.getKey(), point.x());
//                    ys.put(temp.getKey(), point.y());
//                }
//                double minX = Collections.min(xs.values());
//                double maxX = Collections.max(xs.values());
//                double minY = Collections.min(ys.values());
//                double maxY = Collections.max(ys.values());
//                it = dwg.nodeIter();
//                g.setFont(g.getFont().deriveFont(20.0F));
//
//                while (it.hasNext()) {
//                    NodeData temp = it.next();
//
//                    double ansx = scalex(xs.get(temp.getKey()), minX, maxX);
//                    double ansy = scaley(ys.get(temp.getKey()), minY, maxY);
//                    g.setColor(Color.BLACK);
////                    g.drawArc((int) (ansx), (int) (ansy), 20, 20, 0, 360);
//                    g.fillOval((int) (ansx) + 5, (int) (ansy) + 5, 13, 13);
//                    //test
//                    System.out.println("id :" + counter++ + " " + (int) (ansx) + 5 + " , " + (int) (ansy) + 5);
//
//                    ((Graphics2D) g).setStroke(new BasicStroke(3));
//                    //node key
//                    g.drawString(String.valueOf(temp.getKey()), (int) (ansx) - kRADIUS, (int) (ansy));
//
//                }
//                Iterator<EdgeData> edgeIter = dwg.edgeIter();
//
//                //Increase font size
//                g.setFont(g.getFont().deriveFont(15.0F));
//
//                //Edges
//                edgeIter.forEachRemaining(edgeData -> {
//
//                    int x1 = (int) scalex(xs.get(edgeData.getSrc()), minX, maxX);
//                    int y1 = (int) scaley(ys.get(edgeData.getSrc()), minY, maxY);
//                    int x2 = (int) scalex(xs.get(edgeData.getDest()), minX, maxX);
//                    int y2 = (int) scaley(ys.get(edgeData.getDest()), minY, maxY);
//                    g.setColor(Color.BLACK);
////                    ((Graphics2D) g).setStroke(new BasicStroke(3));
//                    drawArrow(g, (int) (x1) + 10, (int) (y1) + 10, x2 + 10, y2 + 10);
//
//                });
//                System.out.println("rePaintGraph");
//            }
//
//        };
//
////        graphPanel.setVisible(true);
////
////        return graphPanel;
//
//    }





    // ****************** Private methods ******************


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

//    public void paint(Graphics g) {
//        // Create a new "canvas"
//        mBuffer_image = createImage(_WIDTH, _HEIGHT);
//        mBuffer_graphics = mBuffer_image.getGraphics();
//
//        // Draw on the new "canvas"
//        paintComponents(mBuffer_graphics);
//
//        // "Switch" the old "canvas" for the new one
//        g.drawImage(mBuffer_image, 0, 0, this);
//    }



    //draw arrow
}
