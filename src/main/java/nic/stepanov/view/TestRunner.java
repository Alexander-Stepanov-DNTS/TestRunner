package nic.stepanov.view;

import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.probl.DichotomyCalculator;
import nic.stepanov.probl.DichotomyForm;
import nic.stepanov.utils.TestLoader;
import nic.stepanov.view.components.TestMenuBar;
import nic.stepanov.view.components.TestTable;
import nic.stepanov.view.dialogForms.TestInputDialog;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TestRunner extends JFrame {
    private final TestTable testTable;
    private final TestLoader testLoader;
    private DichotomyForm dichotomyForm;

    public TestRunner(DichotomyForm dichotomyForm) {
        this.dichotomyForm = dichotomyForm;

        setTitle("Test Runner");
        setSize(800, 600);
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

        // Добавляем слушателя на двойной клик по строке таблицы
        testTable.getTestTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Проверяем двойной клик
                    int selectedRow = testTable.getTestTable().getSelectedRow();
                    if (selectedRow != -1) {
                        String testName = (String) testTable.getTableModel().getValueAt(selectedRow, 1);
                        executeNewTest(testName, false); // Запуск теста
                    }
                }
            }
        });
    }

    private void runAllTests() {
        List<String> testNames = testLoader.getTestNames();
        for (String testName : testNames) {
            executeNewTest(testName, true);
        }
    }

    private void runSelectedTest() {
        int selectedRow = testTable.getTestTable().getSelectedRow();
        if (selectedRow != -1) {
            String testName = (String) testTable.getTableModel().getValueAt(selectedRow, 1);

            JDialog dialog = new JDialog(this, "Подтверждение", Dialog.ModalityType.MODELESS);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            JOptionPane optionPane = new JOptionPane(
                    "Вы хотите запустить тест " + testName + "?",
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.YES_NO_OPTION);

            dialog.setContentPane(optionPane);
            dialog.pack();
            dialog.setLocationRelativeTo(this);

            executeNewTest(testName, false);
        } else {
            JOptionPane.showMessageDialog(this, "Выберите тест для запуска.");
        }
    }

    private void executeTest(String testName, boolean manyTests) {
        try {
            testLoader.runTestByName(testName, manyTests);

            testTable.updateTestResultInTable(testName, "все ок!"); //Заглушка

        } catch (TestFailedException e) {
            showErrorDialog(this, "Произошла ошибка при выполнении теста: " + e.getMessage());

            testTable.updateTestResultInTable(testName, "Тест провален"); //Заглушка
        }
    }

    private void executeNewTest(String testName, boolean manyTests) {
        try {
            int testStatusCode = 4;
            String statusText = "Тестирование не проводилось"; // по умолчанию

            if (dichotomyForm != null) { // Убедитесь, что форма передана
                DichotomyCalculator calculator = dichotomyForm.getCalculator();
                testStatusCode = testLoader.runTestWithCalculator(testName, calculator, manyTests);
            }

            switch (testStatusCode) {
                case 1:
                    statusText = "все ок!";
                    break;
                case 2:
                    statusText = "Тест провален";
                    break;
                case 3:
                    statusText = "Ответ не сошелся";
                    break;
                case 4:
                    statusText = "Тестирование не проводилось";
                    break;
            }

            testTable.updateAllTestsWithDelay(testName, statusText, 1000, manyTests);


        } catch (TestFailedException e) {
            showErrorDialog(this, "Произошла ошибка при выполнении теста: " + e.getMessage());

            testTable.updateTestResultInTable(testName, "Тест провален");
        }
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