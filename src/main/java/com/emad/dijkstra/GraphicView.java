package com.emad.dijkstra;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

public class GraphicView extends VBox {
    public Canvas canvas;
    public GraphicsContext g;
    public GraphicView(){
        this.canvas = new Canvas(830, 504);
        this.getChildren().add(canvas);
        g = this.canvas.getGraphicsContext2D();
        g.setStroke(Color.DARKGREEN);
        g.setLineWidth(2);
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);
    }
    public void draw(){
        g.setFill(Color.WHITE);
        g.fillRect(0,0,830,504);
    }
    public void draw(Node[] nodes, int max){
        g.setFill(Color.WHITE);
        g.fillRect(0,0,830,504);
        g.setFill(Color.BLACK);
        for (int i=0;i<max;i++) {
            Node n = nodes[i];
            g.fillOval(n.x - n.rad,n.y-n.rad, 2*n.rad,2*n.rad);//بعبي الدائرة 
        }
    }
    public void draw(Node n, Color fill){
        g.setFill(fill);
        int rad = n.rad*2;
        g.fillOval(n.x - rad,n.y-rad, 2*rad,2*rad);
    }
    public void drawLine(Node n1, Node n2){
        g.strokeLine(n1.x, n1.y, n2.x,n2.y);
    }
    public void drawText(Node n1, Node n2, String text){
        int x = Math.abs((n1.x + n2.x)/2);//
        int y = Math.abs((n1.y + n2.y)/2);
        g.fillText(text, x, y);
    }
}
