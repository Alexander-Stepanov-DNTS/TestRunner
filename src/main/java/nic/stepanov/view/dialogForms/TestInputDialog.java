package nic.stepanov.view.dialogForms;
import javax.swing.*;
import java.awt.*;

public class TestInputDialog extends JDialog {
    private final JTextField testNameField;
    private final JTextField classNameField;
    private boolean confirmed;

    public TestInputDialog(JFrame parent) {
        super(parent, "Добавить тест", true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Название теста:"));
        testNameField = new JTextField();
        add(testNameField);

        add(new JLabel("Имя класса:"));
        classNameField = new JTextField();
        add(classNameField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(e -> setVisible(false));

        add(okButton);
        add(cancelButton);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getTestName() {
        return testNameField.getText();
    }

    public String getClassName() {
        return classNameField.getText();
    }
}