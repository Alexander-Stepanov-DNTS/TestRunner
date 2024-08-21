package nic.stepanov.view.components;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;

public class TestMenuBar {
    private final JMenuBar menuBar;

    public TestMenuBar(ActionListener runAllTestsListener, ActionListener runSelectedTestListener,
                       ActionListener loadTestsListener, ActionListener addTestManuallyListener, ActionListener stopAllTestsListener) {

        menuBar = new JMenuBar();

        // Меню "Запуск тестов"
        JMenu runMenu = new JMenu("Запуск тестов");
        JMenuItem runAllTestsItem = new JMenuItem("Запустить все тесты");
        JMenuItem runSelectedTestItem = new JMenuItem("Запустить выделенный тест");

        runAllTestsItem.addActionListener(runAllTestsListener);
        runSelectedTestItem.addActionListener(runSelectedTestListener);

        runMenu.add(runAllTestsItem);
        runMenu.add(runSelectedTestItem);
        menuBar.add(runMenu);

        // Меню "Загрузка тестов"
        JMenu loadMenu = new JMenu("Загрузка тестов");
        JMenuItem loadTestsItem = new JMenuItem("Загрузить названия тестов");
        JMenuItem addTestItem = new JMenuItem("Добавить тест вручную");

        loadTestsItem.addActionListener(loadTestsListener);
        addTestItem.addActionListener(addTestManuallyListener);

        loadMenu.add(loadTestsItem);
        loadMenu.add(addTestItem);
        menuBar.add(loadMenu);

        // Mеню "Управление тестами"
        JMenu manageTestsMenu = new JMenu("Управление тестами");
        JMenuItem stopAllTestsItem = new JMenuItem("Закончить выполнение всех тестов");

        stopAllTestsItem.addActionListener(stopAllTestsListener);

        manageTestsMenu.add(stopAllTestsItem);
        menuBar.add(manageTestsMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}