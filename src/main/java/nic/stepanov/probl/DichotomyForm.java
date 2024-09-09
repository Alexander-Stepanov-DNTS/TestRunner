package nic.stepanov.probl;
import nic.stepanov.view.TestRunner;
import nic.stepanov.view.graph.JFreeChartExample;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class DichotomyForm extends JFrame {
    private JTextField fieldA;
    private JTextField fieldB;
    private JTextField fieldX1;
    private JTextField fieldX2;
    private JLabel labelFofA;
    private JLabel labelFofX1;
    private JLabel labelFofX2;
    private JLabel labelFofB;
    private JTextArea resultArea;
    private DichotomyCalculator calculator;
    private JLabel functionLabel;
    private JLabel epsilonLabel;
    private double eps = 0.3;

    public DichotomyForm() {
        setUIFont(new FontUIResource(new Font("Dialog", Font.PLAIN, 18)));

        setTitle("Dichotomy Calculator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        calculator = new DichotomyCalculator();
        calculator.setUpdateListener(this::updateFields);

        fieldA = new JTextField("1.0");
        fieldB = new JTextField("10.0");
        fieldX1 = new JTextField("0");
        fieldX1.setEditable(false);
        fieldX2 = new JTextField("0");
        fieldX2.setEditable(false);

        labelFofA = new JLabel("f(a) = 0");
        labelFofX1 = new JLabel("f(x1) = 0");
        labelFofX2 = new JLabel("f(x2) = 0");
        labelFofB = new JLabel("f(b) = 0");

        JButton runButton = new JButton("Запустить дихотомию");
        runButton.addActionListener(e -> startDichotomy());

        JButton runTestsButton = new JButton("Запустить тесты");
        runTestsButton.addActionListener(e -> {
            TestRunner frame = new TestRunner(this);
            frame.setVisible(true);
        });

        JButton showPlotButton = new JButton("Показать график функции");
        showPlotButton.addActionListener(e -> {
            // Создаем окно для отображения графика
            JFrame chartFrame = new JFrame("График функции");
            chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Создаем экземпляр класса JFreeChartExample
            JFreeChartExample chartExample = new JFreeChartExample();

            // Добавляем панель с графиком в окно
            chartFrame.add(chartExample.createChartPanel());

            chartFrame.pack();
            chartFrame.setLocationRelativeTo(null);
            chartFrame.setVisible(true);
        });

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        functionLabel = new JLabel("Функция: f(x) = (x - 2.5)^2");
        epsilonLabel = new JLabel("Точность eps: " + eps);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.add(functionLabel);
        panel.add(epsilonLabel);
        panel.add(new JLabel(""));
        panel.add(new JLabel("Начальное значение a:"));
        panel.add(fieldA);
        panel.add(labelFofA);
        panel.add(new JLabel("Значение x1:"));
        panel.add(fieldX1);
        panel.add(labelFofX1);
        panel.add(new JLabel("Значение x2:"));
        panel.add(fieldX2);
        panel.add(labelFofX2);
        panel.add(new JLabel("Начальное значение b:"));
        panel.add(fieldB);
        panel.add(labelFofB);
        panel.add(runButton);
        panel.add(runTestsButton);
        panel.add(showPlotButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void startDichotomy() {
        try {
            double initialA = Double.parseDouble(fieldA.getText());
            double initialB = Double.parseDouble(fieldB.getText());

            ResultOfDichotomy result = new ResultOfDichotomy();

            result = calculator.initialSetup(initialA, initialB, eps);
            updateFields();
            JOptionPane.showMessageDialog(this, "Установка начального значения. Результат установки:\n"+ result, "Установка начального значения", JOptionPane.INFORMATION_MESSAGE);
            resultArea.append("Начальные значения:\n" + getIntermediateResults());

            while (!calculator.checkConvergence()) {
                result = calculator.performDichotomy();
                JOptionPane.showMessageDialog(this, "Выполненна дихотомия. Промежуточный результат:\n" + result, "Промежуточный результат", JOptionPane.INFORMATION_MESSAGE);
                result = calculator.updateBoundaryA();
                JOptionPane.showMessageDialog(this, "Выполненн расчет границы А. Промежуточный результат:\n"+ result, "Промежуточный результат", JOptionPane.INFORMATION_MESSAGE);
                result = calculator.updateBoundaryB();
                JOptionPane.showMessageDialog(this, "Выполненн расчет границы Б. Промежуточный результат:\n"+ result, "Промежуточный результат", JOptionPane.INFORMATION_MESSAGE);
            }
            JOptionPane.showMessageDialog(this, "Расчет закончен. Конечный результат:\n"+ result, "Расчет закончен", JOptionPane.INFORMATION_MESSAGE);

            resultArea.append("\nКонечные результаты:\n" + getFinalResult());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, введите корректные числовые значения для a и b.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateFields() {
        fieldA.setText(String.format("%.3f", calculator.getA()));
        fieldB.setText(String.format("%.3f", calculator.getB()));
        fieldX1.setText(String.format("%.3f", calculator.getX1()));
        fieldX2.setText(String.format("%.3f", calculator.getX2()));

        // Обновление значений функции
        labelFofA.setText(String.format("f(a) = %.3f", calculator.function(calculator.getA())));
        labelFofX1.setText(String.format("f(x1) = %.3f", calculator.function(calculator.getX1())));
        labelFofX2.setText(String.format("f(x2) = %.3f", calculator.function(calculator.getX2())));
        labelFofB.setText(String.format("f(b) = %.3f", calculator.function(calculator.getB())));
    }

    private String getFinalResult() {
        double x = (calculator.getX1() + calculator.getX2()) / 2;
        return "Минимум функции находится в интервале: [" + calculator.getX1() + ", " + calculator.getX2() + "]\n" +
                "Приближенное значение x: " + x + "\n" +
                "Точность решения: " + calculator.getEps();
    }

    private String getIntermediateResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("a = ").append(calculator.getA()).append("\n");
        sb.append("x1 = ").append(calculator.getX1()).append("\n");
        sb.append("x2 = ").append(calculator.getX2()).append("\n");
        sb.append("b = ").append(calculator.getB()).append("\n");
        return sb.toString();
    }

    public static void setUIFont(FontUIResource f) {
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public DichotomyCalculator getCalculator(){
        return this.calculator;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DichotomyForm form = new DichotomyForm();
            form.setVisible(true);
        });
    }
}