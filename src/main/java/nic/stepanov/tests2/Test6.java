package nic.stepanov.tests2;

import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.dichotomy.DichotomyCalculator;

import javax.swing.*;

public class Test6 implements ITest {
    @Override
    public int runTest(boolean manyTests, DichotomyCalculator calculator) throws TestFailedException {
        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Тест не был написан",
                    "Тест не был написан",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return 4;
    }
}
