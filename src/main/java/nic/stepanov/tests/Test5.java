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

public class Test5 implements ITest {

    @Override
    public void runTest(boolean manyTests) throws TestFailedException {
        if (!manyTests) {
            showMessage("Запуск теста \"Проверка достижения точности\"");
        }
        runJUnitTest("nic.stepanov.probl.DichotomyCalculatorTest", "testInitialSetup");
        if (!manyTests) {
            showMessage("Тестирование \"Проверка достижения точности\" прошло успешно ");
            showMessage("Этот тест проверяет, сходится ли алгоритм дихотомии, когда выполняется до тех пор, пока интервал не станет достаточно малым. " +
                    "Принцип вычислений заключается в том, что на каждом шаге алгоритм сужает интервал, и процесс продолжается до тех пор, пока разница между границами интервала не станет меньше заданного порога. " +
                    "Мы ожидали, что алгоритм завершится с достаточно малым интервалом, подтверждающим сходимость. " +
                    "В результате выполнения теста было подтверждено, что алгоритм успешно сошелся, и интервал оказался достаточно малым, как и ожидалось.");
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