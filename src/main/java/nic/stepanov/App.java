package nic.stepanov;

import nic.stepanov.view.TestRunner;

public class App
{
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TestRunner frame = new TestRunner();
            frame.setVisible(true);
        });
    }
}