package nic.stepanov.view.graph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class JFreeChartExample {

    public JPanel createChartPanel() {
        // Создаем данные для графика
        XYSeries series = new XYSeries("f(x) = (x - 2.5)^2");

        // Генерируем значения x от 0 до 5 с шагом 0.1
        for (double x = 0; x <= 5; x += 0.1) {
            double y = Math.pow(x - 2.5, 2); // Вычисляем f(x)
            series.add(x, y); // Добавляем точку (x, y) в график
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Создаем график
        JFreeChart chart = ChartFactory.createXYLineChart(
                "График функции f(x) = (x - 2.5)^2",    // Заголовок графика
                "X",                                    // Подпись оси X
                "Y",                                    // Подпись оси Y
                dataset,                                // Данные для графика
                PlotOrientation.VERTICAL,
                true,                                   // Показать легенду
                true,                                   // Показать подсказки
                false                                   // Не генерировать ссылки
        );

        // Возвращаем панель с графиком
        return new ChartPanel(chart);
    }
}

