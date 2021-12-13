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

public class GraphFrame extends JFrame implements ActionListener {
    final int _WIDTH = 1280;
    final int _HEIGHT = 900;
    final double EPS = 0.06;
    DirectedWeightedGraphAlgorithms dwga;
    DirectedWeightedGraph dwg;
    GraphPanel gp ;
    private final int kRADIUS = 2;



    public GraphFrame(DirectedWeightedGraphAlgorithms dwga) {
        super();
        this.dwga = dwga;
        this.dwg = dwga.getGraph();
        initGUI();


    }


    private void initGUI() {
        this.setName("My Graph");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
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


}
