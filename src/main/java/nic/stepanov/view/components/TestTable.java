package nic.stepanov.view.components;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

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

        column0.setMinWidth((int) (tableWidth * 0.2));
        column0.setMaxWidth((int) (tableWidth * 0.2));
        column1.setMinWidth((int) (tableWidth * 0.4));
        column2.setMinWidth((int) (tableWidth * 0.4));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        column0.setCellRenderer(centerRenderer);
    }

    private void setCustomCellRenderer() {
        testTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Устанавливаем цвет фона в зависимости от значения
                if ("все ок!".equals(value)) {
                    cell.setBackground(Color.GREEN);
                } else if ("Тест провален".equals(value)) {
                    cell.setBackground(Color.RED);
                } else {
                    cell.setBackground(Color.WHITE);
                }

                // Обработка выделения ячейки
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

    public void updateTestResultInTable(String testName, String resultText, Color color) {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (tableModel.getValueAt(row, 1).equals(testName)) {
                tableModel.setValueAt(resultText, row, 2);
                break;
            }
        }
        testTable.repaint();
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void addTestToTable(int index, String testName) {
        tableModel.addRow(new Object[]{index, testName, "Не выполнен"});
    }
}