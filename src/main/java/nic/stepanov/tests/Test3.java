package nic.stepanov.tests;


import nic.stepanov.exceptions.TestFailedException;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import javax.swing.JOptionPane;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public class Test3 implements ITest {

    @Override
    public void runTest(boolean manyTests) throws TestFailedException {
        if (!manyTests) {
            showMessage("Запуск теста \"Нахождение границы A\"");
        }
        runJUnitTest("nic.stepanov.probl.DichotomyCalculatorTest", "testUpdateBoundaryA");
        if (!manyTests) {
            showMessage("Тестирование \"Нахождение границы A\" прошло успешно ");
            showMessage("В этом тесте проверялась корректность обновления левой границы интервала после выполнения шага дихотомии. " +
                    "Принцип вычислений заключался в сравнении значений функции в двух точках интервала: если значение функции в первой точке больше, чем во второй, левая граница интервала смещается к первой точке. " +
                    "Мы ожидали, что левая граница будет корректно обновлена в зависимости от сравнительных значений функции в этих точках. " +
                    "Результат теста показал, что граница была обновлена правильно, что подтверждает корректное изменение границы в алгоритме.");
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Сообщение", JOptionPane.INFORMATION_MESSAGE);
    }

    private void runJUnitTest(String className, String methodName) throws TestFailedException {
        try {
            SummaryGeneratingListener listener = new SummaryGeneratingListener();
            Launcher launcher = LauncherFactory.create();

            LauncherDiscoveryRequest request = request()
                    .selectors(selectMethod(className, methodName))
                    .build();

            launcher.execute(request, listener);

            TestExecutionSummary summary = listener.getSummary();
            if (summary.getFailures().isEmpty()) {
                //showMessage("JUnit тест выполнен успешно.");
            } else {
                throw new TestFailedException("JUnit тест провален: " + summary.getFailures().get(0).getException().getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при выполнении JUnit теста: " + e.getMessage());
            throw new TestFailedException("Ошибка при выполнении JUnit теста: " + e.getMessage(), e);
        }
    }
}