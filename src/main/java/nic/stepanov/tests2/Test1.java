package nic.stepanov.tests2;


import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.dichotomy.DichotomyCalculator;

import javax.swing.*;
public class Test1 implements ITest {

    @Override
    public int runTest(boolean manyTests, DichotomyCalculator calculator) throws TestFailedException {
        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Тест \"установка начального значения\".\n",
                    "Установка начального значения",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Будет выполнена установка начального значения.\n",
                    "Установка начального значения",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        calculator.initialSetup(1.0, 10.0, 0.1);

        String result = "Начальное значение A: " + calculator.getA() + "\n" +
                "Начальное значение B: " + calculator.getB();

        if (calculator.getA() != 1.0 || calculator.getB() != 10.0) {
            throw new TestFailedException("Ошибка в начальной установке границ.");
        }

        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Установка начального значения. Результат установки:\n" + result,
                    "Установка начального значения",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return 1;
    }
}