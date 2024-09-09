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

public class Test4 implements ITest {

    @Override
    public void runTest(boolean manyTests) throws TestFailedException {
        if (!manyTests) {
            showMessage("Запуск теста \"Нахождение границы В\"");
        }
        runJUnitTest("nic.stepanov.probl.DichotomyCalculatorTest", "testUpdateBoundaryB");
        if (!manyTests) {
            showMessage("Тестирование \"Нахождение границы В\" прошло успешно ");
            showMessage("Этот тест предназначен для проверки правильности обновления правой границы интервала в процессе дихотомии. " +
                    "Принцип вычислений аналогичен предыдущему тесту, но теперь проверяется обновление правой границы интервала. " +
                    "Если значение функции в первой точке меньше, чем во второй, правая граница смещается к второй точке. " +
                    "Мы ожидали, что правая граница интервала будет правильно обновлена на основании сравнения значений функции в двух точках. " +
                    "Результат теста подтвердил, что правая граница была обновлена корректно, как и ожидалось.");
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