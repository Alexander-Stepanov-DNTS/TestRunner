package nic.stepanov.tests2;

import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.dichotomy.DichotomyCalculator;

import javax.swing.*;

public class Test2 implements ITest {
    @Override
    public int runTest(boolean manyTests, DichotomyCalculator calculator) throws TestFailedException {
        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Тест \"проверка шага дихотомии\".\n",
                    "Тест шаг дихотомии",
                    JOptionPane.INFORMATION_MESSAGE);

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

            JOptionPane.showMessageDialog(null,
                    "\"Сейчас будет выполнен шаг дихотомии.\nРассчитаются значения x1 и x2 в интервале [A, B].\"",
                    "Запуск дихотомии",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        calculator.performDichotomy();

        double expectedX1 = 5.45;
        double expectedX2 = 5.55;

        if (Math.abs(calculator.getX1() - expectedX1) > 0.1 || Math.abs(calculator.getX2() - expectedX2) > 0.1) {
            throw new TestFailedException("Ошибка при выполнении шага дихотомии. Ожидалось: x1=" + expectedX1 + ", x2=" + expectedX2);
        }

        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Результат шага дихотомии:\n" +
                            "x1 = " + calculator.getX1() + "\n" +
                            "x2 = " + calculator.getX2(),
                    "Шаг дихотомии",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return 1;
    }
}