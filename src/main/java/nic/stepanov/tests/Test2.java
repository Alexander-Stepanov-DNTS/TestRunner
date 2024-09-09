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

public class Test2 implements ITest {

    @Override
    public void runTest(boolean manyTests) throws TestFailedException {
        if (!manyTests) {
            showMessage("Запуск теста \"Запуск дихотомии\"");
        }
        runJUnitTest("nic.stepanov.probl.DichotomyCalculatorTest", "testPerformDichotomy");
        if (!manyTests) {
            showMessage("Тестирование \"Запуск дихотомии\" прошло успешно ");
            showMessage("Этот тест предназначен для проверки корректности выполнения одного шага дихотомии. " +
                    "Принцип вычислений заключается в том, что после выполнения шага дихотомии рассчитываются две точки внутри интервала, которые определяют новый подинтервал для следующего шага. " +
                    "Мы ожидали, что рассчитанные точки будут находиться в середине начального интервала с небольшим смещением в обе стороны от центра. " +
                    "Ожидаемый результат — вычисленные точки должны соответствовать заранее определённым значениям, которые представляют центр интервала с учётом смещения. " +
                    "Тест показал, что вычисленные точки были именно такими, как ожидалось, подтверждая корректность выполнения шага алгоритма.");
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