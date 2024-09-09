package nic.stepanov.tests2;

import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.dichotomy.DichotomyCalculator;

import javax.swing.*;

public class Test3 implements ITest {
    @Override
    public int runTest(boolean manyTests, DichotomyCalculator calculator) throws TestFailedException {
        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Тест \"нахождение границы А\".\n",
                    "Тест нахождение границы А",
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

        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Результат шага дихотомии:\n" +
                            "x1 = " + calculator.getX1() + "\n" +
                            "x2 = " + calculator.getX2(),
                    "Шаг дихотомии",
                    JOptionPane.INFORMATION_MESSAGE);

            JOptionPane.showMessageDialog(null,
                    "Сейчас будет проверено корректное обновление границы A после выполнения шага дихотомии.\nЕсли функция в x1 больше, чем в x2, граница A будет обновлена.",
                    "Обновление границы A",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        calculator.updateBoundaryA();

        boolean aUpdatedCorrectly = (calculator.getX1() > calculator.getX2())
                ? (calculator.getA() > 1.0 && calculator.getA() <= calculator.getX1())
                : (calculator.getA() == 1.0);

        if (!aUpdatedCorrectly) {
            throw new TestFailedException("Ошибка при обновлении границы A.");
        }

        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Результат обновления границы A:\n" +
                            "a = " + calculator.getA(),
                    "Обновление границы A",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return 1;
    }
}