package nic.stepanov.probl;
import nic.stepanov.view.TestRunner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class DichotomyForm extends JFrame {
    private JTextField fieldA;
    private JTextField fieldB;
    private JTextArea resultArea;
    private DichotomyCalculator calculator;
    private JLabel functionLabel;
    private JLabel epsilonLabel;

    public DichotomyForm() {
        setTitle("Dichotomy Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        calculator = new DichotomyCalculator();

        fieldA = new JTextField("1.0");
        fieldB = new JTextField("10.0");

        JButton runButton = new JButton("Запустить дихотомию");
        runButton.addActionListener(e -> startDichotomy());

        JButton runTestsButton = new JButton("Запустить тесты");
        runTestsButton.addActionListener(e -> {
            TestRunner frame = new TestRunner();
            frame.setVisible(true);
        });

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        functionLabel = new JLabel("Функция: f(x) = (x - 2.5)^2");
        epsilonLabel = new JLabel("Точность eps: " + calculator.getEps());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(functionLabel);
        panel.add(new JLabel(""));
        panel.add(epsilonLabel);
        panel.add(new JLabel("Начальное значение a:"));
        panel.add(fieldA);
        panel.add(new JLabel(""));
        panel.add(new JLabel("Начальное значение b:"));
        panel.add(fieldB);
        panel.add(new JLabel(""));
        panel.add(runButton);
        panel.add(runTestsButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void startDichotomy() {
        try {
            double initialA = Double.parseDouble(fieldA.getText());
            double initialB = Double.parseDouble(fieldB.getText());

            calculator.initialSetup(initialA, initialB);
            resultArea.append("Начальные значения:\n" + getIntermediateResults());

            while (!calculator.checkConvergence()) {
                System.out.println("Итерация");
                calculator.performDichotomy();
                calculator.updateBoundaryA();
                calculator.updateBoundaryB();
            }

            resultArea.append("\nКонечные результаты:\n" + getFinalResult());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, введите корректные числовые значения для a и b.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DichotomyForm form = new DichotomyForm();
            form.setVisible(true);
        });
    }
}