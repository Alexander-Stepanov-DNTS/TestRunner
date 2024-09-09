package nic.stepanov.view.graph;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.ScatterPlot;
import tech.tablesaw.plotly.components.Figure;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javax.swing.*;

//public class FunctionPlotterFX {
//
//    public void createAndShowPlot(JFXPanel fxPanel) {
//        Platform.runLater(() -> {
//            DoubleColumn xColumn = DoubleColumn.create("X");
//            DoubleColumn yColumn = DoubleColumn.create("f(X) = (X - 2.5)^2");
//
//            for (double x = 0; x <= 5; x += 0.1) {
//                xColumn.append(x);
//                yColumn.append(Math.pow(x - 2.5, 2));
//            }
//
//            Table table = Table.create("Function Data").addColumns(xColumn, yColumn);
//
//            Figure figure = ScatterPlot.create("Function Plot", table, "X", "f(X) = (X - 2.5)^2");
//
//            BorderPane root = new BorderPane();
//            root.setCenter(Plot.show(figure));
//
//            Scene scene = new Scene(root, 800, 600);
//            fxPanel.setScene(scene);
//        });
//    }
//}
