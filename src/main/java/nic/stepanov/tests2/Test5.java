package nic.stepanov.tests2;

import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.dichotomy.DichotomyCalculator;

import javax.swing.*;

public class Test5 implements ITest {
    @Override
    public int runTest(boolean manyTests, DichotomyCalculator calculator) throws TestFailedException {
        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Тест \"достижение сходимости\".\n",
                    "Тест достижение сходимости",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Будет выполнена установка начального значения.\n",
                    "Установка начального значения",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        calculator.initialSetup(1.0, 10.0, 0.1);
        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Была произведена инициализация\n" +
                            "A = " + 1.0 + "\n" +
                            "B = " + 10.0 + "\n" +
                            "eps = " + 0.1,
                    "Инициализация дихотомии",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Сейчас будет выполнен полный цикл алгоритма дихотомии для достижения сходимости.\nАлгоритм будет выполняться до тех пор, пока интервал [A, B] не станет достаточно малым.",
                    "Выполнение дихотомии для достижения сходимости",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        while (!calculator.checkConvergence()) {
            calculator.performDichotomy();
            calculator.updateBoundaryA();
            calculator.updateBoundaryB();
        }

//        if (!calculator.checkConvergence()) {
//            throw new TestFailedException("Ошибка: алгоритм не сошелся.");
//        }
        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                            "Итоговые значения:\n" +
                            "a = " + calculator.getA() + "\n" +
                            "b = " + calculator.getB(),
                    "Проверка сходимости",
                    JOptionPane.INFORMATION_MESSAGE);

            int response = JOptionPane.showConfirmDialog(null,
                    "Сошлись ли результаты? Нажмите 'Yes', если сошлись, или 'No', если нет.",
                    "Проверка сходимости",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                return 1;
            } else {
                return 3;
            }
        }
        return 3;
    }
}