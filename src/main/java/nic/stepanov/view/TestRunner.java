package nic.stepanov.view;

import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.models.DataPoint;
import nic.stepanov.models.Result;
import nic.stepanov.utils.TestLoader;
import nic.stepanov.view.components.TestMenuBar;
import nic.stepanov.view.components.TestTable;
import nic.stepanov.view.dialogForms.TestInputDialog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TestRunner extends JFrame {
    private final TestTable testTable;
    private final TestLoader testLoader;

    public TestRunner() {
        setTitle("Test Runner");
        setSize(600, 400);
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        testTable = new TestTable();
        add(testTable.getScrollPane(), BorderLayout.CENTER);

        testLoader = new TestLoader();

        TestMenuBar testMenuBar = new TestMenuBar(
                e -> runAllTests(),
                e -> runSelectedTest(),
                e -> loadTests(),
                e -> addTestManually(),
                e -> stopAllTests()
        );

        setJMenuBar(testMenuBar.getMenuBar());
    }

    private void runAllTests() {
        List<String> testNames = testLoader.getTestNames();
        for (String testName : testNames) {
            runTest(testName);
        }
    }

    private void runSelectedTest() {
        int selectedRow = testTable.getTestTable().getSelectedRow();
        if (selectedRow != -1) {
            String testName = (String) testTable.getTableModel().getValueAt(selectedRow, 1);
            runTest(testName);
        } else {
            JOptionPane.showMessageDialog(this, "Выберите тест для запуска.");
        }
    }

    private void runTest(String testName) {
        JDialog dialog = new JDialog(this, "Подтверждение", Dialog.ModalityType.MODELESS);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JOptionPane optionPane = new JOptionPane(
                "Вы хотите запустить тест " + testName + "?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);

        dialog.setContentPane(optionPane);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        optionPane.addPropertyChangeListener(e -> {
            String prop = e.getPropertyName();

            if (dialog.isVisible()
                    && (e.getSource() == optionPane)
                    && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                dialog.setVisible(false);

                int confirmation = (int) optionPane.getValue();
                if (confirmation == JOptionPane.NO_OPTION) {
                    return;
                }

                executeTest(testName);
            }
        });

        dialog.setVisible(true);
    }

    private void executeTest(String testName) {
        try {
            Result result = testLoader.runTestByName(testName);

            showResultChart(result);

            showNonModalMessage(this);

            testTable.updateTestResultInTable(testName, "все ок!", Color.GREEN);

        } catch (TestFailedException e) {
            showErrorDialog(this, "Произошла ошибка при выполнении теста: " + e.getMessage());

            testTable.updateTestResultInTable(testName, "Тест провален", Color.RED);
        }
    }

    private void showResultChart(Result result) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (DataPoint point : result.getDataPoints()) {
            dataset.addValue(Double.valueOf(point.getY()), "Test Result", Double.valueOf(point.getX()));
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Результаты теста: " + result.getTestName(),
                "X", "Y", dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame chartFrame = new JFrame("График результатов");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.add(chartPanel);
        chartFrame.pack();
        chartFrame.setLocationRelativeTo(this);
        chartFrame.setVisible(true);
    }

    private void showNonModalMessage(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Сообщение", false);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parent);

        JLabel messageLabel = new JLabel("Тест успешно выполнен", SwingConstants.CENTER);
        dialog.add(messageLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showErrorDialog(JFrame parent, String errorMessage) {
        JDialog dialog = new JDialog(parent, "Ошибка", false);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parent);

        JLabel messageLabel = new JLabel(errorMessage, SwingConstants.CENTER);
        messageLabel.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
        messageLabel.setForeground(Color.RED);
        dialog.add(messageLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void closeAllWindowsExceptMain() {
        for (Window window : Window.getWindows()) {
            if (window != this && window.isDisplayable()) {
                window.dispose();
            }
        }
    }

    private void stopAllTests() {
        closeAllWindowsExceptMain();
    }

    private void loadTests() {
        testLoader.loadTests();
        updateTable();
    }

    private void addTestManually() {
        TestInputDialog dialog = new TestInputDialog(this);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            String testName = dialog.getTestName();
            String className = dialog.getClassName();
            testLoader.addTest(testName, className);
            updateTable();
        }
    }

    private void updateTable() {
        testTable.clearTable();
        int index = 1;
        for (String testName : testLoader.getTestNames()) {
            testTable.addTestToTable(index++, testName);
        }
    }
}