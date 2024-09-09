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

public class Test1 implements ITest {

    @Override
    public void runTest(boolean manyTests) throws TestFailedException {
        if (!manyTests) {
            showMessage("Запуск теста \"Инициализация дихотомии\"");
        }
        runJUnitTest("nic.stepanov.probl.DichotomyCalculatorTest", "testInitialSetup");
        if (!manyTests) {
            showMessage("Тестирование \"Инициализация дихотомии\" прошло успешно ");
            showMessage("В этом тесте мы проверяли корректность начальной установки значений для границ интервала. " +
                    "Принцип вычислений заключается в том, что при инициализации алгоритма дихотомии устанавливаются начальные границы интервала. " +
                    "Мы ожидали, что эти значения будут равны тем, которые были переданы в качестве параметров. " +
                    "Ожидаемый результат — границы интервала должны точно соответствовать заданным значениям. " +
                    "После выполнения теста эти ожидания подтвердились: начальные границы были установлены корректно, что подтвердило правильность работы инициализации.");
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