package nic.stepanov;

import nic.stepanov.dichotomy.DichotomyForm;

import javax.swing.*;

public class App
{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DichotomyForm form = new DichotomyForm();
            form.setVisible(true);
        });
    }
}