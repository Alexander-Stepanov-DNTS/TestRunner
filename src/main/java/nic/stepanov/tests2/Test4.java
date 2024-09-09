package nic.stepanov.tests2;
import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.dichotomy.DichotomyCalculator;

import javax.swing.*;

public class Test4 implements ITest {
    @Override
    public int runTest(boolean manyTests, DichotomyCalculator calculator) throws TestFailedException {
        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Тест \"нахождение границы В\".\n",
                    "Тест нахождение границы В",
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
                    "Сейчас будет проверено корректное обновление границы B после выполнения шага дихотомии.\nЕсли функция в x1 меньше, чем в x2, граница B будет обновлена.",
                    "Обновление границы B",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        calculator.updateBoundaryB();

        boolean bUpdatedCorrectly = calculator.getB() < 10.0 && calculator.getB() >= calculator.getX2();

        if (!bUpdatedCorrectly) {
            throw new TestFailedException("Ошибка при обновлении границы B.");
        }

        if (!manyTests) {
            JOptionPane.showMessageDialog(null,
                    "Результат обновления границы B:\n" +
                            "b = " + calculator.getB(),
                    "Обновление границы B",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return 1;
    }
}