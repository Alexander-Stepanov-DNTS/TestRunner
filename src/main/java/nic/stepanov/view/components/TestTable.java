package nic.stepanov.view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.Color;
import java.awt.Component;

public class TestTable {
    private final JTable testTable;
    private final DefaultTableModel tableModel;

    public TestTable() {
        String[] columnNames = {"Номер", "Название теста", "Результат"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        testTable = new JTable(tableModel);

        setColumnWidths();

        setCustomCellRenderer();
    }

    private void setColumnWidths() {
        TableColumn column0 = testTable.getColumnModel().getColumn(0);
        TableColumn column1 = testTable.getColumnModel().getColumn(1);
        TableColumn column2 = testTable.getColumnModel().getColumn(2);

        int tableWidth = testTable.getPreferredSize().width;

        column0.setMinWidth((int) (tableWidth * 0.3));
        column0.setMaxWidth((int) (tableWidth * 0.3));
        column1.setMinWidth((int) (tableWidth * 0.35));
        column2.setMinWidth((int) (tableWidth * 0.35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        column0.setCellRenderer(centerRenderer);
    }

    private void setCustomCellRenderer() {
        testTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String statusText = (String) tableModel.getValueAt(row, 2);

                switch (statusText) {
                    case "все ок!":
                        cell.setBackground(Color.GREEN); // Успех
                        break;
                    case "Тест провален":
                        cell.setBackground(Color.RED); // Провал
                        break;
                    case "Ответ не сошелся":
                        cell.setBackground(Color.YELLOW); // Ответ не сошелся
                        break;
                    default:
                        cell.setBackground(Color.WHITE); // Тестирование не проводилось или любой другой текст
                        break;
                }

                if (isSelected) {
                    cell.setBackground(cell.getBackground().darker()); // Темнее, если ячейка выделена
                }

                return cell;
            }
        });
    }

    public JTable getTestTable() {
        return testTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JScrollPane getScrollPane() {
        return new JScrollPane(testTable);
    }

    public void updateTestResultInTable(String testName, String resultText) {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (tableModel.getValueAt(row, 1).equals(testName)) {
                tableModel.setValueAt(resultText, row, 2);
                break;
            }
        }
        testTable.repaint();
    }

    public void updateTestResultsWithDelay(String testName, String resultText, int row, long delay) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(delay); // Задержка между изменением строк
                return null;
            }

            @Override
            protected void done() {
                tableModel.setValueAt(resultText, row, 2);
                testTable.repaint();
            }
        }.execute();
    }

    public void updateAllTestsWithDelay(String testName, String resultText, long delay, boolean manyTests) {
        if(!manyTests) {
            updateTestResultInTable(testName, resultText);
        }
        else {
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                if (tableModel.getValueAt(row, 1).equals(testName)) {
                    updateTestResultsWithDelay(testName, resultText, row, delay * row);
                }
            }
        }
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void addTestToTable(int index, String testName) {
        tableModel.addRow(new Object[]{index, testName, "Не выполнен"});
    }
}