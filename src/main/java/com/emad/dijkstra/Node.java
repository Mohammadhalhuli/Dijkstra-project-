package com.emad.dijkstra;

import javafx.scene.shape.Circle;

public class Node {
    public int x;
    public int y;
    public int rad;
    public Node(int X, int Y, int Rad){
        x = 2*Rad*X + Rad;
        y = 2*Rad*Y + Rad;
        rad = Rad;
    }

}
