package nic.stepanov.view.graph;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.knowm.xchart.*;

import java.util.List;

//public class ChartDisplay {
//
//    public XYChart createChart() {
//        XYChart chart = new XYChartBuilder()
//                .width(500)
//                .height(400)
//                .title("Sample Chart")
//                .xAxisTitle("X")
//                .yAxisTitle("Y")
//                .build();
//
//        // Добавляем серию данных на график
//        chart.addSeries("y = f(x)", new double[]{1, 2, 3, 4, 5}, new double[]{2, 1, 0, 1, 2})
//                .setMarker(SeriesMarkers.CIRCLE); // Добавляем маркеры для точек на графике
//
//        return chart;
//    }
//
//    public static void main(String[] args) {
//        ChartDisplay display = new ChartDisplay();
//        XYChart chart = display.createChart();
//
//        // Отображаем график
//        new SwingWrapper<>((List) chart).displayChart();
//    }
//}
