package GUI;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.*;
import java.util.List;

public class GraphPanel extends JPanel implements MouseListener,ActionListener {
    static int srcc, destt;
    static double w;
    static String action = "";
    final int _WIDTH = 1280;
    final int _HEIGHT = 900;
    final double EPS = 0.06;
    DirectedWeightedGraphAlgorithms dwga;
    DirectedWeightedGraph dwg;
    static boolean gFile=false;
    Dimension d;
    boolean flag = false;
    double[] minmaxX = new double[2];
    double[] minmaxY = new double[2];
    NodeData cent;
    private int kRADIUS = 2;
    private static int temp_key;
    private List<NodeData> cities;
    public GraphPanel(DirectedWeightedGraphAlgorithms dwga, Dimension dim) {
        this.dwga = dwga;
        this.dwg = dwga.getGraph();
        this.d = dim;
        this.setSize(dim);
        fillminmax();
        this.setVisible(true);

        this.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        switch (str) {
            case ("Load"):
                action = str;
                dwg = new DirectWeightGraph();
                dwga = new DirectWeightGraphAlgo();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    this.removeAll();
                    System.out.println(selectedFile.getName());
                    dwga.load(selectedFile.getName());
                    if(selectedFile.getName().contains("G1")||selectedFile.getName().contains("G2")||selectedFile.getName().contains("G3")) gFile = true;
                }
                this.repaint();
                this.setVisible(true);
                break;
            case ("Save"):
                action = str;
                options(action);
                break;
            case ("clean-up"):
                //remove(previousPanel);
                action = str;
                this.revalidate();
                this.repaint();
                this.setVisible(true);
                break;
            case ("Add vertex"):
            case ("Add edge"):
            case ("Remove vertex"):
            case ("Remove edge"):
            case ("Is Connected"):
            case ("Shortest Path"):
            case ("TSP"):
                action = str;
                options(action);
                this.repaint();
                this.updateUI();
                break;
            case ("Center"):
                action = str;
                this.repaint();
                this.updateUI();
                break;
        }

    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (action.equals("clean-up")){

            dwga = new DirectWeightGraphAlgo();
            dwg = new DirectWeightGraph();
            dwga.init(dwg);
            action = "";
            return;
        }
        else if(action.equals("Add edge")){
            this.dwga.getGraph().connect(srcc,destt,w);
//            if(dwga.getGraph().getEdge(srcc,destt)== null){
//
//            }
        }
        else if(action.equals("Remove vertex")){
            if(dwga.getGraph().getNode(temp_key)!= null){
                this.dwga.getGraph().removeNode(temp_key);
            }
        }
        else if(action.equals("Remove edge")){
            if(dwga.getGraph().getEdge(srcc,destt)!=null){
                this.dwga.getGraph().removeEdge(srcc,destt);
            }
        }
        if (dwga.getGraph() != null) {
            HashMap<Integer, Double> xs = new HashMap<>();
            HashMap<Integer, Double> ys = new HashMap<>();
            Iterator<NodeData> it = dwga.getGraph().nodeIter();
            int counter = 0;
            while (it.hasNext()) {
                NodeData temp = it.next();
                GeoLocation point = temp.getLocation();
                xs.put(temp.getKey(), point.x());
                ys.put(temp.getKey(), point.y());
            }

            if (xs.values().size() >0) {
                if((action.equals("Load") && gFile)) {
                    double minX = Collections.min(xs.values());
                    double maxX = Collections.max(xs.values());
                    double minY = Collections.min(ys.values());
                    double maxY = Collections.max(ys.values());
                    minmaxX[0] = minX;
                    minmaxY[0] = minY;
                    minmaxX[1] = maxX;
                    minmaxY[1] = maxY;
                    gFile=false;
                }
                it = dwga.getGraph().nodeIter();
                g.setFont(g.getFont().deriveFont(20.0F));


                Object[] x = xs.values().toArray();
                Object[] y = xs.values().toArray();
                while (it.hasNext()) {
                    NodeData temp = it.next();

                    double ansx = scalex(xs.get(temp.getKey()), minmaxX[0], minmaxX[1]);
                    double ansy = scaley(ys.get(temp.getKey()), minmaxY[0], minmaxY[1]);
                    g.setColor(Color.BLACK);
//                    g.drawArc((int) (ansx), (int) (ansy), 20, 20, 0, 360);
                    g.fillOval((int) (ansx) + 5, (int) (ansy) + 5, 13, 13);
                    //test
//                    System.out.println("id :" + counter++ + " " + (int) (ansx) + 5 + " , " + (int) (ansy) + 5);

                    ((Graphics2D) g).setStroke(new BasicStroke(3));
                    //node key
                    g.drawString(String.valueOf(temp.getKey()), (int) (ansx) - kRADIUS, (int) (ansy));

                }

                Iterator<EdgeData> edgeIter = dwga.getGraph().edgeIter();

                //Increase font size
                g.setFont(g.getFont().deriveFont(15.0F));

                //Edges
                edgeIter.forEachRemaining(edgeData -> {

                    int x1 = (int) scalex(xs.get(edgeData.getSrc()), minmaxX[0], minmaxX[1]);
                    int y1 = (int) scaley(ys.get(edgeData.getSrc()), minmaxY[0], minmaxY[1]);
                    int x2 = (int) scalex(xs.get(edgeData.getDest()), minmaxX[0], minmaxX[1]);
                    int y2 = (int) scaley(ys.get(edgeData.getDest()), minmaxY[0], minmaxY[1]);
                    g.setColor(Color.BLACK);
//                    ((Graphics2D) g).setStroke(new BasicStroke(3));
                    drawArrow(g, (int) (x1) + 10, (int) (y1) + 10, x2 + 10, y2 + 10);

                });

                switch (action) {
                    case ("Center"):{
                        if(!dwga.isConnected()) {
                            return;
                        }
//                        fillminmax();
                            cent = dwga.center();
                            int x1 = (int) scalex(cent.getLocation().x(), minmaxX[0], minmaxX[1]);
                            int y1 = (int) scaley(cent.getLocation().y(), minmaxY[0], minmaxY[1]);
                            g.setColor(Color.RED);
                            g.fillOval((int) (x1) + 5, (int) (y1) + 5, 13, 13);
                            return;

                    }
                    case ("Shortest Path"): {
                        List<NodeData> ls;
                        ls = shortestpath();
                        xs.clear();
                        ys.clear();
                        ls.forEach(nodeData1 -> {
                            GeoLocation point = nodeData1.getLocation();
                            xs.put(nodeData1.getKey(), point.x());
                            ys.put(nodeData1.getKey(), point.y());
                        });

                        int i = 0;
                        while (i + 1 < ls.size()) {

                            int x1 = (int) scalex(xs.get(ls.get(i).getKey()), minmaxX[0], minmaxX[1]);
                            int y1 = (int) scaley(ys.get(ls.get(i).getKey()),minmaxY[0], minmaxY[1]);
                            int x2 = (int) scalex(xs.get(ls.get(i + 1).getKey()),minmaxX[0], minmaxX[1]);
                            int y2 = (int) scaley(ys.get(ls.get(i + 1).getKey()),minmaxY[0], minmaxY[1]);


                            g.setColor(Color.RED);
//                    g.setFont(g.getFont().deriveFont(20.0F));
                            ((Graphics2D) g).setStroke(new BasicStroke(4));


                            drawArrow(g, (int) (x1) + 10, (int) (y1) + 10, x2 + 10, y2 + 10);
                            g.fillOval((int) (x1) + 5, (int) (y1) + 5, 13, 13);
//                    g.drawString(String.valueOf(ls.get(i).getKey()), (int) (x1) - kRADIUS, (int) (y1));
                            if (i == ls.size() - 2) {
                                g.fillOval((int) (x2) + 5, (int) (y2) + 5, 13, 13);
//                        g.drawString(String.valueOf(ls.get(i+1).getKey()), (int) (x2) - kRADIUS, (int) (y2));
                            }
                            i += 1;
                        }
                        return;
                    }
                    case ("TSP"):{
                        List<NodeData> ls;
                        ls = tsp(cities);
                        xs.clear();
                        ys.clear();
                        ls.forEach(nodeData1 -> {
                            GeoLocation point = nodeData1.getLocation();
                            xs.put(nodeData1.getKey(), point.x());
                            ys.put(nodeData1.getKey(), point.y());
                        });

                        int i = 0;
                        while (i + 1 < ls.size()) {

                            int x1 = (int) scalex(xs.get(ls.get(i).getKey()), minmaxX[0], minmaxX[1]);
                            int y1 = (int) scaley(ys.get(ls.get(i).getKey()),minmaxY[0], minmaxY[1]);
                            int x2 = (int) scalex(xs.get(ls.get(i + 1).getKey()),minmaxX[0], minmaxX[1]);
                            int y2 = (int) scaley(ys.get(ls.get(i + 1).getKey()),minmaxY[0], minmaxY[1]);


                            g.setColor(Color.RED);
//                    g.setFont(g.getFont().deriveFont(20.0F));
                            ((Graphics2D) g).setStroke(new BasicStroke(4));


                            drawArrow(g, (int) (x1) + 10, (int) (y1) + 10, x2 + 10, y2 + 10);
                            g.fillOval((int) (x1) + 5, (int) (y1) + 5, 13, 13);
//                    g.drawString(String.valueOf(ls.get(i).getKey()), (int) (x1) - kRADIUS, (int) (y1));
                            if (i == ls.size() - 2) {
                                g.fillOval((int) (x2) + 5, (int) (y2) + 5, 13, 13);
//                        g.drawString(String.valueOf(ls.get(i+1).getKey()), (int) (x2) - kRADIUS, (int) (y2));
                            }
                            i += 1;
                        }
                        return;
                    }
                }
            }


        }
    }
    private void options(String str) {
        switch (str) {
            case ("Save"):{
                String inputString1 = JOptionPane.showInputDialog(null, "Enter file name . (Without .json)");
                dwga.save(inputString1+".json");
                break;
            }
            case ("Is Connected") :{
                String ans = "False";
                boolean isC = dwga.isConnected();
                if(isC) ans = "True";
                JOptionPane.showMessageDialog(null,"Is Connected ? \n\n\nAnswer :"+ans ,"IsConnected",JOptionPane.INFORMATION_MESSAGE);
                break;
            }
            case ("Shortest Path"): {
                String inputString1 = JOptionPane.showInputDialog(null, "Enter node source ID");
                String inputString2 = JOptionPane.showInputDialog(null, "Enter node destination ID");
                srcc = Integer.parseInt(inputString1);
                destt = Integer.parseInt(inputString2);
                double path_dist = this.dwga.shortestPathDist(srcc, destt);
                JOptionPane.showMessageDialog(null, "The shortest distance to " + destt + " from " + srcc + " is: " + String.format("%.2f", path_dist), "Shortest path", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
            case ("Add vertex"): {
                String inputString1 = JOptionPane.showInputDialog(null, "Enter x");
                String inputString2 = JOptionPane.showInputDialog(null, "Enter y");
                String key = JOptionPane.showInputDialog(null, "Enter key");

                addVertex(Double.parseDouble(inputString1),Double.parseDouble(inputString2),Integer.parseInt(key));
//                xin = Double.parseDouble(inputString1);
//                yin = Double.parseDouble(inputString2);
//                temp_key = Integer.parseInt(key);
                break;
            }
            case ("Add edge"): {
                String inputString1 = JOptionPane.showInputDialog(null, "Enter key1");
                String inputString2 = JOptionPane.showInputDialog(null, "Enter key2");
                String inputString3 = JOptionPane.showInputDialog(null, "Enter weight");

                srcc = Integer.parseInt(inputString1);
                destt = Integer.parseInt(inputString2);
                w = Double.parseDouble(inputString3);
                break;
            }
            case ("Remove vertex"): {
                String key = JOptionPane.showInputDialog(null, "Enter key");
                temp_key = Integer.parseInt(key);
                break;
            }
            case ("Remove edge"): {
                String inputString1 = JOptionPane.showInputDialog(null, "Enter key 1");
                String inputString2 = JOptionPane.showInputDialog(null, "Enter key 2");
                srcc = Integer.parseInt(inputString1);
                destt = Integer.parseInt(inputString2);
            }
            case ("TSP"): {
                cities = new ArrayList<>();
                int b = 0;
                while (b == JOptionPane.YES_OPTION) {
                    NodeData key = dwga.getGraph().getNode(Integer.parseInt(JOptionPane.showInputDialog("Enter city key to add to list of cities", JOptionPane.OK_CANCEL_OPTION)));
                    cities.add(key);
                    b =JOptionPane.showConfirmDialog(null, "Would you like to input another number? yes or no\n\nClick no to start the algorithm", "More Inputs", JOptionPane.YES_NO_OPTION);
                }
            }
        }
    }


    private void addVertex(double x,double y, int key) {
        try {
            double width = _WIDTH / (minmaxX[1] - minmaxX[0]);
            double height = _HEIGHT / (minmaxY[1] - minmaxY[0]);
            x = minmaxX[0] + (x) / width;
            y = minmaxY[0] + (y) / height;
            dwga.getGraph().addNode(new Node(new Point3D(x, y), 0, key));
        }catch(ArithmeticException e)   {e.printStackTrace();} ;
    }



    // ****************** Private methods ******************

    private double scalex(double x, double min, double max) {
        return ((((x - min) * (_WIDTH - 100)) / (max - min)) + 15) ;//% (_WIDTH - 50);
    }

    private double scaley(double y, double min, double max) {
        return ((((y - min) * (_HEIGHT - 150)) / (max - min)) + 25);// % (_HEIGHT - 100);
    }

    private List<NodeData> shortestpath() {
        return dwga.shortestPath(srcc, destt);
    }
    private List<NodeData> tsp(List<NodeData> cities){
        return dwga.tsp(cities);
    }
    private void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();
        int m = 10;
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);
        g.setStroke(new BasicStroke(2));
        // Draw horizontal arrow starting in (0, 0)
        len = len - (int) (EPS * len);
        g.drawLine(0, 0, len + (int) (EPS * len), 0);
        int ARR_SIZE = 11;
        g.fillPolygon(new int[]{len, len - ARR_SIZE, len - ARR_SIZE, len},
                new int[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);
    }

    private void fillminmax() {
        HashMap<Integer, Double> xs = new HashMap<>();
        HashMap<Integer, Double> ys = new HashMap<>();
        Iterator<NodeData> it = dwga.getGraph().nodeIter();
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
    // ****************************************************

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x+","+y);

        System.out.println("mousePressed");
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
}
