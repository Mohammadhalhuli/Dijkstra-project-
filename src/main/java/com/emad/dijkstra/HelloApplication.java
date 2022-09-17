package com.emad.dijkstra;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.function.UnaryOperator;

public class HelloApplication extends Application implements EventHandler<ActionEvent> {
    Button button1;
    TextField size;
    TextField targetIndex;
    TextField sourceIndex;

    Node[] deafaultNodes;
    Node[] nodes;
    int maxX = 206;
    int maxY = 124;
    int Length = maxX * maxY;

    int[][] graphMatrix;

    Color target = Color.RED;
    Color source = Color.LIMEGREEN;
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        // setting up GUI
        VBox mainFram = new VBox();
        GraphicView graph = new GraphicView();
        VBox tools = new VBox();
        HBox line1 = new HBox();
        HBox line2 = new HBox();

        line1.setSpacing(10);
        line2.setSpacing(10);
        tools.setSpacing(10);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*") ) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);

        // tools line 1

        Label label1 = new Label();
        label1.setText("Random Seed:");
        TextField seed = new TextField();
        seed.setText("1");
        seed.setTextFormatter(new TextFormatter<>(filter));
        Label label2 = new Label();
        label2.setText("Size:              ");
        size = new TextField();
        size.setTextFormatter(new TextFormatter<>(filter));
        size.setText("100");
        Button generate = new Button();

        /* ============= Computation ============== */
        generate.setOnAction(e->{
            int seedNum = Integer.parseInt(seed.getText());
            int sizeNum = Integer.parseInt(size.getText());
            int sourceInd = Integer.parseInt(sourceIndex.getText());
            int targetInd = Integer.parseInt(targetIndex.getText());

            // randomizing
            System.arraycopy(deafaultNodes, 0, nodes, 0, Length);
            HelperFunctions.shuffle(nodes, seedNum);

            // shortest path
            graphMatrix = HelperFunctions.randomGraph(sizeNum, seedNum);
            DijkstrasAlgorithm.dijkstra(graphMatrix, sourceInd);


            graph.draw(nodes, sizeNum);
            graph.draw(nodes[sourceInd], source);
            graph.draw(nodes[targetInd], target);
        });

        generate.setText("Generate");

        line1.getChildren().addAll(label1, seed, label2, size, generate);

        // tools line2
        Label label3 = new Label();
        label3.setText("Source Node: ");

        sourceIndex = new TextField();
        sourceIndex.setText("0");
        sourceIndex.setTextFormatter(textFormatter);
        sourceIndex.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")){
                graph.draw(nodes, Integer.parseInt(size.getText()));
                //Node old = nodes[Integer.parseInt(oldValue)];
                int sourceInd = Integer.parseInt(newValue);
                Node New = nodes[sourceInd];
                Node Target = nodes[Integer.parseInt(targetIndex.getText())];
                //if (oldValue.equals(targetIndex.getText()))
                //    graph.draw(old, target);
                //else
                //    graph.draw(old, Color.BLACK);
                DijkstrasAlgorithm.dijkstra(graphMatrix, sourceInd);
                graph.draw(New, source);
                graph.draw(Target, target);
            }
        });

        Label label4 = new Label();
        label4.setText("Target Node:");
        targetIndex = new TextField();
        targetIndex.setText("1");
        targetIndex.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")){
                graph.draw(nodes, Integer.parseInt(size.getText()));

                //Node old = nodes[Integer.parseInt(oldValue)];
                Node New = nodes[Integer.parseInt(newValue)];
                Node Source = nodes[Integer.parseInt(sourceIndex.getText())];
                //if(oldValue.equals(sourceIndex.getText()))
                //    graph.draw(old, source);
                //else
                //    graph.draw(old, Color.BLACK);
                graph.draw(Source, source);
                graph.draw(New, target);
            }
        });
        TextField cost = new TextField();

        Button compute = new Button();
        compute.setText("Compute cost");
        compute.setOnAction(e->{
            //graph.drawLine(nodes[Integer.parseInt(sourceIndex.getText())], nodes[Integer.parseInt(targetIndex.getText())]);
            // getting indices of paths
            //int source = Integer.parseInt(sourceIndex.getText());
            int target = Integer.parseInt(targetIndex.getText());
            ArrayList<Integer> path = DijkstrasAlgorithm.paths.get(target-1);
            //path.add(0, source);
            int total_cost = DijkstrasAlgorithm.shortestDistances[target];
            cost.setText(total_cost + "");
            for(int i = 0;i<path.size()-1;i++){
                Node node1 = nodes[path.get(i)];
                Node node2 = nodes[path.get(i+1)];
                int localPath = graphMatrix[path.get(i)][path.get(i+1)];
                graph.drawText(node1, node2, localPath + "");
                graph.drawLine(node1, node2);
            }

        });

        Label lbl1 = new Label();
        lbl1.setText("Total Path Cost");

        cost.setText("none");
        cost.setEditable(false);

        line2.getChildren().addAll(label3, sourceIndex, label4, targetIndex, compute, lbl1, cost);

        // graph
        graph.setMinHeight(520);

        tools.getChildren().addAll(line1, line2);
        mainFram.getChildren().addAll(graph, tools);

        mainFram.setPadding(new Insets(10));
        Scene scene = new Scene(mainFram, 850, 610);
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int)(4 * Math.floor((event.getSceneX() - 10.0f)/4.0f)+2);
                int y = (int)(4 * Math.floor((event.getSceneY() - 10.0f)/4.0f)+2);

                for(int i=0;i<Integer.parseInt(size.getText());i++){
                    Node node = nodes[i];
                    if(node.x == x && node.y == y){
                        //graph.draw(node,Color.PALEVIOLETRED);

                        targetIndex.setText(i + "");
                        break;
                    }
                }

            }
        });
        stage.setTitle("Network Tracing");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        // max x : 208, max y : 124
        /*graph.draw(new Node[]{
                new Node(0,0, 4),
                new Node(1,1, 4),
                new Node(99,62, 4),
        });*/
        // initializing nodes

        deafaultNodes = new Node[Length];
        nodes = new Node[Length];

        for(int x=0;x<maxX;x++){
            for(int y=0;y<maxY;y++){
                deafaultNodes[x*maxY + y] = new Node(x, y, 2);
            }
        }
        System.arraycopy(deafaultNodes, 0, nodes, 0, Length);
        // empty graph
        graph.draw();

        //DijkstraExample.main();
        //DijkstrasAlgorithm.main();

    }
    @Override
    public void handle(ActionEvent e){
        if(e.getSource() == button1){
            System.out.println("Clicked!");
        }
    }
    public static void main(String[] args) {
        launch();
    }
}